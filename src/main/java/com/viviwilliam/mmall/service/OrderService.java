package com.viviwilliam.mmall.service;

import com.viviwilliam.mmall.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.viviwilliam.mmall.entity.User;
import com.viviwilliam.mmall.vo.orderVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
public interface OrderService extends IService<Orders> {
    public boolean save(Orders orders, User user,String address, String remark);
    public List<Orders> findAllOrder();
    public List<orderVO> findMyOrder(Integer id);
}
