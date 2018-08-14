package com.fma.closingrepclient.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelArea;
import com.fma.closingrepclient.model.ModelProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fmanda on 07/31/17.
 */

public class ControllerArea {
    private Context context;

    public ControllerArea(Context context) {
        this.context = context;
    }

    public List<ModelArea> getAreas(){
        try {
            List<ModelArea> areas = new ArrayList<ModelArea>();

            DBHelper db = DBHelper.getInstance(context);
            SQLiteDatabase rdb = db.getReadableDatabase();
            Cursor cursor = rdb.rawQuery("select * from area", null);
            while (cursor.moveToNext()) {
                ModelArea area = new ModelArea();
                area.loadFromCursor(cursor);
                areas.add(area);
            }
            return areas;
        }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }



}

