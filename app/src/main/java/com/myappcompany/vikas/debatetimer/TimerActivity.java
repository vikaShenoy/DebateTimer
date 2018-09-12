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

/**
 * @author Vikas Shenoy
 * The timer activity is used to keep track of time during the speaker's speech.
 * Sounds are played to signal protected time. At the completion of a speech, prompts are displayed
 * for the speakers name and score, and then the next speaker begins.
 */
public class TimerActivity extends AppCompatActivity {
    TextView timerTextView;
    TextView formatTextView;
    TextView speakerTextView;
    EditText nameEditText;
    EditText scoreEditText;
    Button pauseButton;
    Button startButton;
    Button endButton;
    Button continueButton;

    int totalTime;
    int currentTime;
    int numSpeakers;
    int currentSpeakerNum;
    String formatName;
    ArrayList<String> speakersNames;

    CountDownTimer timer;
    boolean paused = true;
    Format selectedFormat;
    ArrayList<Speaker> speakerList;

    /**
     * Sets the time back to total time, and resets the buttons so that start/stop/next are
     * displayed. Changes the title bar to indicate which speaker is speaking.
     */
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

        if ((numSpeakers - currentSpeakerNum) <= 1 & selectedFormat.getHasLR()) {
            updateTimer(totalTime / 2);
        } else {
            updateTimer(totalTime);
        }
        currentTime = totalTime;

    }

    /**
     * Called when 'continue' button is clicked. User enters speaker name and score. New speaker
     * object created with these attributes, and is added to the speaker list. Then either resets
     * the screen or calls the end screen activity.
     * @param view The continue button, clicked to proceed.
     */
    public void saveStats(View view) {
        String name = nameEditText.getText().toString();
        int score = 0;
        boolean validEntry = true;
        if (scoreEditText.getText().toString().trim().length() != 0) {
            try {
                score = Integer.parseInt(scoreEditText.getText().toString());
            } catch (Exception e) {
                Toast.makeText(this, "Enter a number for score.", Toast.LENGTH_LONG).show();
                validEntry = false;
            }
        }
        if (name.trim().length() == 0) {
            name = "";
        }
        Speaker speaker = new Speaker(name, score);
        speakerList.add(speaker);

        //Start the next screen
        if (validEntry) {
            if (currentSpeakerNum == numSpeakers) {
                Intent nextScreen = new Intent(TimerActivity.this, EndScreenActivity.class);
                nextScreen.putExtra("Format", selectedFormat);
                nextScreen.putParcelableArrayListExtra("key", speakerList);
                startActivity(nextScreen);
            } else {
                resetScreen();
            }
        }

    }

    /**
     * Called when the 'next' button clicked. Hides buttons and brings up the fields to enter
     * the speakers' name and score.
     * @param view The 'next' button, clicked to proceed.
     */
    public void endSpeaker(View view) {
        startButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        endButton.setVisibility(View.INVISIBLE);
        continueButton.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.INVISIBLE);

        nameEditText.setVisibility(View.VISIBLE);
        scoreEditText.setVisibility(View.VISIBLE);
    }

    /**
     * Called when the user stops the timer. Pauses the timer and sets the pause button to say either
     * 'reset' or 'stop'.
     * @param view The 'pause' button, clicked to proceed.
     */
    public void pauseTimer(View view) {
        endButton.setVisibility(View.VISIBLE);
        // If the timer is paused, button becomes a reset.
        if (!paused) {
            startButton.setVisibility(View.VISIBLE);
            timer.cancel();
            updateTimer(currentTime);
            paused = true;
            pauseButton.setText("Reset");
        } else {
            currentTime = totalTime;
            updateTimer(currentTime);
            timer.cancel();
            pauseButton.setText("Stop");
        }
    }

    /**
     * Called when the user starts timing. Hides the start and end buttons. Halves the total time
     * if there are only two speakers left (leader's replies).
     * Starts the countdown timer, using the current time if timer pause, or total time if not.
     * @param view The 'start' button.
     */
    public void startTimer(View view) {
        startButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
        endButton.setVisibility(View.INVISIBLE);
        pauseButton.setText("Stop");

        int timeLeft = 0;
        if (paused) {
            timeLeft = currentTime;
        } else if ((numSpeakers - currentSpeakerNum) <= 1 & selectedFormat.getHasLR()) {
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
                MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.dinosaur_roar);
                player.start();
            }
        }.start();
    }

    /**
     * Called on each timer click. Updates the textual display of time left.
     * @param time The number of seconds to display on the clock.
     */
    public void updateTimer(int time) {
        int minutes = time / 60;
        int seconds = time - (minutes * 60);
        String secondString = Integer.toString(seconds);
        String minuteString = Integer.toString(minutes);

        if (seconds < 10) {
            secondString = "0" + Integer.toString(seconds);
        }

        if (time == (totalTime - 60) | time == 60) {
            MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.japanese_bell);
            player.start();
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

        Bundle extras = getIntent().getExtras();
        selectedFormat = extras.getParcelable("Format");
        formatName = selectedFormat.getName();
        totalTime = selectedFormat.getSpeakingTime();
        numSpeakers = selectedFormat.getNumSpeakers();

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
