package com.am.bt.order;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable {
	private String date = "";
	private String supplier = "";
	private String customer = "";
	private String customerEmail = "";
	private String supplierEmail = "";
	private String topic = "";
	private String comments = "";
	private String price = "";
	private String duration = "";


	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the supplier
	 */
	public String getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	/**
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * @param topic
	 *            the topic to set
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

}