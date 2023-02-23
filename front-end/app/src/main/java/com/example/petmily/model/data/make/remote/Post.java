package com.example.petmily.model.data.make.remote;

import com.example.petmily.model.data.make.Entity.HashTags;
import com.example.petmily.model.data.make.Entity.Location;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {

    @SerializedName("content")
    String content;

    @SerializedName("location")
    Location location;
    //int category;
    //Coord coord
        //double latitude;
        //double lonngitude;
    @SerializedName("tagUsers")
    List<Integer> tag;

    @SerializedName("hashTag")
    List<HashTags> hashTag;

    @SerializedName("groupId")
    int groupId;//null값으로 보낼 예정

    //카테고리 0번 -> 시설 1-> 개인 2-> 그룹
    @SerializedName("imageUrl")
    List<String> imageUrl;

    public Post(String content, Location location, List<Integer> tag, List<HashTags> hashTag, int groupId, List<String> imageUrl) {
        this.content = content;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Integer> getTag() {
        return tag;
    }

    public void setTag(List<Integer> tag) {
        this.tag = tag;
    }

    public List<HashTags> getHashTag() {
        return hashTag;
    }

    public void setHashTag(List<HashTags> hashTag) {
        this.hashTag = hashTag;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
