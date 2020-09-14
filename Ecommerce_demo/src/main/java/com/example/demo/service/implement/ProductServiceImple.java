package com.example.demo.service.implement;

import com.example.demo.dto.CategoriesDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.jdbc.DataSourceFromMyOwnSql;
import com.example.demo.model.Categories;
import com.example.demo.model.Product;
import com.example.demo.model.SubCategories;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SubCategoriesRepository;
import com.example.demo.service.ProductService;
import com.example.demo.view.Response;
import com.example.demo.view.ResponseBuilder;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("productService")
public class ProductServiceImple implements ProductService {
    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;
    private final SubCategoriesRepository subCategoriesRepository;
    private final DataSourceFromMyOwnSql dataSourceFromMyOwnSql;
    private final ModelMapper modelMapper;
    private String root = "Product";

    @Autowired
    public ProductServiceImple(ProductRepository productRepository, CategoriesRepository categoriesRepository, SubCategoriesRepository subCategoriesRepository, DataSourceFromMyOwnSql dataSourceFromMyOwnSql, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoriesRepository = categoriesRepository;
        this.subCategoriesRepository = subCategoriesRepository;
        this.dataSourceFromMyOwnSql = dataSourceFromMyOwnSql;
        this.modelMapper = modelMapper;
    }

    @Override
    public Response save(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Categories> categoriesList = product.getCategoriesList();
        Product finalProduct = product;
        categoriesList.forEach(categories -> {
            categories.getSubCategoriesList().forEach(subCategories -> {
                SubCategories haveSameSubCategories = subCategoriesRepository.findBySubCategoriesNameAndIsActiveTrue(subCategories.getSubCategoriesName());
                if (haveSameSubCategories != null) {
                    subCategories.setId(haveSameSubCategories.getId());
                    subCategories.setIsActive(true);
                    subCategories.setUpdatedAt(new Date());
                    subCategories.setSubCategoriesName(haveSameSubCategories.getSubCategoriesName());
                    subCategories = subCategoriesRepository.save(subCategories);//just update
                } else {
                    subCategories.setSubCategoriesName(subCategories.getSubCategoriesName());
                    subCategories = subCategoriesRepository.save(subCategories);
                }
            });
            Categories haveSameNameCategories = categoriesRepository.findByCategoryNameAndIsActiveTrue(categories.getCategoryName());
            if (haveSameNameCategories != null) {
                categories.setId(haveSameNameCategories.getId());
                categories.setCategoryName(haveSameNameCategories.getCategoryName());
                categories.setIsActive(true);
                categories.setUpdatedAt(new Date());
                categories = categoriesRepository.save(categories);//just update
                categories.setSubCategoriesList(categories.getSubCategoriesList());
            } else {
                categories.setCategoryName(categories.getCategoryName());
                categories = categoriesRepository.save(categories);
                categories.setSubCategoriesList(categories.getSubCategoriesList());
            }

        });


        product = productRepository.save(finalProduct);
        if (product != null) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, root + " Created Successfully", null);
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @Override
    public Response update(Long id, ProductDto productDto) {
        Product product = productRepository.findByIdAndIsActiveTrue(id);
        product.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        if (product != null) {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());//for ignore null value.
            modelMapper.map(productDto, product);
            product = productRepository.save(product);
            if (product != null) {
                return ResponseBuilder.getSuccessResponce(HttpStatus.OK, root + " Updated Successfully", null);
            }
            return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error occurs");
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.NOT_FOUND, root + " Not Found");
    }

    @Override
    public Response get(Long id) {
        Product product = productRepository.findByIdAndIsActiveTrue(id);
        if (product != null) {
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            if (product != null) {
                int numberOfRow = productRepository.countAllByIsActiveTrue();
                return ResponseBuilder.getSuccessResponce(HttpStatus.OK, root + " retrieved Successfully", productDto, 1, numberOfRow);
            } else {
                return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error Occurs");
            }
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.NOT_FOUND, root + " not found");
    }

    @Override
    public Response delete(Long id) {
        Product product = productRepository.findByIdAndIsActiveTrue(id);
        if (product != null) {
            product.setIsActive(false);
            product.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            product = productRepository.save(product);
            if (product != null) {
                return ResponseBuilder.getSuccessResponce(HttpStatus.OK, root + " deleted Successfully", null);
            }
            return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error Occurs");
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.NOT_FOUND, root + " not found");
    }

    @Override
    public Response getAll() {
        List<Product> products = productRepository.findAllByIsActiveTrue();
        List<ProductDto> productDtos = this.getProducts(products);
        int numberOfRow = productRepository.countAllByIsActiveTrue();
        return ResponseBuilder.getSuccessResponce(HttpStatus.OK, root + "s retrieved Successfully", productDtos, products.size(), numberOfRow);
    }

    @Override
    public Response getAllCategories() {
        List<Categories> categoriesList = categoriesRepository.findAllByIsActiveTrue();
        List<CategoriesDto> categoriesDtos = this.getAllCategories(categoriesList);
        int numberOfRow = categoriesRepository.countAllByIsActiveTrue();
        return ResponseBuilder.getSuccessResponce(HttpStatus.OK, "Categories retrieved Successfully", categoriesDtos, categoriesList.size(), numberOfRow);
    }

    @Override
    public Response getProductsByCategoryId(Long categoryId) {
        List<Long> productIdList = dataSourceFromMyOwnSql.getProductsIdByCategoriesId(categoryId);
        if (productIdList.size() == 0) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.NO_CONTENT, "There is no category with this resource", null);
        }
        List<Product> productList = new ArrayList<>();
        productIdList.forEach(productId -> {
            productList.add(productRepository.findByIdAndIsActiveTrue(productId));
        });
        if (productList.size() != 0) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.OK, "Products retrieved Successfully", productList, productList.size(), productIdList.size());
        } else {
            return ResponseBuilder.getSuccessResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", null);
        }
    }

    @Override
    public Response getSubCategoriesCategoryId(Long categoryId) {
        List<Long> subCategoriesIdList = dataSourceFromMyOwnSql.getSubCategoriesIdByCategoriesId(categoryId);
        if (subCategoriesIdList.size() == 0) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.NO_CONTENT, "There is no sub category with this resource", null);
        }
        List<SubCategories> subCategoriesList = new ArrayList<>();
        subCategoriesIdList.forEach(subCategoriesId -> {
            subCategoriesList.add(subCategoriesRepository.findByIdAndIsActiveTrue(subCategoriesId));
        });
        if (subCategoriesList.size() != 0) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.OK, "Sub Categories retrieved Successfully", subCategoriesList, subCategoriesList.size(), subCategoriesIdList.size());
        } else {
            return ResponseBuilder.getSuccessResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", null);
        }
    }

    private List<CategoriesDto> getAllCategories(List<Categories> categoriesList) {
        List<CategoriesDto> categoriesDtos = new ArrayList<>();
        categoriesList.forEach(categories -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            CategoriesDto categoriesDto = modelMapper.map(categories, CategoriesDto.class);
            categoriesDtos.add(categoriesDto);
        });
        return categoriesDtos;
    }

    private List<ProductDto> getProducts(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        products.forEach(product -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            productDtos.add(productDto);
        });
        return productDtos;
    }
}
