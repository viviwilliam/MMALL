package com.viviwilliam.mmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.viviwilliam.mmall.entity.Cart;
import com.viviwilliam.mmall.entity.Product;
import com.viviwilliam.mmall.enums.ResultEnum;
import com.viviwilliam.mmall.exception.MallException;
import com.viviwilliam.mmall.mapper.CartMapper;
import com.viviwilliam.mmall.mapper.ProductMapper;
import com.viviwilliam.mmall.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.viviwilliam.mmall.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;


//    public List<CartVO> Recommend(){
//        //首先获取到当前user和所有的订单，并分类
//
//        //首先建立物品-用户的倒排索引表
//        //每个物品喜欢他的用户两两之间相同物品加1
//        //计算余弦相似度
//        //然后从矩阵中找到与目标用户最相似的K个用户，
//        List<>
//    }

    @Override
    public boolean save(Cart entity) {

        //扣库存
        Product product = productMapper.selectById(entity.getProductId());
        Integer stock = product.getStock() - entity.getQuantity();
        //抛出异常
        if(stock<0){
            log.error("【添加购物车】库存不足！stock={}",stock);
            throw new MallException(ResultEnum.STOCK_ERROR);
        }

        product.setStock(stock);
        productMapper.updateById(product);

        if(cartMapper.insert(entity)==1){
            return true;
        }
        return false;
    }

    @Override
    public List<CartVO> findAllCartVOByUserId(Integer id) {
        List<CartVO> cartVOList = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",id);
        List<Cart> cartList = cartMapper.selectList(wrapper);
        for(Cart cart:cartList){
            CartVO cartVO = new CartVO();
            Product product = productMapper.selectById(cart.getProductId());
            BeanUtils.copyProperties(product,cartVO);
            BeanUtils.copyProperties(cart,cartVO);
            cartVOList.add(cartVO);
        }
        return cartVOList;
    }


}
