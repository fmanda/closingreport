package com.fma.closingrepclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.model.ModelOrder;
import com.fma.closingrepclient.model.ModelProduct;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by fma on 7/30/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private List<ModelOrder> orders;
    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;

    public OrderAdapter(Context context, List<ModelOrder> list) {
        this.context = context;
        this.orders = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_order_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy H:mm", new Locale("id", "ID"));

        viewHolder.order = orders.get(i);
        viewHolder.txtOrderNo.setText(viewHolder.order.getOrderno());
        viewHolder.txtOrderDate.setText( formatter.format(viewHolder.order.getTanggal()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelOrder order;
        public TextView txtOrderNo;
        public TextView txtOrderDate;

        public ViewHolder(View itemView) {
            super(itemView);
            txtOrderNo = itemView.findViewById(R.id.txtOrderNo);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);

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


