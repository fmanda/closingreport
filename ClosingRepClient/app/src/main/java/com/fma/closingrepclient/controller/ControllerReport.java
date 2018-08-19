package com.fma.closingrepclient.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelMaterial;
import com.fma.closingrepclient.model.ModelReportMaterial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerReport {
    private Context context;
    Calendar calendar = Calendar.getInstance();

    public List<ModelReportMaterial> getReportMaterial(Date startDate, Date endDate){
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startDate = calendar.getTime();

        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endDate = calendar.getTime();

        try {
            List<ModelReportMaterial> reportMaterials = new ArrayList<ModelReportMaterial>();

            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();

            String sql = "select c.nama, c.jenis, sum(b.qty) as qty "
                    + " from detailorder a "
                    + " inner join detailordermaterial b on a.id = b.detailorder_id"
                    + " inner join material c on b.material_id = c.id"
                    + " where a.tanggal between " + String.valueOf(startDate.getTime())
                    + " and " + String.valueOf(endDate.getTime())
                    + " group by c.nama, c.jenis";

            Cursor cursor = rdb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                ModelReportMaterial reportMaterial = new ModelReportMaterial();
                reportMaterial.nama = cursor.getString(cursor.getColumnIndex("nama"));
                reportMaterial.jenis = cursor.getString(cursor.getColumnIndex("jenis"));
                reportMaterial.qty = cursor.getDouble(cursor.getColumnIndex("qty"));
                reportMaterials.add(reportMaterial);
            }
            return reportMaterials;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public ControllerReport(Context context) {
        this.context = context;
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
    }


    public Integer getTotalOrderOpen(){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();

        String sql = "select count(*) from orders where status in ('OPEN','PROCESS','PENDING')";

        Cursor cursor = rdb.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }

    public Integer getTotalOrderClosed(){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();

        String sql = "select count(*) from orders where status in ('CLOSED')";

        Cursor cursor = rdb.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }

    public Integer getTotalOrderCancel(){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();

        String sql = "select count(*) from orders where status in ('CANCEL')";

        Cursor cursor = rdb.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }



    public Integer getTotalOrder(){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();

        String sql = "select count(*) from orders";

        Cursor cursor = rdb.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }

    public Integer getNotUploadedOrder(){
        DBHelper db = DBHelper.getInstance(context);
        SQLiteDatabase rdb = db.getReadableDatabase();
        String sql = "select count(*) from detailorder where uploaded=0";
        Cursor cursor = rdb.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            return cursor.getInt(0);
        }else{
            return 0;
        }
    }
}

