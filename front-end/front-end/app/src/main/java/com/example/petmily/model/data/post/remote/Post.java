package com.example.petmily.model.data.post.remote;

import com.example.petmily.model.data.post.Entity.Comment;
import com.example.petmily.model.data.post.Entity.Location;
import com.example.petmily.model.data.post.Entity.Profile;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {

    @SerializedName("profile")
    Profile profile;
    //String profileImage;
    //int userId;
    //String nickname;

    @SerializedName("location")
    Location location;
    //int category;
    //Coord coord
        //double latitude;
        //double lonngitude;

    @SerializedName("imageUrl")
    List<String> imageUrl;

    @SerializedName("like")
    int like;//좋아요 수

    @SerializedName("likeCheck")
    boolean likeCheck;

    @SerializedName("content")
    String content;

    //카테고리 0번 -> 시설 1-> 개인 2-> 그룹

    @SerializedName("hashTag")
    List<String> hashTag;

    @SerializedName("comments")
    List<Comment> comments;
    //Profile profile;
    //S


    public Post(Profile profile, Location location, List<String> imageUrl, int like, boolean likeCheck, String content, List<String> hashTag, List<Comment> comments) {
        this.profile = profile;
        this.location = location;
        this.imageUrl = imageUrl;
        this.like = like;
        this.likeCheck = likeCheck;
        this.content = content;
        this.hashTag = hashTag;
        this.comments = comments;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isLikeCheck() {
        return likeCheck;
    }

    public void setLikeCheck(boolean likeCheck) {
        this.likeCheck = likeCheck;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getHashTag() {
        return hashTag;
    }

    public void setHashTag(List<String> hashTag) {
        this.hashTag = hashTag;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
