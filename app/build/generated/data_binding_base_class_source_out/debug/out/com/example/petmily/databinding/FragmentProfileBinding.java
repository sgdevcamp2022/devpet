// Generated by data binding compiler. Do not edit!
package com.example.petmily.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.example.petmily.R;
import com.example.petmily.view.Fragment_Profile;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FragmentProfileBinding extends ViewDataBinding {
  @NonNull
  public final TextView about;

  @NonNull
  public final ImageView dm;

  @NonNull
  public final TextView follow;

  @NonNull
  public final ConstraintLayout followLayout;

  @NonNull
  public final TextView followNum;

  @NonNull
  public final TextView follower;

  @NonNull
  public final ConstraintLayout followerLayout;

  @NonNull
  public final TextView followerNum;

  @NonNull
  public final ConstraintLayout info;

  @NonNull
  public final ConstraintLayout infoProfile;

  @NonNull
  public final ImageView myPost;

  @NonNull
  public final LinearLayout myPostLayout;

  @NonNull
  public final TextView nickname;

  @NonNull
  public final ConstraintLayout post;

  @NonNull
  public final TextView postHalf;

  @NonNull
  public final ConstraintLayout postLayout;

  @NonNull
  public final TextView postNum;

  @NonNull
  public final ImageView profileImage;

  @NonNull
  public final RecyclerView searchPost;

  @NonNull
  public final ImageView setting;

  @NonNull
  public final ImageView tagPost;

  @NonNull
  public final LinearLayout tagPostLayout;

  @NonNull
  public final ConstraintLayout toolbar;

  @Bindable
  protected Fragment_Profile mProfile;

  protected FragmentProfileBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView about, ImageView dm, TextView follow, ConstraintLayout followLayout,
      TextView followNum, TextView follower, ConstraintLayout followerLayout, TextView followerNum,
      ConstraintLayout info, ConstraintLayout infoProfile, ImageView myPost,
      LinearLayout myPostLayout, TextView nickname, ConstraintLayout post, TextView postHalf,
      ConstraintLayout postLayout, TextView postNum, ImageView profileImage,
      RecyclerView searchPost, ImageView setting, ImageView tagPost, LinearLayout tagPostLayout,
      ConstraintLayout toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.about = about;
    this.dm = dm;
    this.follow = follow;
    this.followLayout = followLayout;
    this.followNum = followNum;
    this.follower = follower;
    this.followerLayout = followerLayout;
    this.followerNum = followerNum;
    this.info = info;
    this.infoProfile = infoProfile;
    this.myPost = myPost;
    this.myPostLayout = myPostLayout;
    this.nickname = nickname;
    this.post = post;
    this.postHalf = postHalf;
    this.postLayout = postLayout;
    this.postNum = postNum;
    this.profileImage = profileImage;
    this.searchPost = searchPost;
    this.setting = setting;
    this.tagPost = tagPost;
    this.tagPostLayout = tagPostLayout;
    this.toolbar = toolbar;
  }

  public abstract void setProfile(@Nullable Fragment_Profile profile);

  @Nullable
  public Fragment_Profile getProfile() {
    return mProfile;
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_profile, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FragmentProfileBinding>inflateInternal(inflater, R.layout.fragment_profile, root, attachToRoot, component);
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.fragment_profile, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FragmentProfileBinding>inflateInternal(inflater, R.layout.fragment_profile, null, false, component);
  }

  public static FragmentProfileBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static FragmentProfileBinding bind(@NonNull View view, @Nullable Object component) {
    return (FragmentProfileBinding)bind(component, view, R.layout.fragment_profile);
  }
}
