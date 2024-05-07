package com.example.SaleCampaignApplication.Repositories;

import com.example.SaleCampaignApplication.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Integer> {
}
