package com.example.SaleCampaignApplication.Repositories;

import com.example.SaleCampaignApplication.Entities.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory , Integer> {

    PriceHistory findByProductId(int productId);
}
