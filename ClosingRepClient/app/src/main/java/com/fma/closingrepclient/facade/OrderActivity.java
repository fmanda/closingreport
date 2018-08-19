package com.fma.closingrepclient.facade;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.OrderAdapter;
import com.fma.closingrepclient.controller.ControllerOrder;
import com.fma.closingrepclient.model.ModelOrder;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {
    private ControllerOrder controllerOrder;
    private List<ModelOrder> orders;
    private OrderAdapter orderAdapter;
    private RecyclerView rvOrder;
    private SearchView txtSearch;
    private TabLayout tbStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_order, this.mainframe);

        controllerOrder = new ControllerOrder(this);
        orders = new ArrayList<ModelOrder>();
        orderAdapter = new OrderAdapter(this, orders);

        rvOrder = findViewById(R.id.rvOrder);
        txtSearch = findViewById(R.id.txtSearch);
        tbStatus = findViewById(R.id.tbStatus);
        tbStatus.getTabAt(1).select();


        rvOrder.setLayoutManager(new GridLayoutManager(this, 1));
        rvOrder.setAdapter(orderAdapter);

        orderAdapter.setItemClickListener(new OrderAdapter.ItemClickListener() {
            @Override
            public void onClick(ModelOrder modelOrder) {
                infoOrder(modelOrder);
            }
        });

        txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loadOrder(s);
                return false;
            }
        });

        tbStatus.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadOrder(txtSearch.getQuery().toString() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        loadOrder("");
    }

    private void infoOrder(ModelOrder modelOrder){
        Intent intent = new Intent(this, OrderInfoActivity.class);
        intent.putExtra("order", modelOrder);
        startActivity(intent);
    }

    private void loadOrder(String filter){
        String filterStatus = "";
        if (tbStatus.getSelectedTabPosition() == 1){
            filterStatus += "'OPEN','PENDING','PROCESS'";
        }else if (tbStatus.getSelectedTabPosition() == 2){
            filterStatus += "'CLOSED'";
        }

        List<ModelOrder> tmp = controllerOrder.getOrders(filter, filterStatus);
        orders.clear();
        orders.addAll(tmp);
        orderAdapter.notifyDataSetChanged();
    }
}
