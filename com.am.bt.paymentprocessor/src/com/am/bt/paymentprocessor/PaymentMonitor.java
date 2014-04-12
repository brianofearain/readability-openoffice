package com.am.bt.paymentprocessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.MarkerStates;
import com.vaadin.addon.charts.model.PlotOptionsArea;
import com.vaadin.addon.charts.model.State;
import com.vaadin.addon.charts.model.States;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.ZoomType;
import com.vaadin.addon.charts.model.style.GradientColor;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;

public class PaymentMonitor extends AbstractVaadinChart {

	private static final int DAY_IN_MILLIS = 24 * 3600 * 1000;

	private static final int TWO_WEEKS = 14 * DAY_IN_MILLIS;

	@Override
	public String getDescription() {
		return "Payment Monitor";
	}

	@Override
	protected Component getChart() {
		Chart chart = new Chart();
		chart.setHeight("450px");
		chart.setWidth("100%");

		Configuration configuration = new Configuration();
		configuration.getChart().setZoomType(ZoomType.X);
		configuration.getChart().setSpacingRight(20);

		configuration.getTitle().setText("Here is my title");

		String title = Page.getCurrent().getWebBrowser().isTouchDevice() ? "Drag your finger over the plot to zoom in"
				: "Click and drag in the plot area to zoom in";
		configuration.getSubTitle().setText(title);

		configuration.getxAxis().setType(AxisType.DATETIME);
		configuration.getxAxis().setMinRange(TWO_WEEKS);
		configuration.getxAxis().setTitle(new Title(""));

		configuration.getLegend().setEnabled(false);

		Axis yAxis = configuration.getyAxis();
		yAxis.setTitle(new Title("Exchange rate"));
		yAxis.setMin(0.6);
		yAxis.setStartOnTick(false);
		yAxis.setShowFirstLabel(false);

		configuration.getTooltip().setShared(true);

		PlotOptionsArea plotOptions = new PlotOptionsArea();

		GradientColor fillColor = GradientColor.createLinear(0, 0, 0, 1);
		fillColor.addColorStop(0, new SolidColor("#4572A7"));
		fillColor.addColorStop(1, new SolidColor(2, 0, 0, 0));
		plotOptions.setFillColor(fillColor);

		plotOptions.setLineWidth(1);
		plotOptions.setShadow(false);

		Marker marker = new Marker();
		marker.setEnabled(false);
		State hoverState = new State(true);
		hoverState.setRadius(5);
		MarkerStates states = new MarkerStates(hoverState);
		marker.setStates(states);

		State hoverStateForArea = new State(true);
		hoverState.setLineWidth(1);

		plotOptions.setStates(new States(hoverStateForArea));
		plotOptions.setMarker(marker);
		plotOptions.setShadow(false);
		configuration.setPlotOptions(plotOptions);

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		ListSeries ls = new ListSeries();
		PlotOptionsArea options = new PlotOptionsArea();
		options.setPointInterval(DAY_IN_MILLIS);
		ls.setPlotOptions(options);
		ls.setName("Here put whatever");

		try {
			options.setPointStart(df.parse("2006/01/02").getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ls.setData(USD_TO_EUR_EXCHANGE_RATES);
		configuration.setSeries(ls);

		chart.drawChart(configuration);

		return chart;
	}
}