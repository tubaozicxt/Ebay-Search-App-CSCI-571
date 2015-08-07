package com.webtech.cxt.hw9_571;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.WebDialog;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


public class DetailItem extends ActionBarActivity {
    Item itemEntity = null;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("DetailsActivity");
        setContentView(R.layout.activity_detail_item);
        facebookInitialize();
        Bundle bundle = getIntent().getExtras();
        itemEntity = (Item) bundle.getSerializable("sampleTest");
        displayDetailInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_item, menu);
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

    public void displayDetailInfo(){
        ImageView superimageView = (ImageView) findViewById(R.id.detail_image);
        String url = itemEntity.getImageSuperSizeUrl();
        if(url.equals("")||url==null){
            url = itemEntity.getImageUrl();
        }
        ImageProcessor imageProcessor = new ImageProcessor();
        Bitmap superSizeImage = imageProcessor.extract(url);
        superimageView.setImageBitmap(superSizeImage);

        TextView textTitle = (TextView) findViewById(R.id.detail_title);
        String title = itemEntity.getTitle();
        textTitle.setText(title);

        TextView textPrice = (TextView) findViewById(R.id.detail_price);
        String price = itemEntity.getPrice();
        String freeornot = itemEntity.getFreeornot();
        String combine = "Price: $"+price+freeornot;
        textPrice.setText(combine);

        TextView textLocation = (TextView) findViewById(R.id.detail_location);
        String locations = itemEntity.getLocationInfo();
        textLocation.setText(locations);

        ImageView facebookView = (ImageView) findViewById(R.id.facebook_icon);
        Bitmap facebookImage = imageProcessor.extract("http://cs-server.usc.edu:45678/hw/hw8/fb.png");
        facebookView.setImageBitmap(facebookImage);

        ImageView topRatedView = (ImageView) findViewById(R.id.topRated_icon);
        String topRated = itemEntity.getTopRated();
        if(topRated.equals("true")){
            Bitmap topRatedImage = imageProcessor.extract("http://cs-server.usc.edu:45678/hw/hw8/itemTopRated.jpg");
            topRatedView.setImageBitmap(topRatedImage);
        }

        //load tab object
        tabLoader();

        //basic info
        TextView textCategoryName = (TextView) findViewById(R.id.categoryName_display);
        String categoryName = itemEntity.getCategoryName();
        textCategoryName.setText(categoryName);

        TextView textConditonName = (TextView) findViewById(R.id.condition_display);
        String conditionName = itemEntity.getCondition();
        textConditonName.setText(conditionName);

        TextView textBuyingFormat = (TextView) findViewById(R.id.buyingFormat_display);
        String buyingFormat = itemEntity.getBuyingFormat();
        textBuyingFormat.setText(buyingFormat);

        //seller info
        TextView textUsername = (TextView) findViewById(R.id.userName_display);
        String username = itemEntity.getUserName();
        textUsername.setText(username);

        TextView textFeedbackScore = (TextView) findViewById(R.id.feedbackScore_display);
        String feedbackScore = itemEntity.getFeedbackScore();
        textFeedbackScore.setText(feedbackScore);

        TextView textPositiveFeedback = (TextView) findViewById(R.id.positiveFeedback_display);
        String positiveFeedback = itemEntity.getPositiveFeedback();
        textPositiveFeedback.setText(positiveFeedback);

        TextView textFeedbackRating = (TextView) findViewById(R.id.feedbackRating_display);
        String feedbackRating = itemEntity.getFeedbackRating();
        textFeedbackRating.setText(feedbackRating);

        ImageView imageTopTatedSeller = (ImageView) findViewById(R.id.topRatedSeller_display);
        String topRatedSeller = itemEntity.getTopRatedSeller();
        if(topRatedSeller.equals("false")){
            imageTopTatedSeller.setImageDrawable(getResources().getDrawable(R.drawable.cross));
        }else{
            imageTopTatedSeller.setImageDrawable(getResources().getDrawable(R.drawable.ok));
        }

        TextView textStoreName = (TextView) findViewById(R.id.store_display);
        String storeName = itemEntity.getSellerStoreName();
        if(storeName.equals("")||storeName==null){
            textStoreName.setText("N/A");
        }else{
            textStoreName.setText(storeName);
        }

        //shipping info
        TextView textShippingType = (TextView) findViewById(R.id.shippingType_display);
        String shippingType = itemEntity.getShippingType();
        if(shippingType.equals("")){
            textShippingType.setText("N/A");
        }else{
            String shippingTypeResult = "";
            for(int i = 0; i < shippingType.length(); i++){
                char c = shippingType.charAt(i);
                if(i==0){
                    shippingTypeResult += String.valueOf(c);
                }else{
                    if(Character.isUpperCase(c)){
                        shippingTypeResult += ","+String.valueOf(c);
                    }else{
                        shippingTypeResult += String.valueOf(c);
                    }
                }
            }
            textShippingType.setText(shippingTypeResult);
        }


        TextView textHandlingTime = (TextView) findViewById(R.id.handlingTime_display);
        String handlingTime = itemEntity.getHandlingTime();
        if(handlingTime.equals("")||handlingTime==null){
            textHandlingTime.setText("N/A");
        }else{
            textHandlingTime.setText(handlingTime);
        }

        TextView textShippingLocations = (TextView) findViewById(R.id.shippingLocation_display);
        String shippingLocations = itemEntity.getShippingLocation();
        textShippingLocations.setText(shippingLocations);

        ImageView imageExpeditedShipping = (ImageView) findViewById(R.id.expeditedShipping_display);
        String expeditedShipping = itemEntity.getExpeditedShipping();
        if(expeditedShipping.equals("false")){
            imageExpeditedShipping.setImageDrawable(getResources().getDrawable(R.drawable.cross));
        }else{
            imageExpeditedShipping.setImageDrawable(getResources().getDrawable(R.drawable.ok));
        }

        ImageView imageOneDayShipping = (ImageView) findViewById(R.id.oneDayShipping_display);
        String ondayShipping = itemEntity.getOneDayShipping();
        if(ondayShipping.equals("false")){
            imageOneDayShipping.setImageDrawable(getResources().getDrawable(R.drawable.cross));
        }else{
            imageOneDayShipping.setImageDrawable(getResources().getDrawable(R.drawable.ok));
        }

        ImageView imageReturnsAccepted = (ImageView) findViewById(R.id.returnAccepted_display);
        String returnsAccepted = itemEntity.getReturnAccepted();
        if(returnsAccepted.equals("false")){
            imageReturnsAccepted.setImageDrawable(getResources().getDrawable(R.drawable.cross));
        }else{
            imageReturnsAccepted.setImageDrawable(getResources().getDrawable(R.drawable.ok));
        }

    }

    public void tabLoader(){
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("basicinfo");
        tabSpec1.setContent(R.id.basicinfo);
        tabSpec1.setIndicator("BASICINFO");
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("seller");
        tabSpec2.setContent(R.id.seller);
        tabSpec2.setIndicator("SELLER");
        tabHost.addTab(tabSpec2);

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("shipping");
        tabSpec3.setContent(R.id.shipping);
        tabSpec3.setIndicator("SHIPPING");
        tabHost.addTab(tabSpec3);
    }

    public void postFacebook(View view){
        String title = itemEntity.getTitle();
        String itemurl = itemEntity.getViewItemURL();
        String imageurl = itemEntity.getImageUrl();
        String price = itemEntity.getPrice();
        String freeornot = itemEntity.getFreeornot();
        String locations = itemEntity.getLocationInfo();
        String combine = "Price: $"+price+freeornot+", Location:"+locations;

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(title)
                    .setContentUrl(Uri.parse(itemurl))
                    .setImageUrl(Uri.parse(imageurl))
                    .setContentDescription(combine).build();
            shareDialog.show(linkContent, ShareDialog.Mode.FEED);
        }
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                String id = result.getPostId();
                if(id!=null){
                    String output = "Post successfully!!! Posted Story, ID: "+id;
                    Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Post meet Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void facebookInitialize(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

    }

    public void openItemLink(View view){
        String url = itemEntity.getViewItemURL();
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
