package com.fma.closingrepclient.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelDetailOrderMaterial;
import com.fma.closingrepclient.model.ModelMaterial;

import java.util.List;

/**
 * Created by fma on 7/30/2017.
 */

public class DetailMaterialAdapter extends RecyclerView.Adapter<DetailMaterialAdapter.ViewHolder> {
    private Context context;
    private List<ModelDetailOrderMaterial> items;
    private LayoutInflater mInflater;
    private SQLiteDatabase db;
    Boolean disableTrack = true;


    public DetailMaterialAdapter(Context context, List<ModelDetailOrderMaterial> items) {
        this.context = context;
        this.items = items;
        this.mInflater = LayoutInflater.from(context);
        this.db = DBHelper.getInstance(context).getReadableDatabase();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.adapter_detailmaterial_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        disableTrack = true;
        viewHolder.item = items.get(i);
//        viewHolder.item.loadMaterial(db);

        if (viewHolder.item.material == null){
            viewHolder.txtMaterial.setText("");
        }else {
            viewHolder.txtMaterial.setText(viewHolder.item.material.getNama());
        }
        viewHolder.txtQty.setText( viewHolder.item.getQty().toString() );
        viewHolder.txtSerial.setText(viewHolder.item.getSerialnumber());

        disableTrack = false;

        viewHolder.txtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (disableTrack) return;
                String txt = s.toString();
                Double qty = 0.0;
                try{
                    qty = Double.parseDouble(txt);
                    viewHolder.item.setQty(qty);
//                    notifyDataSetChanged();
//                    Toast.makeText(context, "Updte QTY " + viewHolder.item.getQty().toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                }
            }
        });

        viewHolder.txtSerial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (disableTrack) return;
                String txt = s.toString();
                viewHolder.item.setSerialnumber(txt);
//                notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewHolder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(viewHolder.item);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ModelDetailOrderMaterial item;
        public TextView txtMaterial;
        public TextView txtQty;
        public TextView txtSerial;
        public Button btnDel;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMaterial = itemView.findViewById(R.id.txtMaterial);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtSerial = itemView.findViewById(R.id.txtSerial);
            btnDel = itemView.findViewById(R.id.btnDel);
        }
//
        @Override
        public void onClick(View v) {
//            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }


}


