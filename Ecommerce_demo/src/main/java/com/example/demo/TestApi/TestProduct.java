package com.example.demo.TestApi;

import com.example.demo.model.Categories;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class TestProduct {
    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public TestProduct(ProductRepository productRepository, CategoriesRepository categoriesRepository) {
        this.productRepository = productRepository;
        this.categoriesRepository = categoriesRepository;
    }

   @PostConstruct
    public void init() {
        Product product = new Product();
      //  product.setId(Long.parseLong("10"));
        product.setName("Mango");
        product.setImage("https://www.pic.com/image.png");
        product.setMainPrice(500.50);
        product.setDiscountPrice(200.66);
        product.setDiscountNote("20%");
        product.setQuantity(500);
        product.setDescription("This is  Original Raj... mango");
        product.setStock(10000);

        Categories categories = new Categories();
        categories.setName("Mango");
        categories = categoriesRepository.save(categories);
        categories.setProducts(Arrays.asList(product));
        product.setCategoriesList(Arrays.asList(categories));

        product = productRepository.save(product);


        System.out.println("product id = " + product.getId());
        System.out.println("categories id = " + categories.getId());

    }
}
