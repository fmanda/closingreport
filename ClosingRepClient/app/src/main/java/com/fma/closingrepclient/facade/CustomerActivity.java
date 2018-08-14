package com.fma.closingrepclient.facade;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.CustomerAdapter;
import com.fma.closingrepclient.adapter.MaterialAdapter;
import com.fma.closingrepclient.controller.ControllerCustomer;
import com.fma.closingrepclient.controller.ControllerMaterial;
import com.fma.closingrepclient.model.ModelCustomer;
import com.fma.closingrepclient.model.ModelMaterial;

import java.util.List;

public class CustomerActivity extends BaseActivity {
    private ControllerCustomer controllerCustomer;
    private List<ModelCustomer> customers;
    private CustomerAdapter customerAdapter;
    private RecyclerView rvCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_customer, this.mainframe);

        controllerCustomer = new ControllerCustomer(this);
        customers = controllerCustomer.getCustomers();
        customerAdapter = new CustomerAdapter(this, customers);

        rvCustomer = findViewById(R.id.rvCustomer);
        rvCustomer.setLayoutManager(new GridLayoutManager(this, 1));
        rvCustomer.setAdapter(customerAdapter);
    }
}
