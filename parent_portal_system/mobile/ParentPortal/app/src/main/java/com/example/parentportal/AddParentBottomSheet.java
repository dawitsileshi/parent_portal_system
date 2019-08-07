package com.example.parentportal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddParentBottomSheet extends BottomSheetDialogFragment {

    private EditText et_bottom_sheet_add_parent_email, et_bottom_sheet_add_parent_tel;
    private Button button_bottom_sheet_add_parent_submit;

    public interface SubmitClicked {
        void clicked(String email, String tel);
    }

    public SubmitClicked submitClicked;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bottom_sheet_add_parent, container, false);

        et_bottom_sheet_add_parent_email = rootView.findViewById(R.id.et_bottom_sheet_add_parent_email);
        et_bottom_sheet_add_parent_tel = rootView.findViewById(R.id.et_bottom_sheet_add_parent_tel);

        button_bottom_sheet_add_parent_submit = rootView.findViewById(R.id.button_bottom_sheet_add_parent_submit);

        button_bottom_sheet_add_parent_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_bottom_sheet_add_parent_email.getText().toString().trim();
                String tel = et_bottom_sheet_add_parent_tel.getText().toString().trim();

                if(somethingWritten(email, tel)) {
                    submitClicked.clicked(email, tel);
                } else {
                    Toast.makeText(getContext(), "Insert all inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;

    }

    private boolean somethingWritten(String email, String tel) {
        return email.length() != 0 && tel.length() != 0;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        submitClicked = (SubmitClicked) context;
    }
}
