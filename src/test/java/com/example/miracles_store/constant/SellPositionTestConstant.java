package com.example.miracles_store.constant;

import com.example.miracles_store.entity.Product;

public class SellPositionTestConstant {

    public static final Integer AIR_FORCE_POSITION_ID = 1;

    public static final Integer SWEATER_POSITION_ID = 2;

    public static final String URL = "/api/v1/sellPositions";

    public static final Product POSITION_PRODUCT_AIR_FORCE = new Product(
            AIR_FORCE_POSITION_ID,
            ProductTestConstant.PRODUCT_NAME_AIR_FORCE,
            ProductTestConstant.PRODUCT_DESCRIPTION_AIR_FORCE,
            ProductTestConstant.PRODUCT_COST_AIR_FORCE,
            null,
            ProductTestConstant.PRODUCT_TYPE_AIR_FORCE
    );

    public static final Product POSITION_PRODUCT_SWEATER = new Product(
            SWEATER_POSITION_ID,
            ProductTestConstant.PRODUCT_NAME_SWEATER,
            ProductTestConstant.PRODUCT_DESCRIPTION_SWEATER,
            ProductTestConstant.PRODUCT_COST_SWEATER,
            null,
            ProductTestConstant.PRODUCT_TYPE_SWEATER
    );

    public static String POSITION_SIZE_AIR_FORCE = "9.5US";

    public static String POSITION_SIZE_SWEATER = "M";

    public static Integer POSITION_QUANTITY_AIR_FORCE = 12;

    public static Integer POSITION_QUANTITY_SWEATER = 0;

    public static Boolean POSITION_ACTIVE_AIR_FORCE = true;

    public static Boolean POSITION_ACTIVE_SWEATER = false;
}
