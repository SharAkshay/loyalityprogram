package com.supermarket.loyalityprogram.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.model.Purchase;
import com.supermarket.loyalityprogram.model.PurchaseItem;
import com.supermarket.loyalityprogram.model.RedeemMethods;
import com.supermarket.loyalityprogram.repository.AccountRepository;
import com.supermarket.loyalityprogram.repository.PurchaseRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoyalityService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PurchaseRepository purchaseRepository;

	public Account addLoyalityPoints(Purchase purchase, String idCardNumber) {
		Account account = accountRepository.findByIdCardNumber(idCardNumber).orElseThrow();
		if (purchase.getPurchaseAmount().compareTo(new BigDecimal(5)) == -1) {
			log.info("purchase amount is less than minimum to add points");
			purchaseRepository.save(purchase);
			return account;
		} else {
			log.info("updating loyality points");
			BigDecimal currentLoyalityPoints = account.getLoyalityPoints();
			log.info("currentLoyalityPoints-------------> : {}", currentLoyalityPoints);
			BigDecimal pointsToBeUpdated = purchase.getPurchaseAmount().divide(new BigDecimal(5));
			log.info("pointsToBeUpdated-------------> : {}", pointsToBeUpdated);
			account.setLoyalityPoints(currentLoyalityPoints.add(pointsToBeUpdated));
		}
		account.getPurchases().add(purchase);
		account.setUpdatetime(LocalDateTime.now());
		purchaseRepository.save(purchase);
		accountRepository.save(account);
		return account;
	}

	public Purchase redeemLoyalityPoints(Purchase purchase, String idCardNumber) {
		Account account = accountRepository.findByIdCardNumber(idCardNumber).orElseThrow();
		if (!purchase.getRedeemLoyality()) {
			purchase.setPurchaseDiscount(new BigDecimal(0));
			if (purchase.getPurchaseAmount().compareTo(new BigDecimal(5)) == -1) {
				log.info("purchase amount is less than minimum to add points");
				purchaseRepository.save(purchase);
				return purchase;
			} else {
				log.info("updating loyality points");
				BigDecimal currentLoyalityPoints = account.getLoyalityPoints();
				log.info("currentLoyalityPoints-------------> : {}", currentLoyalityPoints);
				BigDecimal pointsToBeUpdated = purchase.getPurchaseAmount().divide(new BigDecimal(5));
				log.info("pointsToBeUpdated-------------> : {}", pointsToBeUpdated);
				account.setLoyalityPoints(currentLoyalityPoints.add(pointsToBeUpdated));
			}
		} else {
			log.info("purchase for redeem type {} and purchase amount {} with available loyality points {}",
					purchase.getRedeemMethod(), purchase.getPurchaseAmount(), account.getLoyalityPoints());
			BigDecimal pointsToRedeem = purchase.getPointsToRedeem();
			if (account.getLoyalityPoints().compareTo(new BigDecimal(100)) == -1) {
				log.info("Available loyality points less than 100");
				account.getPurchases().add(purchase);
				accountRepository.save(account);
				purchaseRepository.save(purchase);
				return purchase;
			} else if (purchase.getRedeemMethod().equals(RedeemMethods.FREE_BOTTLE)
					&& account.getLoyalityPoints().compareTo(new BigDecimal(150)) == -1) {
				log.info("Available loyality points less than 150 with redeem type as FREE_BOTTLE");
				account.getPurchases().add(purchase);
				accountRepository.save(account);
				purchaseRepository.save(purchase);
				return purchase;
			} else {
				BigDecimal availableLoyalityPoints = account.getLoyalityPoints();
				switch (purchase.getRedeemMethod()) {
				case FREE_BOTTLE -> {
					log.info("Enter processing for redeem type as FREE_BOTTLE");
					List<PurchaseItem> purchaseItems = purchase.getPurchaseItems();
					List<PurchaseItem> purchaseItemsFiltered = purchaseItems.stream().map((item) -> {
						if (item.getItemName().equalsIgnoreCase("Water bottle")) {
							BigDecimal quantityOfDiscountedBottles = pointsToRedeem.divide(new BigDecimal(150));
							BigDecimal totalDiscount = item.getItemcost().multiply(quantityOfDiscountedBottles);
							purchase.setPurchaseDiscount(totalDiscount);
							purchase.setPurchaseAmount(purchase.getPurchaseAmount().subtract(totalDiscount));
						}
						return item;
					}).collect(Collectors.toList());
					purchase.setPurchaseItems(purchaseItemsFiltered);
					account.setLoyalityPoints(availableLoyalityPoints.subtract(pointsToRedeem));
					BigDecimal pointsToBeUpdated = purchase.getPurchaseAmount().divide(new BigDecimal(5));
					log.info("pointsToBeUpdated after discount-------------> : {}", pointsToBeUpdated);
					account.setLoyalityPoints(account.getLoyalityPoints().add(pointsToBeUpdated));
					account.getPurchases().add(purchase);
				}
				case DISCOUNT -> {
					log.info("Enter processing for redeem type as DISCOUNT");
					BigDecimal totalDiscount = pointsToRedeem.divide(new BigDecimal(100));
					purchase.setPurchaseAmount(purchase.getPurchaseAmount().subtract(totalDiscount));
					account.setLoyalityPoints(availableLoyalityPoints.subtract(pointsToRedeem));
					BigDecimal pointsToBeUpdated = purchase.getPurchaseAmount().divide(new BigDecimal(5));
					log.info("pointsToBeUpdated after discount-------------> : {}", pointsToBeUpdated);
					account.setLoyalityPoints(account.getLoyalityPoints().add(pointsToBeUpdated));
					purchase.setPurchaseDiscount(totalDiscount);
				}
				}
			}
		}
		purchaseRepository.save(purchase);
		accountRepository.save(account);
		return purchase;
	}

}
