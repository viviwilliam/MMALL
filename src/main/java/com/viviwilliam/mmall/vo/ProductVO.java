package com.viviwilliam.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductVO {
    private Integer id;
    private String name;
    private Float price;
    private String fileName;

    public ProductVO() {
    }
}
