package com.example.parentportal.model;

public class Grade {

    private int year;
    private Semester[] semester;

    private class Semester {

        private int semester;
        private Results[] results;
        private int total;
        private int average;

        private class Results {

            private String courseName;
            private int quiz;
            private int midExam;
            private int assignment;
            private int finalExam;
            private int total;

        }
    }
}
