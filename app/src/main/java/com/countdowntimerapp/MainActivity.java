package com.countdowntimerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timeSlider;
    TextView timeText;
    ImageView playPause;
    ImageView backImage;
    ImageView forwardImage;
    AppCompatButton mReset;
    boolean timerOn=false;
    final static int maxTimeInSec=1800;
    final static int minTimeInSec=0;
    final static String initialTime="00:00";
    CountDownTimer countDownTimer;


    //reset Timer
    public void resetTimer()
    {
        timeText.setText(initialTime);
        countDownTimer.cancel();
        timeSlider.setProgress(minTimeInSec);
        timeSlider.setEnabled(true);
        forwardImage.setVisibility(View.VISIBLE);
        backImage.setVisibility(View.VISIBLE);
        playPause.setImageResource(R.drawable.play);
        timerOn=false;
    }


    //update Time Text
    public void updateTimer(int timeInSeconds)
    {
        int minutes;
        int seconds;

        minutes=timeInSeconds/60;
        timeInSeconds%=60;

        seconds=timeInSeconds;

        String minutesString="",secondsString="";

        if(minutes<=9)
            minutesString="0"+minutes;
        else
            minutesString=Integer.toString(minutes);
        if(seconds<=9)
            secondsString="0"+seconds;
        else
            secondsString=Integer.toString(seconds);


        timeText.setText(minutesString+":"+secondsString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeSlider=(SeekBar) findViewById(R.id.timeSlider);
        timeText=(TextView) findViewById(R.id.timeTextView);
        playPause=(ImageView) findViewById(R.id.playPauseImage);
        backImage=(ImageView) findViewById(R.id.backImage);
        forwardImage=(ImageView) findViewById(R.id.forwardImage);
        mReset=(AppCompatButton) findViewById(R.id.resetButton);


        timeSlider.setMax(maxTimeInSec);
        timeSlider.setProgress(minTimeInSec);


        //seekBar functionality
        timeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //play and pause Image functionality
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerOn==false)
                {
                    timerOn=true;
                    timeSlider.setEnabled(false);
                    forwardImage.setVisibility(View.INVISIBLE);
                    backImage.setVisibility(View.INVISIBLE);
                    playPause.setImageResource(R.drawable.pause);
                    countDownTimer=new CountDownTimer(timeSlider.getProgress()*1000+100,1000) {
                        @Override
                        public void onTick(long l) {
                            timeSlider.setProgress((int)l/1000);
                            updateTimer((int)l/1000);

                        }

                        @Override
                        public void onFinish() {
                            resetTimer();
                            MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                            mediaPlayer.start();
                        }
                    }.start();
                }
                else
                {
                    timerOn=false;
                    timeSlider.setEnabled(true);
                    forwardImage.setVisibility(View.VISIBLE);
                    backImage.setVisibility(View.VISIBLE);
                    playPause.setImageResource(R.drawable.play);
                    countDownTimer.cancel();
                }
            }
        });


        //reset Button functionality
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });


        //forward Image finctionality
        forwardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSlider.setProgress(timeSlider.getProgress()+1);
            }
        });


        //back Image finctionality
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSlider.setProgress(timeSlider.getProgress()-1);
            }
        });
    }
}
