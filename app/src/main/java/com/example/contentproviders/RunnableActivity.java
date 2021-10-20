package com.example.contentproviders;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RunnableActivity extends AppCompatActivity {

    TextView textView;
    int i;

    ProgressDialog progressDialog;

    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runnable);
        textView=findViewById(R.id.MyText);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);


    }

    public void run(View view) {
        //Toast.makeText(this, "runnable finish", Toast.LENGTH_SHORT).show();

        progressDialog.setMessage("File downloading ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.show();


        //reset progress bar and filesize status
        progressBarStatus = 0;
        fileSize = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100) {
                    // performing operation
                    progressBarStatus = doOperation();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Updating the progress bar
                    progressBarHandler.post(new Runnable() {
                        public void run() {
                            progressDialog.setProgress(progressBarStatus);
                        }
                    });
                }

                if (progressBarStatus >= 100) {
                    // sleeping for 1 second after operation completed
                    try {
                        Thread.sleep(1000);
                        progressBarHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                textView.setText("Completed Task");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // close the progress bar dialog
                    progressDialog.dismiss();
                }
            }
        }).start();



        /*Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {


                synchronized (this){
                    try {

                        for(i=0;i<10;i++)
                        {
                            wait(5000);
                            Log.i("Value of i ","= "+i);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    textView.setText(""+i);
                                   // Toast.makeText(RunnableActivity.this, "Runnbale Finish ", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }



                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RunnableActivity.this, "Afetr 10sec Runnbale Finish ", Toast.LENGTH_SHORT).show();

                    }
                },10000);
                Log.i("TAG1","Runnbale Finish :- "+Thread.currentThread().getName());

            }
        };
       // runnable.run();

        Thread thread=new Thread(runnable);
        thread.start();
        Log.i("TAG2",""+Thread.currentThread().getName());

        //Toast.makeText(RunnableActivity.this, ""+Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
*/


    }

    public int doOperation() {
        //The range of ProgressDialog starts from 0 to 10000
        while (fileSize <= 10000) {
            fileSize++;
            if (fileSize == 1000) {
                return 10;
            } else if (fileSize == 2000) {
                return 20;
            } else if (fileSize == 3000) {
                return 30;
            } else if (fileSize == 4000) {
                return 40; // you can add more else if
            }
            if (fileSize == 5000) {
                return 50;
            } else if (fileSize == 6000) {
                return 60;
            } else if (fileSize == 7000) {
                return 70;
            } else if (fileSize == 8000) {
                return 80; // you can add more else if
            }
            else if (fileSize == 9000) {
            return 90; // you can add more else if
            }



         /* else {
                return 100;
            }*/
        }//end of while
        return 100;
    }//end of doOperation
}