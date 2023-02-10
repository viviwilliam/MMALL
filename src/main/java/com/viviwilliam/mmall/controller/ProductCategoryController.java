package com.viviwilliam.mmall.controller;


import com.viviwilliam.mmall.entity.User;
import com.viviwilliam.mmall.service.CartService;
import com.viviwilliam.mmall.service.ProductCategoryService;
import com.viviwilliam.mmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Controller
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;



    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVO());
        User user = (User)session.getAttribute("user");


        if(user ==null){
            modelAndView.addObject("cartList",new ArrayList<>());
            modelAndView.addObject("recommendList",productService.getRecommendProductCategoryVO(12));
        }else{
            modelAndView.addObject("recommendList",productService.getRecommendProductCategoryVO(user.getId()));
            modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        }
        return modelAndView;
    }



}

