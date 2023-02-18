package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class PostListGridBindingImpl extends PostListGridBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_image, 4);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener nicknameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of postGrid.nickname
            //         is postGrid.setNickname((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(nickname);
            // localize variables for thread safety
            // postGrid.nickname
            java.lang.String postGridNickname = null;
            // postGrid
            com.example.petmily.model.data.post.PostGrid postGrid = mPostGrid;
            // postGrid != null
            boolean postGridJavaLangObjectNull = false;



            postGridJavaLangObjectNull = (postGrid) != (null);
            if (postGridJavaLangObjectNull) {




                postGrid.setNickname(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener textandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of postGrid.text
            //         is postGrid.setText((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(text);
            // localize variables for thread safety
            // postGrid
            com.example.petmily.model.data.post.PostGrid postGrid = mPostGrid;
            // postGrid.text
            java.lang.String postGridText = null;
            // postGrid != null
            boolean postGridJavaLangObjectNull = false;



            postGridJavaLangObjectNull = (postGrid) != (null);
            if (postGridJavaLangObjectNull) {




                postGrid.setText(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener timeandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of postGrid.time
            //         is postGrid.setTime((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(time);
            // localize variables for thread safety
            // postGrid
            com.example.petmily.model.data.post.PostGrid postGrid = mPostGrid;
            // postGrid.time
            java.lang.String postGridTime = null;
            // postGrid != null
            boolean postGridJavaLangObjectNull = false;



            postGridJavaLangObjectNull = (postGrid) != (null);
            if (postGridJavaLangObjectNull) {




                postGrid.setTime(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public PostListGridBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private PostListGridBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[1]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nickname.setTag(null);
        this.text.setTag(null);
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
        if (BR.postGrid == variableId) {
            setPostGrid((com.example.petmily.model.data.post.PostGrid) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPostGrid(@Nullable com.example.petmily.model.data.post.PostGrid PostGrid) {
        this.mPostGrid = PostGrid;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.postGrid);
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
        java.lang.String postGridNickname = null;
        java.lang.String postGridText = null;
        com.example.petmily.model.data.post.PostGrid postGrid = mPostGrid;
        java.lang.String postGridTime = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (postGrid != null) {
                    // read postGrid.nickname
                    postGridNickname = postGrid.getNickname();
                    // read postGrid.text
                    postGridText = postGrid.getText();
                    // read postGrid.time
                    postGridTime = postGrid.getTime();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.nickname, postGridNickname);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.text, postGridText);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.time, postGridTime);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.nickname, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, nicknameandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.text, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, textandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.time, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, timeandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): postGrid
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}