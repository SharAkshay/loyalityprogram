package com.supermarket.loyalityprogram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.loyalityprogram.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	
}
