package com.example.hirxjava;

import com.example.hirxjava.entity.LoginEntity;
import com.example.hirxjava.entity.ProductEntity;
import com.example.hirxjava.entity.ResponseEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Author:wangcaiwen
 * Time:2017/11/7.
 * Description:
 */

public interface TestService {
    @POST("product/getProducts")
    @FormUrlEncoded
    Observable<ResponseEntity<List<ProductEntity>>> getCall(@Field("pscid") String cid);

    //登录
    @GET("user/login")
    Observable<ResponseBody> loginCall(@QueryMap Map<String,String> stringMap);

    //注册
    @GET("user/reg")
    Observable<ResponseBody> registerCall(@QueryMap Map<String,String> stringMap);


}
