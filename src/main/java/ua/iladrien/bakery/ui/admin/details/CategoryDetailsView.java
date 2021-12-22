package ua.iladrien.bakery.ui.admin.details;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
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
import ua.iladrien.bakery.web.api.v0.categories.services.CategoryService;
import ua.iladrien.bakery.web.entities.Category;

import java.util.Collection;

@Route(value = "category/:id", layout = AdminLayout.class)
@PageTitle("Edit category | Admin")
public class CategoryDetailsView extends AbstractDetails<Category, CategoryService> {

    private final CategoryService categoryService;

    // [Start]: Fields to bind
    private final TextField name = new TextField("Name");
    private final TextArea description = new TextArea("Description");
    private final ComboBox<Category> parent = new ComboBox<>("Parent");
    // [End]: Fields to bind

    private final Image picture = new Image();

    private final Dialog errorDialog = new Dialog();

    public CategoryDetailsView(CategoryService categoryService) {
        super(Category.class, categoryService);
        this.categoryService = categoryService;
    }

    protected void initContent() {
        initDialog();

        parent.setItems((Collection<Category>) categoryService.findAll());
        parent.setItemLabelGenerator(Category::getName);

        add(initForm());
    }

    private void initDialog() {
        Label label = new Label("Category cannot be parent to itself");
        Button ok = new Button("Ok");
        ok.addClickListener(evt -> errorDialog.close());
        ok.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        ok.addClassName("align-self-end");
        errorDialog.add(new VerticalLayout(label, ok));
    }

    // [Start]: Buttons
    @Override
    protected void save() {
        Category parent = entity.getParent();
        if (parent != null && parent.getId().equals(entity.getId())) {
            errorDialog.open();
            return;
        }
        categoryService.save(entity);
        close();
    }

    @Override
    protected void close() {
        UI.getCurrent().navigate("categories");
    }

    @Override
    protected void delete() {
        categoryService.delete(entity);
        close();
    }
    // [End]: Buttons


    private Component initForm() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setAlignItems(Alignment.START);

        // Form
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        FormLayout formLayout = new FormLayout();
        formLayout.add(
                name,
                parent,
                description
        );

        picture.setMaxWidth("400px");

        verticalLayout.add(
                formLayout,
                new ImageUploader(picture, entity::getImage, entity::setImage),
                initButtons()
        );

        horizontalLayout.add(picture, verticalLayout);
        return horizontalLayout;
    }
}
