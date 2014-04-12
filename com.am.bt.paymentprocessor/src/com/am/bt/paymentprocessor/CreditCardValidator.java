package com.am.bt.paymentprocessor;

public class CreditCardValidator {

	public void doLuhn() {

		if (ccnum.length() == 16) {
			char[] ccArray = ccnum.toCharArray();
			int[] cint = new int[16];
			for (int i = 0; i < 16; i++) {
				if (i % 2 == 1) {
					cint[i] = Integer.parseInt(String.valueOf(ccArray[i])) * 2;
					if (cint[i] > 9)
						cint[i] = 1 + cint[i] % 10;
				} else
					cint[i] = Integer.parseInt(String.valueOf(c[i]));
			}
			int sum = 0;
			for (int i = 0; i < 16; i++) {
				sum += cint[i];
			}
			if (sum % 10 == 0)
				result.isValid(true);
			else
				result.isValid(false);
		} else
			result.isValid(false);

	}

}
