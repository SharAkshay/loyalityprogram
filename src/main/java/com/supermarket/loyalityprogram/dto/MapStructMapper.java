package com.supermarket.loyalityprogram.dto;

import org.mapstruct.Mapper;

import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.model.Purchase;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
	AccountGetDTO accountToAccountGetDTO(Account account);
	Account accountPostDTOToAccount(AccountPostDTO accountPostDTO);
	PurchaseGetDTO purchaseToPurchaseGetDTO(Purchase purchase);
}
