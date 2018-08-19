package com.fma.closingrepclient.facade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.DetailOrderAdapter;
import com.fma.closingrepclient.controller.ControllerDetailOrder;
import com.fma.closingrepclient.model.ModelDetailOrder;
import com.fma.closingrepclient.model.ModelOrder;

import java.util.ArrayList;
import java.util.List;

public class ListDetailOrderActivity extends BaseActivity {
    private ControllerDetailOrder controllerDetailOrder;
    private List<ModelDetailOrder> detailOrders;
    private DetailOrderAdapter detailOrderAdapter;
    private RecyclerView rvDetailOrder;
    private SearchView txtSearch;
    private TabLayout tbStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_list_detail_order, this.mainframe);

        controllerDetailOrder = new ControllerDetailOrder(this);
        detailOrders = new ArrayList<ModelDetailOrder>();
        detailOrderAdapter = new DetailOrderAdapter(this, detailOrders);

        rvDetailOrder = findViewById(R.id.rvDetailOrder);
        txtSearch = findViewById(R.id.txtSearch);
        tbStatus = findViewById(R.id.tbStatus);
        tbStatus.getTabAt(1).select();


        rvDetailOrder.setLayoutManager(new GridLayoutManager(this, 1));
        rvDetailOrder.setAdapter(detailOrderAdapter);

        detailOrderAdapter.setItemClickListener(new DetailOrderAdapter.ItemClickListener() {
            @Override
            public void onClick(ModelDetailOrder detailOrder) {
                infoDetailOrder(detailOrder);
            }
        });

        txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loadDetailOrder(s);
                return false;
            }
        });

        tbStatus.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadDetailOrder(txtSearch.getQuery().toString() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        loadDetailOrder("");
    }

    private void infoDetailOrder(ModelDetailOrder detailOrder){
        Intent intent = new Intent(this, DetailOrderActivity.class);
        intent.putExtra("detailorder", detailOrder);
        startActivity(intent);
    }

    private void loadDetailOrder(String filter){
        Integer filterStatus = -1;
        if (tbStatus.getSelectedTabPosition() == 1){
            filterStatus += 0;
        }else if (tbStatus.getSelectedTabPosition() == 2){
            filterStatus += 1;
        }

        List<ModelDetailOrder> tmp = controllerDetailOrder.getDetailOrders(filter, filterStatus);
        detailOrders.clear();
        detailOrders.addAll(tmp);
        detailOrderAdapter.notifyDataSetChanged();
    }
}
