package com.example.travelbot;

import android.support.annotation.Nullable;
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

public class Register extends Fragment {

    TextView tv;
    EditText regUsernameEt, regPassEt, regContactEt, regEmailEt, regLinkEt;
    Button regBtn;

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

    }

    @Override
    public void onStart() {
        super.onStart();

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

    String usernameStr, passStr, contactStr, emailStr, linkStr;
    Boolean usernameCheck, passCheck, contactCheck, emailCheck, linkCheck;

    public void regBtnClick()
    {
        usernameStr = regUsernameEt.getText().toString();
        passStr = regPassEt.getText().toString();
        contactStr = regContactEt.getText().toString();
        emailStr = regEmailEt.getText().toString();
        linkStr = regLinkEt.getText().toString();

        usernameCheck = (usernameStr.length() > 3);
        passCheck = (passStr.length() > 5);
        contactCheck = true;
        emailCheck = isEmailValid(emailStr);
        linkCheck = Patterns.WEB_URL.matcher(linkStr).matches();

        if(usernameCheck)
        {
            if(passCheck)
            {
                if (contactCheck)
                {
                    if (emailCheck)
                    {
                        if (linkCheck)
                        {
                            Toast.makeText(getActivity(), "Valid", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                        regLinkEt.setError("Invalid");
                        }
                    }
                    else
                    {
                        regEmailEt.setError("Invalid");
                    }
                }
                else
                {
                regContactEt.setError("Invalid");
                }
            }
            else
            {
            regPassEt.setError("Must be greater than 5");
            }
        }
        else
        {
            regUsernameEt.setError("Must be greater than 3");
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
