package com.viviwilliam.mmall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.viviwilliam.mmall.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface ProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> getAllProductCategoryVO();

}
