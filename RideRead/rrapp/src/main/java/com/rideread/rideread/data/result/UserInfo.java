package com.rideread.rideread.data.result;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的用户数据
 */

public class UserInfo extends UserBaseInfo {

    private int sex;//0-未知，1-男，2-女
    private String school;
    private String phonenumber;
    private long updatedAt;
    private int follower;
    private String token;
    private String hometown;
    private String location;
    private long createdAt;
    private long birthday;
    private int following;
    private String career;

    private List<String> tags;
    private double longitude;
    private double latitude;

    private String rideReadId;


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getRideReadId() {
        return rideReadId;
    }

    public void setRideReadId(String rideReadId) {
        this.rideReadId = rideReadId;
    }


    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
}
//"data": {
//        "uid": 6,
//        "username": "启幕票4444",
//        "password": "8611872123a6b667c9729e936b7358858f695cde",
//        "sex": null,
//        "school": null,
//        "phonenumber": "13203356720",
//        "updated_at": 1491398541000,
//        "follower": null,
//        "token": "385ae8849d36ec82bde4884323e8e1627e59e1d3",
//        "hometown": null,
//        "face_url": "localhost:8080/rideread/users/registe",
//        "signature": null,
//        "location": null,
//        "created_at": 1491394654000,
//        "birthday": null,
//        "following": null,
//        "career": null,
//        "tagString": null,
//        "tags": null,
//        "longitude": 113.314086,
//        "latitude": 23.136552,
//        "is_followed": null,
//        "rideReadId": "qimupiao3"
//        }