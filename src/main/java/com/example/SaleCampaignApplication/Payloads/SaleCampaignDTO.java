package com.example.SaleCampaignApplication.Payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleCampaignDTO {

    private int sId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private CampaignDiscountDTO[] campaignDiscountDTOS;
}
