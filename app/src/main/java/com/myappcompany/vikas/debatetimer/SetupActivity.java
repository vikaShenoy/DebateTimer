package com.myappcompany.vikas.debatetimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @author Vikas Shenoy
 * The setup activity displays the different debate formats the user can choose. Once a format is
 * selected, the timer activity starts. If the 'custom' option is chose, the custom activity starts.
 */

public class SetupActivity extends AppCompatActivity {
    Format selectedFormat;

    /**
     * Sets the selected format to the one chose by the user.
     * @param formatTag The string tag taken from the format button the user clicked.
     */
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
        }
    }

    /**
     * The user clicks on their chosen format. The tag is taken, and the applicable format object
     * created. Timer or custom activity is then started.
     * @param view The format button which was clicked.
     */
    public void startTiming(View view) {
        String formatTag = view.getTag().toString();
        if (formatTag.equals("Custom")) {
            Intent custom = new Intent(SetupActivity.this, CustomActivity.class);
            startActivity(custom);
        } else {
            findFormat(formatTag);
            Intent iTimer = new Intent(SetupActivity.this, TimerActivity.class);
            iTimer.putExtra("Format", selectedFormat);
            startActivity(iTimer);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
    }
}
