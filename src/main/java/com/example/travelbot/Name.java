package com.example.travelbot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Name extends Activity {
    EditText et = null;
    EditText et2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        et = findViewById(R.id.editText);
        et2 = findViewById(R.id.editText2);

    }
    String CHAT_PREFS = "ChatPrefs";
    String NAME_KEY = "Name";


    public void saveName(View view) {

if( !(et.getText().toString().isEmpty()) && !(et2.getText().toString().isEmpty()) ) {
    String name = et.getText().toString() + " " + et2.getText().toString();
    SharedPreferences prefs = getSharedPreferences(CHAT_PREFS, 0);
    prefs.edit().putString(NAME_KEY, name).apply();

    Intent mainActivityIntent = new Intent(this, NavDrawer.class);
    finish();
    startActivity(mainActivityIntent);
}
    }
}
