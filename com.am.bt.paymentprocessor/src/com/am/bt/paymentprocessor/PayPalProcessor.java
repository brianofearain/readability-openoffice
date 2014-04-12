package com.am.bt.paymentprocessor;


import paypal.payflow.Address;
import paypal.payflow.CreditCard;

public class PayPalProcessor implements IPayProcessor {

	public void createPayment() {

		String accessToken = new OAuthTokenCredential("<CLIENT_ID>",
				"<CLIENT_SECRET>").getAccessToken();

		Address billingAddress = new Address();
		billingAddress.setLine1("52 FairwaysLawn");
		billingAddress.setCity("bettystown");
		billingAddress.setCountryCode("IE");

		CreditCard creditCard = new CreditCard();
		creditCard.setNumber("5431541343243");
		creditCard.setType("mastercard");
		creditCard.setExpireMonth("11");
		creditCard.setExpireYear("2017");
		creditCard.setCvv2("844");
		creditCard.setFirstName("Cu");
		creditCard.setLastName("Chulainn");
		creditCard.setBillingAddress(billingAddress);

		AmountDetails amountDetails = new AmountDetails();
		amountDetails.setSubtotal("4.51");
		amountDetails.setTax("0.1");
		amountDetails.setShipping("0.2");

		Amount amount = new Amount();
		amountDetails.setSubtotal("4.51");
		amount.setCurrency("EUR");
		amount.setDetails(amountDetails);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("Transaction description.");

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);

		List<FundingInstrument> fundingInstruments = new ArrayList<FundingInstrument>();
		fundingInstruments.add(fundingInstrument);

		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstruments);
		payer.setPaymentMethod("credit_card");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		Payment createdPayment = payment.create(accessToken);
	}

	public void getSaleDetails() {

		String accessToken = new OAuthTokenCredential("<CLIENT_ID>",
				"<CLIENT_SECRET>").getAccessToken();

		Sale sale = Sale.get(accessToken, "54315M123141343243");

	}

	public void refundPayment() {

		String accessToken = new OAuthTokenCredential("<CLIENT_ID>",
				"<CLIENT_SECRET>").getAccessToken();

		Sale sale = Sale.get(accessToken, "54315M123141343243");

		Amount amount = new Amount();
		amount.setTotal("2.34");
		amount.setCurrency("USD");

		Refund refund = new Refund();
		refund.setAmount(amount);

		Refund newRefund = sale.refund(accessToken, refund);

	}

	public void doAuthorisation(){
		String accessToken = new OAuthTokenCredential("<CLIENT_ID>", "<CLIENT_SECRET>").getAccessToken();

		Authorization authorization = Authorization.get(accessToken, "5RA4



	}

	public void captureAuthorisation() {
		String accessToken = new OAuthTokenCredential("<CLIENT_ID>",
				"<CLIENT_SECRET>").getAccessToken();

		Authorization authorization = Authorization.get(accessToken,
				"54315M123141343243");

		Amount captureAmount = new Amount();
		captureAmount.setCurrency("USD");
		captureAmount.setTotal("1");

		Capture capture = new Capture();
		capture.setAmount(captureAmount);
		capture.setIsFinalCapture(true);

		Capture responseCapture = authorization.capture(accessToken, capture);

	}

	public void cancelAuthorisation() {

		String accessToken = new OAuthTokenCredential("<CLIENT_ID>",
				"<CLIENT_SECRET>").getAccessToken();

		Authorization authorization = Authorization.get(accessToken,
				"54315M123141343243");

		Authorization responseAuthorization = authorization.doVoid(accessToken);

	}

	public void reAuthorise(){

		String accessToken = new OAuthTokenCredential("<CLIENT_ID>", "<CLIENT_SECRET>").getAccessToken();

		Authorization authorization = Authorization.get(accessToken, "54315M123141343243");

		Amount reauthorizeAmount = new Amount();
		reauthorizeAmount.setCurrency("USD");
		reauthorizeAmount.setTotal("7.00");
		authorization.setAmount(reauthorizeAmount);

		Authorization reauthorization = authorization.reauthorize(accessTok

	}

	public void capture(){

		String accessToken = new OAuthTokenCredential("<CLIENT_ID>", "<CLIENT_SECRET>").getAccessToken();


		Capture capture = Capture.get(accessToken, "8F148933LY9388354")

	}

}
