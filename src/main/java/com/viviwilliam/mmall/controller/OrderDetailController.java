package com.viviwilliam.mmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.viviwilliam.mmall.entity.Orders;
import com.viviwilliam.mmall.entity.User;
import com.viviwilliam.mmall.mapper.OrderMapper;
import com.viviwilliam.mmall.service.CartService;
import com.viviwilliam.mmall.service.OrderService;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    @GetMapping("/orderList")
    public ModelAndView getAllOrder(HttpSession session){

        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        modelAndView.addObject("orders",orderService.findMyOrder(user.getId()));
        modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        System.out.println(modelAndView);
        return modelAndView;
    }

    @GetMapping("/allOrderList")
    public ModelAndView getAllOrderList(HttpSession session){

        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allOrderList");
        modelAndView.addObject("orders",orderService.findAllOrder());
        modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        System.out.println(modelAndView);
        return modelAndView;
    }

    @GetMapping("/changeById/{id}")
    public String changeById(@PathVariable("id") Integer id){
        UpdateWrapper<Orders> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id).set("state", 1);

        Integer rows = orderMapper.update(null,updateWrapper);
        return "redirect:/orderDetail/allOrderList";

    }
    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        orderService.removeById(id);
        return "redirect:/orderDetail/orderList";

    }

}

