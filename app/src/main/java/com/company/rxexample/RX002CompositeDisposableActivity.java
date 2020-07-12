package com.company.rxexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RX002CompositeDisposableActivity extends AppCompatActivity {

    private DisposableObserver<String> numeroObserver;
    private DisposableObserver<String> numeroLetraObserver;

    private Observable<String> numeroObservable;
    private Observable<String> numeroLetraObservable;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_x002_composite_disposable);

        compositeDisposable = new CompositeDisposable();

        numeroObservable = Observable.just("1", "2", "3", "4");
        numeroLetraObservable = Observable.just("uno", "dos", "tres");

        numeroObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                Log.d("TAG1", "onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TAG1", "onError: ");

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "onComplete: ");

            }
        };

        numeroLetraObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                Log.d("TAG1", "onNextLetra: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("TAG1", "onErrorLetra: ");

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "onCompleteLetra: ");

            }
        };

        //  Permite agrupar observables para despues sea mas facil desecharlos en conjunto
        compositeDisposable.add(
                numeroObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(numeroObserver));

        compositeDisposable.add(
                numeroLetraObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(numeroLetraObserver));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}