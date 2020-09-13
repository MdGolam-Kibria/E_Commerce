package com.example.demo.sql;

public final class Sql {
    private Sql() {
    }

    public static final String sqlForProductsIdByCategoriesId = "SELECT product_id FROM product_categories WHERE category_id = ?";
    public static final String sqlSubCategoriesIdByCategoriesId = "SELECT sub_category_id FROM product_sub_categories WHERE categories_id = ?";
}
