package com.example.hirxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Toast.makeText(MainActivity.this, "======"+e.toString(), Toast.LENGTH_SHORT).show();
                e.onComplete();

            }
        }).subscribe(new Observer<Integer>() {

            private Disposable mDisposable;
            // 观察者接收事件前，默认最先调用复写 onSubscribe（）
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("TAG","开始Subscribe");
                mDisposable=d;
                Toast.makeText(MainActivity.this, "开始Subscribe", Toast.LENGTH_SHORT).show();
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

      /*  //创建观察者
        Observer<Integer> observer = new Observer<Integer>() {

            // 观察者接收事件前，默认最先调用复写 onSubscribe（）
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("TAG","开始Subscribe");
                Toast.makeText(MainActivity.this, "开始Subscribe", Toast.LENGTH_SHORT).show();
            }


            // 当被观察者生产Next事件 & 观察者接收到时，会调用该复写方法 进行响应
            @Override
            public void onNext(Integer integer) {
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
        };


        //3订阅Subscribe,通过subscribe关联observable和observer
        observable.subscribe(observer);*/
      //不懂  新的分支



    }
}
