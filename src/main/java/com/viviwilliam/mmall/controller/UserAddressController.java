package com.viviwilliam.mmall.controller;


import com.viviwilliam.mmall.entity.User;
import com.viviwilliam.mmall.service.CartService;
import com.viviwilliam.mmall.service.OrderService;
import com.viviwilliam.mmall.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/userAddress")
public class UserAddressController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/addressList")
    public ModelAndView getAllOrder(HttpSession session){

        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        modelAndView.addObject("addresss",userAddressService.findMyAddress(user.getId()));
        modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        System.out.println(modelAndView);
        return modelAndView;
    }

    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        userAddressService.removeById(id);
        return "redirect:/userAddress/addressList";

    }

}

