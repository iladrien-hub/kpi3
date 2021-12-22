package ua.iladrien.bakery.ui.admin.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ImageUploader extends HorizontalLayout {

    private final MemoryBuffer image = new MemoryBuffer();
    private final Upload upload = new Upload(image);

    private final Image display;
    private final Supplier<byte[]> supplier;
    private final Consumer<byte[]> consumer;

    public ImageUploader(Image display, Supplier<byte[]> supplier, Consumer<byte[]> consumer) {
        this.display = display;
        this.supplier = supplier;
        this.consumer = consumer;

        updateImage();
        initUpload();

        setWidthFull();
        setAlignItems(Alignment.CENTER);
        add(upload, initDelete());
    }

    private Component initDelete() {
        Button delete = new Button("Delete Picture...");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(evt -> {
            consumer.accept(null);
            updateImage();
        });
        return delete;
    }

    private void updateImage() {
        byte[] img = supplier.get();
        if (img != null) {
            StreamResource rs = new StreamResource("", () -> new ByteArrayInputStream(img));
            rs.setContentType("image/png");
            display.setSrc(rs);
        } else {
            display.setSrc("img/placeholder-image.jpg");
        }
    }

    private void initUpload() {
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.addSucceededListener(evt -> {
            try {
                consumer.accept(image.getInputStream().readAllBytes());
                updateImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
