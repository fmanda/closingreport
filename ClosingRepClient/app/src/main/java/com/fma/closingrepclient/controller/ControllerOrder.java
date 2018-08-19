package com.fma.closingrepclient.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelOrder;
import com.fma.closingrepclient.model.ModelProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerOrder {
    private Context context;

    public ControllerOrder(Context context) {
        this.context = context;
    }

    public List<ModelOrder> getOrders(){
        return this.getOrders("","");
    }

    public List<ModelOrder> getOrders(String filter, String filterStatus){
//        try {
            List<ModelOrder> orders = new ArrayList<ModelOrder>();

            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            String sql = "select a.* from orders a inner join customer b on a.customer_id = b.id";
            sql += " where 1=1";

            if (!filter.equals("")){
                sql += " and (a.orderno like '%" + filter + "%'";
                sql += " or b.nama like '%" + filter + "%'";
                sql += " or b.alamat like '%" + filter + "%')";
            }

            if (!filterStatus.equals("")){
                sql += " and a.status in (" + filterStatus + ")";
            }
            sql += " order by a.id desc limit 1000";

            Cursor cursor = rdb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                ModelOrder order = new ModelOrder();
                order.loadFromCursor(cursor);
                order.loadCustomer(rdb);
                orders.add(order);
            }
            return orders;
//        }catch(Exception e){
//            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
//        }
//        return null;
    }



}

