package com.yangcb.eshop.web;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import com.yangcb.eshop.http.HttpClientUtils;
import com.yangcb.eshop.hystrix.command.GetProductInfoCommand;
import com.yangcb.eshop.hystrix.command.GetProductInfosCommand;
import com.yangcb.eshop.model.ProductInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.Future;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-06-24 23:22
 **/
@Controller
public class CacheController {

    @RequestMapping("/change/product")
    @ResponseBody
    public String changeProduct(Long productId) {


        String url = "http://127.0.0.1:9999/getProductInfo?productId=" + productId;

        String result = HttpClientUtils.sendGetRequest(url);

        return "success result=[" + result + "]";
    }

    @RequestMapping("/getProductInfo")
    @ResponseBody
    public String getProductInfo(Long productId) {

        HystrixCommand<ProductInfo> productCommand = new GetProductInfoCommand(productId);
        Future future = productCommand.queue();


//            try {
//                Thread.sleep(1000);
//                System.out.println(future.get());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


        ProductInfo productInfo = productCommand.execute();
        return "success result=[" + JSON.toJSONString(productInfo) + "]";

        //  return "success";
    }

    @RequestMapping("getProductInfos")
    @ResponseBody
    public String getProductInfos(String productIds) {

        if (null == productIds || "".equals(productIds)) {
            return "error";
        }

        String[] ids = productIds.split(",");
        Long[] productids = new Long[ids.length];

        for (int i = 0; i < ids.length; i++) {
            productids[i] = Long.parseLong(ids[i]);
        }
        HystrixObservableCommand<ProductInfo> productInfoHystrixObservableCommand = new GetProductInfosCommand(productids);
        Observable<ProductInfo> observable = productInfoHystrixObservableCommand.observe();
        observable.subscribe(new Observer<ProductInfo>() {
            @Override
            public void onCompleted() {
                System.out.println("获取完所有的商品数据");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(ProductInfo productInfo) {
                System.out.println(JSON.toJSONString(productInfo));
            }
        });


        return "success";

    }


}
