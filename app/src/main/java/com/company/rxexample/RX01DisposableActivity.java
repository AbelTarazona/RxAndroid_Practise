package com.company.rxexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RX01DisposableActivity extends AppCompatActivity {

    //  Permitira desuscribir a la actividad con Rx
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_x01_disposable);

        //  1. Se crea Observable con Operator
        Observable<String> numerosObservable = Observable.just("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        //  2. Se crea Observer
        Observer<String> numerosObserver = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
                //  Al hacer la conexion entre el observable y el observer
                Log.d("TAG1", "isDispose: "+disposable.isDisposed());
                Log.d("TAG1", "onSubscribe: HILO> "+Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                //  Ira emitiendo cada valor (1..2..3..)
                Log.d("TAG1", "isDispose: "+disposable.isDisposed());
                Log.d("TAG1", "onNext: Numero>" + s + " []HILO " + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                //  En caso ocurra algun error
                Log.d("TAG1", "isDispose: "+disposable.isDisposed());
                Log.d("TAG1", "onError: HILO> "+Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                //  Cuando se ha completado de emitir todos los datos
                Log.d("TAG1", "isDispose: "+disposable.isDisposed());
                Log.d("TAG1", "onComplete: HILO> "+Thread.currentThread().getName());
            }
        };

        //  3. Se hace el Subscribe y se asigna Schedulers
        numerosObservable
                .subscribeOn(Schedulers.io()) // En que hilo queremos que se ejecute el Observable
                .observeOn(AndroidSchedulers.mainThread()) // En que hilo queremos se ejecute el Observer
                .subscribe(numerosObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG1", "isDispose: "+disposable.isDisposed());
        disposable.dispose(); // Elimina la suscripcion entre el Observer y Observable
        Log.d("TAG1", "isDispose: "+disposable.isDisposed());
        Log.d("TAG1", "onDestroy: Desechamos suscripcion []HILO " + Thread.currentThread().getName());
    }
}