package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentHomeBindingImpl extends FragmentHomeBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.sliding_layout, 1);
        sViewsWithIds.put(R.id.map_view, 2);
        sViewsWithIds.put(R.id.sliding, 3);
        sViewsWithIds.put(R.id.post_half_layout, 4);
        sViewsWithIds.put(R.id.up, 5);
        sViewsWithIds.put(R.id.localname, 6);
        sViewsWithIds.put(R.id.place_list, 7);
        sViewsWithIds.put(R.id.post_half_list, 8);
        sViewsWithIds.put(R.id.post_grid_list, 9);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentHomeBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private FragmentHomeBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[6]
            , (com.naver.maps.map.MapView) bindings[2]
            , (androidx.recyclerview.widget.RecyclerView) bindings[7]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (android.widget.LinearLayout) bindings[4]
            , (androidx.recyclerview.widget.RecyclerView) bindings[8]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[3]
            , (com.sothree.slidinguppanel.SlidingUpPanelLayout) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
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
        if (BR.home == variableId) {
            setHome((com.example.petmily.view.Fragment_Home) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setHome(@Nullable com.example.petmily.view.Fragment_Home Home) {
        this.mHome = Home;
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
        flag 0 (0x1L): home
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}