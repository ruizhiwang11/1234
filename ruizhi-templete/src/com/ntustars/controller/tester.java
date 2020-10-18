package com.ntustars.controller;

import com.ntustars.entity.Course;

import java.io.IOException;
import java.util.ArrayList;

public class tester {
    public static void main(String[] args) throws IOException {
        CourseMgr mgr = new CourseMgr();
        ArrayList <Course> arrayList = mgr.buildAllCoursefromDB();
        for(Course course : arrayList){
            course.printCourse();
        }
        Course course = mgr.readCoursefromDB("CZ2006");
        course.printCourse();

        if (mgr.updateCoursetoDB("12126", 28)){
            System.out.println("Successfully update course 12126 slot to 28");
        }
        else{
            System.out.println("Fail to update");
        }
        if (mgr.updateCoursetoDB("13131", 0)){
            System.out.println("Successfully update course 13131 slot to 0");
        }
        else{
            System.out.println("Fail to update");
        }
        if (mgr.updateCoursetoDB("1212121212", 0)){
            System.out.println("Successfully update course 1212121212 slot to 0");
        }
        else{
            System.out.println("Fail to update");
        }

    }
}
