package com.myappcompany.vikas.debatetimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SetupActivity extends AppCompatActivity {
    Format selectedFormat;

    public void findFormat(String formatTag) {

        switch(formatTag) {
            case("Claytons"):
                selectedFormat = new Claytons();
                break;
            case("Easters"):
                selectedFormat = new Easters();
                break;
            case("Joynt"):
                selectedFormat = new Joynt();
                break;
            case("BP"):
                selectedFormat = new BP();
                break;
            /*case("Custom"):
                selectedFormat = new Claytons();
                break;*/
        }
    }

    public void startTiming(View view) {
        String formatTag = view.getTag().toString();
        findFormat(formatTag);

        Intent iTimer = new Intent(SetupActivity.this, TimerActivity.class);
        iTimer.putExtra("Format", selectedFormat);
        startActivity(iTimer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
    }
}
