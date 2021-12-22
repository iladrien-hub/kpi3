package ua.iladrien.bakery.ui.admin.details;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ua.iladrien.bakery.ui.admin.AdminLayout;
import ua.iladrien.bakery.ui.admin.components.ImageUploader;
import ua.iladrien.bakery.ui.admin.forms.ProductOptionForm;
import ua.iladrien.bakery.web.api.v0.categories.services.CategoryService;
import ua.iladrien.bakery.web.api.v0.products.services.ProductOptionService;
import ua.iladrien.bakery.web.api.v0.products.services.ProductService;
import ua.iladrien.bakery.web.entities.Category;
import ua.iladrien.bakery.web.entities.Product;
import ua.iladrien.bakery.web.entities.ProductOption;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Route(value = "product/:id", layout = AdminLayout.class)
@PageTitle("Edit product | Admin")
public class ProductDetailView extends AbstractDetails<Product, ProductService> {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductOptionService productOptionService;

    // [Start]: Fields to bind
    private final TextField name = new TextField("Name");
    private final TextArea description = new TextArea("Description");
    private final ComboBox<Category> category = new ComboBox<>("Category");
    // [End]: Fields to bind

    private final Image picture = new Image();

    // [Start]: ProductOptions junk
    private final Set<ProductOption> optionsToDelete = new HashSet<>();

    private final Grid<ProductOption> grid = new Grid<>(ProductOption.class);
    private final Dialog productOptionDialog = new Dialog();
    private final ProductOptionForm productOptionForm = new ProductOptionForm();
    // [End]: ProductOptions junk

    public ProductDetailView(ProductService productService, CategoryService categoryService,
                             ProductOptionService productOptionService) {
        super(Product.class, productService);
        this.productService = productService;
        this.categoryService = categoryService;
        this.productOptionService = productOptionService;
    }

    public void initContent() {
        initProductOptionsForm();
        add(initForm());
    }

    private Component initForm() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.addClassName("product-detail-view-form-layout");
        horizontalLayout.setAlignItems(Alignment.START);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        FormLayout formLayout = new FormLayout();
        formLayout.add(
                name,
                category,
                description
        );

        picture.setMaxWidth("400px");

        verticalLayout.add(
                formLayout,
                new ImageUploader(picture, entity::getImage, entity::setImage),
                initAddOptionButton(),
                initOptionsGrid(),
                initButtons()
        );
        category.setItems((Collection<Category>) categoryService.findAll());
        category.setItemLabelGenerator(Category::getName);
        verticalLayout.addClassName("product-detail-view-form");

        horizontalLayout.add(picture, verticalLayout);

        return horizontalLayout;
    }

    // [Start]: More ProductOptions junk
    private Component initAddOptionButton() {
        Button button = new Button("+ Option");
        button.addClassName("align-self-end");
        button.addClickListener(buttonClickEvent -> editProductOption(new ProductOption(entity)));
        return button;
    }

    private Component initOptionsGrid() {
        updateOptionsGrid();
        grid.setColumns("title", "price");
        grid.asSingleSelect().addValueChangeListener(evt -> editProductOption(evt.getValue()));
        return grid;
    }

    private void initProductOptionsForm() {
        productOptionDialog.add(new Label("Edit option"), productOptionForm);

        productOptionForm.addListener(ProductOptionForm.SaveEvent.class, evt -> {
            entity.getProductOptions().add(evt.getOption());
            productOptionDialog.close();
            updateOptionsGrid();
        });

        productOptionForm.addListener(ProductOptionForm.DeleteEvent.class, evt -> {
            optionsToDelete.add(evt.getOption());
            entity.getProductOptions().remove(evt.getOption());
            productOptionDialog.close();
            updateOptionsGrid();
        });

        productOptionForm.addListener(ProductOptionForm.CloseEvent.class, evt -> {
            productOptionDialog.close();
            updateOptionsGrid();
        });
    }

    private void updateOptionsGrid() {
        if (entity.getProductOptions() != null)
            grid.setItems(entity.getProductOptions());
    }

    private void editProductOption(ProductOption value) {
        if (value != null) {
            productOptionForm.setProductOption(value);
            productOptionDialog.open();
        }
    }
    // [End]: More ProductOptions junk

    // [Start]: Buttons
    protected void delete() {
        productService.delete(entity);
        productOptionService.removeAll(entity.getProductOptions());
        close();
    }

    protected void close() {
        UI.getCurrent().navigate("");
    }

    protected void save() {
        productService.save(entity);
        productOptionService.removeAll(optionsToDelete);
        close();
    }
    // [End]: Buttons
}
