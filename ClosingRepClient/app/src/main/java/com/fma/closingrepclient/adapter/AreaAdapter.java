package com.fma.closingrepclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.model.ModelArea;
import com.fma.closingrepclient.model.ModelProduct;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    private Context context;
    private List<ModelArea>  areas;
    private LayoutInflater mInflater;

    public AreaAdapter(Context context, List<ModelArea> areas) {
        this.context = context;
        this.areas = areas;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_area_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.modelArea = areas.get(i);
        viewHolder.txtKode.setText(viewHolder.modelArea.getKode());
        viewHolder.txtNama.setText(viewHolder.modelArea.getNama());
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelArea modelArea;
        public TextView txtNama;
        public TextView txtKode;

        public ViewHolder(View itemView) {
            super(itemView);
            txtKode = itemView.findViewById(R.id.txtKode);
            txtNama = itemView.findViewById(R.id.txtNama);
        }
//
        @Override
        public void onClick(View v) {
//            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }


}


