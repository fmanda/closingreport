package com.fma.closingrepclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.model.ModelProduct;
import com.fma.closingrepclient.model.ModelReportMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class ReportMaterialAdapter extends RecyclerView.Adapter<ReportMaterialAdapter.ViewHolder> {
    private Context context;
    private List<ModelReportMaterial>  reportMaterials;
    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;

    public ReportMaterialAdapter(Context context, List<ModelReportMaterial> reportMaterials) {
        this.context = context;
        this.reportMaterials = reportMaterials;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_reportmaterial_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.reportMaterial = reportMaterials.get(i);
        viewHolder.txtNama.setText(viewHolder.reportMaterial.nama);
        viewHolder.txtJenis.setText(viewHolder.reportMaterial.jenis);
        viewHolder.txtQty.setText(String.valueOf(viewHolder.reportMaterial.qty));
    }

    @Override
    public int getItemCount() {
        return reportMaterials.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelReportMaterial reportMaterial;
        public TextView txtNama;
        public TextView txtJenis;
        public TextView txtQty;

        public ViewHolder(View itemView) {
            super(itemView);
            txtJenis = itemView.findViewById(R.id.txtJenis);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtQty = itemView.findViewById(R.id.txtQty);

//            btnCategory = (Button) itemView.findViewById(R.id.btnCategory);
//            btnCategory.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mClickListener.onItemClick(v, getAdapterPosition());
//                }
//            });
//            itemView.setOnClickListener(this);
        }
        //
        @Override
        public void onClick(View v) {
//            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }

//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
//    // allows clicks events to be caught
//    public void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }


}


