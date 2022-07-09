package com.supermarket.loyalityprogram.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supermarket.loyalityprogram.model.PurchaseItem;
import com.supermarket.loyalityprogram.model.RedeemMethods;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PurchasePostDTO {

	@JsonProperty
	private BigDecimal purchaseAmount;
	
	@JsonProperty
	private Boolean redeemLoyality;
	
	@JsonProperty
	private RedeemMethods redeemMethod;
	
	@JsonProperty
	private BigDecimal pointsToRedeem;
	
	@JsonProperty
	private String cashierId;
	
	@JsonProperty
	private List<PurchaseItem> purchaseItems;
	
}
