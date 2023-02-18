package com.example.petmily;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.petmily.databinding.ActivityChatBindingImpl;
import com.example.petmily.databinding.ActivityChatRoomBindingImpl;
import com.example.petmily.databinding.ActivityJoinBindingImpl;
import com.example.petmily.databinding.ActivityLoginBindingImpl;
import com.example.petmily.databinding.ActivityMainBindingImpl;
import com.example.petmily.databinding.ActivityMakeBindingImpl;
import com.example.petmily.databinding.ActivityMakeProfileBindingImpl;
import com.example.petmily.databinding.ActivityPetAppendBindingImpl;
import com.example.petmily.databinding.AlarmListBindingImpl;
import com.example.petmily.databinding.ChatListBindingImpl;
import com.example.petmily.databinding.DialogMakeBindingImpl;
import com.example.petmily.databinding.FragmentAlarmBindingImpl;
import com.example.petmily.databinding.FragmentHomeBindingImpl;
import com.example.petmily.databinding.FragmentMessageBindingImpl;
import com.example.petmily.databinding.FragmentProfileBindingImpl;
import com.example.petmily.databinding.FragmentSearchBindingImpl;
import com.example.petmily.databinding.MakeListBindingImpl;
import com.example.petmily.databinding.MessageListBindingImpl;
import com.example.petmily.databinding.PetListBindingImpl;
import com.example.petmily.databinding.PlaceListBindingImpl;
import com.example.petmily.databinding.PostListFullBindingImpl;
import com.example.petmily.databinding.PostListGridBindingImpl;
import com.example.petmily.databinding.PostListHalfBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCHAT = 1;

  private static final int LAYOUT_ACTIVITYCHATROOM = 2;

  private static final int LAYOUT_ACTIVITYJOIN = 3;

  private static final int LAYOUT_ACTIVITYLOGIN = 4;

  private static final int LAYOUT_ACTIVITYMAIN = 5;

  private static final int LAYOUT_ACTIVITYMAKE = 6;

  private static final int LAYOUT_ACTIVITYMAKEPROFILE = 7;

  private static final int LAYOUT_ACTIVITYPETAPPEND = 8;

  private static final int LAYOUT_ALARMLIST = 9;

  private static final int LAYOUT_CHATLIST = 10;

  private static final int LAYOUT_DIALOGMAKE = 11;

  private static final int LAYOUT_FRAGMENTALARM = 12;

  private static final int LAYOUT_FRAGMENTHOME = 13;

  private static final int LAYOUT_FRAGMENTMESSAGE = 14;

  private static final int LAYOUT_FRAGMENTPROFILE = 15;

  private static final int LAYOUT_FRAGMENTSEARCH = 16;

  private static final int LAYOUT_MAKELIST = 17;

  private static final int LAYOUT_MESSAGELIST = 18;

  private static final int LAYOUT_PETLIST = 19;

  private static final int LAYOUT_PLACELIST = 20;

  private static final int LAYOUT_POSTLISTFULL = 21;

  private static final int LAYOUT_POSTLISTGRID = 22;

  private static final int LAYOUT_POSTLISTHALF = 23;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(23);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.activity_chat, LAYOUT_ACTIVITYCHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.activity_chat_room, LAYOUT_ACTIVITYCHATROOM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.activity_join, LAYOUT_ACTIVITYJOIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.activity_make, LAYOUT_ACTIVITYMAKE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.activity_make_profile, LAYOUT_ACTIVITYMAKEPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.activity_pet_append, LAYOUT_ACTIVITYPETAPPEND);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.alarm_list, LAYOUT_ALARMLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.chat_list, LAYOUT_CHATLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.dialog_make, LAYOUT_DIALOGMAKE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.fragment_alarm, LAYOUT_FRAGMENTALARM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.fragment_home, LAYOUT_FRAGMENTHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.fragment_message, LAYOUT_FRAGMENTMESSAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.fragment_profile, LAYOUT_FRAGMENTPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.fragment_search, LAYOUT_FRAGMENTSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.make_list, LAYOUT_MAKELIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.message_list, LAYOUT_MESSAGELIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.pet_list, LAYOUT_PETLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.place_list, LAYOUT_PLACELIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.post_list_full, LAYOUT_POSTLISTFULL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.post_list_grid, LAYOUT_POSTLISTGRID);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.petmily.R.layout.post_list_half, LAYOUT_POSTLISTHALF);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCHAT: {
          if ("layout/activity_chat_0".equals(tag)) {
            return new ActivityChatBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_chat is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCHATROOM: {
          if ("layout/activity_chat_room_0".equals(tag)) {
            return new ActivityChatRoomBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_chat_room is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYJOIN: {
          if ("layout/activity_join_0".equals(tag)) {
            return new ActivityJoinBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_join is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAKE: {
          if ("layout/activity_make_0".equals(tag)) {
            return new ActivityMakeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_make is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAKEPROFILE: {
          if ("layout/activity_make_profile_0".equals(tag)) {
            return new ActivityMakeProfileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_make_profile is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPETAPPEND: {
          if ("layout/activity_pet_append_0".equals(tag)) {
            return new ActivityPetAppendBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_pet_append is invalid. Received: " + tag);
        }
        case  LAYOUT_ALARMLIST: {
          if ("layout/alarm_list_0".equals(tag)) {
            return new AlarmListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for alarm_list is invalid. Received: " + tag);
        }
        case  LAYOUT_CHATLIST: {
          if ("layout/chat_list_0".equals(tag)) {
            return new ChatListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for chat_list is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGMAKE: {
          if ("layout/dialog_make_0".equals(tag)) {
            return new DialogMakeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_make is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTALARM: {
          if ("layout/fragment_alarm_0".equals(tag)) {
            return new FragmentAlarmBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_alarm is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHOME: {
          if ("layout/fragment_home_0".equals(tag)) {
            return new FragmentHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_home is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMESSAGE: {
          if ("layout/fragment_message_0".equals(tag)) {
            return new FragmentMessageBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_message is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPROFILE: {
          if ("layout/fragment_profile_0".equals(tag)) {
            return new FragmentProfileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_profile is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSEARCH: {
          if ("layout/fragment_search_0".equals(tag)) {
            return new FragmentSearchBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_search is invalid. Received: " + tag);
        }
        case  LAYOUT_MAKELIST: {
          if ("layout/make_list_0".equals(tag)) {
            return new MakeListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for make_list is invalid. Received: " + tag);
        }
        case  LAYOUT_MESSAGELIST: {
          if ("layout/message_list_0".equals(tag)) {
            return new MessageListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for message_list is invalid. Received: " + tag);
        }
        case  LAYOUT_PETLIST: {
          if ("layout/pet_list_0".equals(tag)) {
            return new PetListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for pet_list is invalid. Received: " + tag);
        }
        case  LAYOUT_PLACELIST: {
          if ("layout/place_list_0".equals(tag)) {
            return new PlaceListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for place_list is invalid. Received: " + tag);
        }
        case  LAYOUT_POSTLISTFULL: {
          if ("layout/post_list_full_0".equals(tag)) {
            return new PostListFullBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for post_list_full is invalid. Received: " + tag);
        }
        case  LAYOUT_POSTLISTGRID: {
          if ("layout/post_list_grid_0".equals(tag)) {
            return new PostListGridBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for post_list_grid is invalid. Received: " + tag);
        }
        case  LAYOUT_POSTLISTHALF: {
          if ("layout/post_list_half_0".equals(tag)) {
            return new PostListHalfBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for post_list_half is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(19);

    static {
      sKeys.put(1, "JoinEmail");
      sKeys.put(2, "Place");
      sKeys.put(3, "Post");
      sKeys.put(0, "_all");
      sKeys.put(4, "activity");
      sKeys.put(5, "alarm");
      sKeys.put(6, "authJoin");
      sKeys.put(7, "chatList");
      sKeys.put(8, "chat_room");
      sKeys.put(9, "home");
      sKeys.put(10, "make");
      sKeys.put(11, "makeProfile");
      sKeys.put(12, "message");
      sKeys.put(13, "pet");
      sKeys.put(14, "petAppend");
      sKeys.put(15, "postGrid");
      sKeys.put(16, "postHalf");
      sKeys.put(17, "profile");
      sKeys.put(18, "search");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(23);

    static {
      sKeys.put("layout/activity_chat_0", com.example.petmily.R.layout.activity_chat);
      sKeys.put("layout/activity_chat_room_0", com.example.petmily.R.layout.activity_chat_room);
      sKeys.put("layout/activity_join_0", com.example.petmily.R.layout.activity_join);
      sKeys.put("layout/activity_login_0", com.example.petmily.R.layout.activity_login);
      sKeys.put("layout/activity_main_0", com.example.petmily.R.layout.activity_main);
      sKeys.put("layout/activity_make_0", com.example.petmily.R.layout.activity_make);
      sKeys.put("layout/activity_make_profile_0", com.example.petmily.R.layout.activity_make_profile);
      sKeys.put("layout/activity_pet_append_0", com.example.petmily.R.layout.activity_pet_append);
      sKeys.put("layout/alarm_list_0", com.example.petmily.R.layout.alarm_list);
      sKeys.put("layout/chat_list_0", com.example.petmily.R.layout.chat_list);
      sKeys.put("layout/dialog_make_0", com.example.petmily.R.layout.dialog_make);
      sKeys.put("layout/fragment_alarm_0", com.example.petmily.R.layout.fragment_alarm);
      sKeys.put("layout/fragment_home_0", com.example.petmily.R.layout.fragment_home);
      sKeys.put("layout/fragment_message_0", com.example.petmily.R.layout.fragment_message);
      sKeys.put("layout/fragment_profile_0", com.example.petmily.R.layout.fragment_profile);
      sKeys.put("layout/fragment_search_0", com.example.petmily.R.layout.fragment_search);
      sKeys.put("layout/make_list_0", com.example.petmily.R.layout.make_list);
      sKeys.put("layout/message_list_0", com.example.petmily.R.layout.message_list);
      sKeys.put("layout/pet_list_0", com.example.petmily.R.layout.pet_list);
      sKeys.put("layout/place_list_0", com.example.petmily.R.layout.place_list);
      sKeys.put("layout/post_list_full_0", com.example.petmily.R.layout.post_list_full);
      sKeys.put("layout/post_list_grid_0", com.example.petmily.R.layout.post_list_grid);
      sKeys.put("layout/post_list_half_0", com.example.petmily.R.layout.post_list_half);
    }
  }
}
