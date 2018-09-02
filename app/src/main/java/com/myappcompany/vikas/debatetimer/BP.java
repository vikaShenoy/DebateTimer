package com.myappcompany.vikas.debatetimer;

import java.util.ArrayList;

public class BP extends Format {

    public BP() {
        super("BP", 8, 420, false);
        ArrayList<String> speakers = new ArrayList<String>();
        speakers.add("OG 1");
        speakers.add("OO 1");
        speakers.add("OG 2");
        speakers.add("OO 2");
        speakers.add("CG 1");
        speakers.add("CO 1");
        speakers.add("CG 2");
        speakers.add("CO 2");
        this.setSpeakerNames(speakers);
    }
}
