
package com.example.petmily.model.data.post.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.petmily.model.data.post.Entity.Comment;
import com.example.petmily.model.data.post.Entity.Location;
import com.example.petmily.model.data.post.Entity.Profile;

import java.util.List;

@Entity(tableName = "PostSQL")
public class PostSQL {

    @NonNull
    @PrimaryKey
    String postId;
    Profile profile;
    //String profileImage;
    //int userId;
    //String nickname;

    //카테고리 0번 -> 시설 1-> 개인 2-> 그룹
    Location location;
    //int category;
    //Coord coord
    //double latitude;
    //double lonngitude;

    List<String> imageUrl;
    int like;//좋아요 수
    boolean likeCheck;
    String content;
    List<String> hashTag;
    List<Comment> comments;

    public PostSQL(@NonNull String postId, Profile profile, Location location, List<String> imageUrl, int like, boolean likeCheck, String content, List<String> hashTag, List<Comment> comments) {
        this.postId = postId;
        this.profile = profile;
        this.location = location;
        this.imageUrl = imageUrl;
        this.like = like;
        this.likeCheck = likeCheck;
        this.content = content;
        this.hashTag = hashTag;
        this.comments = comments;
    }

    @NonNull
    public String getPostId() {
        return postId;
    }

    public void setPostId(@NonNull String postId) {
        this.postId = postId;
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
