package ua.iladrien.bakery.ui.shop;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import ua.iladrien.bakery.ui.Theme;
import ua.iladrien.bakery.ui.shop.cards.CartItemCard;
import ua.iladrien.bakery.ui.shop.forms.OrderForm;
import ua.iladrien.bakery.web.api.v0.orders.services.OrderService;
import ua.iladrien.bakery.web.entities.Cart;
import ua.iladrien.bakery.web.entities.CartItem;
import ua.iladrien.bakery.web.entities.OrderData;


@Route(value = "shop/cart", layout = ShopLayout.class)
@PageTitle("Cart")
public class CartView extends VerticalLayout {

    private final OrderService orderService;
    private final OrderForm orderForm = new OrderForm(new OrderData());

    private Cart cart;
    private final VerticalLayout itemsLayout = new VerticalLayout();;
    private final Button submit = new Button("Submit");

    public CartView(OrderService orderService) {
        this.orderService = orderService;
        setMaxWidth(Theme.CONTAINER_WIDTH, Unit.PIXELS);
        addClassName(Theme.AUTO_MARGIN);

        cart = orderService.getCart(VaadinSession.getCurrent().getPushId());

        H1 h1 = new H1("Cart");

        refreshItemsLayout();

        orderForm.add(initSubmit());

        HorizontalLayout horizontalLayout = new HorizontalLayout(itemsLayout, orderForm);
        horizontalLayout.setSizeFull();

        updateSubmit();

        add(h1, horizontalLayout);
    }

    private Component initSubmit() {
        VerticalLayout div = new VerticalLayout();
        div.setWidthFull();
        orderForm.getBinder().addValueChangeListener(evt -> updateSubmit());
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submit.addClassName(Theme.ALIGN_SELF_END);
        submit.addClickListener(evt -> onSubmit());
        div.add(submit);
        return div;
    }

    private void onSubmit() {
        orderService.saveOrder(orderForm.getBinder().getBean(), cart);
        Dialog dialog = new Dialog();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        Button button = new Button("Ok");
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        button.addClassName(Theme.ALIGN_SELF_END);

        button.addClickListener(evt -> dialog.close());
        dialog.addOpenedChangeListener(evt -> {
            if (!evt.isOpened()) {
                UI.getCurrent().navigate("shop");
                dialog.close();
            }
        });

        verticalLayout.add(
                new Label("Order has been sent for processing"),
                button
        );
        dialog.add(verticalLayout);
        dialog.open();
    }

    private void refreshItemsLayout() {
        itemsLayout.removeAll();
        cart.getItems().forEach(item -> itemsLayout.add(new CartItemCard(item, this::onDelete)));
        H3 h3 = new H3(orderService.getPrice(cart) + Theme.EURO);
        h3.addClassName(Theme.ALIGN_SELF_END);
        itemsLayout.add(h3);
    }

    private void onDelete(CartItem cartItem) {
        cart = orderService.deleteCartItem(cart, cartItem);
        refreshItemsLayout();
        updateSubmit();
    }

    private void updateSubmit() {
        submit.setEnabled(cart.getItems().size() > 0 && orderForm.getBinder().isValid());
    }
}
