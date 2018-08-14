package com.fma.closingrepclient.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelMaterial;
import com.fma.closingrepclient.model.ModelProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerMaterial {
    private Context context;

    public ControllerMaterial(Context context) {
        this.context = context;
    }

    public List<ModelMaterial> getMaterials(){
        try {
            List<ModelMaterial> materials = new ArrayList<ModelMaterial>();

            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            Cursor cursor = rdb.rawQuery("select * from material", null);
            while (cursor.moveToNext()) {
                ModelMaterial material = new ModelMaterial();
                material.loadFromCursor(cursor);
                materials.add(material);
            }
            return materials;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }



}

