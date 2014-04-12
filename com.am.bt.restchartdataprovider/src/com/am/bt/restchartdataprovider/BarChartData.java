package com.am.bt.restchartdataprovider;

import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonTypeName;

@JsonTypeName("data")
public class BarChartData extends HashMap<String, String> implements Data {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<?, ?> data;

	public HashMap<?, ?> getData() {
		return data;
	}

	public void setData(HashMap<?, ?> data) {
		this.data = data;
	}

	public void setCountry1(String string) {
		// TODO Auto-generated method stub

	}

}
