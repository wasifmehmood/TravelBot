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
    //private constructor.

    private ListSingleton(){

        botMsgList = new ArrayList<>();
        botDrawList = new ArrayList<>();
    }



    static ListSingleton getInstance() {

        return sSoleInstance;

    }
}
