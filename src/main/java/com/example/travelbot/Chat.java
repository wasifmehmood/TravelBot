package com.example.travelbot;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Chat extends Fragment {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    int flag = 0;                   //Check for floating btn image changed

    String sMsg;                    //Get EditText msg & converted to String


    //FDatabase db1;
    TextView tv;

    EditText msg;

    ListView customListView;

    Bot bot;

    FloatingActionButton fab1;
   // Integer[] botDrawables = {R.drawable.about,R.drawable.logout};

    ListSingleton ls;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Which ","OnCreateView");
        return inflater.inflate(R.layout.activity_chat, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.activity_chat);

        Log.i("Which ","OnCreate");


        bot = new Bot();

        //db1 = new FDatabase();
    }

    @Override
    public void onStart() {
        super.onStart();

            if(ls.botMsgList.size() > 0) {

                ls.botMsgList.clear();
                ls.customAdapter.notifyDataSetChanged();
            }
        /**
         * For Changing Status Bar Color
         */
        //getActivity().getWindow().setStatusBarColor(this.getResources().getColor(R.color.dark_yellow));

        customListView = getActivity().findViewById(R.id.msgListView);
        tv = getActivity().findViewById(R.id.reviewLinkTextView);
        msg = getActivity().findViewById(R.id.msgEditText);
        fab1 = getActivity().findViewById(R.id.fab);

        updatingListView();
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        fab1.setImageResource(R.drawable.ic_mic);
        flag = 1;

        fab1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                sendMsg(view);

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

        ls.botMsgList.clear();
        ls.botDrawList.clear();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void updatingListView()
    {
        ls.botDrawList.add(R.drawable.ic_robot5);
        ls.botMsgList.add("Where do you want to travel?");
        ls.customAdapter = new CustomAdapter(getActivity(), ls.botMsgList, ls.botDrawList);
        customListView.setAdapter(ls.customAdapter);
    }

    public void sendMsg(View view) {

        /**
         * if image on floating btn is of mic
         */
        if(flag == 1)
        {
            promptSpeechInput();
        }

        /**
         * if image on floating btn is of send
         */
        else if (flag == 2)
        {

            if(!(msg.getText().toString().trim().isEmpty()) ) {
                // db1.addData();
                sMsg = msg.getText().toString();

                ls.botMsgList.add(sMsg);
                ls.customAdapter.notifyDataSetChanged();

                bot.setMsg(sMsg);

                getMsg();

                msg.setText("");

                /**
                 * For inflating another fragment(LOGIN)
                 */
                if(ls.botMsgList.size()>4) {
                    NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                    navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) getActivity());

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Login()).commit();
                    navigationView.setCheckedItem(R.id.nav_reviews);
                }
            }

        }

    }


    /**
     * Sending query to bot and getting response
     */
    public void getMsg(){

        Bot.RetrieveFeedTask task = bot.new RetrieveFeedTask();
        task.execute(msg.getText().toString());

    }

    /**
     * Showing google speech input dialog
     */
    protected void promptSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say Something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "orry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == getActivity().RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String userQuery=result.get(0);
                    msg.setText(userQuery);
                    if(!(msg.getText().toString().trim().isEmpty()) ) {

                        /**
                         * Showing text on screen
                         */
                        //db1.addData();
                        sMsg = msg.getText().toString();

                        ls.botMsgList.add(sMsg);
                        ls.customAdapter.notifyDataSetChanged();

                        bot.setMsg(sMsg);

                        getMsg();

                        msg.setText("");
                    }

                }
                break;
            }

        }
    }


    @SuppressLint("ResourceType")
    @Override
    public void onResume() {
        super.onResume();

        msg.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    fab1.setImageResource(R.drawable.ic_send);          //Change image on floating btn
                    flag = 2;
                }
                else {
                    fab1.setImageResource(R.drawable.ic_mic);
                    flag = 1;
                }
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ls = ListSingleton.getInstance();
        ls.fragmentFlag = true;

    }

    @Override
    public void onDetach() {
        super.onDetach();

        ls.fragmentFlag = false;

    }
}

