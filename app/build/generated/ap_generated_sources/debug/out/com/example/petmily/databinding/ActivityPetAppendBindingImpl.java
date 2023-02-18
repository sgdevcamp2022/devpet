package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityPetAppendBindingImpl extends ActivityPetAppendBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.petImage, 1);
        sViewsWithIds.put(R.id.name, 2);
        sViewsWithIds.put(R.id.division, 3);
        sViewsWithIds.put(R.id.birth, 4);
        sViewsWithIds.put(R.id.year, 5);
        sViewsWithIds.put(R.id.month, 6);
        sViewsWithIds.put(R.id.day, 7);
        sViewsWithIds.put(R.id.about, 8);
        sViewsWithIds.put(R.id.save, 9);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityPetAppendBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private ActivityPetAppendBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[8]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.TextView) bindings[7]
            , (android.widget.EditText) bindings[3]
            , (android.widget.TextView) bindings[6]
            , (android.widget.EditText) bindings[2]
            , (android.widget.ImageButton) bindings[1]
            , (android.widget.Button) bindings[9]
            , (android.widget.TextView) bindings[5]
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
        if (BR.petAppend == variableId) {
            setPetAppend((com.example.petmily.view.Activity_PetAppend) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPetAppend(@Nullable com.example.petmily.view.Activity_PetAppend PetAppend) {
        this.mPetAppend = PetAppend;
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
        flag 0 (0x1L): petAppend
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}