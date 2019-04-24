package com.example.travelbot;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

class ListSingleton {



    private static volatile ListSingleton sSoleInstance = new ListSingleton();

    List<Integer> botDrawList;
    List<String> botMsgList;
    CustomAdapter customAdapter;
    SharedPreferences prefs;
    Boolean fragmentFlag;
    //private constructor.

    private ListSingleton(){

        botMsgList = new ArrayList<>();
        botDrawList = new ArrayList<>();
        fragmentFlag = true;

    }



    static ListSingleton getInstance() {

        return sSoleInstance;

    }
}
