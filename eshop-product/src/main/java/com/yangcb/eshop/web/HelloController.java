package com.yangcb.eshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-06-24 23:06
 **/
@Controller
public class HelloController {


    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        return "hello Spring boot";
    }


}
