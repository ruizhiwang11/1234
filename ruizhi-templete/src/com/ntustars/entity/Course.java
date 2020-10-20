package com.ntustars.entity;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseID = "";
    private CourseType courseType;
    private String school;
    private List<CourseInfo> infos;

    public Course(){
        infos = new ArrayList<>();
    }
    public Course(String courseID, CourseType courseType, String school){
        infos = new ArrayList<>();
        this.courseID = courseID;
        this.courseType = courseType;
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public enum CourseType {
        ONLYLEC, WITHTUT, WITHTUTANDLAB
    }
    public String getCourseID() {
        return courseID;
    }
    public CourseType getType() {
        return courseType;
    }

    public List<CourseInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<CourseInfo> infos) {
        this.infos = infos;
    }

    public void addInfo(CourseInfo info){
        infos.add(info);
    }
    public void deleteinfo(int num){
        infos.remove(num);
    }
    public void updateinfo(int index, CourseInfo info){
        infos.set(index, info);
    }
    public void printCourse(){
        System.out.println("Course: "+courseID + "School: "+ school);
        for(int i =0;i< infos.size(); i++){
            infos.get(i).printInfo();
        }
    }
}
