package com.viviwilliam.mmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class RedirectController {

    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url){
        return url;
    }

    @GetMapping("/")
    public String main(){
        return "redirect:/productCategory/list";
    }




    @GetMapping("/session")
    public String session(HttpSession session){
        session.setAttribute("name","张三");
        return "common";
    }
}
