package com.example.SaleCampaignApplication.Repositories;

import com.example.SaleCampaignApplication.Payloads.ProductRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRecordRepository extends JpaRepository<ProductRecord, Integer> {

    ProductRecord findByProductId(int productId);
}
