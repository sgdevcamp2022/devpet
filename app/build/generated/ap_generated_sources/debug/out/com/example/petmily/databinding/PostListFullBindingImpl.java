package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class PostListFullBindingImpl extends PostListFullBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.profile, 1);
        sViewsWithIds.put(R.id.profile_image, 2);
        sViewsWithIds.put(R.id.nickname, 3);
        sViewsWithIds.put(R.id.body, 4);
        sViewsWithIds.put(R.id.imageView4, 5);
        sViewsWithIds.put(R.id.body2, 6);
        sViewsWithIds.put(R.id.action, 7);
        sViewsWithIds.put(R.id.imageView5, 8);
        sViewsWithIds.put(R.id.imageView6, 9);
        sViewsWithIds.put(R.id.coment, 10);
        sViewsWithIds.put(R.id.time, 11);
        sViewsWithIds.put(R.id.comments, 12);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public PostListFullBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds));
    }
    private PostListFullBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.TextView) bindings[10]
            , (android.widget.Button) bindings[12]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.TextView) bindings[3]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.TextView) bindings[11]
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
        if (BR.Post == variableId) {
            setPost((com.example.petmily.model.data.post.remote.Post) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPost(@Nullable com.example.petmily.model.data.post.remote.Post Post) {
        this.mPost = Post;
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
        flag 0 (0x1L): Post
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}