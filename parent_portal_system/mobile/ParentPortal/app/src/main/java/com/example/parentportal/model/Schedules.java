package com.example.parentportal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Schedules implements Parcelable {

    private String _id;
    private String scheduleId;

    protected Schedules(Parcel in) {
        _id = in.readString();
        scheduleId = in.readString();
    }

    public static final Creator<Schedules> CREATOR = new Creator<Schedules>() {
        @Override
        public Schedules createFromParcel(Parcel in) {
            return new Schedules(in);
        }

        @Override
        public Schedules[] newArray(int size) {
            return new Schedules[size];
        }
    };

    public String getScheduleId() {
        return scheduleId;
    }

    public String get_id() {
        return _id;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(scheduleId);
    }

//    private Schedule[] schedule;
//
//    public Schedules(Schedule[] schedule) {
//        this.schedule = schedule;
//    }
//
//    public Schedule[] getSchedule() {
//        return schedule;
//    }
//
//    public void setSchedule(Schedule[] schedule) {
//        this.schedule = schedule;
//    }
}
