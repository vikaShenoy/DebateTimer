package com.myappcompany.vikas.debatetimer;

import java.util.ArrayList;

public class Easters extends Format {

    public Easters() {
        super("Easters",6,360, true);
        ArrayList<String> speakers = new ArrayList<String>();
        speakers.add("1st Aff");
        speakers.add("1st Neg");
        speakers.add("2nd Aff");
        speakers.add("2nd Neg");
        speakers.add("Neg LR");
        speakers.add("Aff LR");
        this.setSpeakerNames(speakers);
    }
}
