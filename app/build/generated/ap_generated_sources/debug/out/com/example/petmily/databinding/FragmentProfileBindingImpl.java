package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentProfileBindingImpl extends FragmentProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.dm, 2);
        sViewsWithIds.put(R.id.setting, 3);
        sViewsWithIds.put(R.id.nickname, 4);
        sViewsWithIds.put(R.id.info, 5);
        sViewsWithIds.put(R.id.profile_image, 6);
        sViewsWithIds.put(R.id.info_profile, 7);
        sViewsWithIds.put(R.id.post_layout, 8);
        sViewsWithIds.put(R.id.postHalf, 9);
        sViewsWithIds.put(R.id.post_num, 10);
        sViewsWithIds.put(R.id.follow_layout, 11);
        sViewsWithIds.put(R.id.follow, 12);
        sViewsWithIds.put(R.id.follow_num, 13);
        sViewsWithIds.put(R.id.follower_layout, 14);
        sViewsWithIds.put(R.id.follower_num, 15);
        sViewsWithIds.put(R.id.follower, 16);
        sViewsWithIds.put(R.id.about, 17);
        sViewsWithIds.put(R.id.post, 18);
        sViewsWithIds.put(R.id.my_post_layout, 19);
        sViewsWithIds.put(R.id.my_post, 20);
        sViewsWithIds.put(R.id.tag_post_layout, 21);
        sViewsWithIds.put(R.id.tag_post, 22);
        sViewsWithIds.put(R.id.search_post, 23);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private FragmentProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[17]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.TextView) bindings[12]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[11]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[16]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[14]
            , (android.widget.TextView) bindings[15]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[7]
            , (android.widget.ImageView) bindings[20]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.TextView) bindings[4]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[18]
            , (android.widget.TextView) bindings[9]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[8]
            , (android.widget.TextView) bindings[10]
            , (android.widget.ImageView) bindings[6]
            , (androidx.recyclerview.widget.RecyclerView) bindings[23]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[22]
            , (android.widget.LinearLayout) bindings[21]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[1]
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
        if (BR.profile == variableId) {
            setProfile((com.example.petmily.view.Fragment_Profile) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setProfile(@Nullable com.example.petmily.view.Fragment_Profile Profile) {
        this.mProfile = Profile;
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
        flag 0 (0x1L): profile
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}