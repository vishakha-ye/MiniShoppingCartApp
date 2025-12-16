package com.vishakha.indroydminishop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewProducts;
    ProductAdapter productAdapter;
    List<Product> productList = new ArrayList<>();

    FloatingActionButton fabCart;
    TextView tvCartBadge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        fabCart = findViewById(R.id.fabCart);
        tvCartBadge = findViewById(R.id.tvCartBadge);

        fabCart.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CartActivity.class))
        );

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        // Sample Products
        productList.add(new Product("T-Shirt", 500, 500, 0.05, R.drawable.tshirt));
        productList.add(new Product("Jeans", 1500, 1200, 0.18, R.drawable.jeans));
        productList.add(new Product("Shoes", 2000, 2000, 0.18, R.drawable.shoes));
        productList.add(new Product("Watch", 1000, 800, 0.05, R.drawable.watch));
        productList.add(new Product("Bag", 800, 800, 0.05, R.drawable.bag));
        productList.add(new Product("Headphones", 1200, 1000, 0.18, R.drawable.headphones));

        productAdapter = new ProductAdapter(this, productList);
        recyclerViewProducts.setAdapter(productAdapter);

        updateCartBadge();
    }

    // Badge Update
    public void updateCartBadge() {
        int count = CartManager.getInstance().getCartCount();
        if (count > 0) {
            tvCartBadge.setText(String.valueOf(count));
            tvCartBadge.setVisibility(View.VISIBLE);
        } else {
            tvCartBadge.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }
}
