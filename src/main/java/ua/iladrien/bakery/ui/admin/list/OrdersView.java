package ua.iladrien.bakery.ui.admin.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import ua.iladrien.bakery.ui.Theme;
import ua.iladrien.bakery.ui.admin.AdminLayout;
import ua.iladrien.bakery.ui.admin.forms.OrderForm;
import ua.iladrien.bakery.web.api.v0.orders.services.OrderService;
import ua.iladrien.bakery.web.entities.OrderData;

import java.util.Collection;

@Route(value = "orders", layout = AdminLayout.class)
public class OrdersView extends VerticalLayout {

    private final Grid<OrderData> grid = new Grid<>(OrderData.class);
    private final ComboBox<OrderData.OrderStatus> filter = new ComboBox<>("Status");

    private final OrderService orderService;

    public OrdersView(OrderService orderService) {
        this.orderService = orderService;

        filter.setItems(OrderData.OrderStatus.values());
        filter.addValueChangeListener(evt -> updateGrid());

        setSizeFull();

        configureGrid();
        updateGrid();
        add(filter, grid);
    }

    private void configureGrid() {
        grid.setColumns("id", "name", "email", "address", "status", "created");
        grid.addColumn(item -> orderService.getPrice(orderService.findCart(item)))
                .setHeader("Price, " + Theme.EURO)
                .setSortable(true);
        grid.getColumns().forEach(orderDataColumn -> orderDataColumn.setAutoWidth(true));
        grid.getColumnByKey("id").setFlexGrow(0);
        grid.asSingleSelect().addValueChangeListener(evt -> editOrder(evt.getValue()));
    }

    private void editOrder(OrderData value) {
        if (value == null)
            return;

        Dialog dialog = new Dialog();
        dialog.setWidthFull();
        dialog.setMaxWidth("750px");


        OrderForm orderForm = new OrderForm(orderService.findCart(value), value);
        Button close = new Button("Close");
        close.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addClickListener(evt -> dialog.close());
        close.addClassName(Theme.ALIGN_SELF_END);
        orderForm.add(close);

        dialog.add(orderForm);
        dialog.addOpenedChangeListener(evt -> {
            if (!evt.isOpened()) {
                orderService.saveOrder(orderForm.getOrder());
                updateGrid();
            }
        });

        dialog.open();
    }

    private void updateGrid() {
        grid.setItems((Collection<OrderData>) orderService.findOrders(filter.getValue()));
    }
}
