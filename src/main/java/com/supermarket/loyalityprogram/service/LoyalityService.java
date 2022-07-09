package com.supermarket.loyalityprogram.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.model.Purchase;
import com.supermarket.loyalityprogram.model.PurchaseItem;
import com.supermarket.loyalityprogram.model.RedeemMethods;
import com.supermarket.loyalityprogram.repository.AccountRepository;

@Service
public class LoyalityService {

	@Autowired
	AccountRepository accountRepository;

	public Account addLoyalityPoints(Purchase purchase, String idCardNumber) {
		Account account = accountRepository.findByIdCardNumber(idCardNumber).orElseThrow();
		if (purchase.getPurchaseAmount().compareTo(new BigDecimal(5)) == -1) {
			return account;
		} else {
			BigDecimal currentLoyalityPoints = account.getLoyalityPoints();
			BigDecimal pointsToBeUpdated = purchase.getPurchaseAmount().divide(new BigDecimal(5));
			account.setLoyalityPoints(currentLoyalityPoints.add(pointsToBeUpdated));
		}
		return account;
	}

	public Purchase redeemLoyalityPoints(Purchase purchase, String idCardNumber) {
		Account account = accountRepository.findByIdCardNumber(idCardNumber).orElseThrow();
		BigDecimal pointsToRedeem = purchase.getPointsToRedeem();
		if (account.getLoyalityPoints().compareTo(new BigDecimal(100)) == -1) {
			return purchase;
		} else if (purchase.getRedeemMethod().equals(RedeemMethods.FREE_BOTTLE)
				&& account.getLoyalityPoints().compareTo(new BigDecimal(150)) == -1) {
			return purchase;
		} else {
			BigDecimal availableLoyalityPoints = account.getLoyalityPoints();
			switch (purchase.getRedeemMethod()) {
			case FREE_BOTTLE -> {
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
				account.getPurchases().add(purchase);
			}
			case DISCOUNT -> {
				BigDecimal totalDiscount = pointsToRedeem.divide(new BigDecimal(150));
				purchase.setPurchaseAmount(purchase.getPurchaseAmount().subtract(totalDiscount));
				account.setLoyalityPoints(availableLoyalityPoints.subtract(pointsToRedeem));
				purchase.setPurchaseDiscount(totalDiscount);
			}
			}
		}
		purchase.setRedeemLoyality(true);
		accountRepository.save(account);
		return purchase;
	}

}
