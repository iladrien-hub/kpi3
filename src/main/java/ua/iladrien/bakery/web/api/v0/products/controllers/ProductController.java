package ua.iladrien.bakery.web.api.v0.products.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.iladrien.bakery.web.api.v0.CRUDController;
import ua.iladrien.bakery.web.api.v0.products.repos.ProductRepository;
import ua.iladrien.bakery.web.entities.Product;

@Controller
@RequestMapping("api/v0/products")
public class ProductController extends CRUDController<Product, ProductRepository> {

}
