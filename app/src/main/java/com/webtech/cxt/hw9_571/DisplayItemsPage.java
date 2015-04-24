package com.webtech.cxt.hw9_571;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class DisplayItemsPage extends ActionBarActivity {

    List<Item> itemList = new ArrayList<Item>();
    private String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("ResultActivity");
        setContentView(R.layout.activity_display_items_page);
        resultsDisplay();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_items_page, menu);
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



    public void resultsDisplay(){
        Intent intent = getIntent();
        keywords = intent.getStringExtra("keywords");
        TextView textKeywords = (TextView) findViewById(R.id.topTitle);
        textKeywords.setText("Result for '" + keywords + "'");
        String itemsJson = intent.getStringExtra("itemsJson");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(itemsJson);
            //extract item info into lists
            extractItems(jsonObject);
            //display list view
            displayListView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayListView(){
        ArrayAdapter<Item> adapter = new CustomAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private class CustomAdapter extends ArrayAdapter<Item>{
        public CustomAdapter(){
            super(DisplayItemsPage.this, R.layout.item_view, itemList);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }
            //get the item entity
            Item itemEntity = itemList.get(position);

            //create view
            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_imageView);
            ImageProcessor imageProcessor = new ImageProcessor();
            Bitmap image = imageProcessor.extract(itemEntity.getImageUrl());
            imageView.setImageBitmap(image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item itemEntity = itemList.get(position);
                    String url = itemEntity.getViewItemURL();
                    Uri uri = Uri.parse(url);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            });

            TextView titleView = (TextView) itemView.findViewById(R.id.item_title);
            titleView.setText(itemEntity.getTitle());
            titleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item itemEntity = itemList.get(position);
                    Intent intent = new Intent(DisplayItemsPage.this, DetailItem.class);
                    intent.putExtra("sampleTest", itemEntity);
                    startActivity(intent);
                }
            });

            TextView priceView = (TextView) itemView.findViewById(R.id.item_price);
            String price = itemEntity.getPrice();
            String freeornot = itemEntity.getFreeornot();
            String combine = "Price: $"+price+freeornot;
            priceView.setText(combine);



            return itemView;
        }

    }

    private void extractItems(JSONObject jsonObject) throws JSONException {
        int itemNumber = 0;
        int itemCount = jsonObject.getInt("itemCount");
        int resultCount = jsonObject.getInt("resultCount");
        if(resultCount<itemCount) {
            itemNumber = resultCount;
        }else{
            itemNumber = itemCount;
        }
        for(int i=0; i<itemNumber; i++){
            String item = "item" + i;
            JSONObject itemObject = jsonObject.getJSONObject(item);
            JSONObject itemBasicInfo = itemObject.getJSONObject("basicInfo");
            String title = itemBasicInfo.getString("title");
            String price = itemBasicInfo.getString("convertedCurrentPrice");
            String imageUrl = itemBasicInfo.getString("galleryURL");
            String imageSuperSizeUrl = itemBasicInfo.getString("pictureURLSuperSize");
            String shippingCost = itemBasicInfo.getString("shippingServiceCost");

            String freeornot = "";
            if(!shippingCost.equals("0.0")&&!shippingCost.equals("")){
                freeornot = "(+$" +shippingCost+ " for shipping)";
            }else{
                freeornot = "(FREE Shipping)";
            }
            String viewItemURL = itemBasicInfo.getString("viewItemURL");
            String location = itemBasicInfo.getString("location");
            String condition = itemBasicInfo.getString("conditionDisplayName");
            String buyingFormat = itemBasicInfo.getString("listingType");
            String categoryName = itemBasicInfo.getString("categoryName");
            String topRated = itemBasicInfo.getString("topRatedListing");

            JSONObject itemSellerInfo = itemObject.getJSONObject("sellerInfo");
            String userName = itemSellerInfo.getString("sellerUserName");
            String feedbackScore = itemSellerInfo.getString("feedbackScore");
            String positiveFeedback = itemSellerInfo.getString("positiveFeedbackPercent");
            String topRatedSeller = itemSellerInfo.getString("topRatedSeller");
            String sellerStoreName = itemSellerInfo.getString("sellerStoreName");
            String feedbackRating = itemSellerInfo.getString("feedbackRatingStar");

            JSONObject itemShippingInfo = itemObject.getJSONObject("shippingInfo");
            String shippingType = itemShippingInfo.getString("shippingType");
            String shippingLocation = itemShippingInfo.getString("shipToLocations");
            String expeditedShipping = itemShippingInfo.getString("expeditedShipping");
            String oneDayShipping = itemShippingInfo.getString("oneDayShippingAvailable");
            String returnAccepted = itemShippingInfo.getString("returnsAccepted");
            String handlingTime = itemShippingInfo.getString("handlingTime");

            Item itemEntity = new Item();
            itemEntity.setTitle(title);
            itemEntity.setPrice(price);
            itemEntity.setFreeornot(freeornot);
            itemEntity.setImageUrl(imageUrl);
            itemEntity.setImageSuperSizeUrl(imageSuperSizeUrl);
            itemEntity.setViewItemURL(viewItemURL);
            itemEntity.setLocationInfo(location);
            itemEntity.setCondition(condition);
            itemEntity.setBuyingFormat(buyingFormat);
            itemEntity.setCategoryName(categoryName);
            itemEntity.setTopRated(topRated);
            itemEntity.setUserName(userName);
            itemEntity.setFeedbackScore(feedbackScore);
            itemEntity.setPositiveFeedback(positiveFeedback);
            itemEntity.setTopRatedSeller(topRatedSeller);
            itemEntity.setSellerStoreName(sellerStoreName);
            itemEntity.setFeedbackRating(feedbackRating);
            itemEntity.setShippingType(shippingType);
            itemEntity.setShippingLocation(shippingLocation);
            itemEntity.setExpeditedShipping(expeditedShipping);
            itemEntity.setOneDayShipping(oneDayShipping);
            itemEntity.setReturnAccepted(returnAccepted);
            itemEntity.setHandlingTime(handlingTime);

            itemList.add(itemEntity);
        }
    }

}
