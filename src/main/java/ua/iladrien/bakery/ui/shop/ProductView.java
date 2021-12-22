package ua.iladrien.bakery.ui.shop;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import ua.iladrien.bakery.ui.Theme;
import ua.iladrien.bakery.web.api.v0.orders.services.OrderService;
import ua.iladrien.bakery.web.api.v0.products.services.ProductOptionService;
import ua.iladrien.bakery.web.api.v0.products.services.ProductService;
import ua.iladrien.bakery.web.entities.Cart;
import ua.iladrien.bakery.web.entities.CartItem;
import ua.iladrien.bakery.web.entities.Product;
import ua.iladrien.bakery.web.entities.ProductOption;

import java.io.ByteArrayInputStream;

@Route(value = "shop/product/:id", layout = ShopLayout.class)
public class ProductView extends VerticalLayout implements BeforeEnterObserver {


    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final OrderService orderService;

    private Product product;

    private final ComboBox<ProductOption> option = new ComboBox<>("Option");
    private final IntegerField quantity = new IntegerField("Quantity");

    private final H5 price = new H5();
    private final Button add_to_cart = new Button("Add to cart");;

    public ProductView(ProductService productService, ProductOptionService productOptionService, OrderService orderService) {
        this.productService = productService;
        this.productOptionService = productOptionService;
        this.orderService = orderService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Integer id = Integer.valueOf(beforeEnterEvent.getRouteParameters().get("id").orElseThrow());
        product = productService.findById(id);
        removeAll();
        if (product != null) {
            initUi();
        }
    }

    private void initUi() {
        setMaxWidth(Theme.CONTAINER_WIDTH, Unit.PIXELS);
        addClassName(Theme.AUTO_MARGIN);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        setAlignItems(Alignment.START);

        initOption();

        horizontalLayout.add(getImage(), getContent());
        add(new H1(product.getName()), horizontalLayout);
    }

    private void initOption() {
        option.addValueChangeListener(evt -> updatePrice(evt.getValue()));
        option.setItems(product.getProductOptions());
        option.setItemLabelGenerator(ProductOption::getTitle);
        updatePrice(null);
    }

    private void updatePrice(ProductOption value) {
        updateButton();
        if (value != null) {
            price.setText(value.getPrice() + "€");
            price.setVisible(true);
        } else {
            price.setVisible(false);
        }
    }

    private void updateButton() {
        add_to_cart.setEnabled(option.getValue() != null && quantity.getValue() > 0);
    }

    private Component getContent() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        verticalLayout.add(
                new Label(product.getDescription()),
                option,
                new Hr(),
                price,
                getAddToCart()
        );
        return verticalLayout;
    }

    private Component getAddToCart() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        add_to_cart.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add_to_cart.addClickListener(evt -> addToCart());
        quantity.setValue(1);
        quantity.setMin(1);
        quantity.addValueChangeListener(evt -> updateButton());
        horizontalLayout.setAlignItems(Alignment.END);
        horizontalLayout.add(quantity, add_to_cart);
        return horizontalLayout;
    }

    private void addToCart() {
        Cart cart = orderService.getCart(VaadinSession.getCurrent().getPushId());
        CartItem item = new CartItem();
        item.setQuantity(quantity.getValue());
        item.setProduct(option.getValue());
        orderService.addToCart(cart, item);
        displayDialog();
    }

    private void displayDialog() {
        Dialog dialog = new Dialog();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(new Label("Product added to cart"));

        Button to_cart = new Button("To Cart");
        Button close = new Button("Close");

        to_cart.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout horizontalLayout = new HorizontalLayout(to_cart, close);
        horizontalLayout.setWidthFull();
        horizontalLayout.setJustifyContentMode(JustifyContentMode.END);

        verticalLayout.add(horizontalLayout);

        to_cart.addClickListener(evt -> {
            UI.getCurrent().navigate("shop/cart");
            dialog.close();
        });
        close.addClickListener(evt -> dialog.close());

        dialog.add(verticalLayout);
        dialog.open();
    }

    private Image getImage() {
        Image image;
        byte[] img = product.getImage();
        if (img != null) {
            StreamResource rs = new StreamResource(product.getName(), () -> new ByteArrayInputStream(img));
            rs.setContentType("image/png");
            image = new Image(rs, product.getName());
        } else {
            image = new Image("img/placeholder-image.jpg", product.getName());
        }
        image.setMaxWidth("50%");
        return image;
    }
}
