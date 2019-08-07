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
import com.example.parentportal.model.Event;

import java.util.ArrayList;

public class EventsRecyclerViewAdapter extends RecyclerView.Adapter<EventsRecyclerViewAdapter.EventViewHolder> {

    private ArrayList<Event> events;
    private Context context;


    public EventsRecyclerViewAdapter(Context context, ArrayList<Event> events, Clicked clicked) {
        this.context = context;
        this.events = events;
        this.clicked = clicked;
    }

    public interface Clicked{
        void click(Event event);
    }

    public Clicked clicked;

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {

        Event event = events.get(i);

        eventViewHolder.tv_item_event.setText(event.getTitle());

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_item_event;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_event = itemView.findViewById(R.id.tv_item_event);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Toast.makeText(context, events.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
            clicked.click(events.get(getAdapterPosition()));
        }
    }
}
