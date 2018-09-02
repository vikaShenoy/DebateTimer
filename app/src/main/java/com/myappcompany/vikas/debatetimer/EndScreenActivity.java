package com.myappcompany.vikas.debatetimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EndScreenActivity extends AppCompatActivity {
    ArrayList<Speaker> speakers;
    Format selectedFormat;

    TextView textViewMVP;
    TextView textViewMargin;
    TextView textViewWinner;
    TextView textViewTotalA;
    TextView textViewTotalN;

    public void exitScreen(View view) {
        Intent exit = new Intent(EndScreenActivity.this, SetupActivity.class);
        startActivity(exit);
    }

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
            affTotal += speakers.get(selectedFormat.getNumSpeakers() - 1).getScore();
            negTotal += speakers.get(selectedFormat.getNumSpeakers() - 2).getScore();
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

    public void calculateMVP() {

        Speaker bestSpeaker = speakers.get(0);
        for (Speaker speaker : speakers) {
            if (speaker.getScore() > bestSpeaker.getScore()) {
                bestSpeaker = speaker;
            }
        }
    }

    public void fillStats() {
        calculateTotals();
        calculateMVP();
    }

    /*public void fillStatsOld() {
        //Log.i("POO", "" + speakers.get(0).getScore());
        int a1 = speakers.get(0).getScore();
        int a2 = speakers.get(2).getScore();
        int a3 = speakers.get(4).getScore();
        int a4 = speakers.get(6).getScore();

        int n1 = speakers.get(1).getScore();
        int n2 = speakers.get(3).getScore();
        int n3 = speakers.get(5).getScore();
        int n4 = speakers.get(7).getScore();

        textViewAff1.setText(Integer.toString(speakers.get(0).getScore()));
        textViewAff2.setText(Integer.toString(speakers.get(2).getScore()));
        textViewAff3.setText(Integer.toString(speakers.get(4).getScore()));
        textViewAffLR.setText(Integer.toString(speakers.get(6).getScore()));

        textViewNeg1.setText(Integer.toString(speakers.get(1).getScore()));
        textViewNeg2.setText(Integer.toString(speakers.get(3).getScore()));
        textViewNeg3.setText(Integer.toString(speakers.get(5).getScore()));
        textViewNegLR.setText(Integer.toString(speakers.get(7).getScore()));

        Speaker bestSpeaker = speakers.get(0);
        for (Speaker speaker : speakers) {
            if (speaker.getScore() > bestSpeaker.getScore()) {
                bestSpeaker = speaker;
            }
        }
        textViewM.setText(bestSpeaker.getName());

        int totalAff = a1 + a2 + a3 + a4;
        int totalNeg = n1 + n2 + n3 + n4;
        int margin = 0;
        String winner = "";

        textViewAffTotal.setText(Integer.toString(totalAff));
        textViewNegTotal.setText(Integer.toString(totalNeg));

        if (totalAff > totalNeg) {
            margin = totalAff - totalNeg;
            winner = "Affirmative";
        } else {
            margin = totalNeg - totalAff;
            winner = "Negative";
        }
        textViewMargin.setText("+" + Integer.toString(margin));
        textViewWin.setText(winner);
    }*/

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
