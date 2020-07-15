package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: timor
 * @date: 2020/7/15 22:36
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(User user, HttpSession session) {
        User userDB = userService.login(user);
        if (userDB != null) {
            session.setAttribute("user", userDB);
            return "redirect:/file/showAll";


        } else{
            return "redirect:/index";
        }
    }
}
