package com.example.parentportal.model.DB;

public class TableItems {

    public static final String TABLE_KID = "kid_table";
    public static final String KID_ID = "kid_id";
    public static final String KID_FNAME = "kid_fname";
    public static final String KID_LNAME = "kid_lname";
    public static final String KID_GENDER = "kid_gender";
    public static final String KID_YEAR = "kid_year";
    public static final String KID_SECTION = "kid_section";
    public static final String KID_IN_SCHOOL = "kid_inSchool";
    public static final String KID_AGE = "kid_age";
    //    public static final String KID_SEMESTER = "kid_semester";

    public static final String CREATE_KID_TABLE = "CREATE TABLE " + TABLE_KID + "( " +
            KID_ID + " TEXT PRIMARY KEY, " + KID_FNAME + " TEXT NOT NULL, " +
            KID_LNAME + " TEXT NOT NULL, " + KID_GENDER + " TEXT NOT NULL, " +
            KID_SECTION + " TEXT NOT NULL, " + KID_IN_SCHOOL + " INTEGER NOT NULL, " +
            KID_AGE + " INTEGER NOT NULL, " + KID_YEAR + " INTEGER NOT NULL);";

    public static final String DROP_KID_TABLE = "DROP TABLE IF EXISTS " + TABLE_KID;

    public static final String TABLE_PARENT = "parent_table";
    public static final String PARENT_ID = "parent_id";
    public static final String PARENT_FNAME = "parent_fname";
    public static final String PARENT_LNAME = "parent_lname";
    public static final String PARENT_RELATION = "parent_relation";
    public static final String PARENT_EMAIL = "parent_email";
    public static final String PARENT_TEL = "parent_tel";

    public static final String CREATE_PARENT_TABLE = "CREATE TABLE " + TABLE_PARENT + "( " +
            PARENT_ID + " TEXT PRIMARY KEY, " + PARENT_FNAME + " TEXT NOT NULL, " + PARENT_LNAME +
            " TEXT NOT NULL," + PARENT_RELATION + " TEXT NOT NULL, " + PARENT_EMAIL + " TEXT NOT NULL, " +
            PARENT_TEL + " TEXT NOT NULL);";

    public static final String DROP_PARENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PARENT;

}
