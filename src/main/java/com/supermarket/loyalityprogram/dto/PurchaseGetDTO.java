package com.supermarket.loyalityprogram.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supermarket.loyalityprogram.model.PurchaseItem;
import com.supermarket.loyalityprogram.model.RedeemMethods;

public class PurchaseGetDTO {

	@JsonProperty
	private BigDecimal purchaseAmount;
	
	@JsonProperty
	private Boolean redeemLoyality;
	
	@JsonProperty
	private RedeemMethods redeemMethod;
	
	@JsonProperty
	private BigDecimal pointsToRedeem;
	
	@JsonProperty
	private BigDecimal purchaseDiscount;
	
	@JsonProperty
	private String cashierId;
	
	@JsonProperty
	private List<PurchaseItem> purchaseItems;
}
