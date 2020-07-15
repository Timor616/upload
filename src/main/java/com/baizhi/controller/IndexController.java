package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: timor
 * @date: 2020/7/15 22:45
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    public String toLogin() {
        return "login";
    }

}
