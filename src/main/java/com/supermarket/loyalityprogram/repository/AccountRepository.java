package com.supermarket.loyalityprogram.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supermarket.loyalityprogram.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByMobileNumber(String mobileNumber);
	Optional<Account> findByIdCardNumber(String idCardNumber);
}
