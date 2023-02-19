package com.example.petmily.model.data.make.remote;

import com.example.petmily.model.data.post.Entity.Location;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {

    @SerializedName("content")
    String content;

    @SerializedName("category")
    int category;

    @SerializedName("location")
    Location location;
    //int category;
    //Coord coord
        //double latitude;
        //double lonngitude;
    @SerializedName("tag")
    List<String> tag;

    @SerializedName("hashTag")
    List<String> hashTag;

    @SerializedName("groupId")
    int groupId;//null값으로 보낼 예정

    //카테고리 0번 -> 시설 1-> 개인 2-> 그룹
    @SerializedName("imageUrl")
    List<String> imageUrl;

    public Post(String content, int category, Location location, List<String> tag, List<String> hashTag, List<String> imageUrl, int groupId) {
        this.content = content;
        this.category = category;
        this.location = location;
        this.tag = tag;
        this.hashTag = hashTag;
        this.groupId = groupId;
        this.imageUrl = imageUrl;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<String> getHashTag() {
        return hashTag;
    }

    public void setHashTag(List<String> hashTag) {
        this.hashTag = hashTag;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Post{" +
                "content='" + content + '\'' +
                ", category=" + category +
                ", location_latitude=" + location.getCoord().getLatitude() +
                ", location_longitude=" + location.getCoord().getLonngitude() +
                ", tag=" + tag +
                ", hashTag=" + hashTag +
                ", groupId=" + groupId +
                ", imageUrl=" + imageUrl +
                '}';
    }
}
