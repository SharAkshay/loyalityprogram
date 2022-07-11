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
import com.supermarket.loyalityprogram.exceptions.InvalidCashierIdException;
import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.model.Purchase;
import com.supermarket.loyalityprogram.service.LoyalityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/loyality/")
public class LoyalityController {

	@Autowired
	LoyalityService loyalityService;

	@Autowired
	MapStructMapper mapper;
	
	@Operation(summary = "Purchase is processed with option of redeeming the loyality points")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Purchase completed", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Account details not found", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "An unexpected error occured", content = {
					@Content(mediaType = "application/json") }) })
	@PostMapping("/redeem/{idCardNumber}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PurchaseGetDTO redeemLoyalityPoints(@RequestBody PurchasePostDTO purchasePostDTO,
			@RequestParam(value = "idCardNumber") String idCardNumber) throws InvalidCashierIdException {
		log.info("Request intercepted for new purchase with points redemption for idCardNumber : {}", idCardNumber);
		Purchase purchaseUpdated = loyalityService
				.redeemLoyalityPoints(mapper.purchasePostDTOToPurchase(purchasePostDTO), idCardNumber);
		return mapper.purchaseToPurchaseGetDTO(purchaseUpdated);
	}

}
