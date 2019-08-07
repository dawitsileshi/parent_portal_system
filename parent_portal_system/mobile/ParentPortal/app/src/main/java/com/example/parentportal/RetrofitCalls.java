package com.example.parentportal;

import com.example.parentportal.model.Attendance;
import com.example.parentportal.model.Discipline;
import com.example.parentportal.model.Event;
import com.example.parentportal.model.FamilyContact;
import com.example.parentportal.model.Grade;
import com.example.parentportal.model.MedicalRecord;
import com.example.parentportal.model.Parent;
import com.example.parentportal.model.Schedule;
import com.example.parentportal.model.Schedules;
import com.example.parentportal.model.Student;
import com.example.parentportal.model.Teacher;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitCalls {

    // get your child(ren)'s data
    @GET("student/{id}")
    Call<Student> getStudentByID(@Path("id") String id);

    @GET("student/email/{email}")
    Call<ArrayList<Student>> getStudentByEmail(@Path("email") String email);

    // get all the current schedules of the child
    @GET("schedule/{id}")
    Call<Schedule> getCurrentSchedule(@Path("id") String id);

    // get all the current schedules of the child
    @GET("teacher/{id}")
    Call<Teacher> getTeacher(@Path("id") String id);

    // get all the current medical records of the child
    @GET("/student/medical/{id}")
    Call<ArrayList<MedicalRecord>> getCurrentMedicalRecord(@Path("id") String id);

    // get all the current attendance records of the child
    @GET("/student/attendance/{id}")
    Call<ArrayList<Attendance>> getCurrentAttendance(@Path("id") String id);

    // get all the current discipline records of the child
    @GET("/student/discipline/{id}")
    Call<ArrayList<Discipline>> getCurrentDiscipline(@Path("id") String id);

    // get all the current grade records of the child
    @GET("/student/grade/{id}")
    Call<ArrayList<Grade>> getCurrentGrade(@Path("id") String id);

    // get new updated data


    //update the emergency contact, in case of a change in telephone number
    @POST("/student/emergency/{id}")
    Call<FamilyContact> getUpdatedEmergencyContact(@Path("id") String id, String name, String
            tel, String email);

    // get a specific teacher's info like tel and other contact details, this will happen if the
    // parent clicks on teacher that teaches a specific course
    @GET("/teacher/{id}")
    Call<Teacher> getTeacherInfo(@Path("id") String id);

    @FormUrlEncoded
    @POST("parent/login")
    Call<Parent> parentLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("parent/register")
    Call<Parent> registerParent(@Field("fname") String fname,
                                @Field("lname") String lname,
                                @Field("email") String email,
                                @Field("tel") String tel,
                                @Field("relation") String relation,
                                @Field("password") String password,
                                @Field("tokens") ArrayList<String> token);

    @GET("events")
    Call<ArrayList<Event>> listEvents();

    @FormUrlEncoded
    @POST("newparent")
    Call<String> addParent(@Field("email") String email, @Field("tel") String tel, @Field("studentIds") String[] studentIds);
}
