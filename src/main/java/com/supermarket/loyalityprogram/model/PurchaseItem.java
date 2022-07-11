package com.supermarket.loyalityprogram.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "purchaseitem")
public class PurchaseItem {

	public PurchaseItem(String itemName, BigDecimal itemcost, Integer quantity, BigDecimal totalCost) {
		this.itemName= itemName;
		this.itemcost=itemcost;
		this.quantity=quantity;
		this.totalCost=totalCost;
	}

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String itemName;
	
	@Column
	private BigDecimal itemcost;
	
	@Column
	private Integer quantity;
	
	@Column
	private BigDecimal totalCost;
	
	@ManyToOne
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;
	
}
