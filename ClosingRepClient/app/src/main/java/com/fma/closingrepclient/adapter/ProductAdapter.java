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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<ModelProduct>  products;
    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;

    public ProductAdapter(Context context, List<ModelProduct> productList) {
        this.context = context;
        this.products = productList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_product_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.modelProduct = products.get(i);
        viewHolder.txtKode.setText(viewHolder.modelProduct.getKode());
        viewHolder.txtNama.setText(viewHolder.modelProduct.getNama());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelProduct modelProduct;
        public TextView txtNama;
        public TextView txtKode;

        public ViewHolder(View itemView) {
            super(itemView);
            txtKode = itemView.findViewById(R.id.txtKode);
            txtNama = itemView.findViewById(R.id.txtNama);

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


