package com.supermarket.loyalityprogram.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.model.Purchase;
import com.supermarket.loyalityprogram.model.RedeemMethods;
import com.supermarket.loyalityprogram.repository.AccountRepository;
import com.supermarket.loyalityprogram.repository.PurchaseRepository;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class LoyalityServiceTest {

	@MockBean
	AccountRepository accountRepository;

	@InjectMocks
	AccountService accountService;

	@InjectMocks
	LoyalityService loyalityService;

	@MockBean
	PurchaseRepository purchaseRepository;

	SoftAssertions softAssertions;

	@Test
	void whenNotRedeemed_purchaseLessThan5() {
		softAssertions = new SoftAssertions();
		Optional<Account> account = Optional.ofNullable(new Account(Long.getLong("1"), "Akshay", "Sharma", "980000111",
				"5W697MDEXS", new BigDecimal(0), null, null, null));
		Purchase purchase = new Purchase(Long.getLong("1"), new BigDecimal(2), false, null, null, null, "ln12345", null,
				account.get());
		Mockito.when(purchaseRepository.save(purchase)).thenReturn(purchase);
		Mockito.when(accountRepository.findByIdCardNumber("5W697MDEXS")).thenReturn(account);
		softAssertions.assertThat(purchase.getPointsToRedeem()).isEqualTo(new BigDecimal(0));
		assertThat(loyalityService.redeemLoyalityPoints(purchase, "5W697MDEXS")).isEqualTo(purchase);
	}

	@Test
	void whenNotRedeemed_purchaseGreaterThan5() {
		softAssertions = new SoftAssertions();
		Optional<Account> account = Optional.ofNullable(new Account(Long.getLong("1"), "Akshay", "Sharma", "980000111",
				"5W697MDEXS", new BigDecimal(0), null, null, null));
		Purchase purchase = new Purchase(Long.getLong("1"), new BigDecimal(1000), false, null, null, null, "ln12345",
				null, account.get());
		Mockito.when(purchaseRepository.save(purchase)).thenReturn(purchase);
		Mockito.when(accountRepository.findByIdCardNumber("5W697MDEXS")).thenReturn(account);
		softAssertions.assertThat(purchase.getPointsToRedeem()).isEqualTo(new BigDecimal(2000));
		assertThat(loyalityService.redeemLoyalityPoints(purchase, "5W697MDEXS")).isEqualTo(purchase);
	}

	@Test
	void whenRedeemedWithDiscount() {
		softAssertions = new SoftAssertions();
		Optional<Account> account = Optional.ofNullable(new Account(Long.getLong("1"), "Akshay", "Sharma", "980000111",
				"5W697MDEXS", new BigDecimal(1000), null, null, null));
		Purchase purchase = new Purchase(Long.getLong("1"), new BigDecimal(1000), true, RedeemMethods.DISCOUNT,
				new BigDecimal(500), null, "ln12345", null, account.get());
		Mockito.when(purchaseRepository.save(purchase)).thenReturn(purchase);
		Mockito.when(accountRepository.findByIdCardNumber("5W697MDEXS")).thenReturn(account);
		softAssertions.assertThat(purchase.getPointsToRedeem()).isEqualTo(new BigDecimal(2000));
		assertThat(loyalityService.redeemLoyalityPoints(purchase, "5W697MDEXS")).isEqualTo(purchase);
	}
}
