package com.example.travelbot;

import android.content.SharedPreferences;
import android.widget.ListView;

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
    String username;
    String from, to;
    List<String> list;
    List<String> list2;
    List<String> list3;
    List<String> list4;
    FirebaseDatabase database;
    String email,uId, id, user_id;
    FirebaseAuth mAuth;
    boolean writeFlag;
    float stars;
    List<ServiceUtils> serviceUtilsList;
    List<ReviewUtils> reviewUtilsList;

    ReviewsCustomAdapter reviewCustomAdapter;
    ListView reviewListView;
    //private constructor.

    private ListSingleton(){

        botMsgList = new ArrayList<>();
        botDrawList = new ArrayList<>();
        fragmentFlag = true;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        username = null;
        from = null;
        to = null;
        email = null;
        uId = null;
        id = null;
        user_id = null;
        list = new ArrayList<String>();
        list2 = new ArrayList<String>();
        list3 = new ArrayList<String>();
        list4 = new ArrayList<String>();
        writeFlag = false;
        stars = 0;
        serviceUtilsList = new ArrayList<>();
        reviewUtilsList = new ArrayList<>();
        reviewListView = null;
    }



    static ListSingleton getInstance() {

        return sSoleInstance;

    }
}
