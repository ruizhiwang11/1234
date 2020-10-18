package com.ntustars.entity;

public class CourseCompoment{
    private String courseCompomentType;
    private String day;
    private String time;

    public CourseCompoment(String courseCompomentType, String day, String time) {
        this.courseCompomentType = courseCompomentType;
        this.day = day;
        this.time = time;
    }

    public String getCourseCompomentType() {
        return courseCompomentType;
    }

    public void setCourseCompomentType(String courseCompomentType) {
        this.courseCompomentType = courseCompomentType;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void printCompoment(){
        System.out.println(courseCompomentType+ "\t" + day + "\t" + time);
    }
}
