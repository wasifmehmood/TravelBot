package com.example.travelbot;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Login extends Fragment {

    TextView tv;

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

        tv = getActivity().findViewById(R.id.loginTextView);
        tv.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
