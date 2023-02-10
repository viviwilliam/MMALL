package com.viviwilliam.mmall.vo;


import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryVO {

    private Integer id;
    private String name;
    private List<ProductCategoryVO> children;
    private String bannerImg;
    private String topImg;
    private List<ProductVO> productVOList;

    public ProductCategoryVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
