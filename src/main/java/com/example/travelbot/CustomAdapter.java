package com.example.travelbot;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class CustomAdapter extends BaseAdapter {

    Activity activity;
    List<String> msgList;
    List<Integer> imageList;
    LayoutInflater layoutInflater;


    public CustomAdapter(Activity activity, List<String> msgList, List<Integer> imageList){
        this.activity = activity;
        this.msgList = msgList;
        this.imageList = imageList;

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return msgList.size();
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
            v = layoutInflater.inflate(R.layout.custom_list, null);
        }

        TextView countryTxtV = (TextView) v.findViewById(R.id.botMsg);
        ImageView countryImageV = (ImageView) v.findViewById(R.id.botImage);

        countryTxtV.setText(msgList.get(i));
        countryImageV.setImageResource(imageList.get(0));

        return v;
    }
}
