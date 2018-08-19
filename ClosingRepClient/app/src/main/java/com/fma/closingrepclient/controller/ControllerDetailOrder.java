package com.fma.closingrepclient.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelDetailOrder;
import com.fma.closingrepclient.model.ModelOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerDetailOrder {
    private Context context;

    public ControllerDetailOrder(Context context) {
        this.context = context;
    }

    public List<ModelDetailOrder> getDetailOrders(){
        return this.getDetailOrders("",0);
    }

    public List<ModelDetailOrder> getDetailOrders(String filter, Integer filterUploaded){
//        try {
            List<ModelDetailOrder> detailOrders = new ArrayList<ModelDetailOrder>();

            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            String sql = "select a.*, b.orderno from detailorder a inner join orders b on a.order_id = b.id"
                        +" inner join customer c on b.customer_id = c.id";
            sql += " where 1=1";

            if (filterUploaded >= 0){
                sql += " and a.uploaded = " + String.valueOf(filterUploaded);
            }

            if (!filter.equals("")){
                sql += " and (b.orderno like '%" + filter + "%'";
                sql += " or c.nama like '%" + filter + "%'";
                sql += " or c.alamat like '%" + filter + "%')";
            }


            sql += " order by a.id desc limit 1000";

            Cursor cursor = rdb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                ModelDetailOrder detailOrder = new ModelDetailOrder();
                detailOrder.loadFromCursor(cursor);
                detailOrder.loadOrder(rdb);
                detailOrder.order.loadCustomer(rdb);
                detailOrders.add(detailOrder);
            }
            return detailOrders;
//        }catch(Exception e){
//            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
//        }
//        return null;
    }



}

