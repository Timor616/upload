package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: timor
 * @date: 2020/7/15 22:41
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @GetMapping("/showAll")
    public String findAll() {
        System.out.println("查询所有进入...");
        return "showAll";
    }
}
