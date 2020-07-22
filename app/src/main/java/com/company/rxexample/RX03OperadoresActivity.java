package com.company.rxexample;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RX03OperadoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_x03_operadores);
        /*
        * Operadores que crean observables
        * */
        //probarJust();
        //probarJustArray();
        //probarFromArray();
        //probarRange();
        //probarRepeat();
        //probarInterval();
        //probarCreate();
        //probarCreateException();
        //probarCreateLargaDuracion();
        probarLambda();
    }

    private void probarLambda() {
        Sumar sumar = (a, b) -> a + b;
    }

    // classic
    Sumar sumar = new Sumar() {
        @Override
        public int apply(int a, int b) {
            int res;
            res = a + b;
            return res;
        }
    };

    private void probarCreateLargaDuracion() {
        Log.d("TAG", "--------------Create Larga Duracion--------------");
        Observable
                .create((ObservableOnSubscribe<String>) emitter -> {
                    try {
                        Log.d("TAG", "Hilo: "+Thread.currentThread().getName());
                        emitter.onNext(largaDuracion());
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        s   -> Log.d("TAG1", "onNext: "+s),
                        e   -> Log.d("TAG1", "onError: "+e.getMessage()),
                        ()  -> Log.d("TAG1", "onComplete")
                );
    }

    private String largaDuracion() { // Simula un caso de uso con larga duracion
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Terminado";
    }

    private void probarCreateException() {
        Log.d("TAG", "--------------Create Exception--------------");
        Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> {
                    try {
                        emitter.onNext(15/3);
                        emitter.onNext(3/0);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer s) {
                        Log.d("TAG", "onNext: "+s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("TAG", "onError: "+e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void probarInterval() {
        Log.d("TAG", "--------------Interval--------------");
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        Log.d("TAG1", "onNext: " + aLong);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void probarCreate() {
        Log.d("TAG", "--------------Create--------------");
        Observable
                .create((ObservableOnSubscribe<String>) emitter -> {
                    try {
                        Log.d("TAG", "Hilo: "+Thread.currentThread().getName());
                        emitter.onNext("A");
                        emitter.onNext("B");
                        emitter.onNext("C");
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d("TAG", "Hilo: "+Thread.currentThread().getName());
                        Log.d("TAG", "onNext: "+s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void probarRepeat() {
        Log.d("TAG", "--------------Repeat--------------");
        Observable.range(4, 3)
                .repeat(4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Integer>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Integer s) {
                                Log.d("TAG", "onNext: "+s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );
    }

    private void probarRange() {
        Log.d("TAG", "--------------Range--------------");
        Observable.range(4, 9)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Integer>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Integer s) {
                                Log.d("TAG", "onNext: "+s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );
    }

    private void probarFromArray() {
        Log.d("TAG", "--------------FromArray--------------");
        String[] numeros = {"1", "2", "3", "4"};
        Observable.fromArray(numeros)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                Log.d("TAG", "onNext: "+s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );
    }

    private void probarJustArray() {
        Log.d("TAG", "--------------JustArray--------------");
        String[] numeros = {"1", "2", "3", "4"};
        Observable.just(numeros)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<String[]>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(String @NonNull [] strings) {
                                Log.d("TAG", "onNext: "+strings.length);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );

    }

    private void probarJust() {
        Log.d("TAG", "--------------Just--------------");
        Observable.just("1", "2", "3", "4")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                Log.d("TAG", "onNext: "+s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );
    }
}