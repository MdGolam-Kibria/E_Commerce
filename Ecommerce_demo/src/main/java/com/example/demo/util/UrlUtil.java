package com.example.demo.util;

public class UrlUtil {
    private static String allPrefix = "/*";
    public static String permitAllUrl[] = {
            UrlConstraint.AuthManagement.ROOT + allPrefix,
            UrlConstraint.ProductManagement.ROOT + UrlConstraint.ProductManagement.GET,
            UrlConstraint.ProductManagement.ROOT + UrlConstraint.ProductManagement.GET_ALL
    };
}
