package com.fma.closingrepclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.model.ModelArea;
import com.fma.closingrepclient.model.ModelCustomer;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private Context context;
    private List<ModelCustomer> customers;
    private LayoutInflater mInflater;

    public CustomerAdapter(Context context, List<ModelCustomer> customers) {
        this.context = context;
        this.customers = customers;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_customer_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.modelCustomer = customers.get(i);
        viewHolder.txtAlamat.setText(viewHolder.modelCustomer.getAlamat());
        viewHolder.txtNama.setText(viewHolder.modelCustomer.getNama());
        viewHolder.txtCustPhone.setText(viewHolder.modelCustomer.getCustphone());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelCustomer modelCustomer;
        public TextView txtNama;
        public TextView txtAlamat;
        public TextView txtCustPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            txtAlamat = itemView.findViewById(R.id.txtAlamat);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtCustPhone = itemView.findViewById(R.id.txtCustPhone);
        }
//
        @Override
        public void onClick(View v) {
//            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }


}


