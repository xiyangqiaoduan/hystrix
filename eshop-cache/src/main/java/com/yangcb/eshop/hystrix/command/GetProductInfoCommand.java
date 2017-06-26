package com.yangcb.eshop.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.yangcb.eshop.http.HttpClientUtils;
import com.yangcb.eshop.model.ProductInfo;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-06-25 0:01
 **/

public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    private Long productId;

    public GetProductInfoCommand(Long productId){
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
        this.productId=productId;
    }


    @Override
    protected ProductInfo run() throws Exception {

        String url = "http://127.0.0.1:9999/getProductInfo?productId=" + productId;
        String result = HttpClientUtils.sendGetRequest(url);
        return JSONObject.parseObject(result,ProductInfo.class);
    }
}
