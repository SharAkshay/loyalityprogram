package com.supermarket.loyalityprogram.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;
	
	public Account createAccount(Account account) {
		account.setIdCardNumber(generateIdNumber());
		account.setCreateTime(LocalDateTime.now());
		account.setUpdatetime(LocalDateTime.now());
		account.setLoyalityPoints(new BigDecimal(0));
		return accountRepository.save(account);
	}
	
	public Account getAccountDetailsByMobileNumber(String mobileNumber) {
		return accountRepository.findByMobileNumber(mobileNumber).orElseThrow();
	}
	
	public Account getAccountDetailsByIdCardNumber(String idCardNumber) {
		return accountRepository.findByIdCardNumber(idCardNumber).orElseThrow();
	}
	
	public BigDecimal getAvailablePoints(String idCardNumber) {
		Account account = accountRepository.findByIdCardNumber(idCardNumber).orElseThrow();
		account.getLoyalityPoints();
		return account.getLoyalityPoints();
	}
	
	private String generateIdNumber() {
		String alphaNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";
		StringBuilder sb = new StringBuilder(10);
		for (int i = 0; i < 10; i++) {
			int index = (int) (alphaNum.length() * Math.random());
			sb.append(alphaNum.charAt(index));
		}
		String idCardNumber = sb.toString();
		return idCardNumber;
	}
}
