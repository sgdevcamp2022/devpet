package com.example.petmily.databinding;
import com.example.petmily.R;
import com.example.petmily.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class PetListBindingImpl extends PetListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.petImage, 5);
        sViewsWithIds.put(R.id.right, 6);
        sViewsWithIds.put(androidx.databinding.library.baseAdapters.R.id.top, 7);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener aboutandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of pet.about
            //         is pet.setAbout((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(about);
            // localize variables for thread safety
            // pet != null
            boolean petJavaLangObjectNull = false;
            // pet
            com.example.petmily.model.data.profile.Pet pet = mPet;
            // pet.about
            java.lang.String petAbout = null;



            petJavaLangObjectNull = (pet) != (null);
            if (petJavaLangObjectNull) {




                pet.setAbout(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener birthandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of pet.birth
            //         is pet.setBirth((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(birth);
            // localize variables for thread safety
            // pet != null
            boolean petJavaLangObjectNull = false;
            // pet.birth
            java.lang.String petBirth = null;
            // pet
            com.example.petmily.model.data.profile.Pet pet = mPet;



            petJavaLangObjectNull = (pet) != (null);
            if (petJavaLangObjectNull) {




                pet.setBirth(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener divisionandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of pet.division
            //         is pet.setDivision((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(division);
            // localize variables for thread safety
            // pet != null
            boolean petJavaLangObjectNull = false;
            // pet
            com.example.petmily.model.data.profile.Pet pet = mPet;
            // pet.division
            java.lang.String petDivision = null;



            petJavaLangObjectNull = (pet) != (null);
            if (petJavaLangObjectNull) {




                pet.setDivision(((java.lang.String) (callbackArg_0)));
            }
        }
    };
    private androidx.databinding.InverseBindingListener nameandroidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of pet.name
            //         is pet.setName((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(name);
            // localize variables for thread safety
            // pet != null
            boolean petJavaLangObjectNull = false;
            // pet.name
            java.lang.String petName = null;
            // pet
            com.example.petmily.model.data.profile.Pet pet = mPet;



            petJavaLangObjectNull = (pet) != (null);
            if (petJavaLangObjectNull) {




                pet.setName(((java.lang.String) (callbackArg_0)));
            }
        }
    };

    public PetListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private PetListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[1]
            , (android.widget.ImageView) bindings[5]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[7]
            );
        this.about.setTag(null);
        this.birth.setTag(null);
        this.division.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name.setTag(null);
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
        if (BR.pet == variableId) {
            setPet((com.example.petmily.model.data.profile.Pet) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setPet(@Nullable com.example.petmily.model.data.profile.Pet Pet) {
        this.mPet = Pet;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.pet);
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
        java.lang.String petName = null;
        java.lang.String petBirth = null;
        java.lang.String petDivision = null;
        com.example.petmily.model.data.profile.Pet pet = mPet;
        java.lang.String petAbout = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (pet != null) {
                    // read pet.name
                    petName = pet.getName();
                    // read pet.birth
                    petBirth = pet.getBirth();
                    // read pet.division
                    petDivision = pet.getDivision();
                    // read pet.about
                    petAbout = pet.getAbout();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.about, petAbout);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.birth, petBirth);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.division, petDivision);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.name, petName);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.about, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, aboutandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.birth, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, birthandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.division, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, divisionandroidTextAttrChanged);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.name, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, nameandroidTextAttrChanged);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): pet
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}