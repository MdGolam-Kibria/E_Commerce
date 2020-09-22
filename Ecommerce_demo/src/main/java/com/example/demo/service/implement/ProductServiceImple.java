package com.example.demo.service.implement;

import com.example.demo.dto.CategoriesDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.SubCategoriesDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("productService")
public class ProductServiceImple implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImple.class.getName());
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
    public Response createSubCategory(SubCategoriesDto subCategoriesDto) {
        SubCategories subCategories = modelMapper.map(subCategoriesDto, SubCategories.class);
        SubCategories subCat = subCategoriesRepository.findBySubCategoriesNameAndIsActiveTrue(subCategories.getSubCategoriesName());
        if (subCat != null) {//if have any previous subCategory with this requested name
            subCat = subCategoriesRepository.save(subCat);//update
            return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, "SubCategories Update Successfully", subCategories.getSubCategoriesName());
        }
        //don't have any previous subCategory with this requested name
        subCategories = subCategoriesRepository.save(subCategories);
        if (subCategories != null) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, "SubCategories Created Successfully", subCategories.getSubCategoriesName());
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @Override
    public Response createCategory(CategoriesDto categoriesDto) {
        Categories categories = modelMapper.map(categoriesDto, Categories.class);

        Categories cat = categoriesRepository.findByCategoryNameAndIsActiveTrue(categories.getCategoryName());

        if (cat != null) {//have any category with this requested category name
            if (categories.getSubCategoriesList() != null) {//if have any subCategory with this request
                cat.setSubCategoriesList(categories.getSubCategoriesList());//if have any subCategories create reference
            }
            cat = categoriesRepository.save(cat);//update
            if (cat != null) {
                return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, "Category Update Successfully", categories.getCategoryName());
            }
        }


        //if don't have any category with this requested category name
        if (categories.getSubCategoriesList() != null) {//if have any subCategory with this request
            categories.setSubCategoriesList(categories.getSubCategoriesList());//if have any subCategories create reference
        }
        categories = categoriesRepository.save(categories);//create
        if (categories != null) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, "Category Created Successfully", categories.getCategoryName());
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @Override
    public Response createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        if (!(product.getMainPrice() >= product.getDiscountPrice())) {
            return ResponseBuilder.getFailureResponce(HttpStatus.NOT_ACCEPTABLE, "product price should be  less or equal  product discount price");
        }

        Product haveProduct = productRepository.findByNameAndIsActiveTrue(product.getName());

        if (haveProduct != null) {//have previous product with this name
            if (product.getCategoriesList() != null) {
                haveProduct.setCategoriesList(product.getCategoriesList());//if have any category create reference
            }
            haveProduct = productRepository.save(haveProduct);
            if (haveProduct != null) {
                return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, "Product Update Successfully", product.getName());
            }
        }

        //don't have any previous product with this name so create a new product
        if (product.getCategoriesList() != null) {
            product.setCategoriesList(product.getCategoriesList());//if have any category create reference
        }
        product = productRepository.save(product);
        if (product != null) {
            return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED, "Product Creation Successfully", product.getName());
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
