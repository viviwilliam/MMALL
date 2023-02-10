package com.viviwilliam.mmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.viviwilliam.mmall.entity.Product;
import com.viviwilliam.mmall.entity.ProductCategory;
import com.viviwilliam.mmall.mapper.ProductCategoryMapper;
import com.viviwilliam.mmall.mapper.ProductMapper;
import com.viviwilliam.mmall.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.viviwilliam.mmall.service.ProductService;
import com.viviwilliam.mmall.vo.ProductCategoryVO;
import com.viviwilliam.mmall.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductMapper productMapper;




    @Override
    public List<ProductCategoryVO> getAllProductCategoryVO() {
        //查询一级分类,条件查询
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type",1);
        List<ProductCategory> levelOne = productCategoryMapper.selectList(wrapper);
        List<ProductCategoryVO> levelOneVO =  levelOne.stream().map(e-> new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
        //图片赋值
        //商品信息赋值
        for(int i=0;i<levelOneVO.size();i++){
            levelOneVO.get(i).setBannerImg("/images/banner"+i+".png");
            levelOneVO.get(i).setTopImg("/images/top"+i+".png");
            wrapper = new QueryWrapper();
            wrapper.eq("categorylevelone_id",levelOneVO.get(i).getId());

            List<Product> productList =  productMapper.selectList(wrapper);
            List<ProductVO> productVOList = productList.stream().map(e->new ProductVO(
                    e.getId(),
                    e.getName(),
                    e.getPrice(),
                    e.getFileName()
                    )).collect(Collectors.toList());
            levelOneVO.get(i).setProductVOList(productVOList);
        }

        for( ProductCategoryVO levelOneProductCategoryVO: levelOneVO){
            wrapper = new QueryWrapper();
            wrapper.eq("type",2);
            wrapper.eq("parent_id",levelOneProductCategoryVO.getId());
            List<ProductCategory> levelTwo = productCategoryMapper.selectList(wrapper);
            List<ProductCategoryVO> levelTwoVO = levelTwo.stream().map(e-> new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
            levelOneProductCategoryVO.setChildren(levelTwoVO);
            for(ProductCategoryVO levelTwoProductCategoryVO: levelTwoVO){
                wrapper = new QueryWrapper();
                wrapper.eq("type",3);
                wrapper.eq("parent_id",levelTwoProductCategoryVO.getId());
                List<ProductCategory> levelThree = productCategoryMapper.selectList(wrapper);
                List<ProductCategoryVO> levelThreeVO = levelThree.stream().map(e-> new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
                levelTwoProductCategoryVO.setChildren(levelThreeVO);
            }
        }
        return levelOneVO;
    }


}
