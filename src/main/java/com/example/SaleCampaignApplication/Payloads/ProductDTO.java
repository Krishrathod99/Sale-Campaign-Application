package com.example.SaleCampaignApplication.Payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private int productId;
    private String title;
    private int mrp;
    private int currentPrice;
    private int discount;
    private int inventory;

}
