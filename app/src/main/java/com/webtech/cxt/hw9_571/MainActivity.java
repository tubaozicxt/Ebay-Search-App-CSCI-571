package com.webtech.cxt.hw9_571;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    private String pricefrom;
    private String priceto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("EbaySearch");
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

        EditText keywordsInput = (EditText) findViewById(R.id.keywords);
        keywords = keywordsInput.getText().toString();
        EditText pricefromInput = (EditText) findViewById(R.id.pricefrom);
        pricefrom = pricefromInput.getText().toString();
        EditText pricetoInput = (EditText) findViewById(R.id.priceto);
        priceto = pricetoInput.getText().toString();
        Spinner sortbySpinner = (Spinner) findViewById(R.id.sortby);
        int sortbyposition = sortbySpinner.getSelectedItemPosition();
        String sortedBy = "";
        switch (sortbyposition){
            case 0: sortedBy = "BestMatch";
                    break;
            case 1: sortedBy = "CurrentPriceHighest";
                    break;
            case 2: sortedBy = "PricePlusShippingHighest";
                    break;
            case 3: sortedBy = "PricePlusShippingLowest";
                    break;
        }

        boolean flag = validation();
        if(flag){
            String basicurl = "http://571php-env.elasticbeanstalk.com/core.php?";
            basicurl += "keywords=" + keywords +"&minPrice="+pricefrom+"&maxPrice="+priceto+"&shippingTime=&sortOrder="+sortedBy+"&pagination=5";
            DataProcessor dataProcessor = new DataProcessor();
            dataProcessor.execute(basicurl);
        }
    }

    private class DataProcessor extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return sendUrlAndGetJson(params[0]);
        }

        @Override
        protected void onPostExecute(String result){
//            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(result);
                String ack = jsonObject.getString("ack");
                if(ack.equals("Success")){
                    Intent intent = new Intent(MainActivity.this, DisplayItemsPage.class);
                    intent.putExtra("itemsJson", result);
                    intent.putExtra("keywords", keywords);
                    startActivity(intent);
                }else{
                    //No Result
                    TextView validateOutput = (TextView) findViewById(R.id.validation);
                    validateOutput.setText("No Results");
                    validateOutput.setTextColor(Color.rgb(0, 0, 0));
                    validateOutput.setTextSize(23);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    public boolean validation(){
        TextView validateOutput = (TextView) findViewById(R.id.validation);
        if(keywords.equals("")||keywords==null){
            validateOutput.setText("Please enter a keyword");
            return false;
        }
        if(!pricefrom.equals("")){
            if(!pricefrom.matches("-?[0-9]+.*[0-9]*")){
                validateOutput.setText("Price range must be number");
                return false;
            }
        }
        if(!priceto.equals("")){
            if(!priceto.matches("-?[0-9]+.*[0-9]*")){
                validateOutput.setText("Price range must be number");
                return false;
            }
        }
        if(!pricefrom.equals("")&&!priceto.equals("")){
            if(Integer.parseInt(pricefrom)<0){
                validateOutput.setText("Minimum price could not below 0");
                return false;
            }
            if(Integer.parseInt(priceto)<0){
                validateOutput.setText("Maximum price could not below 0");
                return false;
            }
            if(Integer.parseInt(pricefrom)>Integer.parseInt(priceto)){
                validateOutput.setText("Maxmum price cannot be less than munimum price");
                return false;
            }
        }
        return true;
    }

    public void cleanForm(View view){
        EditText keywordsInput = (EditText) findViewById(R.id.keywords);
        EditText pricefromInput = (EditText) findViewById(R.id.pricefrom);
        EditText pricetoInput = (EditText) findViewById(R.id.priceto);
        Spinner sortbySpinner = (Spinner) findViewById(R.id.sortby);
        TextView validateOutput = (TextView) findViewById(R.id.validation);

        keywordsInput.setText("");
        pricefromInput.setText("");
        pricetoInput.setText("");
        sortbySpinner.setSelection(0);
        validateOutput.setText("");
    }


}
