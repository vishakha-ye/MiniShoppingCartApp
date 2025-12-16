package com.vishakha.indroydminishop;

public class Product {

    private int imageResId;  // new field
    private String name;
    private double originalPrice;
    private double discountedPrice; // If no discount, same as originalPrice
    private boolean isDiscounted; // True if discountedPrice < originalPrice
    private double taxRate; // 0.05 for 5% or 0.18 for 18%

    public Product(String name, double originalPrice, double discountedPrice, double taxRate, int imageResId) {
        this.imageResId = imageResId;
        this.name = name;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.isDiscounted = (discountedPrice < originalPrice);
        this.taxRate = taxRate;
    }

    public int getImageResId() {
        return imageResId;
    }
    public String getName() {
        return name;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public boolean isDiscounted() {
        return isDiscounted;
    }

    public double getTaxRate() {
        return taxRate;
    }
}
