package com.myappcompany.vikas.debatetimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Vikas Shenoy
 * The screen shown once all the speakers have finished. Displays stats on the team scores, who won,
 * and by how much. Resets back to the setup activity once the user clicks the finish button.
 */
public class EndScreenActivity extends AppCompatActivity {
    ArrayList<Speaker> speakers;
    Format selectedFormat;

    TextView textViewMVP;
    TextView textViewMargin;
    TextView textViewWinner;
    TextView textViewTotalA;
    TextView textViewTotalN;

    /**
     * Starts the setup activity.
     * @param view The finish button, clicked to proceed.
     */
    public void exitScreen(View view) {
        Intent exit = new Intent(EndScreenActivity.this, SetupActivity.class);
        startActivity(exit);
    }

    /**
     * Calculates each team's total points. Calculates the margin, and displays these totals,
     * the margin, and the winner, in the relevant textviews.
     */
    public void calculateTotals() {
        int affTotal = 0;
        int negTotal = 0;
        String name = selectedFormat.getName();

        if (name.equals("BP")) {
            int og = speakers.get(0).getScore() + speakers.get(2).getScore();
            int oo = speakers.get(1).getScore() + speakers.get(3).getScore();
            int cg = speakers.get(4).getScore() + speakers.get(6).getScore();
            int co = speakers.get(5).getScore() + speakers.get(7).getScore();
            textViewTotalA.setText(Integer.toString(og) + "/" + Integer.toString(cg));
            textViewTotalN.setText(Integer.toString(oo) + "/" + Integer.toString(co));

            if (og > oo & og > cg & og > co) {
                textViewWinner.setText("Opening Government");
            } else if (oo > og & oo > cg & oo > co) {
                textViewWinner.setText("Opening Opposition");
            } else if (cg > og & cg > oo & cg > co) {
                textViewWinner.setText("Closing Government");
            } else if (co > og & co > oo & co > cg) {
                textViewWinner.setText("Closing Opposition");
            } else {
                textViewWinner.setText("Multiple");
            }
            textViewMargin.setText("-");

        } else {
            for (int i = 0; i < (selectedFormat.getNumSpeakers() - 1); i++) {
                if (i % 2 == 0) {
                    affTotal += speakers.get(i).getScore();
                } else {
                    negTotal += speakers.get(i).getScore();
                }
            }
            if (selectedFormat.getHasLR()) {
                affTotal += speakers.get(selectedFormat.getNumSpeakers() - 1).getScore();
                negTotal += speakers.get(selectedFormat.getNumSpeakers() - 2).getScore();
            } else {
                affTotal += speakers.get(selectedFormat.getNumSpeakers() - 2).getScore();
                negTotal += speakers.get(selectedFormat.getNumSpeakers() - 1).getScore();
            }

            int margin = 0;
            if (affTotal > negTotal) {
                textViewWinner.setText("Affirmative");
                margin = affTotal - negTotal;
            } else if (affTotal == negTotal) {
                textViewWinner.setText("Scores tied");
            } else {
                textViewWinner.setText("Negative");
                margin = negTotal - affTotal;
            }
            textViewMargin.setText("+" + Integer.toString(margin));

            textViewTotalA.setText(Integer.toString(affTotal));
            textViewTotalN.setText(Integer.toString(negTotal));
        }

    }

    /**
     * Checks the speaker list for the person with the highest score. Displays their name in the
     * MVP textview.
     */
    public void calculateMVP() {

        Speaker bestSpeaker = speakers.get(0);
        for (Speaker speaker : speakers) {
            if (speaker.getScore() > bestSpeaker.getScore()) {
                bestSpeaker = speaker;
            }
        }
    }

    /**
     * Fills in the textviews with all the relevant statistics.
     */
    public void fillStats() {
        calculateTotals();
        calculateMVP();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        speakers = getIntent().getParcelableArrayListExtra("key");
        selectedFormat = getIntent().getParcelableExtra("Format");

        textViewMVP = (TextView) findViewById(R.id.textViewMVP);
        textViewMargin = (TextView) findViewById(R.id.textViewMargin);
        textViewWinner = (TextView) findViewById(R.id.textViewWinner);

        textViewTotalA = (TextView) findViewById(R.id.textViewTotalA);
        textViewTotalN = (TextView) findViewById(R.id.textViewTotalN);

        fillStats();
    }
}
