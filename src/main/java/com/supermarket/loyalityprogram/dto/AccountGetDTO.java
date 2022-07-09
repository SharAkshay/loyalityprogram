package com.supermarket.loyalityprogram.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class AccountGetDTO {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("surname")
	private String surname;
	
	@JsonProperty("mobileNumber")
	private String mobileNumber;
	
	@JsonProperty("idCardNumber")
	private String idCardNumber;
	
	@JsonProperty("createTime")
	private LocalDateTime createTime;
	
	@JsonProperty("updatetime")
	private LocalDateTime updatetime;
}
