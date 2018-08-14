package com.fma.closingrepclient.facade;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.AreaAdapter;
import com.fma.closingrepclient.adapter.ProductAdapter;
import com.fma.closingrepclient.controller.ControllerArea;
import com.fma.closingrepclient.controller.ControllerProduct;
import com.fma.closingrepclient.model.ModelArea;
import com.fma.closingrepclient.model.ModelProduct;

import java.util.List;

public class AreaActivity extends BaseActivity {
    private ControllerArea controllerArea;
    private List<ModelArea> areas;
    private AreaAdapter areaAdapter;
    private RecyclerView rvArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_area, this.mainframe);

        controllerArea = new ControllerArea(this);
        areas = controllerArea.getAreas();
        areaAdapter = new AreaAdapter(this, areas);

        rvArea = findViewById(R.id.rvArea);
        rvArea.setLayoutManager(new GridLayoutManager(this, 1));
        rvArea.setAdapter(areaAdapter);
    }
}
