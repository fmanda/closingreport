package com.fma.closingrepclient.facade;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.DetailMaterialAdapter;
import com.fma.closingrepclient.controller.ControllerMaterial;
import com.fma.closingrepclient.fragment.PickMaterialFragment;
import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelDetailOrder;
import com.fma.closingrepclient.model.ModelDetailOrderMaterial;
import com.fma.closingrepclient.model.ModelMaterial;
import com.fma.closingrepclient.model.ModelOrder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailOrderActivity extends AppCompatActivity {
    private ModelOrder modelOrder;
    private ModelDetailOrder detailOrder = new ModelDetailOrder();
    private DBHelper db;

    private TextView txtOrderNo;
    private TextView txtNoBukti;
    private TextView txtOrderDate;
    private TextView txtODP;
    private TextView txtQRCode;
    private TextView txtKeterangan;
    private RecyclerView rvItem;
    private RadioGroup rgStatus;
    private RadioButton rbClosed;
    private RadioButton rbPending;
    private RadioButton rbCancel;
    private Button btnAdd;

    private DetailMaterialAdapter detailMaterialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        db = DBHelper.getInstance(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setTitle("Detail Order");

        }

        txtOrderNo = findViewById(R.id.txtOrderNo);
        txtNoBukti = findViewById(R.id.txtNoBukti);
        txtOrderDate = findViewById(R.id.txtOrderDate);
        txtODP = findViewById(R.id.txtODP);
        txtQRCode = findViewById(R.id.txtQRCode);
        txtKeterangan = findViewById(R.id.txtKeterangan);
        rvItem = findViewById(R.id.rvItem);
        rgStatus = findViewById(R.id.rgStatus);
        rbClosed = findViewById(R.id.rbClosed);
        rbPending = findViewById(R.id.rbPending);
        rbCancel = findViewById(R.id.rbCancel);
        btnAdd = findViewById(R.id.btnAdd);

        detailMaterialAdapter = new DetailMaterialAdapter(this, detailOrder.items);
        rvItem.setLayoutManager(new GridLayoutManager(this, 1));
        rvItem.setAdapter(detailMaterialAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lookupMaterial();
            }
        });

        initData();
    }

    private void lookupMaterial() {
        List<ModelMaterial> materials= new ControllerMaterial(this).getMaterials();
        if (!materials.isEmpty()) {
            FragmentManager fm = getFragmentManager();
            PickMaterialFragment pickMaterialFragment = new PickMaterialFragment();

            pickMaterialFragment.SetMaterialListener(new PickMaterialFragment.MaterialSelectListener() {
                @Override
                public void OnSelectMaterial(ModelMaterial modelMaterial) {
                    addDetailMaterial(modelMaterial);
                }
            });
            pickMaterialFragment.show(fm, "Pilih Bahan");
        }


    }

    private void addDetailMaterial(ModelMaterial material){
        ModelDetailOrderMaterial item = new ModelDetailOrderMaterial();
        item.setMaterial_id(material.getId());
        item.loadMaterial(db.getReadableDatabase());
        item.setSerialnumber("");
        item.setQty(1.0);
        detailOrder.items.add(item);
        detailMaterialAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, 0, 0, "Done").setIcon(R.drawable.ic_menu_checkall)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("detailorder")) {
                detailOrder = (ModelDetailOrder) intent.getSerializableExtra("detailorder");
                detailOrder.reLoadAll(db.getReadableDatabase());

                if (detailOrder.getNobukti().equals(""))  detailOrder.generateNoBukti();
                modelOrder = detailOrder.order;

                detailMaterialAdapter = new DetailMaterialAdapter(this, detailOrder.items);
                rvItem.setAdapter(detailMaterialAdapter);
            }
            if (extras.containsKey("order")) {
                modelOrder = (ModelOrder) intent.getSerializableExtra("order");
            }
        };

        if (modelOrder == null) {
            modelOrder = new ModelOrder();
        }

        SQLiteDatabase db = DBHelper.getInstance(this).getReadableDatabase();
        modelOrder.loadCustomer(db);
        modelOrder.loadProduct(db);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm", new Locale("id", "ID"));

        txtOrderNo.setText(modelOrder.getOrderno());
        txtNoBukti.setText(detailOrder.getNobukti());
        txtOrderDate.setText(formatter.format(modelOrder.getTanggal()));
        txtODP.setText(detailOrder.getOdp());
        txtQRCode.setText(detailOrder.getQrcode());
        txtKeterangan.setText(detailOrder.getKeterangan());
        rgStatus.check(R.id.rbClosed);

        if (modelOrder.getStatus().equals("PENDING")) {
            rgStatus.check(R.id.rbPending);
        }else if (modelOrder.getStatus().equals("CANCEL")){
            rgStatus.check(R.id.rbCancel);
        }

        detailMaterialAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }else if (item.getTitle().equals("Done")) {
            this.saveData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveData(){
        detailMaterialAdapter.notifyDataSetChanged(); //important
        if (modelOrder == null) {
            Toast.makeText(this, "Order ID null", Toast.LENGTH_SHORT).show();
            return;
        }
        detailOrder.setOrder_id(modelOrder.getId());
        detailOrder.setTanggal(new Date());
        detailOrder.setQrcode(txtQRCode.getText().toString());
        detailOrder.setKeterangan(txtKeterangan.getText().toString());
        detailOrder.setOdp(txtODP.getText().toString());
        detailOrder.setUploaded(0);

        if (rbClosed.isChecked()){
            modelOrder.setStatus("CLOSED");
        };

        if (rbPending.isChecked()){
            modelOrder.setStatus("PENDING");
        }

        if (rbCancel.isChecked()){
            modelOrder.setStatus("CANCEL");
        }

        detailOrder.setStatus(modelOrder.getStatus());

        detailOrder.saveToDBAll(db.getWritableDatabase());

        startActivity(new Intent(this, OrderActivity.class));
        finish();

    }

}
