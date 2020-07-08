package com.rxjava2.android.samples.ui.operators;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.rxjava2.android.samples.R;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DeferExampleActivity2 extends AppCompatActivity {

    Disposable disposable1,disposable2,disposable3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        MyRectangle myRectangle1 = new MyRectangle("InitialValue-1");
        Observable<String> width1 = myRectangle1.getMyObservable();
        myRectangle1.setWidth("ChangedValue-1");
        disposable1 = width1.subscribe(new Consumer<String>() {
            @Override
            public void accept(String x) throws Exception {
                System.out.println("omertest1 - " + x);
            }
        });

        MyRectangle myRectangle2 = new MyRectangle("InitialValue-2");
        Observable<String> width2 = myRectangle2.getMyObservable2();
        myRectangle2.setWidth("ChangedValue-2");
        disposable2 = width2.subscribe(new Consumer<String>() {
            @Override
            public void accept(String x) throws Exception {
                System.out.println("omertest2 - " + x);
            }
        });

        MyRectangle myRectangle3 = new MyRectangle("InitialValue-3");
        Observable<String> width3 = myRectangle3.getMyObservable3();
        myRectangle3.setWidth("ChangedValue-3");
        disposable3 = width3.subscribe(new Consumer<String>() {
            @Override
            public void accept(String x) throws Exception {
                System.out.println("omertest3 - " + x);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable1.dispose();
        disposable2.dispose();
        disposable3.dispose();
    }
}

class MyRectangle {
    private String width;

    MyRectangle(String width){
        this.width = width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Observable<String> getMyObservable() {
        return Observable.just(width);
    }

    public Observable<String> getMyObservable2() {
        return Observable.defer(
                () -> Observable.just(width)
        );
    }

    public Observable<String> getMyObservable3() {
        return Observable.create(subscriber -> {
            subscriber.onNext(width);
            subscriber.onComplete();
        });
    }
}