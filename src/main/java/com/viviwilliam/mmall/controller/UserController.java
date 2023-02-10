package com.viviwilliam.mmall.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.viviwilliam.mmall.entity.User;
import com.viviwilliam.mmall.service.CartService;
import com.viviwilliam.mmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.management.MBeanServer;
import javax.net.ssl.HttpsURLConnection;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping("/asd")
    public String dl(){
        return "login";
    }



    @PostMapping("/register")
    public String register(User user, Model model){
        boolean result = false;
        user.setFileName("head/3.jpg");
        try{
            result = userService.save(user);
        }catch (Exception e){
            model.addAttribute("error",user.getLoginName()+"已存在！");
            return "register";
        }

        if(result){
            return "login";
        }
        else {
            return "register";
        }
    }




    /**
     * 登录
     * @param loginName
     * @param password
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(String loginName, String password, HttpSession session){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("login_name",loginName);
        wrapper.eq("password",password);
        User user = userService.getOne(wrapper);
        if(user == null){
            return "login";
        }
        else if(user.getType()==0){
            session.setAttribute("user",user);
            return "redirect:/productCategory/list";
        }
        //管理员页面
        else{
            session.setAttribute("user",user);
            return "redirect:/user/managerInfo";

        }
    }

    /**
     * 退出
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

@GetMapping("/userInfo")
 public ModelAndView userInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
     modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        return modelAndView;
 }
    @GetMapping("/managerInfo")
    public ModelAndView managerInfo(HttpSession session){
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("managerInfo");
        modelAndView.addObject("cartList",cartService.findAllCartVOByUserId(user.getId()));
        return modelAndView;
    }
}

