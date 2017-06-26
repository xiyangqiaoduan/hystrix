package com.yangcb.eshop.web;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-06-24 23:28
 **/
@Controller
public class ProductController {

    @RequestMapping("getProductInfo")
    @ResponseBody
    public String productInfo(String productId){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id",productId);
        map.put("name","iphone7手机");
        map.put("price",5599);
        map.put("pictureList","a.jpg,b.jpg");
        map.put("specification","iphone7的规格");
        map.put("service","iphone7的售后服务");
        map.put("color","红色,白色,黑色");
        map.put("size","5.5");
        map.put("shopid",1);
        map.put("modifiedTime","2017-01-01 12:00:00");
        return JSON.toJSONString(map);

    }


}
