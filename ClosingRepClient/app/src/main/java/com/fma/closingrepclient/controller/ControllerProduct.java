package com.fma.closingrepclient.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelProduct;
import com.fma.closingrepclient.model.ModelSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerProduct {
    private Context context;

    public ControllerProduct(Context context) {
        this.context = context;
    }

    public List<ModelProduct> getProducts(){
        try {
            List<ModelProduct> products = new ArrayList<ModelProduct>();

            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            Cursor cursor = rdb.rawQuery("select * from product", null);
            while (cursor.moveToNext()) {
                ModelProduct product = new ModelProduct();
                product.loadFromCursor(cursor);
                products.add(product);
            }
            return products;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }



}

