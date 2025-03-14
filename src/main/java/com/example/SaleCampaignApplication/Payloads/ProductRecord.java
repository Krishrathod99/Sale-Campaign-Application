package com.example.SaleCampaignApplication.Payloads;

import jakarta.persistence.*;

@Entity
@Table(name = "productrecord")
public class ProductRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int productId;
    private int currentPrice;
    private double discount;

    public ProductRecord() {
    }


    public ProductRecord(int id, int productId, int currentPrice, double discount) {
        this.id = id;
        this.productId = productId;
        this.currentPrice = currentPrice;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
