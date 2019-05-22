package com.example.travelbot;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Login extends Fragment {

    TextView tv;
    EditText getEmail, getPass;
    String getEmailStr, getPassStr;
    Button loginBtn;
    ListSingleton ls;
    MenuItem menuItem;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Agencies");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Which ","OnCreateView");
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getActivity().setContentView(R.layout.activity_login);

    }

    @Override
    public void onStart() {
        super.onStart();

        ls = ListSingleton.getInstance();
        tv = getActivity().findViewById(R.id.loginTextView);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        getEmail = getActivity().findViewById(R.id.loginUsernameEt);
        getPass = getActivity().findViewById(R.id.loginPassEt);
        loginBtn = getActivity().findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    login();
            }
        });
    }

    boolean isConnected;

    public void login()  {

        isInternetConnected();

        if(!(isConnected))
        {
            Toast.makeText(getActivity(),
                    "Not connected to the internet.", Toast.LENGTH_LONG).show();
            return;
        }

        getEmailStr = getEmail.getText().toString();
        getPassStr = getPass.getText().toString();

        if(!(getEmailStr.equals("")) && !(getPassStr.equals("")))
        {
            //Sign In
            ls.mAuth.signInWithEmailAndPassword(getEmailStr, getPassStr)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                Log.d("ssss", "signInWithEmail:onComplete:" + task.isSuccessful());
                                Toast.makeText(getActivity(),
                                        "Authentication Success.", Toast.LENGTH_LONG).show();

                                FDatabase fDatabase = new FDatabase();
                                fDatabase.readData("agencies", "user_id", ls.mAuth.getUid());
//                            reference.orderByChild("user_id").equalTo(ls.mAuth.getUid());
                                ls.uId = ls.mAuth.getUid();
                                ls.email = getEmailStr;
//                            readFromDatabase();

                                ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                                        "Signing In. Please wait...", true);
                                thread();

                            }
                            else if (!task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Username or password incorrect.", Toast.LENGTH_LONG).show();
                                Log.d("sss", "signInWithEmail:onComplete:" + task.isSuccessful());
                            }

                        }
                    });

        }

        else if(getEmailStr.equals("") && getPassStr.equals(""))
        {
            getEmail.setError("Field required");
            getPass.setError("Field required");
        }
        else if(getEmailStr.equals(""))
        {
            getEmail.setError("Field required");
        }
        else if(getPassStr.equals(""))
        {
            getPass.setError("Field required");
        }
    }

    void isInternetConnected()
    {
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    void thread()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                synchronized (this) {
                    try {
                        wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            Intent i = new Intent(getActivity(), AgencyNavDrawer.class);
                            startActivity(i);
                        }
                    });
                }
            }
        });

        thread.start();
    }

//    public void readFromDatabase()
//    {
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        HashMap<String, String> map = (HashMap<String, String>) snapshot.getValue();
//                        ls.username = map.get("username");
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//    }

    public void setTitle()
    {
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        menuItem = navigationView.getMenu().findItem(R.id.nav_login);
        menuItem.setTitle("Log Out");

    }

}
