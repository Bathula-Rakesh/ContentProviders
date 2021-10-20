package com.example.contentproviders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SpalashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                synchronized (this)
                {
                    try {
                        wait(4000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(SpalashActivity.this,RunnableActivity.class));
                    }
                });

            }
        };
       // runnable.run();

        Thread thread=new Thread(runnable);
        thread.start();
    }
}