package com.am.bt.restchartdataprovider;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.lang.Math;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;

@Path("chartData")
public class GraphDataResource {

	@GET
	@Path("/getLatePayments")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGraphData(@QueryParam("from") String fromDate,
			@QueryParam("to") String toDate) {
		// "Clicks:" 1 + (int)(Math.random() * ((20 - 1) + 1));

		Date todate = null;
		Date frodate = null;
		try {
			todate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
			.parse(fromDate);
			frodate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
			.parse(toDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Chart chart = new Chart();
		chart.setName("Late Payments Chart");
		DataCIC data = new DataCIC();

		DateTime dateTime = new DateTime(frodate.getTime());

		DateTime dateTime1 = new DateTime(todate.getTime());

		List<Date> dates = new ArrayList<Date>();
		int days = Days.daysBetween(dateTime, dateTime1).getDays();

		for (int i = 0; i > days; i--) {
			DateTime d = dateTime.withFieldAdded(DurationFieldType.days(), i);
			dates.add(d.toDate());
		}

		ArrayList<Item> items = new ArrayList<Item>();

		int j = 0;
		for (Date date : dates) {
			j++;
			ArrayList<Payment> payments = dataAccessor.getPayments();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(sdf.parse(fromDate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.add(Calendar.DATE, j); // number of days to add
			String datte = sdf.format(c.getTime());

			Item i = new Item();
			i.setPayments(payments);
			i.setDate(datte);
			items.add(i);

		}

		data.setItems(items);

		chart.setId("4711");
		chart.setFromDate(fromDate);

		chart.setToDate(toDate);
		Links links = new Links();

		Filter filter = new Filter();
		filter.setTemplate("http://...");
		Icon icon = new Icon();
		icon.setHref("http://...");
		Self self = new Self();
		self.setHref("http://...");

		links.setFilter(filter);
		links.setIcon(icon);
		links.setSelf(self);

		chart.setLinks(links);

		chart.setData(data);

		String json = "myjson";
		try {
			json = new ObjectMapper().writerWithDefaultPrettyPrinter()
					.writeValueAsString(chart);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// String json = ow.writeValueAsString(object);
		return json;
	}

}