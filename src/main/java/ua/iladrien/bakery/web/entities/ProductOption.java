package ua.iladrien.bakery.web.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOption {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    private BigDecimal price;

    @ManyToOne
    private Product product;

    public ProductOption(@NotNull Product product) {
        this.product = product;
    }
}
