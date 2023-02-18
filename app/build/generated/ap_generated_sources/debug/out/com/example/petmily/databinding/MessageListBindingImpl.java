package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class MessageListBindingImpl extends MessageListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.profile_image, 5);
        sViewsWithIds.put(R.id.name, 6);
        sViewsWithIds.put(R.id.right, 7);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener alarmTextandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of chatList.lastText
            //         is chatList.setLastText((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(alarmText);
            // localize variables for thread safety
            // chatList
            com.example.petmily.model.data.chat.list.ChatList chatList = mChatList;
            // chatList.lastText
            java.lang.String chatListLastText = null;
            // chatList != null
            boolean chatListJavaLangObjectNull = false;



            chatListJavaLangObjectNull = (chatList) != (null);
            if (chatListJavaLangObjectNull) {




                chatList.setLastText(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener checkandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of chatList.alarm
            //         is chatList.setAlarm((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(check);
            // localize variables for thread safety
            // chatList
            com.example.petmily.model.data.chat.list.ChatList chatList = mChatList;
            // chatList != null
            boolean chatListJavaLangObjectNull = false;
            // chatList.alarm
            java.lang.String chatListAlarm = null;



            chatListJavaLangObjectNull = (chatList) != (null);
            if (chatListJavaLangObjectNull) {




                chatList.setAlarm(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener nicknameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of chatList.sender
            //         is chatList.setSender((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(nickname);
            // localize variables for thread safety
            // chatList
            com.example.petmily.model.data.chat.list.ChatList chatList = mChatList;
            // chatList != null
            boolean chatListJavaLangObjectNull = false;
            // chatList.sender
            java.lang.String chatListSender = null;



            chatListJavaLangObjectNull = (chatList) != (null);
            if (chatListJavaLangObjectNull) {




                chatList.setSender(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener timeandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of chatList.timeLog
            //         is chatList.setTimeLog((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(time);
            // localize variables for thread safety
            // chatList
            com.example.petmily.model.data.chat.list.ChatList chatList = mChatList;
            // chatList.timeLog
            java.lang.String chatListTimeLog = null;
            // chatList != null
            boolean chatListJavaLangObjectNull = false;



            chatListJavaLangObjectNull = (chatList) != (null);
            if (chatListJavaLangObjectNull) {




                chatList.setTimeLog(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public MessageListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private MessageListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[4]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.TextView) bindings[1]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.TextView) bindings[3]
            );
        this.alarmText.setTag(null);
        this.check.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nickname.setTag(null);
        this.time.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.chatList == variableId) {
            setChatList((com.example.petmily.model.data.chat.list.ChatList) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setChatList(@Nullable com.example.petmily.model.data.chat.list.ChatList ChatList) {
        this.mChatList = ChatList;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.chatList);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String chatListLastText = null;
        java.lang.String chatListAlarm = null;
        com.example.petmily.model.data.chat.list.ChatList chatList = mChatList;
        java.lang.String chatListTimeLog = null;
        java.lang.String chatListSender = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (chatList != null) {
                    // read chatList.lastText
                    chatListLastText = chatList.getLastText();
                    // read chatList.alarm
                    chatListAlarm = chatList.getAlarm();
                    // read chatList.timeLog
                    chatListTimeLog = chatList.getTimeLog();
                    // read chatList.sender
                    chatListSender = chatList.getSender();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.alarmText, chatListLastText);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.check, chatListAlarm);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.nickname, chatListSender);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.time, chatListTimeLog);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.alarmText, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, alarmTextandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.check, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, checkandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.nickname, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, nicknameandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.time, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, timeandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): chatList
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}