package com.example.travelbot;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class ReviewsCustomAdapter extends BaseAdapter {

    Activity activity;
    private List<ReviewUtils> reviewUtils;
    LayoutInflater layoutInflater;


    public ReviewsCustomAdapter(Activity activity, List reviewUtils){
        this.activity = activity;
        this.reviewUtils = reviewUtils;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return reviewUtils.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        if (v == null) {
            v = layoutInflater.inflate(R.layout.review_custom_listview, null);
        }

        TextView nameTv = v.findViewById(R.id.reviewNameTv);
        TextView typeTv = v.findViewById(R.id.reviewTypeTv);
        TextView textTv = v.findViewById(R.id.reviewTextTv);


        nameTv.setText(reviewUtils.get(i).getName().toString());
        typeTv.setText(reviewUtils.get(i).getType().toString());
        textTv.setText(reviewUtils.get(i).getText().toString());



//        countryTxtV.setText(msgList.get(i));
//        countryImageV.setImageResource(imageList.get(0));

        return v;
    }
}
