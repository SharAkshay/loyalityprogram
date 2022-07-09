package com.supermarket.loyalityprogram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.loyalityprogram.dto.AccountGetDTO;
import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.model.Purchase;
import com.supermarket.loyalityprogram.service.LoyalityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/loyality/")
public class LoyalityController {

	LoyalityService loyalityService;

	@PutMapping("/update")
	@ResponseStatus(code = HttpStatus.OK)
	public AccountGetDTO updateLoyalityPoints(@RequestBody Purchase purchase, @PathVariable String idCardNumber) {
		Account account = loyalityService.addLoyalityPoints(purchase, idCardNumber);
		AccountGetDTO accountdto = new AccountGetDTO();
		return accountdto;
	}

	@PutMapping("/redeem")
	@ResponseStatus(code = HttpStatus.OK)
	public AccountGetDTO redeemLoyalityPoints(@RequestBody Purchase purchase, @PathVariable String idCardNumber) {
		Purchase purchaseUpdated = loyalityService.redeemLoyalityPoints(purchase, idCardNumber);
		AccountGetDTO accountdto = new AccountGetDTO();
		return accountdto;
	}

}
