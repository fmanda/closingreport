package com.fma.closingrepclient.facade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.OrderAdapter;
import com.fma.closingrepclient.adapter.ProductAdapter;
import com.fma.closingrepclient.controller.ControllerOrder;
import com.fma.closingrepclient.controller.ControllerProduct;
import com.fma.closingrepclient.model.ModelOrder;
import com.fma.closingrepclient.model.ModelProduct;

import java.util.List;

public class OrderActivity extends BaseActivity {
    private ControllerOrder controllerOrder;
    private List<ModelOrder> orders;
    private OrderAdapter productAdapter;
    private RecyclerView rvOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_order, this.mainframe);

        controllerOrder = new ControllerOrder(this);
        orders = controllerOrder.getOrders();
        productAdapter = new OrderAdapter(this, orders);

        rvOrder = findViewById(R.id.rvOrder);
        rvOrder.setLayoutManager(new GridLayoutManager(this, 1));
        rvOrder.setAdapter(productAdapter);

        productAdapter.setItemClickListener(new OrderAdapter.ItemClickListener() {
            @Override
            public void onClick(ModelOrder modelOrder) {
                infoOrder(modelOrder);
            }
        });
    }

    private void infoOrder(ModelOrder modelOrder){
        Intent intent = new Intent(this, OrderInfoActivity.class);
        intent.putExtra("order", modelOrder);
        startActivity(intent);
    }
}
