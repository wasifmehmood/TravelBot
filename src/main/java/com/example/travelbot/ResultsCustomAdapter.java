package com.example.travelbot;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ResultsCustomAdapter extends BaseAdapter {

    Activity activity;
    private List<ResultUtils> resultUtils;
    LayoutInflater layoutInflater;


    public ResultsCustomAdapter(Activity activity, List resultUtils){
        this.activity = activity;
        this.resultUtils = resultUtils;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return resultUtils.size();
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
            v = layoutInflater.inflate(R.layout.results_custom_listview, null);
        }

        TextView nameTv = v.findViewById(R.id.resultNameTv);
        TextView typeTv = v.findViewById(R.id.resultTypeTv);
        TextView textTv = v.findViewById(R.id.resultPriceTv);
        TextView starsTv = v.findViewById(R.id.resultStarsTv);

        nameTv.setText(resultUtils.get(i).getName().toString());
        typeTv.setText(resultUtils.get(i).getType().toString());
        textTv.setText(resultUtils.get(i).getStars().toString());
        starsTv.setText(resultUtils.get(i).getPrice().toString());


//        countryTxtV.setText(msgList.get(i));
//        countryImageV.setImageResource(imageList.get(0));

        return v;
    }
}
