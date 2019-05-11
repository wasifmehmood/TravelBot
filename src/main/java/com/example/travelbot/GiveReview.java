package com.example.travelbot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GiveReview extends Fragment {

    Spinner spinner, spinner2, spinner3, spinner4;
    EditText reviewEt;
    String reviewText;
    List<String> list;
    List<String> list2;
    ListSingleton ls = ListSingleton.getInstance();
    String username, key;
    String type;
    Boolean flag = false;
    String spin, spin2, spin3, spin4;
    Button addReviewBtn;
    RatingBar ratingBar;
    FDatabase fDatabase;
    float rating;
    String id;
    String collectionReview = "reviews";

    public GiveReview()
    {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Which ","OnCreateView");
        return inflater.inflate(R.layout.activity_give_review, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_give_review);
    }

    @Override
    public void onStart() {
        super.onStart();

        fDatabase = new FDatabase();

        flag = true;
//        readFromDatabase();
//        readFromDatabase5();

        addReviewBtn = getActivity().findViewById(R.id.addReviewBtn);
        ratingBar = getActivity().findViewById(R.id.reviewBar);
        spinner = getActivity().findViewById(R.id.spinner);
        spinner2 = getActivity().findViewById(R.id.spinner2);
        spinner3 = getActivity().findViewById(R.id.spinner3);
        spinner4 = getActivity().findViewById(R.id.spinner4);
        reviewEt = getActivity().findViewById(R.id.reviewEt);


        String collectionAgencies = "agencies";

        ls.list.clear();
        try {
            fDatabase.readDataForSpinner(collectionAgencies, getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                readFromDatabase3();
//                writeToDatabase();
                final String collectionServices = "services";
//                final Map<String, Object> map = new HashMap<>();
//                final Map<String, Object> reviewMap = new HashMap<>();
                try {


                    spin = spinner.getSelectedItem().toString();
                    spin2 = spinner2.getSelectedItem().toString();
                    spin3 = spinner3.getSelectedItem().toString();
                    spin4 = spinner4.getSelectedItem().toString();
                    reviewText = reviewEt.getText().toString();


                    fDatabase.readStarsDatabase(getActivity(),spin, spin2, spin3, spin4, ratingBar, reviewText);
                }
                catch (Exception ie)
                {
                    Toast.makeText(getActivity(), "connect to internet", Toast.LENGTH_SHORT).show();
                }


                


            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item != null) {

                    ls.list2.clear();
//                    readFromDatabase2();
                    try {
                        fDatabase.readDataForSpinner2(collectionServices2, spinner.getSelectedItem().toString(), getActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
//                Toast.makeText(MainActivity.this, "Selected",
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ls.list3.clear();
                ls.list4.clear();
                try {
                    fDatabase.readDataForSpinner3(collectionServices2, spinner.getSelectedItem().toString(), spinner2.getSelectedItem().toString()
                            , getActivity());
                    fDatabase.readDataForSpinner4(collectionServices2, spinner.getSelectedItem().toString(), spinner2.getSelectedItem().toString()
                            , getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    String collectionServices2 = "services";

    @Override
    public void onResume() {
        super.onResume();

//        fDatabase.readDataForSpinner2(collection2, spinner.getSelectedItem().toString());

    }

    public void addItemsOnSpinner(String username, Activity activity) {
        spinner = activity.findViewById(R.id.spinner);

        ls.list.add(username);
//        list.add("daewoo");
//        list.add("niazi");
//        list.add("skyways");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, ls.list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    public void addItemsOnSpinner2(String type, Activity activity) {

        spinner2 = activity.findViewById(R.id.spinner2);


        ls.list2.add(type);
//        list2.add("daewoo");
//        list2.add("niazi");
//        list2.add("skyways");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, ls.list2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

    }

    public void addItemsOnSpinner3(String from, Activity activity)
    {
        spinner3 = activity.findViewById(R.id.spinner3);


        ls.list3.add(from);
//        list2.add("daewoo");
//        list2.add("niazi");
//        list2.add("skyways");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, ls.list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinner4(String to, Activity activity)
    {
        spinner4 = activity.findViewById(R.id.spinner4);


        ls.list4.add(to);
//        list2.add("daewoo");
//        list2.add("niazi");
//        list2.add("skyways");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_spinner_item, ls.list4);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter);
    }
//
//public void readFromDatabase()
//    {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Agencies");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        HashMap<String, String> map = (HashMap<String, String>) snapshot.getValue();
//
//                        username = map.get("username");
//                        if(flag)
//                        {
//                            addItemsOnSpinner();
//                            readFromDatabase2();
//                            Toast.makeText(getActivity(), "" + username, Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//    }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//
//
//    public void readFromDatabase2()
//    {
//
//        readFromDatabase4();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Services").child(username).child("location")
//                .child(from).child(to);
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        HashMap<String, String> map = (HashMap<String, String>) snapshot.getValue();
//
//                        type = snapshot.getKey();
////                        type = map.get("type");
//                        if(!(list2.contains(type)))
//                        {
//                            addItemsOnSpinner2();
//                        }
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//    String from, to;
//    public void readFromDatabase4()
//    {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Services").child(username).child("location");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        HashMap<String, String> map = (HashMap<String, String>) snapshot.getValue();
//
//                        from = snapshot.getKey();
////                        type = map.get("type");
////                        to = reference.child(from).getKey();
//                        Log.d("aa","from"+from);
////                        Log.d("aa","to"+to);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//    public void readFromDatabase5()
//    {
//
//        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Services");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                Toast.makeText(getActivity(), "Realy"+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
//
//                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        HashMap<String, String> map = (HashMap<String, String>) snapshot.getValue();
//                        Toast.makeText(getActivity(), "Realy", Toast.LENGTH_SHORT).show();
//
//                        ls.username = snapshot.getKey();
//
//                        Log.d("aa",""+ls.username);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


DatabaseReference reference;
    public void writeToDatabase()
    {

        Map map = new HashMap<>();
        map.put("stars", Float.toString(ratingBar.getRating()));

        reference = ls.database.getReference("Services").child(spinner.getSelectedItem().toString()).child(key);
        reference.orderByChild("type").equalTo(spinner2.getSelectedItem().toString());

        reference.updateChildren(map);

    }

//    public void readFromDatabase3()
//    {
//        Log.d("aaaa","before"+spinner.getSelectedItem().toString());
//        Log.d("aaaa","before"+spinner2.getSelectedItem().toString());
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Services")
//                .child(spinner.getSelectedItem().toString());
//        reference.orderByChild("type").equalTo(spinner2.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//               Log.d("aaaa","before"+key);
//
//                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        HashMap<String, String> map = (HashMap<String, String>) snapshot.getValue();
//
//                        key = map.get("key");
//                        Log.d("","after"+key);
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    @Override
    public void onPause() {
        super.onPause();

        flag = false;
    }
}