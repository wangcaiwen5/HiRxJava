package com.example.hirxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hirxjava.entity.LoginEntity;
import com.example.hirxjava.entity.ResponseEntity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
     //login1();
        loginTest();

    }

    private void loginTest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Api.PRODUCT)
                .build();

        TestService testService = retrofit.create(TestService.class);
        Map<String, String> map = new HashMap<>();
        map.put("mobile","18813146927");
        map.put("password","111111");
        //注册
        Observable<ResponseBody> registerCall = testService.registerCall(map);
        //登录
        final Observable<ResponseBody> loginCall = testService.loginCall(map);
        registerCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody value) {
                try {
                    Toast.makeText(LoginActivity.this, "12313"+value.string()+"", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
       /* loginCall.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseEntity<List<LoginEntity>>>() {
                    @Override
                    public void accept(ResponseEntity<List<LoginEntity>> listResponseEntity) throws Exception {
                        Toast.makeText(LoginActivity.this, listResponseEntity.msg+"登录", Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    private void login1() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Api.PRODUCT)
                .build();

        TestService testService = retrofit.create(TestService.class);
        Map<String, String> map = new HashMap<>();
        map.put("mobile","18813146927");
        map.put("password","111111");
        //注册
        Observable<ResponseBody> registerCall = testService.registerCall(map);
        //登录
        final Observable<ResponseBody> loginCall = testService.loginCall(map);

        registerCall.subscribeOn(Schedulers.io())//在io线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())//主线程处理数据
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseEntity) throws Exception {
                        System.out.println("=="+responseEntity.string());

                    }
                }).observeOn(Schedulers.io())//切换io线程进行网络请求
                .flatMap(new Function<ResponseBody, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(ResponseBody responseBody) throws Exception {
                        return loginCall;
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody responseBody) throws Exception {
                System.out.println("=="+responseBody.string());
            }
        });

    }
}
