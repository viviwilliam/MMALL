package com.viviwilliam.mmall.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 订单VO
 * 订单号，地址，总金额
 * 商品名称，商品图片，商品数量，单价，总价
 *
 *
 * */

@Data
public class orderVO {
//根据用户id可以获得订单号，地址，总金额
// 通过订单id可以获得商品id，商品数量，商品总价
// 通过id，可以生成名称，商品图片，单价
    public Integer Id;
    private String serialNumber;
    private String address;
    private Float cost;
    private List<ProductVO> products;
    private Integer state;
}
