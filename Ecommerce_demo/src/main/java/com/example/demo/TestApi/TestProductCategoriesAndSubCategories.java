package com.example.demo.TestApi;

import com.example.demo.model.Categories;
import com.example.demo.model.Product;
import com.example.demo.model.SubCategories;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SubCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class TestProductCategoriesAndSubCategories {
    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;
    private final SubCategoriesRepository subCategoriesRepository;

    @Autowired
    public TestProductCategoriesAndSubCategories(ProductRepository productRepository, CategoriesRepository categoriesRepository, SubCategoriesRepository subCategoriesRepository) {
        this.productRepository = productRepository;
        this.categoriesRepository = categoriesRepository;
        this.subCategoriesRepository = subCategoriesRepository;
    }

    @PostConstruct
    public void initialize() {
        Product product = new Product();
        product.setName("FOOD");
        product.setImage("https://www.pic.com/image.png");
        product.setMainPrice(500.50);
        product.setDiscountPrice(200.66);
        product.setDiscountNote("20%");
        product.setQuantity(500);
        product.setDescription("This is  Original Raj... mango");
        product.setStock(10000);

        Categories categories = new Categories();
        categories.setCategoryName("Fruits");//new
        categories = categoriesRepository.save(categories);//new

        SubCategories subCategories = new SubCategories();
        subCategories.setSubCategoriesName("Fresh Fruits");
        subCategories = subCategoriesRepository.save(subCategories);

        categories.setSubCategoriesList(Arrays.asList(subCategories));
        categories = categoriesRepository.save(categories);

        subCategories.setCategoriesList(Arrays.asList(categories));
        categories.setSubCategoriesList(Arrays.asList(subCategories));

        subCategories = subCategoriesRepository.save(subCategories);

        product.setCategoriesList(Arrays.asList(categories));
        product = productRepository.save(product);

        System.out.println("product id = " + product.getId() + " Product Name = " + product.getName());
        System.out.println("categories id = " + categories.getId() + " Name = " + categories.getCategoryName());
        System.out.println("Sub Categories id = " + subCategories.getId() + " Name = " + subCategories.getSubCategoriesName());
    }
}