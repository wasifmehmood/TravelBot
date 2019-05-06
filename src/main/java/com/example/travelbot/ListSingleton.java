package com.example.travelbot;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

class ListSingleton {



    private static volatile ListSingleton sSoleInstance = new ListSingleton();

    List<Integer> botDrawList;
    List<String> botMsgList;
    CustomAdapter customAdapter;
    SharedPreferences prefs;
    Boolean fragmentFlag;

    FirebaseDatabase database;

    FirebaseAuth mAuth;
    //private constructor.

    private ListSingleton(){

        botMsgList = new ArrayList<>();
        botDrawList = new ArrayList<>();
        fragmentFlag = true;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }



    static ListSingleton getInstance() {

        return sSoleInstance;

    }
}
