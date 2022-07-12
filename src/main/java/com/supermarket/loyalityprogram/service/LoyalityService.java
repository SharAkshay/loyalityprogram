package com.supermarket.loyalityprogram.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermarket.loyalityprogram.constants.ApplicationConstants;
import com.supermarket.loyalityprogram.exceptions.InvalidCashierIdException;
import com.supermarket.loyalityprogram.exceptions.UserNotFoundException;
import com.supermarket.loyalityprogram.model.Account;
import com.supermarket.loyalityprogram.model.Cashier;
import com.supermarket.loyalityprogram.model.Items;
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

	public Purchase redeemLoyalityPoints(Purchase purchase, String idCardNumber) throws InvalidCashierIdException {
		if (!validateTransaction(purchase.getCashierId()))
			throw new InvalidCashierIdException("Cashier Id not validated", ApplicationConstants.CASHIER_NOT_FOUND);
		purchase.setPurchaseItems(getPurchaseItems());
		purchase.setPurchaseAmount(getPurchaseAmount(getPurchaseItems()));
		Account account = accountRepository.findByIdCardNumber(idCardNumber)
				.orElseThrow(() -> new UserNotFoundException("User with given ID card number not found", ApplicationConstants.USER_NOT_FOUND));
		if (!purchase.getRedeemLoyality()) {
			purchase.setPurchaseDiscount(new BigDecimal(0));
			if (purchase.getPurchaseAmount().compareTo(ApplicationConstants.MINIMUM_PURCHASE_VALUE) == -1) {
				log.info("purchase amount is less than minimum to add points");
				purchase.setAccount(account);
				purchaseRepository.save(purchase);
				return purchase;
			} else {
				log.info("updating loyality points");
				BigDecimal currentLoyalityPoints = account.getLoyalityPoints();
				log.info("currentLoyalityPoints-------------> : {}", currentLoyalityPoints);
				BigDecimal pointsToBeUpdated = purchase.getPurchaseAmount()
						.divide(ApplicationConstants.MINIMUM_PURCHASE_VALUE)
						.multiply(ApplicationConstants.POINTS_ADDITION_VALUE);
				log.info("pointsToBeUpdated-------------> : {}", pointsToBeUpdated);
				account.setLoyalityPoints(currentLoyalityPoints.add(pointsToBeUpdated));
			}
		} else {
			log.info("purchase for redeem type {} and purchase amount {} with available loyality points {}",
					purchase.getRedeemMethod(), purchase.getPurchaseAmount(), account.getLoyalityPoints());
			BigDecimal pointsToRedeem = purchase.getPointsToRedeem();
			if (account.getLoyalityPoints().compareTo(ApplicationConstants.BUCKS_REDEEM_VALUE) == -1) {
				log.info("Available loyality points less than 100");
				purchase.setAccount(account);
				accountRepository.save(account);
				purchaseRepository.save(purchase);
				return purchase;
			} else if (purchase.getRedeemMethod().equals(RedeemMethods.FREE_BOTTLE)
					&& account.getLoyalityPoints().compareTo(ApplicationConstants.BOTTLE_REDEEM_VALUE) == -1) {
				log.info("Available loyality points less than 150 with redeem type as FREE_BOTTLE");
				purchase.setAccount(account);
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
						if (item.getItemName().equalsIgnoreCase(ApplicationConstants.WATER_BOTTLE_VALUE)) {
							BigDecimal quantityOfDiscountedBottles = pointsToRedeem
									.divide(ApplicationConstants.BOTTLE_REDEEM_VALUE);
							BigDecimal totalDiscount = item.getItemcost().multiply(quantityOfDiscountedBottles);
							purchase.setPurchaseDiscount(totalDiscount);
							purchase.setPurchaseAmount(purchase.getPurchaseAmount().subtract(totalDiscount));
						}
						return item;
					}).collect(Collectors.toList());
					purchase.setPurchaseItems(purchaseItemsFiltered);
					account.setLoyalityPoints(availableLoyalityPoints.subtract(pointsToRedeem));
					BigDecimal pointsToBeUpdated = purchase.getPurchaseAmount()
							.divide(ApplicationConstants.MINIMUM_PURCHASE_VALUE);
					log.info("pointsToBeUpdated after discount-------------> : {}", pointsToBeUpdated);
					account.setLoyalityPoints(account.getLoyalityPoints().add(pointsToBeUpdated));
				}
				case DISCOUNT -> {
					log.info("Enter processing for redeem type as DISCOUNT");
					BigDecimal totalDiscount = pointsToRedeem.divide(ApplicationConstants.BUCKS_REDEEM_VALUE);
					purchase.setPurchaseAmount(purchase.getPurchaseAmount().subtract(totalDiscount));
					account.setLoyalityPoints(availableLoyalityPoints.subtract(pointsToRedeem));
					BigDecimal pointsToBeUpdated = purchase.getPurchaseAmount()
							.divide(ApplicationConstants.MINIMUM_PURCHASE_VALUE);
					log.info("pointsToBeUpdated after discount-------------> : {}", pointsToBeUpdated);
					account.setLoyalityPoints(account.getLoyalityPoints().add(pointsToBeUpdated));
					purchase.setPurchaseDiscount(totalDiscount);
				}
				}
			}
		}
		purchase.setAccount(account);
		purchaseRepository.save(purchase);
		accountRepository.save(account);
		return purchase;
	}

	private boolean validateTransaction(String cashierId) {
		return getCashierList().stream().anyMatch(cashier -> cashierId.equalsIgnoreCase(cashier.cashierId()));
	}

	// Below methods are hard coded but ideally all these values should come from
	// the system

	private List<Items> getItemList() {

		List<Items> listOfItems = new ArrayList<Items>();
		listOfItems.add(new Items(ApplicationConstants.WATER_BOTTLE_VALUE, new BigDecimal(20)));
		listOfItems.add(new Items("Hobz", new BigDecimal(4)));
		listOfItems.add(new Items("Patata", new BigDecimal(4)));
		listOfItems.add(new Items("Basla", new BigDecimal(6)));
		listOfItems.add(new Items("Halib", new BigDecimal(3)));
		listOfItems.add(new Items("Affarijiet", new BigDecimal(11)));

		return listOfItems;
	}

	private List<Cashier> getCashierList() {
		List<Cashier> cashierList = new ArrayList<Cashier>();
		cashierList.add(new Cashier("nd12345", "Novak"));
		cashierList.add(new Cashier("ln12345", "Lionel"));
		cashierList.add(new Cashier("tw12345", "Tiger"));
		cashierList.add(new Cashier("lh12345", "Lewis"));
		cashierList.add(new Cashier("ub12345", "Usain"));
		return cashierList;
	}

	private List<PurchaseItem> getPurchaseItems() {
		List<PurchaseItem> purchaseList = new ArrayList<PurchaseItem>();
		purchaseList.add(new PurchaseItem(ApplicationConstants.WATER_BOTTLE_VALUE, new BigDecimal(20), Integer.valueOf(50), new BigDecimal(1000)));
		purchaseList.add(new PurchaseItem("Hobz", new BigDecimal(4), Integer.valueOf(5), new BigDecimal(20)));
		purchaseList.add(new PurchaseItem("Patata", new BigDecimal(4), Integer.valueOf(5), new BigDecimal(20)));
		return purchaseList;

	}

	private BigDecimal getPurchaseAmount(List<PurchaseItem> purchaseItems) {

		return new BigDecimal(purchaseItems.stream().map(purchaseItem -> purchaseItem.getTotalCost().intValue())
				.reduce(0, Integer::sum));
	}
}
