package com.example.SaleCampaignApplication.Entities;

import com.example.SaleCampaignApplication.Payloads.CampaignDiscountDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "salecampaign")
@NoArgsConstructor
@Getter
@Setter
public class SaleCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int saleId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    private String status;

}
