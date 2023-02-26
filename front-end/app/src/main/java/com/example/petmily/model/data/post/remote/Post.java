package com.example.petmily.model.data.post.remote;

import com.example.petmily.model.data.post.Entity.HashTags;
import com.example.petmily.model.data.post.Entity.Location;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {

    @SerializedName("createdAt")
    String createdAt;

    @SerializedName("updatedAt")
    String updatedAt;

    @SerializedName("feedId")
    int feedId;

    @SerializedName("content")
    String content;

    @SerializedName("location")
    Location location;

    @SerializedName("tagUsers")
    List<Integer> tagUsers;

    @SerializedName("groupId")
    int groupId;//null

    @SerializedName("imageUrl")
    List<String> imageUrl;

    @SerializedName("profileId")
    int userId;

    @SerializedName("hashTag")
    HashTags hashTag;

    @SerializedName("comments")
    String comments;

    @SerializedName("favorite")
    boolean favorite;

    @SerializedName("used")
    boolean used;

    public Post(String createdAt, String updatedAt, int feedId, String content, Location location, List<Integer> tagUsers, int groupId, List<String> imageUrl, int userId, HashTags hashTag, String comments, boolean favorite, boolean used) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.feedId = feedId;
        this.content = content;
        this.location = location;
        this.tagUsers = tagUsers;
        this.groupId = groupId;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.hashTag = hashTag;
        this.comments = comments;
        this.favorite = favorite;
        this.used = used;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getFeedId() {
        return feedId;
    }

    public void setFeedId(int feedId) {
        this.feedId = feedId;
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

    public List<Integer> getTagUsers() {
        return tagUsers;
    }

    public void setTagUsers(List<Integer> tagUsers) {
        this.tagUsers = tagUsers;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public HashTags getHashTag() {
        return hashTag;
    }

    public void setHashTag(HashTags hashTag) {
        this.hashTag = hashTag;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
