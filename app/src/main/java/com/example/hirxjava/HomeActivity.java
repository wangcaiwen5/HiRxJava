package com.example.hirxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hirxjava.entity.ProductEntity;
import com.example.hirxjava.entity.ResponseEntity;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //轮询请求
        //getData1();
        //拦截请求
       // getData2();
    }

    private void getData2() {
        //创建被观察者observable
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);

                e.onNext(4);
                e.onNext(5);
                e.onNext(6);
                Toast.makeText(HomeActivity.this, "======"+e.toString(), Toast.LENGTH_SHORT).show();
                e.onComplete();

            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer%2==0;//拦截的条件
            }
        }).subscribe(new Observer<Integer>() {

            private Disposable mDisposable;
            // 观察者接收事件前，默认最先调用复写 onSubscribe（）
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("TAG","开始Subscribe");
                mDisposable=d;
                Toast.makeText(HomeActivity.this, "开始Subscribe", Toast.LENGTH_SHORT).show();
            }


            // 当被观察者生产Next事件 & 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onNext(Integer integer) {
                if(integer==3){
                    mDisposable.dispose();
                }
                Log.d("TAG","onNext响应"+integer);
            }

            // 当被观察者生产Error事件& 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onError(Throwable e) {
                Log.d("TAG",e+"Error事件");
            }

            @Override
            public void onComplete() {
                Log.d("TAG","onComplete");
            }
        });

    }

    private void getData1() {
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
                                System.out.println("接收"+value.code);//同一行代码冲突测试

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


    public void login(View view){




    }
}
