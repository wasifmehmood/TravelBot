package com.example.travelbot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends Fragment {

    TextView tv;
    EditText regUsernameEt, regPassEt, regContactEt, regEmailEt, regLinkEt;
    Button regBtn;
    ListSingleton ls;
    String usernameStr, passStr, contactStr, emailStr, linkStr;
    Boolean usernameCheck, passCheck, contactCheck, emailCheck, linkCheck;
    Activity activity;
    boolean isConnected;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Which ", "OnCreateView");
        return inflater.inflate(R.layout.activity_register, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);


        ls = ListSingleton.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        activity = getActivity();
        tv = getActivity().findViewById(R.id.regLoginTv);
        tv.setMovementMethod(LinkMovementMethod.getInstance());


        regUsernameEt = getActivity().findViewById(R.id.registerUsernameEt);
        regPassEt = getActivity().findViewById(R.id.registerPassEt);
        regContactEt = getActivity().findViewById(R.id.registerContactEt);
        regEmailEt = getActivity().findViewById(R.id.registerEmailEt);
        regLinkEt = getActivity().findViewById(R.id.registerLinkEt);

        regBtn = getActivity().findViewById(R.id.registerBtn);

//        regPassEt.addTextChangedListener(regTextWatcher);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regBtnClick();
            }
        });

    }


    public void regBtnClick()
    {
        isInternetConnected();

        if(!(isConnected))
        {
            Toast.makeText(getActivity(),
                    "Not connected to the internet.", Toast.LENGTH_LONG).show();
            return;
        }
        
        usernameStr = regUsernameEt.getText().toString();
        passStr = regPassEt.getText().toString();
        contactStr = regContactEt.getText().toString();
        emailStr = regEmailEt.getText().toString();
        linkStr = regLinkEt.getText().toString();


        usernameCheck = (usernameStr.length() > 3);
        passCheck = (passStr.length() > 5);
        contactCheck = isValidMobile(contactStr);
        emailCheck = isEmailValid(emailStr);
        linkCheck = Patterns.WEB_URL.matcher(linkStr).matches();

        boolean isFilled = true;

        if(!(usernameCheck))
        {
            regUsernameEt.setError("Must be greater than 3");
            isFilled=false;
        }

        if(!(passCheck))
        {
            regPassEt.setError("Must be greater than 5");

            isFilled=false;
        }

        if (!(contactCheck))
        {
            regContactEt.setError("Invalid");
            isFilled=false;
        }

        if (!(emailCheck))
        {
            regEmailEt.setError("Invalid");
            isFilled=false;
        }

        if (!(linkCheck))
        {

            regLinkEt.setError("Invalid");
            isFilled=false;

        }
        if(isFilled)
        {
            createUser();
        }
        Log.d("wwww", "createUserWithEmail:onComplete:" + isFilled);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check;

        if(phone.length() < 6 || phone.length() > 13)
        {
            check = false;
        }
        else
        {
            check = true;
        }
        return check;
    }

    public void createUser()
    {

        ls.mAuth.createUserWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(Objects.requireNonNull(getActivity()),
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("wwww", "createUserWithEmail:onComplete:" + task.isSuccessful());

//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Agencies");
//                String id = reference.push().getKey();
                Log.d("wwww", "createUserWithEmail:onComplete:" + ls.mAuth.getCurrentUser().getUid());

                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("user_id", ls.mAuth.getCurrentUser().getUid());
                    map.put("username", usernameStr);
                    map.put("contact", contactStr);
                    map.put("email", emailStr);
                    map.put("link", linkStr);
//                map.put("key", id);
                    Log.d("wwww", "createUserWithEmail:onComplete:" + ls.mAuth.getCurrentUser().getUid());

                    String collection = "agencies";

                    FDatabase fDatabase = new FDatabase();
                    fDatabase.addData(collection, map);


//                reference.child(id).setValue(map);

                    ls.username = usernameStr;

//                SharedPreferences prefs = getActivity().getSharedPreferences("PREFS", 0);
//                prefs.edit().putString("key", id).apply();

                    snackBar();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Login()).commit();
                }

                else if (!task.isSuccessful())
                {
                    Toast.makeText(activity,
                            "Email already exist",  Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void isInternetConnected()
    {
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void snackBar() {

        Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout), "Agency Registered", Snackbar.LENGTH_SHORT).show();

    }

//    private TextWatcher regTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            String checkPass = regPassEt.getText().toString().trim();
//            if (checkPass.length() > 5) {
//
//                Toast.makeText(getActivity(), ""+s, Toast.LENGTH_SHORT).show();
//                regBtn.setEnabled(true);
//            } else {
//
//                regPassEt.setError("Failed");
//            }
//
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//
//        }
//    };

}
