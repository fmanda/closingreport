package com.fma.closingrepclient.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelCustomer;
import com.fma.closingrepclient.model.ModelProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerCustomer {
    private Context context;

    public ControllerCustomer(Context context) {
        this.context = context;
    }

    public List<ModelCustomer> getCustomers(String filter){
        try {
            List<ModelCustomer> customers = new ArrayList<ModelCustomer>();

            String sql = "select * from customer";
            sql += " where 1=1";

            if (!filter.equals("")){
                sql += " and (nama like '%" + filter + "%'";
                sql += " or alamat like '%" + filter + "%')";
            }

            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            Cursor cursor = rdb.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                ModelCustomer customer = new ModelCustomer();
                customer.loadFromCursor(cursor);
                customers.add(customer);
            }
            return customers;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }



}

