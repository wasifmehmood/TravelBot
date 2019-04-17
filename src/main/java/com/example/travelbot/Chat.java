package com.example.travelbot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Chat extends Activity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    int flag = 0;                   //Check for floating btn image changed
    String user_Name;
    String sMsg;                    //Get EditText msg & converted to String
    Name name = new Name();
    Intent nameIntent;
    //FDatabase db1;
    TextView tv;

    EditText msg;

    ListView customListView;

    Bot bot;

    FloatingActionButton fab1;
   // Integer[] botDrawables = {R.drawable.about,R.drawable.logout};


    List<String> personMsgList;
    ListSingleton ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        /**
         * For Changing Status Bar Color
         */
        this.getWindow().setStatusBarColor(this.getResources().getColor(R.color.dark_yellow));


            tv = findViewById(R.id.reviewLinkTextView);
            tv.setMovementMethod(LinkMovementMethod.getInstance());

            customListView = findViewById(R.id.msgListView);

            ls = ListSingleton.getInstance();
            ls.botDrawList.add(R.drawable.ic_robot5);


            ls.botMsgList.add("Where do you want to travel?");

            ls.customAdapter = new CustomAdapter(this, ls.botMsgList, ls.botDrawList);

            customListView.setAdapter(ls.customAdapter);

            msg = findViewById(R.id.msgEditText);
            //db1 = new FDatabase();

            fab1 = findViewById(R.id.fab);
            fab1.setImageResource(R.drawable.ic_mic);
            flag = 1;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ls.botMsgList.clear();
        ls.botDrawList.clear();
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

                bot = new Bot();
                bot.setMsg(sMsg);

                getMsg();

                msg.setText("");
            }

        }

    }



    /**
     * Sending query to bot and getting response
     */
    public void getMsg(){

        bot = new Bot();

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
            Toast.makeText(getApplicationContext(),
                    "orry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

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

                        bot = new Bot();
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
    protected void onResume() {
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

}

