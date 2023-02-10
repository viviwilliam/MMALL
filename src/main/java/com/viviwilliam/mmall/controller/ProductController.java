package com.viviwilliam.mmall.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.viviwilliam.mmall.entity.Orders;
import com.viviwilliam.mmall.entity.Product;
import com.viviwilliam.mmall.entity.User;
import com.viviwilliam.mmall.mapper.ProductMapper;
import com.viviwilliam.mmall.service.CartService;
import com.viviwilliam.mmall.service.ProductCategoryService;
import com.viviwilliam.mmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wwl
 * @since 2021-05-25
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductMapper productMapper;


    @GetMapping("/list/{type}/{id}")
    public ModelAndView list(@PathVariable("type") String type ,
                             @PathVariable("id") Integer id,
                             HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User user = (User)session.getAttribute("user");
        modelAndView.setViewName("productList");
        modelAndView.addObject("productList",productService.findByCategoryId(type,id));
        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVO());


        if(user ==null){
            modelAndView.addObject("cartList",new ArrayList<>());
            modelAndView.addObject("recommendList",productService.getRecommendProductCategoryVO(12));

        }else{
            modelAndView.addObject("recommendList",productService.getRecommendProductCategoryVO(user.getId()));
            modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        }
        return modelAndView;
    }

    @GetMapping("/findById/{id}")
    public ModelAndView fiindById(@PathVariable("id") Integer id,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        User user = (User)session.getAttribute("user");
        modelAndView.addObject("product",productService.getById(id));
        modelAndView.addObject("list",productCategoryService.getAllProductCategoryVO());

        if(user ==null){
            modelAndView.addObject("cartList",new ArrayList<>());
            modelAndView.addObject("recommendList",productService.getRecommendProductCategoryVO(12));

        }else{
            modelAndView.addObject("recommendList",productService.getRecommendProductCategoryVO(user.getId()));
            modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        }
        return modelAndView;

    }

    @GetMapping("/allProductList")
    public ModelAndView getAllOrderList(HttpSession session){

        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allProduct");
        modelAndView.addObject("products",productService.findAllProduct());
        modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        System.out.println(modelAndView);
        return modelAndView;
    }


    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Integer id){
        productService.removeById(id);
        return "redirect:/product/allProductList";

    }

    @GetMapping("/addProduct")
    public ModelAndView addProduct(HttpSession session){

        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addProduct");
       // modelAndView.addObject("products",productService.findAllProduct());
        modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        System.out.println(modelAndView);
        return modelAndView;
    }


    @PostMapping("/addProducts")
    public String register(HttpServletRequest request, Model model){
        boolean result = false;
        Product product= new Product();
        product.setName((String)request.getParameter("productName"));
        product.setDescription((String)request.getParameter("Description"));
        product.setPrice(Float.parseFloat(request.getParameter("price")));
        product.setStock(Integer.parseInt(request.getParameter("stock")));
        product.setFileName(request.getParameter("ImgValue"));
        String levelone = request.getParameter("lines");
        String levetwo = request.getParameter("station");
        int leveoneData;
        int levetwoData;
        //化妆品548", "家用商品628", "进口食品660", "电子商品670", "保健食品676", "箱包681
          /*["面部护理654"],
            ["餐具656","卫具657","客厅专用696"],
            ["零食/糖果/巧克力661"],
            ["手机671","手环674","电脑690"],
            ["老年保健品677","中年营养品678","儿童保健品679"],
            ["旅行箱682","手提箱683"]
        * */
        if(levelone.equals("化妆品")){
            product.setCategoryleveloneId(548);
            if(levetwo.equals("面部护理")){
                product.setCategoryleveltwoId(654);
            }
        }
        else if(levelone.equals("家用商品")){
            product.setCategoryleveloneId(628);
            if(levetwo.equals("餐具")){
                product.setCategoryleveltwoId(656);
            }
            else if(levetwo.equals("卫具")){
                product.setCategoryleveltwoId(657);
            }
            else if(levetwo.equals("客厅专用")){
                product.setCategoryleveltwoId(696);
            }
        }
        else if(levelone.equals("进口食品")){
            product.setCategoryleveloneId(660);
            if(levetwo.equals("零食/糖果/巧克力")){
                product.setCategoryleveltwoId(661);
            }
        }
        else if(levelone.equals("电子商品")){
            product.setCategoryleveloneId(670);
            if(levetwo.equals("手机")){
                product.setCategoryleveltwoId(671);
            }
            else if(levetwo.equals("手环")){
                product.setCategoryleveltwoId(674);
            }
            else if(levetwo.equals("电脑")){
                product.setCategoryleveltwoId(690);
            }
        }
        else if(levelone.equals("保健食品")){
            product.setCategoryleveloneId(676);
            if(levetwo.equals("老年保健品")){
                product.setCategoryleveltwoId(677);
            }
            else if(levetwo.equals("中年营养品")){
                product.setCategoryleveltwoId(678);
            }
            else if(levetwo.equals("儿童保健品")){
                product.setCategoryleveltwoId(679);
            }
        }
        else if(levelone.equals("箱包")){
            product.setCategoryleveloneId(681);
            if(levetwo.equals("旅行箱")){
                product.setCategoryleveltwoId(682);
            }
            else if(levetwo.equals("手提箱")){
                product.setCategoryleveltwoId(683);
            }
        }

        product.setCategorylevelthreeId(0);
        result = productService.save(product);

        return "redirect:/product/allProductList";
    }

    @PostMapping("/changeProducts")
    public String changeProduct(HttpServletRequest request, Model model){
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name",request.getParameter("productName")).set("name", request.getParameter("productName"));
        updateWrapper.eq("name",request.getParameter("productName")).set("description", request.getParameter("Description"));
        updateWrapper.eq("name",request.getParameter("productName")).set("price", request.getParameter("price"));
        updateWrapper.eq("name",request.getParameter("productName")).set("stock", request.getParameter("stock"));
        Integer rows = productMapper.update(null,updateWrapper);

        return "redirect:/product/allProductList";
    }
}

