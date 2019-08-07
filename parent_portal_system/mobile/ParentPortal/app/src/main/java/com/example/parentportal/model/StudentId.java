package com.example.parentportal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentId implements Parcelable {

    private String studentId;

    protected StudentId(Parcel in) {
        studentId = in.readString();
    }

    public static final Creator<StudentId> CREATOR = new Creator<StudentId>() {
        @Override
        public StudentId createFromParcel(Parcel in) {
            return new StudentId(in);
        }

        @Override
        public StudentId[] newArray(int size) {
            return new StudentId[size];
        }
    };

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(studentId);
    }
}
