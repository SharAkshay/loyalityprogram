package com.supermarket.loyalityprogram.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.supermarket.loyalityprogram.dto.AccountGetDTO;
import com.supermarket.loyalityprogram.dto.AccountPostDTO;
import com.supermarket.loyalityprogram.dto.MapStructMapper;
import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ResponseBody
@RequestMapping("/resource")
public class AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	MapStructMapper mapper;

	@Operation(summary = "Creation of account for new user.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "New account is created", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "An unexpected error occured", content = {
					@Content(mediaType = "application/json") }) })
	@PostMapping(value = "/account")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AccountGetDTO createAccount(@RequestBody AccountPostDTO accountRequest) {
		log.info("Request intercepted for create account");
		Account account = accountService.createAccount(mapper.accountPostDTOToAccount(accountRequest));
		return mapper.accountToAccountGetDTO(account);
	}

	@Operation(summary = "Get details of an account using mobile number.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Account details successfully retrieved", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Account details not found", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "An unexpected error occured", content = {
					@Content(mediaType = "application/json") }) })
	@GetMapping(path = "/contact/{mobileNumber}")
	@ResponseStatus(code = HttpStatus.OK)
	public AccountGetDTO getAccountDetailsByMobileNumber(@RequestParam(value = "mobileNumber") String mobileNumber) {
		log.info("Request intercepted for get account details by mobileNumber : {}", mobileNumber);
		Account account = accountService.getAccountDetailsByMobileNumber(mobileNumber);
		return mapper.accountToAccountGetDTO(account);
	}

	@Operation(summary = "Get details of an account using unique Id-Card number.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Account details successfully retrieved", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Account details not found", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "An unexpected error occured", content = {
					@Content(mediaType = "application/json") }) })
	@GetMapping(path = "/idCardNumber/{idCardNumber}")
	@ResponseStatus(code = HttpStatus.OK)
	public AccountGetDTO getAccountDetailsByIdNumber(@RequestParam(value = "idCardNumber") String idCardNumber) {
		log.info("Request intercepted for get account details by idCardNumber : {}", idCardNumber);
		Account account = accountService.getAccountDetailsByIdCardNumber(idCardNumber);
		return mapper.accountToAccountGetDTO(account);
	}

	@Operation(summary = "Get available points to redeem using unique Id-Card number.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Account details successfully retrieved", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "Account details not found", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "An unexpected error occured", content = {
					@Content(mediaType = "application/json") }) })
	@GetMapping(path = "/availablePoints/{idCardNumber}")
	@ResponseStatus(code = HttpStatus.OK)
	public BigDecimal getAvailablePoints(@RequestParam String idCardNumber) {
		log.info("Request intercepted for get available points to redeem by idCardNumber : {}", idCardNumber);
		return accountService.getAvailablePoints(idCardNumber);
	}

	@Operation(summary = "Update the account details (Name, Surname, mobile number) using unique Id-Card number.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Account details updated", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "An unexpected error occured", content = {
					@Content(mediaType = "application/json") }) })
	@PostMapping(path = "/account/update/{idCardNumber}")
	@ResponseStatus(code = HttpStatus.OK)
	public AccountGetDTO updateAccountDetails(@RequestBody AccountPostDTO accountRequest,
			@RequestParam String idCardNumber) {
		Account account = accountService.createAccount(mapper.accountPostDTOToAccount(accountRequest));
		return mapper.accountToAccountGetDTO(account);
	}

}
