package com.fma.closingrepclient.facade;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.CustomerAdapter;
import com.fma.closingrepclient.adapter.ReportMaterialAdapter;
import com.fma.closingrepclient.controller.ControllerCustomer;
import com.fma.closingrepclient.controller.ControllerReport;
import com.fma.closingrepclient.model.ModelCustomer;
import com.fma.closingrepclient.model.ModelReportMaterial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportMaterialActivity extends BaseActivity {
    private ControllerReport controllerReport;
    private List<ModelReportMaterial> reportMaterials;
    private ReportMaterialAdapter reportMaterialAdapter;
    private RecyclerView rvReport;
    private EditText dtStart;
    private EditText dtEnd;
    private Date startDate = new Date();
    private Date endDate = new Date();
    Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getLayoutInflater().inflate(R.layout.activity_reportmaterial, this.mainframe);
//
        controllerReport = new ControllerReport(this);
        reportMaterials = new ArrayList<>();
        reportMaterialAdapter = new ReportMaterialAdapter(this, reportMaterials);
        rvReport = findViewById(R.id.rvReport);
        rvReport.setLayoutManager(new GridLayoutManager(this, 1));
        rvReport.setAdapter(reportMaterialAdapter);
        dtStart = findViewById(R.id.dtStart);
        dtEnd = findViewById(R.id.dtEnd);

        dtStart.setText(sdf.format(startDate));
        dtEnd.setText(sdf.format(endDate));

        final DatePickerDialog.OnDateSetListener dtStartListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setStartDate(myCalendar);
            }
        };

        final DatePickerDialog.OnDateSetListener dtEndListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setEndDate(myCalendar);
            }
        };

        dtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, dtStartListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, dtEndListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        loadData();
    }

    private void setStartDate(Calendar calendar) {
        dtStart.setText(sdf.format(calendar.getTime()));
        startDate = calendar.getTime();
        loadData();
    }
    private void setEndDate(Calendar calendar) {
        dtEnd.setText(sdf.format(calendar.getTime()));
        endDate = calendar.getTime();
        loadData();
    }

    private void loadData(){
        List<ModelReportMaterial> temp = controllerReport.getReportMaterial(startDate, endDate);
        reportMaterials.clear();
        reportMaterials.addAll(temp);
        reportMaterialAdapter.notifyDataSetChanged();
    }
}
