package com.viviwilliam.mmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.viviwilliam.mmall.entity.*;
import com.viviwilliam.mmall.mapper.*;
import com.viviwilliam.mmall.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.viviwilliam.mmall.vo.ProductVO;
import com.viviwilliam.mmall.vo.orderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ProductMapper productMapper;

//查找用户自己的order
    public List<orderVO> findMyOrder(Integer id){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",id);
        //获取到了当前用户的所有订单id
        List<Orders> orders = orderMapper.selectList(wrapper);
        List<orderVO> myOrders = new ArrayList<>();
        //获取订单的详细商品

        for(int i=0;i<orders.size();i++){
            orderVO orderVO = new orderVO();
            orderVO.setId(orders.get(i).getId());
            orderVO.setSerialNumber(orders.get(i).getSerialnumber());
            orderVO.setState(orders.get(i).getState());
        //根据用户id可以获得订单号，地址，总金额
        // 通过订单id可以获得商品id，商品数量，商品总价
        // 通过id，可以生成名称，商品图片，单价
            //订单地址
            orderVO.setAddress(orders.get(i).getUserAddress());
            //订单总金额
            orderVO.setCost(orders.get(i).getCost());
            wrapper = new QueryWrapper();
            //所有本订单的订单详情取出
            wrapper.eq("order_id",orders.get(i).getId());
            List<OrderDetail> orderDetails = orderDetailMapper.selectList(wrapper);

            //具体订单下的每个商品信息
            List<ProductVO> allProduct = new ArrayList<>();
            ProductVO productVO;
            for(int j=0;j<orderDetails.size();j++){
               productVO = new ProductVO();
                //购买的商品的id
                productVO.setId(orderDetails.get(j).getProductId());
                //购买用户的名字
                productVO.setName(productMapper.selectById(orderDetails.get(j).getProductId()).getName());
                productVO.setFileName(productMapper.selectById(orderDetails.get(j).getProductId()).getFileName());
                //价格
                productVO.setPrice(productMapper.selectById(orderDetails.get(j).getProductId()).getPrice());
                allProduct.add(productVO);
            }
            orderVO.setProducts(allProduct);

            //productVO.setPrice();
            myOrders.add(orderVO);
        }


        return myOrders;
    }

//    @Override
//    public void changeById(Integer id) {
//        orderMapper
//    }


    //查找所有的order
    public List<Orders> findAllOrder(){
        List<Orders> orders = orderMapper.selectList(null);
        return orders;
    }

    @Override
    public boolean save(Orders orders, User user,String address, String remark) {
        //判断是否为新地址
        if(orders.getUserAddress().equals("newAddress")){
            //存入数据库
            UserAddress userAddress = new UserAddress();
            userAddress.setAddress(address);
            userAddress.setRemark(remark);
            userAddress.setIsdefault(1);
            userAddress.setUserId(user.getId());


            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("isdefault",1);
            UserAddress oldDefault = userAddressMapper.selectOne(wrapper);
            if(oldDefault!=null){
                oldDefault.setIsdefault(0);
                userAddressMapper.updateById(oldDefault);
            }

            userAddressMapper.insert(userAddress);
            orders.setUserAddress(address);
        }
        //存储orders
        orders.setUserId(user.getId());
        orders.setLoginName(user.getLoginName());
        orders.setState(0);
        //自动生成订单
        String seriaNumber = null;
        try{
            StringBuffer result = new StringBuffer();
            for(int i=0;i<32;i++){
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriaNumber = result.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.setSerialnumber(seriaNumber);
        orderMapper.insert(orders);

        //存储orderdetail
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        List<Cart> cartList = cartMapper.selectList(wrapper);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(Cart cart :cartList){
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setId(null);
            orderDetail.setOrderId(orders.getId());
            orderDetailMapper.insert(orderDetail);
        }
        //清空购物车
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("user_id",user.getId());
        cartMapper.delete(wrapper1);



        return true;
    }


}
