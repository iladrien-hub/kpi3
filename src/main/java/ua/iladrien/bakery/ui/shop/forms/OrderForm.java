package ua.iladrien.bakery.ui.shop.forms;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import ua.iladrien.bakery.web.entities.OrderData;

public class OrderForm extends FormLayout {

    private final OrderData order;

    // [Start]: Fields to bind
    private final TextField name = new TextField("Name");
    private final EmailField email = new EmailField("Email");
    private final TextField address = new TextField("Address");
    // [End]: Fields to bind

    protected final Binder<OrderData> binder = new BeanValidationBinder<>(OrderData.class);

    public OrderForm(OrderData order) {
        this.order = order;
        setSizeFull();

        binder.bindInstanceFields(this);
        binder.setBean(order);

        add(name, email, address);
    }

    public OrderData getOrder() {
        return order;
    }

    public Binder<OrderData> getBinder() {
        return binder;
    }
}
