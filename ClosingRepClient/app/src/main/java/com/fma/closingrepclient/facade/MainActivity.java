package com.fma.closingrepclient.facade;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fma.closingrepclient.R;

public class MainActivity extends BaseActivity {
    Button btntest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, this.mainframe);

//        btntest = (Button) findViewById(R.id.btnTest);
//        btntest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "TEST", Toast.LENGTH_SHORT).show();
//            }
//        });
        startActivity(new Intent(this, SyncActivity.class));
    }
}
