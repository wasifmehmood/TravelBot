package com.example.travelbot;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Reviews extends Fragment {
    ListView reviewListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Which ","OnCreateView");
        return inflater.inflate(R.layout.activity_reviews, container, false);
    }

    FDatabase fDatabase;
    Activity activity;
    ReviewsCustomAdapter reviewCustomAdapter;
    ListSingleton ls = ListSingleton.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_reviews);
        fDatabase = new FDatabase();
        activity = getActivity();
        fDatabase.readReviewData(activity);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("bbbb", "ccc");

        reviewListView = activity.findViewById(R.id.reviewListView);
        ls.reviewListView = reviewListView;

    }

    @Override
    public void onPause() {
        super.onPause();

        ls.reviewUtilsList.clear();
    }
}
