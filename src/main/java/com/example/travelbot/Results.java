package com.example.travelbot;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Results extends Fragment  {

    FDatabase fDatabase;
    Activity activity;
    ListView resultListView;
    ListSingleton ls = ListSingleton.getInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Which ","OnCreateView");
        return inflater.inflate(R.layout.activity_results, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_results);
        fDatabase = new FDatabase();
        activity = getActivity();
        ls.city2 = ls.city;
        ls.priority2 = ls.priority;

        if(ls.price) {
            fDatabase.readResultsData(activity);
        }
        else if(ls.economy){
            fDatabase.readResultsData2(activity);
        }
        else if(ls.standard){
            fDatabase.readResultsData3(activity);
        }
        else if(ls.luxury){
            fDatabase.readResultsData4(activity);
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("bbbb", "ccc");

        resultListView = activity.findViewById(R.id.resultListView);
        ls.resultListView = resultListView;

        ls.city = "";
        ls.priority = "";
    }

    @Override
    public void onPause() {
        super.onPause();

        ls.resultUtilsList.clear();
    }


}
