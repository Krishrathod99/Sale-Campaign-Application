package com.example.SaleCampaignApplication.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Persistent;

@Entity
@Table(name = "campaigndiscount")
@Getter
@Setter
@NoArgsConstructor
public class CampaignDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer campaignDid;

    private int productId;
    private double pDiscount;

    @ManyToOne
    @JoinColumn(name = "SaleCampaign_Id")
    private SaleCampaign saleCampaign;

    @Persistent
    @ManyToOne
    private Product product;

}
