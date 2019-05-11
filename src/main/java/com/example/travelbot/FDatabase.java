package com.example.travelbot;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class FDatabase {


    FirebaseFirestore db;

    FDatabase()
    {
        db = FirebaseFirestore.getInstance();

    }

    void addData(String collection, Map<String,Object> map)
    {
        // Create a new user with a first and last name


        // Add a new document with a generated ID
        db.collection(collection)
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        documentReference.update("id",documentReference.getId());
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }


    Activity activity;
    float stars;
    String id;
    float rating;

    void readStarsDatabase(final Activity activity, final String spin, final String spin2, final String spin3, final String spin4, final RatingBar ratingBar, final String reviewText)

    {Log.d("aaa",""+giveReview.id);
        Log.d("aaa",""+spin);
        Log.d("aaa",""+spin2);
        Log.d("aaa",""+spin3);
        Log.d("aaa",""+spin4);
        db.collection("services").whereEqualTo("name", spin).whereEqualTo("type", spin2).whereEqualTo("from", spin3)
            .whereEqualTo("to", spin4)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("bbb",  " => ");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("bbb", document.getId() + " => " + document.getData());

                                ls.stars = Float.valueOf(document.get("stars").toString());
                                ls.id = document.getId();
                                ls.user_id = document.get("user_id").toString();


                                starsCalc(activity, spin, spin2, spin3, spin4, ratingBar, reviewText);

                            }
                        } else {
                            Log.d("aaaa", "Error getting documents.", task.getException());
                        }
                    }
                });


    }

    void starsCalc(Activity activity, final String spin, final String spin2, final String spin3, final String spin4, final RatingBar ratingBar, final String reviewText)
    {
        final Map<String, Object> map = new HashMap<>();
        if (ls.stars != 0)
        {
            Log.d("check",  " => ");
            rating = (ls.stars + ratingBar.getRating()) / 2;
            map.put("stars", String.valueOf(rating));
        }
        else
        {
            rating = ratingBar.getRating();
            map.put("stars", String.valueOf(rating));
        }
        updateStarsDatabase("services", spin, spin2, spin3, spin4, map, ls.id, reviewText, activity);

    }
    void updateStarsDatabase(String collection, final String spin, final String spin2, final String spin3, final String spin4, Map<String, Object> map
            , String id, final String reviewText, final Activity activity)
    {
        // Create a new user with a first and last name

        Log.d("star",""+map.get("stars"));
        // Add a new document with a generated ID
        db.collection(collection).document(id).update("stars", map.get("stars"))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {

                        final Map<String, Object> reviewMap = new HashMap<>();
                        if(reviewText.length() > 1)
                        {
                            reviewMap.put("name", spin);
                            reviewMap.put("type", spin2);
                            reviewMap.put("from", spin3);
                            reviewMap.put("to", spin4);
                            reviewMap.put("review", reviewText);
                            reviewMap.put("id", ls.id);
                            reviewMap.put("user_id", ls.user_id);

                            writeReviewToDatabase("reviews", reviewMap);
                        }
                        Intent i = new Intent(activity, NavDrawer.class);
                        activity.startActivity(i);
                        activity.finish();

                    }
                });


    }
    void writeReviewToDatabase(String collection, Map<String, Object> map)
    {
        db.collection(collection)
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    ListSingleton ls = ListSingleton.getInstance();

    void readData(String collection, String field, String value)
    {
        db.collection(collection).whereEqualTo(field,value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("aaaa", document.getId() + " => " + document.getData());

                                ls.username = document.get("username").toString();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    String username;
    GiveReview giveReview = new GiveReview();
    void readDataForSpinner(String collection, final Activity activity)
    {
//        Log.d("aa", " "+collection);
        db.collection(collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                username = document.get("username").toString();
                                Log.d("aa", document.getId() + " => " + document.getData() + " " + username);
                                if (!(ls.list.contains(username)))
                                {

                                    giveReview.addItemsOnSpinner(username, activity);
                                }
                            }
                        } else {
                            Log.w("aa", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    String type;
    void readDataForSpinner2(String collection, String selectedItem, final Activity activity)
    {
        db.collection(collection).whereEqualTo("name", selectedItem)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                type = document.get("type").toString();
                                if(!(ls.list2.contains(type)))
                                {
                                    giveReview.addItemsOnSpinner2(type, activity);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
String from;
    void readDataForSpinner3(String collection, String selectedItem, String selectedItem2, final Activity activity)
    {
        db.collection(collection).whereEqualTo("name", selectedItem).whereEqualTo("type", selectedItem2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                from = document.get("from").toString();
                                if(!(ls.list3.contains(from)))
                                {
                                    giveReview.addItemsOnSpinner3(from, activity);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    String to;
    void readDataForSpinner4(String collection, String selectedItem, String selectedItem2, final Activity activity)
    {
        db.collection(collection).whereEqualTo("name", selectedItem).whereEqualTo("type", selectedItem2)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                to = document.get("to").toString();
                                if(!(ls.list4.contains(to)))
                                {
                                    giveReview.addItemsOnSpinner4(to, activity);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    void readOwnServicesData(String uId)
    {
        db.collection("services").whereEqualTo("user_id", uId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Log.d("aa", document.getId() + " => " + document.getData());
                                HashMap<String, Object> map = (HashMap<String, Object>) document.getData();
                                ls.serviceUtilsList.add(new ServiceUtils(map.get("name"),map.get("type"), map.get("price")
                                        , map.get("link"), map.get("from"), map.get("to")));
                                Log.d("aa", document.getId() + " => " + map.get("name"));

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    Thread thread;
    void readReviewData(final Activity activity)
    {
        db.collection("reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Log.d("bbbb", document.getId() + " => " + document.getData());
                                HashMap<String, Object> map = (HashMap<String, Object>) document.getData();
                                ls.reviewUtilsList.add(new ReviewUtils(map.get("name"),map.get("type"), map.get("review")));
                                Log.d("bbbb", document.getId() + " => " + map.get("name"));

                                thread(activity);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    void thread(final Activity activity)
    {
        thread = new Thread(){
            @Override
            public void run() {
                try {
                    Log.d("b", "ccc");
                    synchronized (this) {
                        wait(1);

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ls.reviewCustomAdapter = new ReviewsCustomAdapter(activity, ls.reviewUtilsList);
                                ls.reviewListView.setAdapter(ls.reviewCustomAdapter);
                                Log.d("bbbb", "ccc");
                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }

}
