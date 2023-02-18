package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ChatListBindingImpl extends ChatListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.profile_image, 4);
        sViewsWithIds.put(R.id.content, 5);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener chatListandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of message.message
            //         is message.setMessage((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(chatList);
            // localize variables for thread safety
            // message != null
            boolean messageJavaLangObjectNull = false;
            // message.message
            java.lang.String messageMessage = null;
            // message
            com.example.petmily.model.data.chat.room.Message message = mMessage;



            messageJavaLangObjectNull = (message) != (null);
            if (messageJavaLangObjectNull) {




                message.setMessage(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener nicknameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of message.sender
            //         is message.setSender((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(nickname);
            // localize variables for thread safety
            // message != null
            boolean messageJavaLangObjectNull = false;
            // message
            com.example.petmily.model.data.chat.room.Message message = mMessage;
            // message.sender
            java.lang.String messageSender = null;



            messageJavaLangObjectNull = (message) != (null);
            if (messageJavaLangObjectNull) {




                message.setSender(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener timeandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of message.timeLog
            //         is message.setTimeLog((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(time);
            // localize variables for thread safety
            // message.timeLog
            java.lang.String messageTimeLog = null;
            // message != null
            boolean messageJavaLangObjectNull = false;
            // message
            com.example.petmily.model.data.chat.room.Message message = mMessage;



            messageJavaLangObjectNull = (message) != (null);
            if (messageJavaLangObjectNull) {




                message.setTimeLog(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public ChatListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private ChatListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
            , (android.widget.TextView) bindings[1]
            , (android.widget.ImageButton) bindings[4]
            , (android.widget.TextView) bindings[3]
            );
        this.chatList.setTag(null);
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
        if (BR.message == variableId) {
            setMessage((com.example.petmily.model.data.chat.room.Message) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMessage(@Nullable com.example.petmily.model.data.chat.room.Message Message) {
        this.mMessage = Message;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.message);
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
        com.example.petmily.model.data.chat.room.Message message = mMessage;
        java.lang.String messageSender = null;
        java.lang.String messageTimeLog = null;
        java.lang.String messageMessage = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (message != null) {
                    // read message.sender
                    messageSender = message.getSender();
                    // read message.timeLog
                    messageTimeLog = message.getTimeLog();
                    // read message.message
                    messageMessage = message.getMessage();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.chatList, messageMessage);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.nickname, messageSender);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.time, messageTimeLog);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.chatList, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, chatListandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.nickname, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, nicknameandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.time, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, timeandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): message
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}