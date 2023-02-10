package com.viviwilliam.mmall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.viviwilliam.mmall.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.viviwilliam.mmall.entity.ProductCategory;
import com.viviwilliam.mmall.vo.ProductCategoryVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
public interface ProductService extends IService<Product> {

    public List<Product> findByCategoryId(String type,Integer categoryId);

    public List<Product> getRecommendProductCategoryVO(Integer id);

    public List<Product> findAllProduct();


}
