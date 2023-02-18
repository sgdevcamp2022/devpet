package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMakeProfileBindingImpl extends ActivityMakeProfileBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.profileImage, 1);
        sViewsWithIds.put(R.id.about, 2);
        sViewsWithIds.put(R.id.birth, 3);
        sViewsWithIds.put(R.id.year, 4);
        sViewsWithIds.put(R.id.month, 5);
        sViewsWithIds.put(R.id.day, 6);
        sViewsWithIds.put(R.id.petButton, 7);
        sViewsWithIds.put(R.id.textView5, 8);
        sViewsWithIds.put(R.id.petAppend, 9);
        sViewsWithIds.put(R.id.petList, 10);
        sViewsWithIds.put(R.id.save, 11);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMakeProfileBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private ActivityMakeProfileBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[2]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[5]
            , (android.widget.Button) bindings[9]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[7]
            , (androidx.recyclerview.widget.RecyclerView) bindings[10]
            , (android.widget.ImageButton) bindings[1]
            , (android.widget.Button) bindings[11]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[4]
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
        if (BR.makeProfile == variableId) {
            setMakeProfile((com.example.petmily.view.Activity_MakeProfile) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setMakeProfile(@Nullable com.example.petmily.view.Activity_MakeProfile MakeProfile) {
        this.mMakeProfile = MakeProfile;
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
        flag 0 (0x1L): makeProfile
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}