package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMakeBindingImpl extends ActivityMakeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back_toolbar, 1);
        sViewsWithIds.put(R.id.Appname_chat, 2);
        sViewsWithIds.put(R.id.back, 3);
        sViewsWithIds.put(R.id.up, 4);
        sViewsWithIds.put(R.id.makeImage, 5);
        sViewsWithIds.put(R.id.about, 6);
        sViewsWithIds.put(R.id.body, 7);
        sViewsWithIds.put(R.id.hashTagButton, 8);
        sViewsWithIds.put(R.id.hashTag, 9);
        sViewsWithIds.put(R.id.body2, 10);
        sViewsWithIds.put(R.id.userTagButton, 11);
        sViewsWithIds.put(R.id.userTag, 12);
        sViewsWithIds.put(R.id.bottom, 13);
        sViewsWithIds.put(R.id.address, 14);
        sViewsWithIds.put(R.id.map_view, 15);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMakeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private ActivityMakeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (android.widget.EditText) bindings[6]
            , (android.widget.TextView) bindings[14]
            , (android.widget.ImageView) bindings[3]
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.LinearLayout) bindings[13]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (android.widget.Button) bindings[8]
            , (android.widget.ImageView) bindings[5]
            , (com.naver.maps.map.MapView) bindings[15]
            , (android.widget.LinearLayout) bindings[4]
            , (androidx.recyclerview.widget.RecyclerView) bindings[12]
            , (android.widget.Button) bindings[11]
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
        if (BR.make == variableId) {
            setMake((com.example.petmily.view.Activity_Make) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMake(@Nullable com.example.petmily.view.Activity_Make Make) {
        this.mMake = Make;
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
        flag 0 (0x1L): make
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}