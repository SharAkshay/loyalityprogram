package com.supermarket.loyalityprogram.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.supermarket.loyalityprogram.exceptions.UserNotFoundException;
import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {

	@MockBean
	AccountRepository accountRepository;

	@InjectMocks
	AccountService accountService;

	@Test
	void createAccountTest() {
		Account account = new Account(Long.getLong("1"), "Akshay", "Sharma", "980000111", "5W697MDEXS",
				new BigDecimal(0), null, null, null);
		Mockito.when(accountRepository.save(account)).thenReturn(account);
		assertThat(accountService.createAccount(account)).isEqualTo(account);
	}

	@Test
	void getAccountDetailsByMobileNumberTest() {
		Optional<Account> account = Optional.ofNullable(new Account(Long.getLong("1"), "Akshay", "Sharma", "980000111",
				"5W697MDEXS", new BigDecimal(0), null, null, null));
		Mockito.when(accountRepository.findByMobileNumber("980000111")).thenReturn(account);
		assertThat(accountService.getAccountDetailsByMobileNumber("980000111")).isEqualTo(account.get());
	}
	
	@Test
	void getAccountDetailsByIdCardNumberTest() {
		Optional<Account> account = Optional.ofNullable(new Account(Long.getLong("1"), "Akshay", "Sharma", "980000111",
				"5W697MDEXS", new BigDecimal(0), null, null, null));
		Mockito.when(accountRepository.findByIdCardNumber("5W697MDEXS")).thenReturn(account);
		assertThat(accountService.getAccountDetailsByIdCardNumber("5W697MDEXS")).isEqualTo(account.get());
	}

	@Test
	void getAccountDetailsByMobileNumberTest_Negative() {
		Optional<Account> account = Optional.ofNullable(new Account(Long.getLong("1"), "Akshay", "Sharma", "980000111",
				"5W697MDEXS", new BigDecimal(0), null, null, null));
		Mockito.when(accountRepository.findByMobileNumber("9800001")).thenReturn(account);
		UserNotFoundException thrown = Assertions.assertThrows(UserNotFoundException.class, () -> {
			accountService.getAccountDetailsByMobileNumber("980000111");
		});
		assertTrue(thrown.getCode().equalsIgnoreCase("User with given mobile number not found"));
	}
	
	@Test
	void getAccountDetailsByIdCardNumberTest_Negative() {
		Optional<Account> account = Optional.ofNullable(new Account(Long.getLong("1"), "Akshay", "Sharma", "980000111",
				"5W697MDEXS", new BigDecimal(0), null, null, null));
		Mockito.when(accountRepository.findByIdCardNumber("5W697MDEX")).thenReturn(account);
		UserNotFoundException thrown = Assertions.assertThrows(UserNotFoundException.class, () -> {
			accountService.getAccountDetailsByIdCardNumber("5W697MDEXS");
		});
		assertTrue(thrown.getCode().equalsIgnoreCase("User with given ID card number not found"));
	}
}
