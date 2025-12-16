package com.vishakha.indroydminishop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private com.airbnb.lottie.LottieAnimationView lottieConfetti;
    private RecyclerView recyclerViewCart;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private TextView tvSubtotal, tvTax, tvDiscount, tvFinalAmount;
    private EditText etCoupon;
    private Button btnApplyCoupon, btnCheckout;
    private double subtotal = 0;
    private double totalTax = 0;
    private double discount = 0;
    private boolean couponApplied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);


        lottieConfetti = findViewById(R.id.lottieConfetti);

        Toolbar toolbar = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));

        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvTax = findViewById(R.id.tvTax);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvFinalAmount = findViewById(R.id.tvFinalAmount);
        etCoupon = findViewById(R.id.etCoupon);
        btnApplyCoupon = findViewById(R.id.btnApplyCoupon);
        btnCheckout = findViewById(R.id.btnCheckout);

        cartItems = CartManager.getInstance().getCartItems();
        cartAdapter = new CartAdapter(this, cartItems);
        recyclerViewCart.setAdapter(cartAdapter);

        updateTotals();

        btnApplyCoupon.setOnClickListener(v -> applyCoupon());
        btnCheckout.setOnClickListener(v -> checkout());
    }

    public void updateTotals() {
        subtotal = 0;
        totalTax = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getItemTotal();
            totalTax += item.getItemTax();
        }
        double finalAmount = subtotal + totalTax - discount;
        tvSubtotal.setText("Subtotal: ₹" + String.format("%.2f", subtotal));
        tvTax.setText("Tax: ₹" + String.format("%.2f", totalTax));
        tvDiscount.setText("Discount: -₹" + String.format("%.2f", discount));
        tvFinalAmount.setText("Total: ₹" + String.format("%.2f", finalAmount));
    }

    private void applyCoupon() {
        String code = etCoupon.getText().toString().trim().toUpperCase();
        if (code.isEmpty()) {
            Toast.makeText(this, "Enter coupon code", Toast.LENGTH_SHORT).show();
            return;
        }
        if (couponApplied) {
            Toast.makeText(this, "Coupon already applied!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (subtotal < 1000) {
            Toast.makeText(this, "Minimum cart value ₹1000 required for coupon", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("SAVE20".equals(code)) {  // Yeh coupon code assume kiya – tu change kar sakti hai
            double eligibleAmount = 0;
            for (CartItem item : cartItems) {
                if (!item.getProduct().isDiscounted()) {  // Only non-discounted items
                    eligibleAmount += item.getItemTotal();
                }
            }
            double potentialDiscount = eligibleAmount * 0.20;
            discount = Math.min(potentialDiscount, 300);
            couponApplied = true;
            updateTotals();
            etCoupon.setEnabled(false);
            btnApplyCoupon.setEnabled(false);
            Toast.makeText(this, "Coupon applied! Saved ₹" + String.format("%.2f", discount), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid coupon code", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkout() {
        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Show confetti
        lottieConfetti.setVisibility(View.VISIBLE);
        lottieConfetti.playAnimation();

        // Animation end pe cart clear + back
        lottieConfetti.addAnimatorListener(new android.animation.Animator.AnimatorListener() {
            @Override public void onAnimationStart(android.animation.Animator animation) {}
            @Override public void onAnimationEnd(android.animation.Animator animation) {
                Toast.makeText(CartActivity.this, "Checkout successful! Thank you 🎉", Toast.LENGTH_LONG).show();
                CartManager.getInstance().clearCart();
                cartAdapter.notifyDataSetChanged();
                updateTotals();
                lottieConfetti.setVisibility(View.GONE);
            }
            @Override public void onAnimationCancel(android.animation.Animator animation) {}
            @Override public void onAnimationRepeat(android.animation.Animator animation) {}
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

