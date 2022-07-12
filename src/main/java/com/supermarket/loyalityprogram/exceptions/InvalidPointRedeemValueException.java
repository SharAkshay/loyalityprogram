package com.supermarket.loyalityprogram.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidPointRedeemValueException extends RuntimeException { 
	
	private String code;
    private String message;
    
    public InvalidPointRedeemValueException(String errorMessage) {
        super(errorMessage);
    }

}
