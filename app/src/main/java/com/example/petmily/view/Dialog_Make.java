package com.example.petmily.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.databinding.DataBindingUtil;

import com.example.petmily.R;
import com.example.petmily.databinding.DialogMakeBinding;

public class Dialog_Make extends Dialog {

    private DialogMakeBinding binding;
    private Dialog_MakeListener dialog_makeListener;

    private Context context;

    public Dialog_Make(Context context, Dialog_MakeListener dialog_makeListener){
        super(context);
        this.context = context;
        this.dialog_makeListener = dialog_makeListener;
    }

    public interface Dialog_MakeListener{
        void clickBtn(String data);
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_make, null, false);
        binding.setMake(this);
        setContentView(binding.getRoot());
        //getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_makeListener.clickBtn(binding.tag.getText().toString());
                dismiss();
            }
        });
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
