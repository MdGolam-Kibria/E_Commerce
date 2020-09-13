package com.example.demo.jdbc;

import com.example.demo.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class DataSourceFromMyOwnSql {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public DataSourceFromMyOwnSql(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> getProductsIdByCategoriesId(Long categoriesId) {
        List<Long> productId = jdbcTemplate.queryForList(Sql.sqlForProductsIdByCategoriesId, new Object[]{categoriesId}, Long.TYPE);
        return productId;
    }

    public List<Long> getSubCategoriesIdByCategoriesId(Long categoriesId) {
        List<Long> productId = jdbcTemplate.queryForList(Sql.sqlSubCategoriesIdByCategoriesId, new Object[]{categoriesId}, Long.TYPE);
        return productId;
    }

    //@PostConstruct
    public void test() {
        List<Long> a = getProductsIdByCategoriesId(Long.parseLong("2"));
        List<Long> b = getSubCategoriesIdByCategoriesId(Long.parseLong("2"));
        a.forEach(System.out::println);
        b.forEach(System.out::println);

    }
}