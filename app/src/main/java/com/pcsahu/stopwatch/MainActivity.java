package com.pcsahu.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton start, stop,reset;
    private TextView textView;
    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        reset = findViewById( R.id.reset );
        stop = findViewById(R.id.stop);
        textView = findViewById(R.id.timetv);
        handler = new Handler();

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running && !wasRunning){
                    running = true;
                    runTimer();
                }
                else if(wasRunning){
                    running = true;
                    wasRunning = false;
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                wasRunning = true;
            }
        });
        reset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running){
                    wasRunning = true;
                }else{
                    wasRunning = false;
                }
                running = false;
                textView.setText( "00:00:00" );
                seconds = 0;

            }
        } );

//        runTimer();
    }

    private void runTimer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;


                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
                textView.setText(time);

                if (running) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }
}
