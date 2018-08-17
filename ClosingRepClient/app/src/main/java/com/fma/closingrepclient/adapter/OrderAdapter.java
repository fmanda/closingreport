package com.fma.closingrepclient.adapter;

import android.content.Context;
import android.graphics.ColorSpace;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private ItemClickListener itemClickListener;
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
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.order = orders.get(i);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm", new Locale("id", "ID"));
        String orderNo = viewHolder.order.getOrderno();

        viewHolder.txtOrderNo.setText(orderNo);
        viewHolder.txtOrderDate.setText(formatter.format(viewHolder.order.getTanggal()));
        viewHolder.txtJenis.setText(viewHolder.order.getJenis_order());
        viewHolder.txtStatus.setText(viewHolder.order.getStatus());

        switch (viewHolder.order.getStatus()) {
            case "OPEN":
                viewHolder.lnvOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOpen));
                break;
            case "PROCESS":
                viewHolder.lnvOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorProcess));
                break;
            case "PENDING":
                viewHolder.lnvOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPending));
                break;
            case "CANCEL":
                viewHolder.lnvOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorCancel));
                break;
            case "CLOSED":
                viewHolder.lnvOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.colorClosed));
                break;
            default: break;
        }
//        viewHolder.txtOrderNo.setTextColor(viewHolder.txtStatus.getTextColors());


        if (viewHolder.order.customer != null) {
            String alamat = viewHolder.order.customer.getNama()  + ", " + viewHolder.order.customer.getAlamat();
            viewHolder.txtAlamat.setText(alamat);
        }

        if (viewHolder.order.focused){
            viewHolder.lnhButton.setVisibility(View.VISIBLE);
        }else{
            viewHolder.lnhButton.setVisibility(View.GONE);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onClick(viewHolder.order);
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
        return orders.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ModelOrder order;
        public TextView txtOrderNo;
        public TextView txtOrderDate;
        public TextView txtStatus;
        public TextView txtJenis;
        public TextView txtAlamat;
        public LinearLayout lnhButton;
        public LinearLayout lnvOrder;
        public Button btnUpdate;
        public Button btnPending;
        public Button btnCancel;



        public ViewHolder(final View itemView) {
            super(itemView);
            txtOrderNo = itemView.findViewById(R.id.txtOrderNo);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtJenis = itemView.findViewById(R.id.txtJenis);
            txtAlamat = itemView.findViewById(R.id.txtAlamat);

            lnvOrder = itemView.findViewById(R.id.lnvOrder);
            lnhButton = itemView.findViewById(R.id.lnhButton);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnPending = itemView.findViewById(R.id.btnPending);
            btnCancel = itemView.findViewById(R.id.btnCancel);

//            btnUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (itemClickListener==null) return;
//                    itemClickListener.onUpdate(order);
//                    Toast.makeText(context,"Update " + order.getOrderno(), Toast.LENGTH_SHORT).show();
//                }
//            });

//            lnvOrder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    order.focused = !order.focused;
//                    notifyDataSetChanged();
//                }
//            });


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
        void onClick(ModelOrder modelOrder);
//        void onItemLongClick(View view, int position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


}


