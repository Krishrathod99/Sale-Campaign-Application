package com.example.SaleCampaignApplication.Repositories;

import com.example.SaleCampaignApplication.Entities.CampaignDiscount;
import com.example.SaleCampaignApplication.Entities.SaleCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignDiscountRepository extends JpaRepository<CampaignDiscount , Integer> {

    CampaignDiscount[] findBySaleCampaign(SaleCampaign saleCampaign);
}
