package com.example.parentportal.model;

import java.util.ArrayList;

public class Schedule {

    private String _id;
    private String day;
    private int dayNumber;
    private ArrayList<Program> program;

    public Schedule(String day, int dayNumber, ArrayList<Program> program) {
        this.day = day;
        this.dayNumber = dayNumber;
        this.program = program;
    }

    public Schedule() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public ArrayList<Program> getProgram() {
        return program;
    }

    public void setProgram(ArrayList<Program> program) {
        this.program = program;
    }
}
