package com.brayo.allparks.util;

public class Util {
    public static final String PARKS_URL = "https://developer.nps.gov/api/v1/parks?parkCode=hi&api_key=jcknK7ZSZaoToLISmzxonol4lIVskTGXgfKgUXae";

    public static String getParksUrl(String stateCode){
        return "https://developer.nps.gov/api/v1/parks?stateCode="+stateCode+"&api_key=jcknK7ZSZaoToLISmzxonol4lIVskTGXgfKgUXae";
    }
}
