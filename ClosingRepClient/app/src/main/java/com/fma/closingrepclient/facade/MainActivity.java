package com.fma.closingrepclient.facade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.controller.ControllerReport;

public class MainActivity extends BaseActivity {
    TextView txtOrderOpen;
    TextView txtOrderClosed;
    TextView txtOrderCancel;
    TextView txtOrderTotal;
    TextView txtNotUploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, this.mainframe);

        txtOrderOpen = findViewById(R.id.txtOrderOpen);
        txtOrderClosed = findViewById(R.id.txtOrderClosed);
        txtOrderCancel = findViewById(R.id.txtOrderCancel);
        txtOrderTotal = findViewById(R.id.txtOrderTotal);
        txtNotUploaded = findViewById(R.id.txtNotUploaded);

        ControllerReport controllerReport = new ControllerReport(this);
        txtOrderOpen.setText(String.valueOf(controllerReport.getTotalOrderOpen()));
        txtOrderCancel.setText(String.valueOf(controllerReport.getTotalOrderCancel()));
        txtOrderClosed.setText(String.valueOf(controllerReport.getTotalOrderClosed()));
        txtOrderTotal.setText(String.valueOf(controllerReport.getTotalOrder()));
        txtNotUploaded.setText(String.valueOf(controllerReport.getNotUploadedOrder()));

//        startActivity(new Intent(this, SyncActivity.class));
    }
}
