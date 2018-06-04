package com.zscdumin.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.textView)
    TextView mRxOperatorsText;
    @Bind(R.id.textView1)
    TextView textView1;

    public String TAG = "MainActivity";

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                //调用观察者的回调
//                emitter.onNext("我是");
//                emitter.onNext("RxJava");
//                emitter.onNext("简单示例");
//                emitter.onError(new Throwable("出错了"));
//                //emitter.onComplete(); //onCompleted和onError不会同时调用，只会调用其中之一
//            }
//        });
//
//        //创建观察者
//        Observer<String> observer = new Observer<String>() {
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete");
//            }
//
//            //onSubscribe()方法是最先调用的
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.i(TAG, "onSubscribe");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i(TAG, s);
//            }
//        };
//
//        //注册，将观察者和被观察者关联，将会触发OnSubscribe.call方法
//        observable.subscribe(observer);


        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                emitter.onNext(getResponse());
            }
        });

        //创建观察者
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String mResponse) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                textView1.setText(mResponse);
            }
        };
        //subscribeOn() 指定的是发送事件的线程, observeOn() 指定的是接收事件的线程.
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

    }

    //使用okhttp访问网上提供的接口，由于是同步get请求，需要在子线程进行
    private String getResponse() {
        String url = "https://www.zybuluo.com/mdeditor#1169029";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;

        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return "error";
        }
    }

}
