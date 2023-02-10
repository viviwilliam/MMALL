package com.viviwilliam.mmall.service;

import com.viviwilliam.mmall.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.viviwilliam.mmall.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
public interface CartService extends IService<Cart> {
    public List<CartVO> findAllCartVOByUserId(Integer id);
}
