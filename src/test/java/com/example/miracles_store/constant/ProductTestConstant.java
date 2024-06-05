package com.example.miracles_store.constant;

import com.example.miracles_store.entity.ProductType;

import java.math.BigDecimal;

public class ProductTestConstant {

    public static final Integer AIR_FORCE_PRODUCT_ID = 1;

    public static final Integer SWEATER_PRODUCT_ID = 2;

    public static final String URL = "/api/v1/products";

    public static final String PRODUCT_NAME_AIR_FORCE = "Nike air force 1";

    public static final String PRODUCT_NAME_SWEATER = "Miracles sweater";

    public static final String PRODUCT_DESCRIPTION_AIR_FORCE = "White/Premium leather";

    public static final String PRODUCT_DESCRIPTION_SWEATER = "Cotton 100%";

    public static BigDecimal PRODUCT_COST_AIR_FORCE = BigDecimal.valueOf(100);

    public static BigDecimal PRODUCT_COST_SWEATER = BigDecimal.valueOf(80);

    public static ProductType PRODUCT_TYPE_AIR_FORCE = new ProductType(ProductTypeTestConstant.SHOES_TYPE_ID,
            ProductTypeTestConstant.TYPE_NAME_SHOES);

    public static ProductType PRODUCT_TYPE_SWEATER = new ProductType(ProductTypeTestConstant.SWEATERS_TYPE_ID,
            ProductTypeTestConstant.TYPE_NAME_SWEATERS);
}
