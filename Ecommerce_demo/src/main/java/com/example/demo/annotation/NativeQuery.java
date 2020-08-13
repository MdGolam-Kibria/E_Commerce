package com.example.demo.annotation;

@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.ANNOTATION_TYPE})
@org.springframework.data.annotation.QueryAnnotation
@java.lang.annotation.Documented
public @interface NativeQuery {
    java.lang.String value() default "";

    java.lang.String countQuery() default "";

    java.lang.String countProjection() default "";

    boolean nativeQuery() default true;

    java.lang.String name() default "";

    java.lang.String countName() default "";
}
