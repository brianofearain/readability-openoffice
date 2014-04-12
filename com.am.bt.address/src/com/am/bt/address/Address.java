package com.am.bt.address;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Embeddable
public class Address implements Serializable {
	private static final long serialVersionUID = 4317859655330969141L;

	private Street strAddress;

	private String city;

	private String county;

	private String country;

	private String dubPostCode;

	@Column(length = 150)
	public String getStrAddress() {
		return address;
	}

	@Column(nullable = false, length = 50)
	public String getCity() {
		return city;
	}

	@Column(length = 100)
	public String getProvince() {
		return county;
	}

	@Column(length = 100)
	public String getCountry() {
		return country;
	}

	@Column(name = "postal_code", nullable = false, length = 8)
	public String getDubPostCode() {
		return dubPostCode;
	}

	public void setStrAddress(Street strAddress) {
		this.strAddress = strAddress;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setPostalCode(String postalCode) {
		this.dubPostCode = postalCode;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * Overridden equals method for object comparison. Compares based on
	 * hashCode.
	 * 
	 * @param o
	 *            Object to compare
	 * @return true/false based on hashCode
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Address)) {
			return false;
		}

		final Address address1 = (Address) o;

		return this.hashCode() == address1.hashCode();
	}

	/**
	 * Overridden hashCode method - compares on address, city, province, country
	 * and postal code.
	 * 
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		int result;
		result = (strAddress != null ? strAddress.hashCode() : 0);
		result = 21 * result + (city != null ? city.hashCode() : 0);
		result = 21 * result + (county != null ? county.hashCode() : 0);
		result = 21 * result + (country != null ? country.hashCode() : 0);
		result = 21 * result
				+ (dubPostCode != null ? dubPostCode.hashCode() : 0);
		return result;
	}

	/**
	 * Returns a multi-line String with key=value pairs.
	 * 
	 * @return a String representation of this class.
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("country", this.country)
		.append("streetAddress", this.strAddress)
		.append("county", this.county)
		.append("Dublin PostCode", this.dubPostCode)
		.append("city", this.city).toString();
	}
}