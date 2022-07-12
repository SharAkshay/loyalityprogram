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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String surname;
	
	@Column
	private String mobileNumber;
	
	@Column
	private String idCardNumber;
	
	@Column
	private BigDecimal loyalityPoints;
	
	@Column
	private LocalDateTime createTime;
	
	@Column
	private LocalDateTime updatetime;
	
	@OneToMany(mappedBy = "account",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Purchase> purchases;
}
