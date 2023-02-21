
package com.example.petmily.model.data.post.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.petmily.model.data.post.Entity.Comment;
import com.example.petmily.model.data.post.Entity.HashTags;
import com.example.petmily.model.data.post.Entity.Location;
import com.example.petmily.model.data.post.Entity.Profile;

import java.util.List;

@Entity(tableName = "PostSQL")
public class PostSQL {


    String createdAt;

    String updatedAt;
    @NonNull
    @PrimaryKey
    int feedId;

    String content;

    Location location;

    List<Integer> tagUsers;

    int groupId;//null

    List<String> imageUrl;

    int userId;

    HashTags hashTag;

    String comments;

    boolean favorite;

    boolean used;

//    String postId;
//    Profile profile;
//    //String profileImage;
//    //int userId;
//    //String nickname;
//
//    //카테고리 0번 -> 시설 1-> 개인 2-> 그룹
//    Location location;
//    //int category;
//    //Coord coord
//    //double latitude;
//    //double lonngitude;
//
//    List<String> imageUrl;
//    int like;//좋아요 수
//    boolean likeCheck;
//    String content;
//    List<String> hashTag;
//    List<Comment> comments;


    public PostSQL(String createdAt, String updatedAt, int feedId, String content, Location location, List<Integer> tagUsers, int groupId, List<String> imageUrl, int userId, HashTags hashTag, String comments, boolean favorite, boolean used) {
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
