package com.am.bt.paymentprocessor;


import java.util.HashMap;
import java.util.Map;

import com.stripe.model.Charge;
import com.stripe.model.Coupon;

public class StripeProcessor implements IPayProcessor {

	public void createCharge() {
		Map<String, Object> stripeChargeParams = new HashMap<String, Object>();
		stripeChargeParams.put("amount", 400);
		stripeChargeParams.put("currency", "usd");
		stripeChargeParams.put("card", "tok_103mC22eZvKYlo2CZpkGAIA8");
		stripeChargeParams.put("description", "Charge for ustomer Y");
		stripeChargeParams.put("metadata", initialMetadata);
		Charge.create(stripeChargeParams);
	}

	// retrieve a charge:

	public void retrieveCharge() {
		Stripe.apiKey = "testKeyValue12";
		Charge.retrieve("chargeValue121212");
	}

	public void refundCharge() {
		Stripe.apiKey = "testKeyValue121";
		ch = Charge.retrieve("chargeValue121212");
		ch.refund();
	}

	public void doTransfer() {
		Map<String, Object> stripeTransferParams = new HashMap<String, Object>();
		stripeTransferParams.put("amount", 400);
		stripeTransferParams.put("currency", "usd");
		stripeTransferParams.put("recipient", "2134123413423");
		stripeTransferParams.put("description", "Transfer for Customer X");

		Transfer.create(stripeTransferParams);
	}

	public void createCoupon() {

		Map<String, Object> stripeCouponParams = new HashMap<String, Object>();
		stripeCouponParams.put("percent_off", 30);
		stripeCouponParams.put("duration", "repeating");
		stripeCouponParams.put("duration_in_months", 2);
		stripeCouponParams.put("id", "25OFF");

		Coupon.create(stripeCouponParams);

	}

}
