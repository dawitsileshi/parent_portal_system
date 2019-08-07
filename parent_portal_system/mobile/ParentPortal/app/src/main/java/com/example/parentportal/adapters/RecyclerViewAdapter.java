package com.example.parentportal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parentportal.R;
import com.example.parentportal.model.Student;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<Student> students;
    private ArrayList<Object> objects;


    private int maxSize;

//    public RecyclerViewAdapter(ArrayList<Student> students) {
//
//        this.students = students;
//
//    }
//
    public RecyclerViewAdapter(int maxSize) {
        this.maxSize = maxSize;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    @Override
    public int getItemViewType(int position) {

        Log.i("adapter", "getViewType called");
//        if(students.get(position) != null) {
//            return 1;
//        }
        if(students != null) {
            return 1;
        }
        return -1;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.i("adapter", "createViewHolder called");
        BaseViewHolder baseViewHolder = null;

        if(i == 1) {
            View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_kid, viewGroup, false);
            baseViewHolder = new KidViewHolder(rootView);
        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        Log.i("adapter", "bindViewHolder called");
        if(baseViewHolder instanceof KidViewHolder) {
            baseViewHolder.setData(this, students.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return maxSize;
    }
}
