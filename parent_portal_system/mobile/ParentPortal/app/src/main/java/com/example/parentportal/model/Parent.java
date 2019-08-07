package com.example.parentportal.model;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.parentportal.model.DB.DataSource;
import com.example.parentportal.model.DB.TableItems;

public class Parent implements Parcelable {

    private String fname;
    private String relation;
    private String lname;
//    private String password;
    private String email;
    private String tel;
    private StudentId[] students;

    private DataSource dataSource;

    public Parent() {
    }

    public Parent(Context context) {
        dataSource = new DataSource(context);
    }

    public Parent(String fname, String relation, String lname, String email, String tel) {
        this.fname = fname;
        this.relation = relation;
        this.lname = lname;
        this.email = email;
        this.tel = tel;
    }

    protected Parent(Parcel in) {
        fname = in.readString();
        relation = in.readString();
        lname = in.readString();
        email = in.readString();
        tel = in.readString();
    }

    public static final Creator<Parent> CREATOR = new Creator<Parent>() {
        @Override
        public Parent createFromParcel(Parcel in) {
            return new Parent(in);
        }

        @Override
        public Parent[] newArray(int size) {
            return new Parent[size];
        }
    };

    public String getFName() {
        return fname;
    }

    public void setName(String fname) {
        this.fname = fname;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getLName() {
        return lname;
    }

    public void setUsername(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public StudentId[] getStudents() {
        return students;
    }

    public void setStudents(StudentId[] students) {
        this.students = students;
    }

    public long insertParent(Parent parent) {

        String fname = parent.fname;
        String lname = parent.lname;
        String relation = parent.relation;
        String email = parent.email;
        String tel = parent.tel;

        ContentValues contentValues = new ContentValues();

        contentValues.put(TableItems.PARENT_FNAME, fname);
        contentValues.put(TableItems.PARENT_LNAME, lname);
        contentValues.put(TableItems.PARENT_RELATION, relation);
        contentValues.put(TableItems.PARENT_EMAIL, email);
        contentValues.put(TableItems.PARENT_TEL, tel);

        return dataSource.createItem(contentValues, TableItems.TABLE_PARENT);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fname);
        dest.writeString(relation);
        dest.writeString(lname);
        dest.writeString(email);
        dest.writeString(tel);
    }
}
