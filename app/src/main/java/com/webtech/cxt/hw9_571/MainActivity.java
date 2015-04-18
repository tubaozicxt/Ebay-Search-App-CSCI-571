package com.webtech.cxt.hw9_571;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.webtech.cxt.MESSAGE";
    private String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view){
        // get input object
        EditText keywordsInput = (EditText) findViewById(R.id.keywords);
        //get the content of input object
        keywords = keywordsInput.getText().toString();

        String basicurl = "http://571php-env.elasticbeanstalk.com/core.php?";
        basicurl += "keywords=" + keywords +"&minPrice=&maxPrice=&shippingTime=&sortOrder=BestMatch&pagination=5";
        DataProcessor dataProcessor = new DataProcessor();
        dataProcessor.execute(basicurl);
    }

    private class DataProcessor extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return sendUrlAndGetJson(params[0]);
        }

        @Override
        protected void onPostExecute(String result){
//            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, DisplayItemsPage.class);
            intent.putExtra("itemsJson", result);
            startActivity(intent);

        }
    }

    //send Url and get json as string
    public String sendUrlAndGetJson(String url){
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode= statusLine.getStatusCode();
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }
            }else{
                Log.e(MainActivity.class.toString(),"Failedet JSON object");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }



}
