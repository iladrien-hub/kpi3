package ua.iladrien.bakery.web.api.v0.categories.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.iladrien.bakery.web.api.v0.CRUDController;
import ua.iladrien.bakery.web.api.v0.categories.repos.CategoryRepository;
import ua.iladrien.bakery.web.entities.Category;

@Controller
@RequestMapping("api/v0/category")
public class CategoryController extends CRUDController<Category, CategoryRepository> {
}
