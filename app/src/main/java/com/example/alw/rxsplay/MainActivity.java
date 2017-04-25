package com.example.alw.rxsplay;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.example.alw.rxsplay.utils.BitmapUtils.returnBitmap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "playrxs";
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn10;
    Button btn11;
    Button btn12;
    Button btn13;
    Button btn14;
    ImageView showIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
        btn7 = (Button) findViewById(R.id.btn_7);
        btn8 = (Button) findViewById(R.id.btn_8);
        btn9 = (Button) findViewById(R.id.btn_9);
        btn10 = (Button) findViewById(R.id.btn_10);
        btn11 = (Button) findViewById(R.id.btn_11);
        btn12 = (Button) findViewById(R.id.btn_12);
        btn13 = (Button) findViewById(R.id.btn_13);
        btn14 = (Button) findViewById(R.id.btn_14);
        showIv = (ImageView) findViewById(R.id.showIv);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                createObservable();
                break;
            case R.id.btn_2:
                justShowImg();
                break;
            case R.id.btn_3:
                fromObservable();
                break;
            case R.id.btn_4:
                repeatObservable();
                break;
            case R.id.btn_5:
                rangeObservable();
                break;
            case R.id.btn_6:
                intervalObservable();
                break;
            case R.id.btn_7:
                timerObservable();
                break;
            case R.id.btn_8:
                filterObservable();
                break;
            case R.id.btn_9:
                takeObservable();
                break;
            case R.id.btn_10:
                takeLastObservable();
                break;
            case R.id.btn_11:
                distinctObservable();
                break;
            case R.id.btn_12:
                skipObservable();
                break;
            case R.id.btn_13:
                skipLastObservable();
                break;
            case R.id.btn_14:
                timeoutObservable();
                break;

        }
    }


    /**
     * author : David
     * date : 2017/4/25 16:39
     * des : interval关键字，轮询时间(第一个参数)为x,轮询时间单位（第二个参数）为n开始轮询处理事件.
     */
    private void intervalObservable() {
        Observable.interval(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.e(TAG, "call: " + aLong);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 16:44
     * des : timerObservable 定一定时间后才发射
     */
    private void timerObservable() {
        Observable.timer(4, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.e(TAG, "call: " + aLong);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 15:46
     * des : 创建被观察者和观察者，并让他们发生联系
     */
    private void createObservable() {
        // 通过create创建observable对象，在call中调用subscriber的onnext方法
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 0; i < 20; i++) {
                    subscriber.onNext("i = " + i);
                }
                subscriber.onCompleted();
            }
        });
        // 上面的代码我们已经构建了一个被观察者，我们接下来新建一个订阅者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext:" + s);
            }

            @Override
            public void onStart() {
                Log.e(TAG, "onStart");
            }
        };
        // 通过调用subscribe方法使被观察者和订阅者产生关联，一旦订阅就被观察者就开始发送消息
        observable.subscribe(subscriber);
    }

    /**
     * author : David
     * date : 2017/4/25 15:56
     * des : 子线程获取图片，主线程显示图片
     */
    private void justShowImg() {
        // 图片地址
        String url = "http://img.ivsky.com/img/tupian/pre/201612/26/jidujiao_caihui-001.jpg";
        // map变换将url转换成bitmap
        Observable.just(url).map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap call(String s) {
                // 通过url拿去bitmap
                return returnBitmap(s);
            }
        }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        showIv.setImageBitmap(bitmap);
                        showIv.setVisibility(View.VISIBLE);
                    }
                });
    }

    /**
     * author : David
     * date : 2017/4/25 16:17
     * des : 使用from创建被观察者
     */
    private void fromObservable() {
        List<String> fromList = new ArrayList<>();
        fromList.add("1");
        fromList.add("2");
        fromList.add("3");
        Observable.from(fromList).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: " + s);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 16:28
     * des : 重复操作符
     */
    private void repeatObservable() {
        List<Integer> integers = new ArrayList<>();
        integers.add(0);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        Observable.from(integers).repeat(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "call: " + integer);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 16:37
     * des : range，从指定的数字x开始发射n个数字
     */
    private void rangeObservable() {
        Observable.range(15, 5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e(TAG, "call: " + integer);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 16:54
     * des : 使用filter方法过滤内容中不需要的值
     */
    private void filterObservable() {
        Observable.just("A1", "B1", "A2", "C3").filter(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                // 条件筛选以“A”开始的内容
                return s.startsWith("A");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                // 打印结果
                Log.e(TAG, "call: " + s);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 16:56
     * des : take(n) 提取原数据前n个数据发射
     */
    private void takeObservable() {
        Observable.just("1", "2", "3", "4", "5").take(4).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                // 打印结果
                Log.e(TAG, "call: " + s);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 16:59
     * des : takeLast(n) 提取原数据倒数前n个数据发射
     */
    private void takeLastObservable() {
        Observable.just("1", "2", "3", "4", "5").takeLast(2).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                // 打印结果
                Log.e(TAG, "call: " + s);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 17:03
     * des : 将发射数据重复五遍再去重复
     */
    private void distinctObservable() {
        Observable.just("A1", "A1", "B1", "C1", "D1", "B1", "A1").repeat(5).distinct().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: " + s);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 17:06
     * des : skip(n) 提取原数据前n个数据跳过不发射
     */
    private void skipObservable() {
        Observable.just("1", "2", "3", "4", "5").skip(4).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                // 打印结果
                Log.e(TAG, "call: " + s);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 17:08
     * des :  skipLast(n)筛选出原始数据中从倒数前n个数据跳过不发射
     */
    private void skipLastObservable() {
        Observable.just("1", "2", "3", "4", "5").skipLast(2).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                // 打印结果
                Log.e(TAG, "call: " + s);
            }
        });
    }

    /**
     * author : David
     * date : 2017/4/25 17:11
     * des : 在指定的时间间隔内Observable不发射值的话,监听的原始的Observable时就会触发onError()函数
     */


    private void timeoutObservable() {
        // 指定4秒后再发射，再指定3秒后如果未发射就触发onError函数
        Observable.just("1", "2").timer(4, TimeUnit.SECONDS).timeout(3, TimeUnit.SECONDS).subscribe(
                new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "onNext: " + aLong);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (showIv == null) {
            return;
        }
        switch (showIv.getVisibility()) {
            case View.VISIBLE:
                showIv.setVisibility(View.GONE);
                break;
            default:
                super.onBackPressed();
                break;
        }
    }
}
