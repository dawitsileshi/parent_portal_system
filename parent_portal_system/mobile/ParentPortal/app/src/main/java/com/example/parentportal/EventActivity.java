package com.example.parentportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.parentportal.Utils.RequestController;
import com.example.parentportal.adapters.EventsRecyclerViewAdapter;
import com.example.parentportal.model.Event;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventActivity extends AppCompatActivity implements EventsRecyclerViewAdapter.Clicked {

    private Retrofit retrofit;
    private RetrofitCalls retrofitCalls;

    EventsRecyclerViewAdapter eventsRecyclerViewAdapter;

    ArrayList<Event> events;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        recyclerView = findViewById(R.id.rv_activity_event);

        events = new ArrayList<>();
        eventsRecyclerViewAdapter = new EventsRecyclerViewAdapter(this, events, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventsRecyclerViewAdapter);

        retrofit = RequestController.getRetrofit();

        retrofitCalls = retrofit.create(RetrofitCalls.class);

        Call<ArrayList<Event>> call = retrofitCalls.listEvents();

        call.enqueue(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Event>> call, @NonNull Response<ArrayList<Event>> response) {

                if(!response.isSuccessful()) {
                    Toast.makeText(EventActivity.this, "The server returned a status code of " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                assert response.body() != null;
                events.addAll(response.body());
                Toast.makeText(EventActivity.this, events.get(0).getCreatedDate().toString(), Toast.LENGTH_SHORT).show();
                eventsRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Event>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void click(Event event) {

        Intent intent = new Intent(EventActivity.this, EventDisplayActivity.class);
        intent.putExtra("event", event);
        startActivity(intent);

    }
}
