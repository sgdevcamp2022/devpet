package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class PostListHalfBindingImpl extends PostListHalfBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_image, 2);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener postHalfPlaceandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of postHalf.placename
            //         is postHalf.setPlacename((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(postHalfPlace);
            // localize variables for thread safety
            // postHalf
            com.example.petmily.model.data.post.PostHalf postHalf = mPostHalf;
            // postHalf != null
            boolean postHalfJavaLangObjectNull = false;
            // postHalf.placename
            java.lang.String postHalfPlacename = null;



            postHalfJavaLangObjectNull = (postHalf) != (null);
            if (postHalfJavaLangObjectNull) {




                postHalf.setPlacename(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public PostListHalfBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private PostListHalfBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[1]
            , (android.widget.ImageView) bindings[2]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.postHalfPlace.setTag(null);
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
        if (BR.postHalf == variableId) {
            setPostHalf((com.example.petmily.model.data.post.PostHalf) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPostHalf(@Nullable com.example.petmily.model.data.post.PostHalf PostHalf) {
        this.mPostHalf = PostHalf;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.postHalf);
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
        com.example.petmily.model.data.post.PostHalf postHalf = mPostHalf;
        java.lang.String postHalfPlacename = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (postHalf != null) {
                    // read postHalf.placename
                    postHalfPlacename = postHalf.getPlacename();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.postHalfPlace, postHalfPlacename);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.postHalfPlace, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, postHalfPlaceandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): postHalf
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}