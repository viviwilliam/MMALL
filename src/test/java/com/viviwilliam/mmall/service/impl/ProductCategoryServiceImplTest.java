package com.viviwilliam.mmall.service.impl;

import com.viviwilliam.mmall.service.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService service;

    @Test
    void getAllProductCategoryVO(){
        service.getAllProductCategoryVO();
    }


}