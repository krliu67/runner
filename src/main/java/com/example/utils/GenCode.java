package com.example.utils;

import java.util.Random;
import java.util.UUID;

public class GenCode {

    public String GeneralNumAndChar(int n){
        StringBuffer sb = new StringBuffer();
        String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }
    public String GenInviteCode(){
        return GeneralNumAndChar(4);
    }

    public String GenUserId(){
        return GeneralNumAndChar(8);
    }


}
