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
        try {
            List<ModelOrder> orders = new ArrayList<ModelOrder>();

            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            Cursor cursor = rdb.rawQuery("select * from orders", null);
            while (cursor.moveToNext()) {
                ModelOrder order = new ModelOrder();
                order.loadFromCursor(cursor);
                order.loadCustomer(rdb);
                orders.add(order);
            }
            return orders;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }



}

