package com.example.demo.util;

public final class UrlUtil {
    private UrlUtil() {
    }

    private static String allPrefix = "/*";
    public static String permitAllUrl[] = {
            UrlConstraint.AuthManagement.ROOT + allPrefix,
            UrlConstraint.CustomerManagement.ROOT + UrlConstraint.CustomerManagement.CREATE,
            UrlConstraint.ProductManagement.ROOT + UrlConstraint.ProductManagement.GET,
            UrlConstraint.ProductManagement.ROOT + UrlConstraint.ProductManagement.GET_ALL,
            UrlConstraint.ProductManagement.ROOT + UrlConstraint.ProductManagement.GET_ALL + UrlConstraint.CATEGORIES,
            UrlConstraint.ProductManagement.ROOT+UrlConstraint.CATEGORIES + UrlConstraint.ProductManagement.GET,
            UrlConstraint.ProductManagement.ROOT+UrlConstraint.SUB_CATEGORIES + UrlConstraint.ProductManagement.GET,
    };
}
