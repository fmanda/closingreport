package com.fma.closingrepclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.model.ModelArea;
import com.fma.closingrepclient.model.ModelMaterial;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> {
    private Context context;
    private List<ModelMaterial> materials;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;

    public MaterialAdapter(Context context, List<ModelMaterial> areas) {
        this.context = context;
        this.materials = areas;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_material_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.modelMaterial = materials.get(i);
        viewHolder.txtMerk.setText(viewHolder.modelMaterial.getMerk());
        viewHolder.txtNama.setText(viewHolder.modelMaterial.getNama());
        viewHolder.txtJenis.setText(viewHolder.modelMaterial.getJenis());

        final ModelMaterial material = viewHolder.modelMaterial;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            //
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) itemClickListener.onItemClick(material);
//                Toast.makeText(context, material.getNama(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ModelMaterial modelMaterial;
        public TextView txtNama;
        public TextView txtMerk;
        public TextView txtJenis;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMerk = itemView.findViewById(R.id.txtMerk);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtJenis = itemView.findViewById(R.id.txtJenis);
        }

    }

    public interface ItemClickListener {
        void onItemClick(ModelMaterial material);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

}


