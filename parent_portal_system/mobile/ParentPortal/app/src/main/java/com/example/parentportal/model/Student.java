package com.example.parentportal.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.parentportal.model.DB.DataSource;
import com.example.parentportal.model.DB.TableItems;

import java.nio.Buffer;
import java.util.ArrayList;

public class Student implements Parcelable {

    private String _id;
    private String fname;
    private String lname;
//    private byte[] pic;
//    private String pic;
    private Pic pic;
//    private BinData binData;
    private int year;
    private int age;
    private String gender;
    private String section;
//    private int semester;
    private boolean inSchool;
    private FamilyContact[] familyContact;
    private MedicalRecord[] medicalRecord;
    private Grade[] grade;
    private Schedule[] schedule;
    private Schedules[] schedules;
    private String scheduleId;
    private Context context;
    private DataSource dataSource;

    public Student(String _id, String fname, String lname, int year, int age, String gender, String section, boolean inSchool, int semester, FamilyContact[] familyContact, MedicalRecord[] medicalRecord, Grade[] grade, Schedule[] schedule) {
        this._id = _id;
        this.fname = fname;
        this.lname = lname;
        this.year = year;
        this.age = age;
        this.gender = gender;
        this.section = section;
        this.inSchool = inSchool;
//        this.semester = semester;
        this.familyContact = familyContact;
        this.medicalRecord = medicalRecord;
        this.grade = grade;
        this.schedule = schedule;
    }

    public Student(String fname, String lname, String gender, int age, int year, String section, boolean inSchool) {
        this.fname = fname;
        this.lname = lname;
        this.year = year;
        this.gender = gender;
        this.section = section;
        this.age = age;
        this.inSchool = inSchool;
    }

    public Student(Context context) {

        this.context = context;
        dataSource = new DataSource(context);
    }

    public Student(String kidId, String kidFName, String kidLName, String kidSection, boolean kidInSchool, int kidYear) {
        _id = kidId;
        fname = kidFName;
        lname = kidLName;
        section = kidSection;
//        semester = kidSemester;
        inSchool = kidInSchool;
        year = kidYear;
    }

    public Student(String _id, String kidFName, String kidLName, int kidYear, String kidSection) {
        this._id = _id;
        this.fname = kidFName;
        this.lname = kidLName;
        this.year = kidYear;
        this.section = kidSection;
    }

    protected Student(Parcel in) {
        _id = in.readString();
        fname = in.readString();
        lname = in.readString();
        year = in.readInt();
        age = in.readInt();
        gender = in.readString();
        section = in.readString();
//        semester = in.readInt();
        inSchool = in.readByte() != 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }


//    public Pic getPic() {
//        return pic;
//    }
//
//    public void setPic(Pic pic) {
//        this.pic = pic;
//    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean getInSchool() {
        return inSchool;
    }

    public void setInSchool(boolean inSchool) {
        this.inSchool = inSchool;
    }

//    public int getSemester() {
//        return semester;
//    }
//
//    public void setSemester(int semester) {
//        this.semester = semester;
//    }

    public FamilyContact[] getFamilyContact() {
        return familyContact;
    }

    public void setFamilyContact(FamilyContact[] familyContact) {
        this.familyContact = familyContact;
    }

    public MedicalRecord[] getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord[] medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public Grade[] getGrade() {
        return grade;
    }

    public void setGrade(Grade[] grade) {
        this.grade = grade;
    }

    public Schedule[] getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule[] schedule) {
        this.schedule = schedule;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Schedules[] getSchedules() {
        return schedules;
    }

    public void setSchedules(Schedules[] schedules) {
        this.schedules = schedules;
    }

    public Pic getPic() {
        return pic;
    }

    public void setPic(Pic pic) {
        this.pic = pic;
    }

    //    public BinData getBinData() {
//        return binData;
//    }
//
//    public void setBinData(BinData binData) {
//        this.binData = binData;
//    }
//

    public long insertStudent(Student student) {

        ContentValues contentValues = new ContentValues();
        Log.i("mongo id", student.getId());
//        Log.i("mongo name", student.getName());
        Log.i("mongo year", String.valueOf(student.getYear()));
        Log.i("mongo section", student.getSection());
//        for(Student student: students) {

            contentValues.put(TableItems.KID_ID, student.getId());
            contentValues.put(TableItems.KID_FNAME, student.getFname());
            contentValues.put(TableItems.KID_LNAME, student.getLname());
            contentValues.put(TableItems.KID_AGE, student.getAge());
            contentValues.put(TableItems.KID_YEAR, student.getYear());
            contentValues.put(TableItems.KID_GENDER, student.getGender());
            contentValues.put(TableItems.KID_SECTION, student.getSection());
            if(student.getInSchool()) {
                contentValues.put(TableItems.KID_IN_SCHOOL, 1);
            } else {
                contentValues.put(TableItems.KID_IN_SCHOOL, 0);
            }

//        }
        return dataSource.createItem(contentValues, TableItems.TABLE_KID);

//        for (int i = 0; i < students.size(); i++) {
//
//            Student student = students.get(i);
//            ContentValues contentValues = new ContentValues();
//
//            contentValues.put(TableItems.KID_ID, student.getId());
//            contentValues.put(TableItems.KID_NAME, student.getName());
//            contentValues.put(TableItems.KID_YEAR, student.getYear());
//            contentValues.put(TableItems.KID_SECTION, student.getSection());
////        if(student.getInSchool() )
//            contentValues.put(TableItems.KID_IN_SCHOOL, student.getInSchool());
//
//            return dataSource.createItem(contentValues, TableItems.TABLE_KID);
//        }
//        return -1;

    }
    public ArrayList<Student> students() {

        Cursor cursor = dataSource.getAllKids();

        ArrayList<Student> students = new ArrayList<>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {

            String kidId = cursor.getString(0);
            String kidFName = cursor.getString(1);
            String kidLName = cursor.getString(2);
            String kidSection = cursor.getString(3);
//            int kidSemester = cursor.getInt(cursor.getColumnIndex(TableItems.KID_SEMESTER));
            int kidInSchool = cursor.getInt(4);
            int kidYear = cursor.getInt(5);

            Student student = new Student(kidId, kidFName, kidLName, kidYear, kidSection);
//            Student student = new Student(kidId, kidName, kidSection, kidInSchool, kidYear);
            students.add(student);

            cursor.moveToNext();

        }

//        for (int i = 0; i < cursor.getCount(); i++) {
//
//            String kidId = cursor.getString(cursor.getColumnIndex(TableItems.KID_ID));
//
//        }
//
        return students;
//
    }

    public Student getStudentById(String id) {

        Cursor cursor = dataSource.singleStudent(id);
        boolean inSchoolFlag;

        Log.i("The size is ", String.valueOf(cursor.getCount()));
        cursor.moveToFirst();

        String _id = cursor.getString(cursor.getColumnIndex(TableItems.KID_ID));
        String fname = cursor.getString(cursor.getColumnIndex(TableItems.KID_FNAME));
        String lname = cursor.getString(cursor.getColumnIndex(TableItems.KID_LNAME));
        String gender = cursor.getString(cursor.getColumnIndex(TableItems.KID_GENDER));
        int year = cursor.getInt(cursor.getColumnIndex(TableItems.KID_YEAR));
        int age = cursor.getInt(cursor.getColumnIndex(TableItems.KID_AGE));
        String section = cursor.getString(cursor.getColumnIndex(TableItems.KID_SECTION));
        int inSchool = cursor.getInt(cursor.getColumnIndex(TableItems.KID_IN_SCHOOL));
        if(inSchool == 0) {
            inSchoolFlag = false;
        } else {
            inSchoolFlag = true;
        }
        return new Student(fname, lname, gender, age, year , section, inSchoolFlag);

    }

    public String[] studentIds() {

        Cursor cursor = dataSource.getAllKids();

        cursor.moveToFirst();

        String[] studentIds = new String[cursor.getCount()];
//        ArrayList<String> studentIds = new ArrayList<>();

        int index = 0;
        while(!cursor.isAfterLast()) {

            String id = cursor.getString(cursor.getColumnIndex(TableItems.KID_ID));
            studentIds[index] = id;
            index++;
            cursor.moveToNext();
        }

        return studentIds;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(fname);
        dest.writeString(lname);
        dest.writeInt(year);
        dest.writeInt(age);
        dest.writeString(gender);
        dest.writeString(section);
//        dest.writeInt(semester);
        dest.writeByte((byte) (inSchool ? 1 : 0));
    }

//    public class Pic {
//
//        private Buffer data;
////        private byte[] data;
//        private String contentType;
//
////        public byte[] getData() {
////            return data;
////        }
////
////        public void setData(byte[] data) {
////            this.data = data;
////        }
//
//        public String getContentType() {
//            return contentType;
//        }
//
//        public void setContentType(String contentType) {
//            this.contentType = contentType;
//        }
//    }
//
//    public class BinData {
//
//        private byte[] data;
//
//        public byte[] getData() {
//            return data;
//        }
//
//        public void setData(byte[] data) {
//            this.data = data;
//        }
//    }
}
