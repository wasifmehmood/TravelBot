package com.example.travelbot;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {

   String user_Name;
   Name name = new Name();
   Intent nameIntent;
    ListSingleton ls;

    private static int SPLASH_TIME_OUT = 2000;
    TextView botTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ls = ListSingleton.getInstance();

        botTextView = findViewById(R.id.textViewTravel);
        //botTextView.animate().alpha(1).setDuration(1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ls.prefs = getSharedPreferences(name.CHAT_PREFS, MODE_PRIVATE);
                user_Name = ls.prefs.getString(name.NAME_KEY, null);

                if(user_Name == null) {

                    Intent splashIntent = new Intent(MainActivity.this, Name.class);
                    startActivity(splashIntent);
                    finish();
                }

                else {
//                    Intent splashIntent = new Intent(MainActivity.this, Chat.class);
//                    startActivity(splashIntent);
//                    finish();

                    Intent i = new Intent(MainActivity.this, NavDrawer.class);
                    startActivity(i);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);


   }
}
