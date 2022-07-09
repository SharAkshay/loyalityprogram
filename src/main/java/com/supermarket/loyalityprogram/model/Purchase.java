package com.supermarket.loyalityprogram.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "purchase")
public class Purchase {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private BigDecimal purchaseAmount;
	
	@Column
	private Boolean redeemLoyality;
	
	@Column
	private RedeemMethods redeemMethod;
	
	@Column
	private BigDecimal pointsToRedeem;
	
	@Column
	private BigDecimal purchaseDiscount;
	
	@Column
	private String cashierId;
	
	@OneToMany(mappedBy = "purchase",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PurchaseItem> purchaseItems;
	
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
}
