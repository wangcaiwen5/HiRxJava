package com.example.hirxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hirxjava.entity.ProductEntity;
import com.example.hirxjava.entity.ResponseEntity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Observable.interval(1,2,TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("第"+aLong+"次");
                //retrofit网络请求
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.PRODUCT)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                TestService testService = retrofit.create(TestService.class);

                Observable<ResponseEntity<List<ProductEntity>>> call = testService.getCall("1");


                call.subscribeOn(Schedulers.io())// 切换到IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread()) //切换到主线程处理数据
                .subscribe(new Observer<ResponseEntity<List<ProductEntity>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseEntity<List<ProductEntity>> value) {
                        //接收

                        System.out.println("接收"+value.code);

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }






}
