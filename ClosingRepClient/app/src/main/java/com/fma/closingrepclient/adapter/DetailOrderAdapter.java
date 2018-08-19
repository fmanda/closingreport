package com.fma.closingrepclient.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.model.ModelDetailOrder;
import com.fma.closingrepclient.model.ModelOrder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by fma on 7/30/2017.
 */

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.ViewHolder> {
    private Context context;
    private List<ModelDetailOrder> detailOrders;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;
//    private ItemClickListener mClickListener;

    public DetailOrderAdapter(Context context, List<ModelDetailOrder> list) {
        this.context = context;
        this.detailOrders = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_detailorder_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.detailOrder = detailOrders.get(i);
        ModelDetailOrder detailOrder = viewHolder.detailOrder;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm", new Locale("id", "ID"));
        String noBukti = detailOrder.getNobukti();
        String orderNo = "";
        String status = "";
        String customer = "";
        String alamat = "";

        if (detailOrder.order != null){
            orderNo = detailOrder.order.getOrderno();
            status = detailOrder.order.getStatus();
            if (detailOrder.order.customer != null){
                customer = detailOrder.order.customer.getNama();
                alamat = detailOrder.order.customer.getAlamat();
            }
        }

        viewHolder.txtNoBukti.setText(noBukti);
        viewHolder.txtOrderNo.setText(orderNo);
        viewHolder.txtTanggal.setText(formatter.format(detailOrder.getTanggal()));
        viewHolder.txtStatus.setText(status);
        viewHolder.txtCustomer.setText(customer);
        viewHolder.txtAlamat.setText(alamat);




        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onClick(viewHolder.detailOrder);
//                viewHolder.order.focused = !viewHolder.order.focused;
//                for (ModelOrder order : orders){
//                    if (order.getId() != viewHolder.order.getId())
//                        order.focused = Boolean.FALSE;
//                }
//                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailOrders.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ModelDetailOrder detailOrder;
        public TextView txtOrderNo;
        public TextView txtNoBukti;
        public TextView txtTanggal;
        public TextView txtStatus;
        public TextView txtCustomer;
        public TextView txtAlamat;

        public ViewHolder(final View itemView) {
            super(itemView);
            txtOrderNo = itemView.findViewById(R.id.txtOrderNo);
            txtNoBukti = itemView.findViewById(R.id.txtNoBukti);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtTanggal = itemView.findViewById(R.id.txtTanggal);
            txtAlamat = itemView.findViewById(R.id.txtAlamat);
            txtCustomer = itemView.findViewById(R.id.txtCustomer);

        }
//
        @Override
        public void onClick(View v) {
//            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
//            changeFocus();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

//        private void changeFocus(){
//            for (ModelOrder order : orders){
//                order.focused = Boolean.FALSE;
//            }
//            ModelOrder focusedorder = orders.get(getAdapterPosition());
//            if (focusedorder!=null) focusedorder.focused = Boolean.TRUE;
//        }
    }

    public interface ItemClickListener {
//        void onUpdate(ModelOrder modelOrder);
//        void onPending(ModelOrder modelOrder);
//        void onCancel(ModelOrder modelOrder);
        void onClick(ModelDetailOrder detailOrder);
//        void onItemLongClick(View view, int position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


}


