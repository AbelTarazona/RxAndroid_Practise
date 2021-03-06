package com.company.rxexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnRX00Introduccion).setOnClickListener(v -> startActivity(new Intent(this, RX00IntroActivity.class)));
        findViewById(R.id.btnRX01Disposable).setOnClickListener(v -> startActivity(new Intent(this, RX01DisposableActivity.class)));
        findViewById(R.id.btnRX02CompositeDisposable).setOnClickListener(v -> startActivity(new Intent(this, RX002CompositeDisposableActivity.class)));
        findViewById(R.id.btnRX03Operadores).setOnClickListener(v -> startActivity(new Intent(this, RX03OperadoresActivity.class)));
    }
}