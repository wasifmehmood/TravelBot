package com.example.travelbot;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

public class Bot {

    Chat c;
    String msg;
    int count = 0;
    ListSingleton ls = ListSingleton.getInstance();
    String priority = "";
    public Bot(){

        c = new Chat();

    }

    Boolean flag = true;


    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg0){
        msg=msg0;
    }
    String city = "";
    // Create GetText Metod
    public String getText(String query) throws UnsupportedEncodingException {

        String text = "";
        BufferedReader reader = null;

        // Send data
        try {
            Log.i("Did "," execute ");
            // Defined URL  where to send data
            URL url = new URL("https://api.api.ai/v1/query?v=20150910");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Authorization", "Bearer 108cbc36d2a4412cba7307096374df40");
            conn.setRequestProperty("Content-Type", "application/json");

            //Create JSONObject here
            JSONObject jsonParam = new JSONObject();
            JSONArray queryArray = new JSONArray();
            queryArray.put(query);
            jsonParam.put("query", queryArray);
//            jsonParam.put("name", "order a medium pizza");
            jsonParam.put("lang", "en");
            jsonParam.put("sessionId", "1234567890");

            Log.i("Did "," execute ");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            Log.d("karma", "after conversion is " + jsonParam.toString());
            wr.write(jsonParam.toString());
            wr.flush();
            Log.d("karma", "json is " + jsonParam);

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;


            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();



            JSONObject object1 = new JSONObject(text);
            JSONObject object = object1.getJSONObject("result");
            JSONObject fulfillment = null;
            String speech = null;
            JSONObject parameters = null;
            String variable = null;


//            if (object.has("fulfillment")) {
            fulfillment = object.getJSONObject("fulfillment");
//                if (fulfillment.has("speech")) {
            speech = fulfillment.optString("speech");
            parameters = object.getJSONObject("parameters");
            variable = parameters.optString("geo-city");


            if(!(variable.equals("")) && flag) {

                ls.city = variable;
                count++;
                flag = false;
            }
            if(!(ls.city.equals("")) && count >= 2)
            {
                variable = parameters.optString("any");
                ls.priority = variable;
                flag = true;
            }
            count++;


//                }
//            }

            Log.i("Did "," execute ");
            Log.d("karma ", "response is " + text);
            Log.d("karma ", "response is " + fulfillment);
            Log.d("karma ", "response is " + speech);
            Log.d("karma ", "response is " + variable);
            Log.d("karma ", "response is " + ls.city+" "+ls.priority);

            return speech;

        } catch (Exception ex) {
            Log.d("karma", "exception at last " + ex);
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        return null;
    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... voids) {
            String s = null;
            try {

                s = getText(voids[0]);


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.d("karma", "Exception occurred " + e);
            }

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            ls = ListSingleton.getInstance();

            ls.botMsgList.add(s);
            ls.customAdapter.notifyDataSetChanged();

        }
    }



}
