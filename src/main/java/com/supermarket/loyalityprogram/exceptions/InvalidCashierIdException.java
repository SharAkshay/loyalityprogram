package com.supermarket.loyalityprogram.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvalidCashierIdException extends RuntimeException { 
	
	private String code;
    private String message;
	
    public InvalidCashierIdException(String errorMessage) {
        super(errorMessage);
    }
}
