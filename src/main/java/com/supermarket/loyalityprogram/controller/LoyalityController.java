package com.supermarket.loyalityprogram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.loyalityprogram.dto.AccountGetDTO;
import com.supermarket.loyalityprogram.dto.MapStructMapper;
import com.supermarket.loyalityprogram.dto.PurchaseGetDTO;
import com.supermarket.loyalityprogram.dto.PurchasePostDTO;
import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.model.Purchase;
import com.supermarket.loyalityprogram.service.LoyalityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/loyality/")
public class LoyalityController {

	@Autowired
	LoyalityService loyalityService;

	@Autowired
	MapStructMapper mapper;
	/*
	 * @PutMapping("/update")
	 * 
	 * @ResponseStatus(code = HttpStatus.OK) public AccountGetDTO
	 * updateLoyalityPoints(@RequestBody PurchasePostDTO purchasePostDTO,
	 * 
	 * @RequestParam String idCardNumber) { log.
	 * info("Request intercepted for new purchase without redemption for idCardNUmber : {}"
	 * , idCardNumber); Account account =
	 * loyalityService.addLoyalityPoints(mapper.purchasePostDTOToPurchase(
	 * purchasePostDTO), idCardNumber); return
	 * mapper.accountToAccountGetDTO(account); }
	 */

	@PostMapping("/redeem/{idCardNumber}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PurchaseGetDTO redeemLoyalityPoints(@RequestBody PurchasePostDTO purchasePostDTO,
			@RequestParam(value = "idCardNumber") String idCardNumber) {
		log.info("Request intercepted for new purchase with points redemption for idCardNumber : {}", idCardNumber);
		Purchase purchaseUpdated = loyalityService
				.redeemLoyalityPoints(mapper.purchasePostDTOToPurchase(purchasePostDTO), idCardNumber);
		return mapper.purchaseToPurchaseGetDTO(purchaseUpdated);
	}

}
