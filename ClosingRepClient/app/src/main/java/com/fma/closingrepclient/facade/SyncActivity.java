package com.fma.closingrepclient.facade;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.controller.ControllerRest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SyncActivity extends BaseActivity {
    private TextView txtLog;
    private Button btnSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_sync, this.mainframe);

        txtLog = findViewById(R.id.txtLog);
        btnSync = findViewById(R.id.btnSync);

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncData();
            }
        });

        syncData();
    }

    private void syncData(){
        addLog("Start Sync");
        ControllerRest controllerRest = new ControllerRest(this);
        controllerRest.setListener(new ControllerRest.Listener() {
            @Override
            public void onSuccess(String msg) {
                addLog(msg);
            }

            @Override
            public void onError(String msg) {
                addLog(msg);
            }
        });

        controllerRest.SyncData(Boolean.FALSE);
    }

    private void addLog(String log){
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm:ss");
        String currentTime = sdf.format(d);

        txtLog.append(currentTime + " : " + log  + "\n");
    }


}
