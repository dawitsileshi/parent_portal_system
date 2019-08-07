package com.example.parentportal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public abstract void setData(RecyclerViewAdapter recyclerViewAdapter, Object object, int position);

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
