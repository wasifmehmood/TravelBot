package com.example.travelbot;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Services extends Fragment {

    EditText serviceNameEt, serviceTypeEt, servicePriceEt, serviceLinkEt, serviceFromEt, serviceToEt;
    String serviceNameStr, serviceTypeStr, servicePriceStr, serviceLinkStr, serviceFromStr, serviceToStr;
    SharedPreferences prefs;
    String key;
    ListSingleton ls = ListSingleton.getInstance();
    FDatabase fDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Which ","OnCreateView");
        return inflater.inflate(R.layout.activity_services, container, false);
    }

    FloatingActionButton addServicesFab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_services);

        fDatabase = new FDatabase();
        uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fDatabase.readOwnServicesData(uId);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                serviceCustomAdapter = new ServiceCustomAdapter(activity, ls.serviceUtilsList);
                                Log.d("b", "ccc");
                                serviceListView.setAdapter(serviceCustomAdapter);
                            }
                        });

                    }},3000);

    }

    Activity activity;
    ListView serviceListView;
    String uId;
    ServiceCustomAdapter serviceCustomAdapter;

    @Override
    public void onStart() {
        super.onStart();


//        readFromDatabase();


        String collection = "agencies", field = "email", value = ls.email;
        fDatabase.readData(collection, field, value);

        activity = getActivity();
        serviceListView = getActivity().findViewById(R.id.serviceListView);





        Log.d("b", "ddd");
        addServicesFab = getActivity().findViewById(R.id.addServicesFab);
        addServicesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customDialog();

            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        ls.serviceUtilsList.clear();
    }

    //DIALOG
    public void customDialog()
    {

        // custom dialog
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.services_dialog);
        dialog.setTitle("Title...");


        // set the custom dialog components - text, image and button
        serviceNameEt = dialog.findViewById(R.id.serviceNameEt);
        serviceTypeEt = dialog.findViewById(R.id.serviceTypeEt);
        servicePriceEt = dialog.findViewById(R.id.servicePriceEt);
        serviceLinkEt = dialog.findViewById(R.id.serviceLinkEt);
        serviceFromEt = dialog.findViewById(R.id.serviceFromEt);
        serviceToEt = dialog.findViewById(R.id.serviceToEt);

        Button addServiceBtn = dialog.findViewById(R.id.addServiceBtn);

        addServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                serviceNameStr = serviceNameEt.getText().toString();
                serviceTypeStr = serviceTypeEt.getText().toString();
                servicePriceStr = servicePriceEt.getText().toString();
                serviceLinkStr = serviceLinkEt.getText().toString();
                serviceFromStr = serviceFromEt.getText().toString();
                serviceToStr = serviceToEt.getText().toString();

                ls.from = serviceFromStr;
                ls.to = serviceToStr;

//                prefs = getActivity().getSharedPreferences("PREFS", MODE_PRIVATE);
//                key = prefs.getString("key", null);


//                String id = reference.push().getKey();

                Map<String, Object> map = new HashMap<>();
                map.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
                map.put("name", serviceNameStr);
                map.put("type", serviceTypeStr);
                map.put("link", serviceLinkStr);
//                map.put("stars", "0");
//                map.put("key", id);
                map.put("from", serviceFromStr);
                map.put("to", serviceToStr);
//


//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Services").child(ls.username).child("location")
//                        .child(serviceFromStr).child(serviceToStr).child(serviceTypeStr);

//                Map<String, Object> map1 = new HashMap<>();
                map.put("price", servicePriceStr);
                map.put("stars", "0");
//                map1.put("type", reference.getKey());

                String collection = "services";

                fDatabase = new FDatabase();
                fDatabase.addData(collection, map);

//                reference.setValue(map1);

//                reference.child(id).child("route").setValue(mapLocation);

                dialog.dismiss();
            }
        });

        dialog.show();
    }


//    public void readFromDatabase()
////    {
////
////        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Agencies");
////        reference.orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                // This method is called once with the initial value and again
////                // whenever data at this location is updated.
////                Toast.makeText(getActivity(), "Realy"+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
////
////                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
////                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
////                        HashMap<String, String> map = (HashMap<String, String>) snapshot.getValue();
////                        Toast.makeText(getActivity(), "Realy", Toast.LENGTH_SHORT).show();
////
////                        ls.username = map.get("username");
////
////                    }
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////            }
////        });
//    }



}
