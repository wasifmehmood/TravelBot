package com.example.travelbot;

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

public class Login extends Fragment {

    TextView tv;
    EditText getEmail, getPass;
    String getEmailStr, getPassStr;
    Button loginBtn;
    ListSingleton ls;
    MenuItem menuItem;

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


    public void login()
    {
        getEmailStr = getEmail.getText().toString();
        getPassStr = getPass.getText().toString();

        if(getEmailStr != null && getPassStr != null)
        {
            //Sign In
            ls.mAuth.signInWithEmailAndPassword(getEmailStr, getPassStr)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("", "signInWithEmail:onComplete:" + task.isSuccessful());
                            Toast.makeText(getActivity(),
                                    "Authentication Success.", Toast.LENGTH_SHORT).show();

                            setTitle();

                            if (!task.isSuccessful()) {
                                Toast.makeText(getActivity(),
                                        "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

        else if(getEmailStr == null && getPassStr == null)
        {
            getEmail.setError("Field required");
            getPass.setError("Field required");
        }
        else if(getEmailStr == null)
        {
            getEmail.setError("Field required");
        }
        else if(getPassStr == null)
        {
            getPass.setError("Field required");
        }
    }

    public void setTitle()
    {
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        menuItem = navigationView.getMenu().findItem(R.id.nav_login);
        menuItem.setTitle("Log Out");

    }

}
