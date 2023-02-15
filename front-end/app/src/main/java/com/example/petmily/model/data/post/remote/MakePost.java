package com.example.petmily.model.data.post.remote;

import com.example.petmily.model.data.post.Entity.Location;

import java.util.List;

public class MakePost {

    String content;//포스트 내용
    String category;     //카테고리 0번 -> 시설 1-> 개인 2-> 그룹
    Location location;
    List<Integer> tag; //태그된 유저 uuid
    String hashTag;
    List<String> imageUrl;


    public MakePost(String content, String category, Location location, List<Integer> tag, String hashTag, List<String> imageUrl) {
        this.content = content;
        this.category = category;
        this.location = location;
        this.tag = tag;
        this.hashTag = hashTag;
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
}
