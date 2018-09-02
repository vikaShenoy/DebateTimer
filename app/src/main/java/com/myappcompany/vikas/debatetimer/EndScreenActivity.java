package com.myappcompany.vikas.debatetimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class EndScreenActivity extends AppCompatActivity {
    ArrayList<Speaker> speakers;
    TextView textViewAff1;
    TextView textViewAff2;
    TextView textViewAff3;
    TextView textViewAffLR;
    TextView textViewNeg1;
    TextView textViewNeg2;
    TextView textViewNeg3;
    TextView textViewNegLR;

    TextView textViewAffTotal;
    TextView textViewNegTotal;
    TextView textViewM;
    TextView textViewMargin;
    TextView textViewWin;

    public void exitScreen(View view) {
        Intent exit = new Intent(EndScreenActivity.this, SetupActivity.class);
        startActivity(exit);
    }

    public void fillStats() {
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        speakers = getIntent().getParcelableArrayListExtra("key");

        textViewAff1 = (TextView) findViewById(R.id.textViewAff1);
        textViewAff2 = (TextView) findViewById(R.id.textViewAff2);
        textViewAff3 = (TextView) findViewById(R.id.textViewAff3);
        textViewAffLR = (TextView) findViewById(R.id.textViewAffLR);

        textViewNeg1 = (TextView) findViewById(R.id.textViewNeg1);
        textViewNeg2 = (TextView) findViewById(R.id.textViewNeg2);
        textViewNeg3 = (TextView) findViewById(R.id.textViewNeg3);
        textViewNegLR = (TextView) findViewById(R.id.textViewNegLR);

        textViewAffTotal = (TextView) findViewById(R.id.textViewAffTotal);
        textViewNegTotal = (TextView) findViewById(R.id.textViewNegTotal);
        textViewM = (TextView) findViewById(R.id.textViewM);
        textViewMargin = (TextView) findViewById(R.id.textViewMargin);
        textViewWin = (TextView) findViewById(R.id.textViewWinner);

        fillStats();
    }
}
