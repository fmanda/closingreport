package com.fma.closingrepclient.facade;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fma.closingrepclient.R;
import com.fma.closingrepclient.adapter.ProductAdapter;
import com.fma.closingrepclient.controller.ControllerProduct;
import com.fma.closingrepclient.model.ModelProduct;

import java.util.List;

public class ProductActivity extends BaseActivity {
    private ControllerProduct controllerProduct;
    private List<ModelProduct> products;
    private ProductAdapter productAdapter;
    private RecyclerView rvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_product, this.mainframe);

        controllerProduct = new ControllerProduct(this);
        products = controllerProduct.getProducts();
        productAdapter = new ProductAdapter(this, products);

        rvProduct = findViewById(R.id.rvProduct);
        rvProduct.setLayoutManager(new GridLayoutManager(this, 1));
        rvProduct.setAdapter(productAdapter);
    }
}
