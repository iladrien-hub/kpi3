package ua.iladrien.bakery.web.api.v0.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.iladrien.bakery.web.api.v0.products.repos.ProductOptionRepository;
import ua.iladrien.bakery.web.entities.ProductOption;

import java.util.Set;

@Service
public class ProductOptionService {

    @Autowired
    private ProductOptionRepository productOptionRepository;


    public void saveAll(Set<ProductOption> productOptions) {
        productOptionRepository.saveAll(productOptions);
    }

    public void removeAll(Set<ProductOption> productOptions) {
        productOptionRepository.deleteAll(productOptions);
    }

    public void remove(ProductOption option) {
        productOptionRepository.delete(option);
    }
}
