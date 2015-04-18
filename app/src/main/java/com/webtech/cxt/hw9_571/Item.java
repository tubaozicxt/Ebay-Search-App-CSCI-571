package com.webtech.cxt.hw9_571;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zjjcxt on 4/16/15.
 */
public class Item implements Serializable {

    //basicinfo
    private String title;
    private String viewItemURL;
    private String price;
    private String freeornot;
    private String locationInfo;
    private String categoryName;
    private String condition;
    private String buyingFormat;
    private String topRated;
    private String imageUrl;
    private String imageSuperSizeUrl;

    //sellerinfo
    private String userName;
    private String feedbackScore;
    private String positiveFeedback;
    private String feedbackRating;
    private String sellerStoreName;
    private String topRatedSeller;

    //shippinginfo
    private String shippingType;
    private String handlingTime;
    private String shippingLocation;
    private String expeditedShipping;
    private String oneDayShipping;
    private String returnAccepted;


    public String getImageSuperSizeUrl() {
        return imageSuperSizeUrl;
    }

    public void setImageSuperSizeUrl(String imageSuperSizeUrl) {
        this.imageSuperSizeUrl = imageSuperSizeUrl;
    }

    public String getTopRatedSeller() {
        return topRatedSeller;
    }

    public void setTopRatedSeller(String topRatedSeller) {
        this.topRatedSeller = topRatedSeller;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewItemURL() {
        return viewItemURL;
    }

    public void setViewItemURL(String viewItemURL) {
        this.viewItemURL = viewItemURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFreeornot() {
        return freeornot;
    }

    public void setFreeornot(String freeornot) {
        this.freeornot = freeornot;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getBuyingFormat() {
        return buyingFormat;
    }

    public void setBuyingFormat(String buyingFormat) {
        this.buyingFormat = buyingFormat;
    }

    public String getTopRated() {
        return topRated;
    }

    public void setTopRated(String topRated) {
        this.topRated = topRated;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFeedbackScore() {
        return feedbackScore;
    }

    public void setFeedbackScore(String feedbackScore) {
        this.feedbackScore = feedbackScore;
    }

    public String getPositiveFeedback() {
        return positiveFeedback;
    }

    public void setPositiveFeedback(String positiveFeedback) {
        this.positiveFeedback = positiveFeedback;
    }

    public String getFeedbackRating() {
        return feedbackRating;
    }

    public void setFeedbackRating(String feedbackRating) {
        this.feedbackRating = feedbackRating;
    }

    public String getSellerStoreName() {
        return sellerStoreName;
    }

    public void setSellerStoreName(String sellerStoreName) {
        this.sellerStoreName = sellerStoreName;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(String handlingTime) {
        this.handlingTime = handlingTime;
    }

    public String getShippingLocation() {
        return shippingLocation;
    }

    public void setShippingLocation(String shippingLocation) {
        this.shippingLocation = shippingLocation;
    }

    public String getExpeditedShipping() {
        return expeditedShipping;
    }

    public void setExpeditedShipping(String expeditedShipping) {
        this.expeditedShipping = expeditedShipping;
    }

    public String getOneDayShipping() {
        return oneDayShipping;
    }

    public void setOneDayShipping(String oneDayShipping) {
        this.oneDayShipping = oneDayShipping;
    }

    public String getReturnAccepted() {
        return returnAccepted;
    }

    public void setReturnAccepted(String returnAccepted) {
        this.returnAccepted = returnAccepted;
    }

    public Item(String title, String viewItemURL, String price, String freeornot, String locationInfo, String categoryName, String condition, String buyingFormat, String topRated, String imageUrl, String userName, String feedbackScore, String positiveFeedback, String feedbackRating, String sellerStoreName, String shippingType, String handlingTime, String shippingLocation, String expeditedShipping, String oneDayShipping, String returnAccepted, String topRatedSeller, String imageSuperSizeUrl) {
        this.title = title;
        this.viewItemURL = viewItemURL;
        this.price = price;
        this.freeornot = freeornot;
        this.locationInfo = locationInfo;
        this.categoryName = categoryName;
        this.condition = condition;
        this.buyingFormat = buyingFormat;
        this.topRated = topRated;
        this.imageUrl = imageUrl;
        this.userName = userName;
        this.feedbackScore = feedbackScore;
        this.positiveFeedback = positiveFeedback;
        this.feedbackRating = feedbackRating;
        this.sellerStoreName = sellerStoreName;
        this.shippingType = shippingType;
        this.handlingTime = handlingTime;
        this.shippingLocation = shippingLocation;
        this.expeditedShipping = expeditedShipping;
        this.oneDayShipping = oneDayShipping;
        this.returnAccepted = returnAccepted;
        this.topRatedSeller = topRatedSeller;
        this.imageSuperSizeUrl = imageSuperSizeUrl;
    }

    public Item() {
    }


}
