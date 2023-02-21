package com.example.petmily.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.databinding.DataBindingUtil;

import com.example.petmily.R;
import com.example.petmily.databinding.DialogDatepickerBinding;

public class Dialog_DatePicker extends Dialog {

    private DialogDatepickerBinding binding;
    private Dialog_DateListener dialog_makeListener;

    private Context context;
    private DatePicker datePicker;

    private int year;
    private int month;
    private int day;

    public Dialog_DatePicker(Context context, Dialog_DateListener dialog_makeListener, int year, int month, int day){
        super(context);
        this.context = context;
        this.dialog_makeListener = dialog_makeListener;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public interface Dialog_DateListener{
        void onDateSet(DatePicker datePicker, int i, int i1, int i2);
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_datepicker, null, false);
        binding.setDialog(this);
        setContentView(binding.getRoot());
        //getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        datePicker = binding.datePicker;
        datePicker.updateDate(year, month, day);

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_makeListener.onDateSet(datePicker,
                        datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth());
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
