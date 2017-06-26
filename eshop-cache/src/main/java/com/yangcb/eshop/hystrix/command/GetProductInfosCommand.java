package com.yangcb.eshop.hystrix.command;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.yangcb.eshop.http.HttpClientUtils;
import com.yangcb.eshop.model.ProductInfo;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ${DESCRIPTION}
 *
 * @author yangcb
 * @create 2017-06-25 10:32
 **/
public class GetProductInfosCommand extends HystrixObservableCommand<ProductInfo> {


    private Long[] productIds;

    public GetProductInfosCommand(Long[] productIds) {

        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
        this.productIds = productIds;

    }


    @Override
    protected Observable<ProductInfo> construct() {

        return Observable.create(new Observable.OnSubscribe<ProductInfo>() {

            @Override
            public void call(Subscriber<? super ProductInfo> observer) {
                try {
                        for (Long productId : productIds) {
                            String url = "http://127.0.0.1:9999/getProductInfo?productId=" + productId;
                            String response = HttpClientUtils.sendGetRequest(url);
                            ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class);
                            observer.onNext(productInfo);
                        }
                        observer.onCompleted();
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
