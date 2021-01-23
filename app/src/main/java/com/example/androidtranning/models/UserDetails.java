package com.example.androidtranning.models;




public class UserDetails {
    public String userId;
    public String userEmail;
    public String userFirstName;
    public String userLastName;
    public String fcmToken;
    public String userToken;
    public String userType;
    public String imageUrl;
    public String createdOn;
    public boolean active;
    public String userLatitude;
    public String userLongitude;
    public String address;
    public String bloodGroup;
    public String city;
    public String dateOfBirth;
    public String gender;
    public String mobileNumber;
    public String modifiedOn;
    public String pincode;
    public String stateId;

    public UserDetails() {
    }

    public UserDetails(String userId, String userEmail, String userFirstName, String userLastName, String fcmToken, String userToken, String userType, String imageUrl, String createdOn, boolean active, String userLatitude, String userLongitude, String address, String bloodGroup, String city, String dateOfBirth, String gender, String mobileNumber, String modifiedOn, String pincode, String stateId) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.fcmToken = fcmToken;
        this.userToken = userToken;
        this.userType = userType;
        this.imageUrl = imageUrl;
        this.createdOn = createdOn;
        this.active = active;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.mobileNumber = mobileNumber;
        this.modifiedOn = modifiedOn;
        this.pincode = pincode;
        this.stateId = stateId;
    }

}
