package com.myappcompany.vikas.debatetimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;

public class CustomActivity extends AppCompatActivity {
    Spinner numSpinner;
    Spinner timeSpinner;
    CheckBox checkbox;

    public void createFormat(View view) {
        int numSpeakers = (Integer.parseInt(numSpinner.getSelectedItem().toString())) * 2;
        int speakingTime = Integer.parseInt(timeSpinner.getSelectedItem().toString());

        boolean hasLR = false;
        if (checkbox.isChecked()) {
            hasLR = true;
            numSpeakers += 2;
        }

        Format customFormat = new Format("Custom", numSpeakers, speakingTime * 60 , hasLR);
        ArrayList<String> speakerNames = new ArrayList<String>();
        for (int i = 1 ; i <= numSpeakers ; i++) {
            String name = Integer.toString(i);
            speakerNames.add(name);
        }

        customFormat.setSpeakerNames(speakerNames);

        Intent iTimer = new Intent(CustomActivity.this, TimerActivity.class);
        iTimer.putExtra("Format", customFormat);
        startActivity(iTimer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        numSpinner = findViewById(R.id.spinnerSpeakerNum);
        timeSpinner = findViewById(R.id.spinnerTime);
        checkbox = findViewById(R.id.checkbox);

        String[] numOptions = new String[]{"2", "3"};
        String[] timeOptions = new String[]{"4", "5", "6", "7", "8", "9", "10"};

        ArrayAdapter<String> adapterNum = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, numOptions);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeOptions);

        numSpinner.setAdapter(adapterNum);
        timeSpinner.setAdapter(adapterTime);
    }
}
