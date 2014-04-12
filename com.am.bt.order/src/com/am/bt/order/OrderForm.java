package com.am.bt.order;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.orders.ordersApplication;
import com.vaadin.orders.data.Order;
import com.vaadin.orders.data.OrderContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class OrderForm extends Form implements ClickListener {

	private Button save = new Button("Save", (ClickListener) this);
	private Button cancel = new Button("Cancel", (ClickListener) this);
	private Button edit = new Button("Edit", (ClickListener) this);
	private final ComboBox duration = new ComboBox("Duration");

	private ordersApplication app;
	private boolean newContactMode = false;
	private Order newOrder = null;

	public OrderForm(ordersApplication app) {
		this.app = app;

		/*
		 * Enable buffering so that commit() must be called for the form before
		 * input is written to the data. (Form input is not written immediately
		 * through to the underlying object.)
		 */
		setWriteThrough(false);

		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(cancel);
		footer.addComponent(edit);
		footer.setVisible(false);

		setFooter(footer);

		duration.setNewItemsAllowed(true);
		/* We do not want to use null values */
		duration.setNullSelectionAllowed(false);
		/* Add an empty duration used for selecting no duration */
		duration.addItem("");

		/* Populate duration select using the duration in the data container */
		OrderContainer ds = app.getDataSource();
		for (Iterator<Order> it = ds.getItemIds().iterator(); it.hasNext();) {
			String dur = (it.next()).getDuration();
			duration.addItem(dur);
		}

		/*
		 * Field factory for overriding how the component for duration is
		 * created
		 */
		setFormFieldFactory(new DefaultFieldFactory() {
			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				if (propertyId.equals("duration")) {
				duration.setWidth("200px");
					return duration;
				}

				Field field = super.createField(item, propertyId, uiContext);
				if (propertyId.equals("price")) {
					TextField tf = (TextField) field;
					/*
					 * We do not want to display "null" to the user when the
					 * field is empty
					 */
					tf.setNullRepresentation("");

					/* Add a validator for price and make it required */
					tf
							.addValidator(new RegexpValidator("[1-9][0-9]{4}",
									"Price must be a five digit number and cannot start with a zero."));
					tf.setRequired(true);
				} else if (propertyId.equals("email")) {
					/* Add a validator for email and make it required */
					field.addValidator(new EmailValidator(
							"Email must contain '@' and have full domain."));
					field.setRequired(true);

				}

				field.setWidth("200px");
				return field;
			}
		});
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();

		if (source == save) {
			/* If the given input is not valid there is no point in continuing */
			if (!isValid()) {
				return;
			}

			commit();
			if (newContactMode) {
				/* We need to add the new person to the container */
				Item addedItem = app.getDataSource().addItem(newOrder);
				/*
				 * We must update the form to use the Item from our datasource
				 * as we are now in edit mode (no longer in add mode)
				 */
				setItemDataSource(addedItem);

				newContactMode = false;
			}
			setReadOnly(true);
		} else if (source == cancel) {
			if (newContactMode) {
				newContactMode = false;
				/* Clear the form and make it invisible */
				setItemDataSource(null);
			} else {
				discard();
			}
			setReadOnly(true);
		} else if (source == edit) {
			setReadOnly(false);
		}
	}

	@Override
	public void setItemDataSource(Item newDataSource) {
            newContactMode = false;
		if (newDataSource != null) {
			List<Object> orderedProperties = Arrays
					.asList(OrderContainer.NATURAL_COL_ORDER);
			super.setItemDataSource(newDataSource, orderedProperties);

			setReadOnly(true);
			getFooter().setVisible(true);
		} else {
			super.setItemDataSource(null);
			getFooter().setVisible(false);
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		save.setVisible(!readOnly);
		cancel.setVisible(!readOnly);
		edit.setVisible(readOnly);
	}

	public void addContact() {
		// Create a temporary item for the form
		newOrder = new Order();
		setItemDataSource(new BeanItem(newOrder));
                newContactMode = true;
		setReadOnly(false);
	}

}