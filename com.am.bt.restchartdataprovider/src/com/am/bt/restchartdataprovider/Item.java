package com.am.bt.restchartdataprovider;

import java.util.ArrayList;
import java.util.Date;

public class Item {

	private String date;
	private ArrayList<Campaign> campaigns;

	public ArrayList<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(ArrayList<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String fromDate) {
		this.date = fromDate;
	}
}
