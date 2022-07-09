package com.supermarket.loyalityprogram.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class AccountPostDTO {
	
	@NotNull
	@JsonProperty("name")
	String name;
	
	@NotNull
	@JsonProperty("surname")
	String surname;
	
	@NotNull
	@JsonProperty("mobileNumber")
	String mobileNumber;
	
}
