package com.supermarket.loyalityprogram.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidCashierIdException.class)
	protected ResponseEntity handleInvalidCashierIdException(InvalidCashierIdException invalidCashierIdException) {
		return ResponseEntity.badRequest().body(ErrorResponse.builder().code(invalidCashierIdException.getCode())
				.message(invalidCashierIdException.getMessage()).build());

	}

	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity handleUserNotFoundException(UserNotFoundException userNotFoundException) {
		return ResponseEntity.badRequest().body(ErrorResponse.builder().code(userNotFoundException.getCode())
				.message(userNotFoundException.getMessage()).build());

	}

	@ExceptionHandler(InvalidPointRedeemValueException.class)
	protected ResponseEntity handleAccountAlreadyRegisteredException(
			InvalidPointRedeemValueException invalidPointRedeemValueException) {
		return ResponseEntity.badRequest().body(ErrorResponse.builder().code(invalidPointRedeemValueException.getCode())
				.message(invalidPointRedeemValueException.getMessage()).build());

	}
}
