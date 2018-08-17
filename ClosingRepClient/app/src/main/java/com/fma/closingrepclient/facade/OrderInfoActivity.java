package com.fma.closingrepclient.facade;

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
import android.widget.TextView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.OrderAdapter;
import com.fma.closingrepclient.controller.ControllerOrder;
import com.fma.closingrepclient.helper.DBHelper;
import com.fma.closingrepclient.model.ModelDetailOrder;
import com.fma.closingrepclient.model.ModelOrder;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderInfoActivity extends AppCompatActivity {
    private TextView txtOrderNo;
    private TextView txtOrderDate;
    private TextView txtJenis;
    private TextView txtProduct;
    private TextView txtStatus;
    private TextView txtPhone;
    private TextView txtInetNumber;
    private TextView txtCustomer;
    private TextView txtAlamat;
    private TextView txtCustPhone;
    private ModelOrder modelOrder;

    private Button btnUpdate;
    private Button btnPending;
    private Button btnCancel;
    private ModelDetailOrder detailOrder = new ModelDetailOrder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setTitle("Detail Order");

        }

        txtOrderNo = this.findViewById(R.id.txtOrderNo);
        txtOrderDate = this.findViewById(R.id.txtOrderDate);
        txtJenis = this.findViewById(R.id.txtJenis);
        txtProduct = this.findViewById(R.id.txtProduct);
        txtStatus = this.findViewById(R.id.txtStatus);
        txtPhone = this.findViewById(R.id.txtPhone);
        txtInetNumber = this.findViewById(R.id.txtInetNumber);
        txtCustomer = this.findViewById(R.id.txtCustomer);
        txtAlamat = this.findViewById(R.id.txtAlamat);
        txtCustPhone = this.findViewById(R.id.txtCustPhone);
        btnUpdate = this.findViewById(R.id.btnUpdate);
        btnPending = this.findViewById(R.id.btnPending);
        btnCancel = this.findViewById(R.id.btnCancel);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDetailorder();
            }
        });

        initData();
    }

    private void createDetailorder() {
        Intent intent = new Intent(this, DetailOrderActivity.class);
        intent.putExtra("order", modelOrder);
        startActivity(intent);
        this.finish();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("order")) {
                modelOrder = (ModelOrder) intent.getSerializableExtra("order");
//                getSupportActionBar().setTitle("Detail Order");
            }
        }
        ;

        if (modelOrder == null) {
            modelOrder = new ModelOrder();
        }


        SQLiteDatabase db = DBHelper.getInstance(this).getReadableDatabase();
        modelOrder.loadCustomer(db);
        modelOrder.loadProduct(db);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy hh:mm", new Locale("id", "ID"));


        txtOrderNo.setText(modelOrder.getOrderno());
        txtOrderDate.setText(formatter.format(modelOrder.getTanggal()));
        txtJenis.setText(modelOrder.getJenis_order());
        txtProduct.setText(modelOrder.product.getNama());
        txtStatus.setText(modelOrder.getStatus());
        txtPhone.setText(modelOrder.getPhone());
        txtInetNumber.setText(modelOrder.getInetnumber());
        txtCustomer.setText(modelOrder.customer.getNama());
        txtAlamat.setText(modelOrder.customer.getAlamat());
        txtCustPhone.setText(modelOrder.customer.getPhone());

        switch (txtStatus.getText().toString()) {
            case "OPEN":
                txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorOpen));
                break;
            case "PROCESS":
                txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorProcess));
                break;
            case "PENDING":
                txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorPending));
                break;
            case "CANCEL":
                txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorCancel));
                break;
            case "CLOSED":
                txtStatus.setTextColor(ContextCompat.getColor(this, R.color.colorClosed));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }

}
