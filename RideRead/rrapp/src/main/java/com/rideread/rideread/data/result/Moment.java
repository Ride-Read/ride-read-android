package com.rideread.rideread.data.result;

import java.util.List;

/**
 * Created by SkyXiao on 2017/4/5.
 */

public class Moment {
    /**
     * mid : 20
     * uid : 7
     * type : 1
     * cover : null
     * video : null
     * msg : 测试测试测试测试测试测试测试测试上厕所测试测试测试测试
     * thumbs : null
     * pictureString : http://pic8.qiyipic.com/image/20160728/ed/a7/a_100013977_m_601_m5_195_260.jpg
     * created_at : 1491488791000
     * updated_at : 1491488791000
     * pictures : ["http://pic8.qiyipic.com/image/20160728/ed/a7/a_100013977_m_601_m5_195_260.jpg"]
     * user : {"uid":7,"username":"亮亮","face_url":"rideread/users/registe","is_followed":-1}
     * comment : []
     * thumbs_up : []
     * latitude : 22.647
     * longitude : 114.037
     * distance : 92011
     * distanceString : 92.01km
     */

    private int mid;
    private int uid;
    private int type;
    private String cover;
    private String video;
    private String msg;
    //    private Object thumbs;
    private long createdAt;
    private long updatedAt;
    private MomentUser user;

    private double latitude;
    private double longitude;
    private int distance;
    private String distanceString;
    private String momentLocation;

    private List<String> pictures;
    private List<Comment> comment;
    private List<ThumbsUpUser> thumbsUp;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public MomentUser getUser() {
        return user;
    }

    public void setUser(MomentUser user) {
        this.user = user;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDistanceString() {
        return distanceString;
    }

    public void setDistanceString(String distanceString) {
        this.distanceString = distanceString;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<ThumbsUpUser> getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(List<ThumbsUpUser> thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public String getMomentLocation() {
        return momentLocation;
    }

    public void setMomentLocation(String momentLocation) {
        this.momentLocation = momentLocation;
    }
}
