package uk.co.barclaycard.bsocial.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ashishgodani on 11/06/2016.
 */
public class Suggestions {

    private Map<String, String> score = new HashMap<>();

    public Map addScore(String key, String value) {
        score.put(key, value);
        return score;
    }
}
