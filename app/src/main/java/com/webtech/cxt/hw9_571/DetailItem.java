package com.webtech.cxt.hw9_571;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.internal.WebDialog;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


public class DetailItem extends ActionBarActivity {
    Item itemEntity = null;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //load tab object
        tabLoader();

        //basic info
        TextView textCategoryName = (TextView) findViewById(R.id.categoryName_display);
        String categoryName = itemEntity.getCategoryName();
        textCategoryName.setText(categoryName);


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
        String url = itemEntity.getViewItemURL();
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(title)
                    .setContentUrl(Uri.parse(url)).build();
            shareDialog.show(linkContent, ShareDialog.Mode.FEED);
        }
    }

    public void facebookInitialize(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
    }
}
