package com.example.demo.util;

public final class UrlConstraint {
    private UrlConstraint() {
    }

    public static final String TOKEN_VALIDATION = "/tokenValidation";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
    private static final String VERSION = "/v1";
    private static final String API = "/api";
    public static final String CATEGORIES = "/categories";
    public static final String SUB_CATEGORIES = "/subCategories";
    public static final String ORDER = "/order";

    public static class ProductManagement {
        public static final String ROOT = API + VERSION + "/products";
        public static final String CREATE = "/create";
        public static final String UPDATE = "/{id}";
        public static final String DELETE = "/{id}";
        public static final String GET = "/{id}";
        public static final String GET_ALL = "/all";
    }
    public static class OrderManagement {
        public static final String ROOT = API + VERSION + "/order";
        public static final String CREATE = "/create";
    }
    public static class CustomerManagement {
        public static final String ROOT = API + VERSION + "/customers";
        public static final String GET_ALL_CUSTOMER = "/all-Customer";
        public static final String CREATE = "/create";
    }
    public static class AuthManagement {
        public static final String ROOT = API + "/auth";
        public static final String LOGIN = "/login";
    }
    public static class FileManagement {
        public static final String ROOT = API + VERSION + "/files";
        public static final String SAVE = "/save";
    }
}
