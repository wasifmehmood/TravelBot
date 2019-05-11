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


public class ServiceCustomAdapter extends BaseAdapter {

    Activity activity;
    private List<ServiceUtils> serviceUtils;
    LayoutInflater layoutInflater;


    public ServiceCustomAdapter(Activity activity, List serviceUtils){
        this.activity = activity;
        this.serviceUtils = serviceUtils;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return serviceUtils.size();
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
            v = layoutInflater.inflate(R.layout.service_custom_listview, null);
        }

        TextView nameTv = v.findViewById(R.id.serviceNameTv);
        TextView typeTv = v.findViewById(R.id.serviceTypeTv);
        TextView priceTv = v.findViewById(R.id.servicePriceTv);
        TextView linkTv = v.findViewById(R.id.serviceLinkTv);
        TextView fromTv = v.findViewById(R.id.serviceFromTv);
        TextView toTv = v.findViewById(R.id.serviceToTv);

        nameTv.setText(serviceUtils.get(i).getName().toString());
        typeTv.setText(serviceUtils.get(i).getType().toString());
        priceTv.setText(serviceUtils.get(i).getPrice().toString());
        linkTv.setText(serviceUtils.get(i).getLink().toString());
        fromTv.setText(serviceUtils.get(i).getFrom().toString());
        toTv.setText(serviceUtils.get(i).getTo().toString());


//        countryTxtV.setText(msgList.get(i));
//        countryImageV.setImageResource(imageList.get(0));

        return v;
    }
}
