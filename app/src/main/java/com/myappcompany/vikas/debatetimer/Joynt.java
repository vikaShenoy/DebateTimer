package com.myappcompany.vikas.debatetimer;

import java.util.ArrayList;

public class Joynt extends Format {

    public Joynt() {
        super("Joynt",8,600, true);
        ArrayList<String> speakers = new ArrayList<String>();
        speakers.add("1st Aff");
        speakers.add("1st Neg");
        speakers.add("2nd Aff");
        speakers.add("2nd Neg");
        speakers.add("3rd Aff");
        speakers.add("3rd Neg");
        speakers.add("Aff LR");
        speakers.add("Neg LR");
        this.setSpeakerNames(speakers);
    }
}
