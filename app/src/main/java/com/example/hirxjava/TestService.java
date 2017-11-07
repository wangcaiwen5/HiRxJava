package com.example.hirxjava;

import com.example.hirxjava.entity.ProductEntity;
import com.example.hirxjava.entity.ResponseEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author:wangcaiwen
 * Time:2017/11/7.
 * Description:
 */

public interface TestService {
    @POST("product/getProducts")
    @FormUrlEncoded
    Observable<ResponseEntity<List<ProductEntity>>> getCall(@Field("pscid") String cid);
}
