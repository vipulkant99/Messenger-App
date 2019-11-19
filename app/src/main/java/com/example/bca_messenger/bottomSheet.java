package com.example.bca_messenger;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class bottomSheet extends BottomSheetDialogFragment {
    private BottomSheetListner mlistner;
    public EditText name;
    public EditText bio;
    private Button submit;
    private TextView text;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.namebottomsheet,container,false);
        name = (EditText)v.findViewById(R.id.nameBS);
        bio=(EditText) v.findViewById(R.id.bioBS);
        submit=(Button)v.findViewById(R.id.submit);
        text=(TextView)v.findViewById(R.id.text);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nm=name.getText().toString();
                String bo=bio.getText().toString();
                mlistner.onclicked(nm,bo);


            }
        });
        return  v;
    }
    public interface BottomSheetListner{
        void onclicked(String text1,String text2);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mlistner = (BottomSheetListner) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString()+"must implement BottomSheetListner");
        }
    }
}
