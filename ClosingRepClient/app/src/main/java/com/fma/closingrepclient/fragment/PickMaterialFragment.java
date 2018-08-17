package com.fma.closingrepclient.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.MaterialAdapter;
import com.fma.closingrepclient.controller.ControllerMaterial;
import com.fma.closingrepclient.model.ModelMaterial;

import java.util.List;

/**
 * Created by fma on 8/13/2017.
 */

public class PickMaterialFragment extends DialogFragment {
    List<ModelMaterial> materials;
    ControllerMaterial controllerMaterial;
    MaterialAdapter materialAdapter;
    RecyclerView rvMaterial;
    MaterialSelectListener materialSelectListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_material, container, false);

        controllerMaterial = new ControllerMaterial(getActivity());
        materials = controllerMaterial.getMaterials();
        materialAdapter = new MaterialAdapter(getActivity(), materials);

        rvMaterial = view.findViewById(R.id.rvMaterial);
        rvMaterial.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rvMaterial.setAdapter(materialAdapter);
        getDialog().setTitle("Pilih Material");

        materialAdapter.setItemClickListener(new MaterialAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ModelMaterial material) {
                if (materialSelectListener != null) {
                    materialSelectListener.OnSelectMaterial(material);
                    dismiss();
                }
            }
        });

        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


//        int width = ViewGroup.LayoutParams.MATCH_PARENT;
//        int height = ViewGroup.LayoutParams.MATCH_PARENT;
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
//        view.setLayoutParams(layoutParams);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);
    }


    public interface MaterialSelectListener{
        void OnSelectMaterial(ModelMaterial modelMaterial);
    }

    public void SetMaterialListener(MaterialSelectListener materialSelectListener){
        this.materialSelectListener = materialSelectListener;
    }






}
