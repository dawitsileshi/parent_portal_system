package com.example.parentportal.adapters;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.parentportal.R;
import com.example.parentportal.model.Student;

public class KidViewHolder extends BaseViewHolder {

    private TextView tv_item_kid;

    public KidViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_item_kid = itemView.findViewById(R.id.tv_item_kid);
    }

    @Override
    public void setData(RecyclerViewAdapter recyclerViewAdapter, Object object, int position) {

        Student student = (Student) object;

        tv_item_kid.setText(student.getFname() + " " +  student.getLname());

    }
}
