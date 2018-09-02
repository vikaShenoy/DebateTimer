package com.myappcompany.vikas.debatetimer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;

public class TimerActivity extends AppCompatActivity {
    TextView labelTextView;
    TextView timerTextView;
    TextView formatTextView;
    TextView speakerTextView;

    EditText nameEditText;
    EditText scoreEditText;

    Button pauseButton;
    Button startButton;
    Button endButton;
    Button continueButton;

    CountDownTimer timer;
    int totalTime;
    int currentTime;
    int numSpeakers;
    int currentSpeakerNum;
    String formatName;
    ArrayList<String> speakersNames;
    boolean paused = true;
    boolean hasLR;

    Format selectedFormat;
    ArrayList<Speaker> speakerList;


    public void resetScreen() {
        if (currentSpeakerNum == 0) {
            speakerTextView.setText("\n" + speakersNames.get(1));
            currentSpeakerNum += 2;
        } else {
            speakerTextView.setText("\n" + speakersNames.get(currentSpeakerNum));
            currentSpeakerNum += 1;
        }


        startButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
        endButton.setVisibility(View.VISIBLE);
        continueButton.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.VISIBLE);

        nameEditText.setVisibility(View.INVISIBLE);
        nameEditText.getText().clear();
        scoreEditText.setVisibility(View.INVISIBLE);
        scoreEditText.getText().clear();

        if ((numSpeakers - currentSpeakerNum) <= 1) {
            updateTimer(totalTime / 2);
        } else {
            updateTimer(totalTime);
        }
        currentTime = totalTime;

    }

    public void saveStats(View view) {
        String name = nameEditText.getText().toString();
        int score = 0;
        if (scoreEditText.getText().toString().trim().length() != 0) {
            score = Integer.parseInt(scoreEditText.getText().toString());
        }
        if (name.trim().length() == 0) {
            name = "";
        }
        Speaker speaker = new Speaker(name, score);
        Toast.makeText(this, speaker.getName(), Toast.LENGTH_SHORT).show();
        speakerList.add(speaker);

        if (currentSpeakerNum == numSpeakers) {
            //Log.i("POO", "YOTE");
            Intent nextScreen = new Intent(TimerActivity.this, EndScreenActivity.class);
            nextScreen.putParcelableArrayListExtra("key", speakerList);
            startActivity(nextScreen);
        } else {
            resetScreen();
        }

    }

    public void endSpeaker(View view) {
        startButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        endButton.setVisibility(View.INVISIBLE);
        continueButton.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.INVISIBLE);

        nameEditText.setVisibility(View.VISIBLE);
        scoreEditText.setVisibility(View.VISIBLE);
    }

    public void pauseTimer(View view) {
        endButton.setVisibility(View.VISIBLE);
        if (!paused) {
            startButton.setVisibility(View.VISIBLE);
            timer.cancel();
            updateTimer(currentTime);
            paused = true;
            pauseButton.setText("Reset");
        // If the timer is paused, button becomes a reset.
        } else {
            currentTime = totalTime;
            updateTimer(currentTime);
            timer.cancel();
            pauseButton.setText("Stop");
        }
    }

    public void startTimer(View view) {
        startButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
        endButton.setVisibility(View.INVISIBLE);
        pauseButton.setText("Stop");

        int timeLeft = 0;
        if (paused) {
            timeLeft = currentTime;
        } else if (numSpeakers - currentSpeakerNum <= 1) {
            timeLeft = totalTime / 2;
        } else {
            timeLeft = totalTime;
        }
        paused = false;

        timer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long l) {
                updateTimer((int) l / 1000);
            }
            @Override
            public void onFinish() {
                endSpeaker(continueButton);
                //MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.truefire);
                //player.start();
            }
        }.start();
    }

    public void updateTimer(int time) {
        int minutes = time / 60;
        int seconds = time - (minutes * 60);
        String secondString = Integer.toString(seconds);
        String minuteString = Integer.toString(minutes);

        if (seconds < 10) {
            secondString = "0" + Integer.toString(seconds);
        }

        currentTime = time;
        timerTextView.setText(minuteString + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        formatTextView = (TextView) findViewById(R.id.formatTextView);
        speakerTextView = (TextView) findViewById(R.id.speakerTextView);

        pauseButton = (Button) findViewById(R.id.pauseButton);
        startButton = (Button) findViewById(R.id.startButton);
        endButton = (Button) findViewById(R.id.endButton);
        continueButton = (Button) findViewById(R.id.continueButton);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        scoreEditText = (EditText) findViewById(R.id.scoreEditText);

        //calculateTime();
        Bundle extras = getIntent().getExtras();
        selectedFormat = extras.getParcelable("Format");
        formatName = selectedFormat.getName();
        totalTime = selectedFormat.getSpeakingTime();
        numSpeakers = selectedFormat.getNumSpeakers();
        hasLR = selectedFormat.getHasLR();

        speakersNames = selectedFormat.getSpeakerNames();
        currentTime = totalTime;
        speakerList = new ArrayList<Speaker>();
        currentSpeakerNum = 0;
        formatTextView.setText("\n" + formatName);
        updateTimer(totalTime);
        currentTime = totalTime;
        speakerTextView.setText("\n" + speakersNames.get(currentSpeakerNum));

        endButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        continueButton.setVisibility(View.INVISIBLE);

    }
}
