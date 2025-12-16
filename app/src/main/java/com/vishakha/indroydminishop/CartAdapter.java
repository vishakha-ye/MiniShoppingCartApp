package com.vishakha.indroydminishop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartItem> cartItems;
    private Context context;
    ImageView ivCartItemImage;  // NEW

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        Product product = cartItem.getProduct();

        holder.ivCartItemImage.setImageResource(cartItem.getProduct().getImageResId());

        holder.tvItemName.setText(product.getName());
        holder.tvItemPrice.setText("₹" + product.getDiscountedPrice() + " x " + cartItem.getQuantity());
        holder.tvItemTotal.setText("Total: ₹" + String.format("%.2f", cartItem.getItemTotal()));
        holder.tvItemTax.setText("Tax: ₹" + String.format("%.2f", cartItem.getItemTax()));

        holder.btnIncrease.setOnClickListener(v -> {
            cartItem.increaseQuantity();
            notifyItemChanged(position);
            ((CartActivity) context).updateTotals();
        });

        holder.btnDecrease.setOnClickListener(v -> {
            cartItem.decreaseQuantity();
            if (cartItem.getQuantity() == 0) {
                cartItems.remove(position);
                notifyItemRemoved(position);
            } else {
                notifyItemChanged(position);
            }
            ((CartActivity) context).updateTotals();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemPrice, tvItemTotal, tvItemTax;
        Button btnIncrease, btnDecrease;
        ImageView ivCartItemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCartItemImage = itemView.findViewById(R.id.ivCartItemImage);  // NEW
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvItemTotal = itemView.findViewById(R.id.tvItemTotal);
            tvItemTax = itemView.findViewById(R.id.tvItemTax);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}
