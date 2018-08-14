package com.fma.closingrepclient.facade;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.AreaAdapter;
import com.fma.closingrepclient.adapter.MaterialAdapter;
import com.fma.closingrepclient.controller.ControllerArea;
import com.fma.closingrepclient.controller.ControllerMaterial;
import com.fma.closingrepclient.model.ModelArea;
import com.fma.closingrepclient.model.ModelMaterial;

import java.util.List;

public class MaterialActivity extends BaseActivity {
    private ControllerMaterial controllerMaterialArea;
    private List<ModelMaterial> materials;
    private MaterialAdapter materialAdapter;
    private RecyclerView rvMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_material, this.mainframe);

        controllerMaterialArea = new ControllerMaterial(this);
        materials = controllerMaterialArea.getMaterials();
        materialAdapter = new MaterialAdapter(this, materials);

        rvMaterial = findViewById(R.id.rvMaterial);
        rvMaterial.setLayoutManager(new GridLayoutManager(this, 1));
        rvMaterial.setAdapter(materialAdapter);
    }
}
