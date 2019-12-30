package com.example.example3introducingoperator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private Disposable disposable;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable nameObservable = getNameObservable();

        Observer nameObserver = getNameObserver();

        //Observer dang ky Observable
        nameObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate() {
                    @Override
                    public boolean test(Object o) throws Exception {
                        return o.toString().startsWith("T");
                    }
                })
                .subscribeWith(nameObserver);
    }

    private Observer getNameObserver() {
        return new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
                disposable = d;
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "onNext: " + o);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
    }

    //tao Observable de phat du lieu
    private Observable<String> getNameObservable() {
        return Observable.fromArray("Hai", "Vinh", "Duc", "Ta", "Hai", "Son", "Namnb", "Phuong",
                "Nga", "Ly", "Dieu", "Tan", "Loan", "Hanh", "nambn", "Quyet", "Tuanntq", "Tuanda", "Tuanka");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //huy dang ky
        disposable.dispose();
    }
}
