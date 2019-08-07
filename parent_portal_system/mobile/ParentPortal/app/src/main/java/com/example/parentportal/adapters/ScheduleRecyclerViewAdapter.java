package com.example.parentportal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parentportal.R;
import com.example.parentportal.model.Schedule;

import java.util.ArrayList;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ScheduleViewHolder> {

    private ArrayList<Schedule> schedules;
    private Context context;
    public ClickLongClick clickLongClick;


    public ScheduleRecyclerViewAdapter(Context context, ArrayList<Schedule> schedules, ClickLongClick clickLongClick) {

        this.context = context;
        this.schedules = schedules;
        this.clickLongClick = clickLongClick;

    }
    public interface ClickLongClick {
        void click(Schedule schedule, int position);

    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ScheduleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_schedule, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder scheduleViewHolder, int i) {
        String day = schedules.get(i).getDay();
        scheduleViewHolder.tv_item_schedule.setText(day);
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_item_schedule;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_schedule = itemView.findViewById(R.id.tv_item_schedule);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(context, "Touched", Toast.LENGTH_SHORT).show();
            clickLongClick.click(schedules.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
