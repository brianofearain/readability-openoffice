package com.am.bt.order;

import com.vaadin.orders.OrdersApplication;
import com.vaadin.orders.data.Order;
import com.vaadin.orders.data.OrderContainer;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class OrderList extends Table {
	public OrderList(TrainingOrdersApplication app) {
		setSizeFull();
		setContainerDataSource(app.getDataSource());

		setVisibleColumns(OrderContainer.NATURAL_COL_ORDER);
		setColumnHeaders(OrderContainer.COL_HEADERS_ENGLISH);

		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);

		/*
		 * Make table selectable, react immediatedly to user events, and pass
		 * events to the controller (our main application)
		 */
		setSelectable(true);
		setImmediate(true);
		addListener((ValueChangeListener) app);
		/* We don't want to allow users to de-select a row */
		setNullSelectionAllowed(false);

		// customize email column to have mailto: links using column generator
	/*	addGeneratedColumn("supplier_email", new ColumnGenerator() {
			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				Order o = (Order) itemId;
				Link l = new Link();
				l.setResource(new ExternalResource("mailto:" + o.getSupplierEmail()));
				l.setCaption(o.getSupplierEmail());
				return l;
			}
		});
		
		
		addGeneratedColumn("customer_email", new ColumnGenerator() {
			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				Order o = (Order) itemId;
				Link l = new Link();
				l.setResource(new ExternalResource("mailto:" + o.getCustomerEmail()));
				l.setCaption(o.getCustomerEmail());
				return l;
			}
		}); */
	}

}