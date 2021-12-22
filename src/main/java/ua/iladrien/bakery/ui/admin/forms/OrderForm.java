package ua.iladrien.bakery.ui.admin.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import ua.iladrien.bakery.web.entities.Cart;
import ua.iladrien.bakery.web.entities.CartItem;
import ua.iladrien.bakery.web.entities.OrderData;

public class OrderForm extends VerticalLayout {

    private final Cart cart;
    private final OrderData order;

    private final ComboBox<OrderData.OrderStatus> status = new ComboBox<>("Status");
    private final Binder<OrderData> binder = new BeanValidationBinder<>(OrderData.class);

    private final Grid<CartItem> grid = new Grid<>(CartItem.class);

    public OrderForm(Cart cart, OrderData order) {
        this.cart = cart;
        this.order = order;

        setSizeFull();

        status.setItems(OrderData.OrderStatus.values());

        binder.bindInstanceFields(this);
        binder.setBean(order);

        Div div = new Div();
        div.addClassName("spacer");
        HorizontalLayout header = new HorizontalLayout(new H4("Order #" + order.getId()), div, status);
        header.setAlignItems(Alignment.END);
        header.setWidthFull();

        grid.setItems(cart.getItems());
        grid.setColumns("id");
        grid.addColumn(item -> item.getProduct().getProduct().getName()).setHeader("Product").setSortable(true);
        grid.addColumn(item -> item.getProduct().getTitle()).setHeader("Option").setSortable(true);
        grid.addColumn(CartItem::getQuantity).setHeader("Quantity").setSortable(true);

        grid.getColumns().forEach(cartItemColumn -> cartItemColumn.setAutoWidth(true));
        grid.getColumnByKey("id").setFlexGrow(0);

        add(header, grid);
    }

    public OrderData getOrder() {
        return order;
    }
}
