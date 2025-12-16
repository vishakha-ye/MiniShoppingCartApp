package com.vishakha.indroydminishop;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public void decreaseQuantity() {
        if (quantity > 1) quantity--;
    }

    public double getItemTotal() {
        return quantity * product.getDiscountedPrice();
    }

    public double getItemTax() {
        return quantity * product.getDiscountedPrice() * product.getTaxRate();
    }
}
