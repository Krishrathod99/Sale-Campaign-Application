package com.example.SaleCampaignApplication.Repositories;

import com.example.SaleCampaignApplication.Entities.SaleCampaign;
import com.example.SaleCampaignApplication.Payloads.SaleCampaignDTO;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SaleCampaignRepository extends JpaRepository<SaleCampaign , Integer> {

    SaleCampaignDTO findSaleCampaignDTOBySaleId(int saleId);
}
