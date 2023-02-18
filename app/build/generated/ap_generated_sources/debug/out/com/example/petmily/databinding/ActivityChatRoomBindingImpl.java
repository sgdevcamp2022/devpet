package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityChatRoomBindingImpl extends ActivityChatRoomBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.chat_room_toolbar, 1);
        sViewsWithIds.put(R.id.titlename, 2);
        sViewsWithIds.put(R.id.chatlist, 3);
        sViewsWithIds.put(R.id.message, 4);
        sViewsWithIds.put(R.id.plus, 5);
        sViewsWithIds.put(R.id.content, 6);
        sViewsWithIds.put(R.id.send, 7);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityChatRoomBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private ActivityChatRoomBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[3]
            , (android.widget.EditText) bindings[6]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.Button) bindings[5]
            , (android.widget.Button) bindings[7]
            , (android.widget.TextView) bindings[2]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
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
        if (BR.chat_room == variableId) {
            setChatRoom((com.example.petmily.view.Activity_Chat_Room) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setChatRoom(@Nullable com.example.petmily.view.Activity_Chat_Room ChatRoom) {
        this.mChatRoom = ChatRoom;
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
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): chat_room
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}