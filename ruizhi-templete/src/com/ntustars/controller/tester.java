package com.ntustars.controller;

import com.ntustars.entity.Course;
import com.ntustars.entity.CourseCompoment;
import com.ntustars.entity.CourseInfo;

import java.io.IOException;
import java.util.ArrayList;

public class tester {
    public static void main(String[] args) throws IOException {
        CourseMgr mgr = new CourseMgr();
        /*ArrayList <Course> arrayList = mgr.buildAllCourseFromDB();
        for(Course course : arrayList){
            course.printCourse();
        }*/

        /*Course course = mgr.readCourseFromDB("CZ2006");
        course.printCourse();*/

        if (mgr.updateCourseIndexSlot("12126", 28)){
            System.out.println("Successfully update course 12126 slot to 28");
        }
        else{
            System.out.println("Fail to update");
        }
        if (mgr.updateCourseIndexSlot("13131", 0)){
            System.out.println("Successfully update course 13131 slot to 0");
        }
        else{
            System.out.println("Fail to update");
        }
        if (mgr.updateCourseIndexSlot("1212121212", 0)){
            System.out.println("Successfully update course 1212121212 slot to 0");
        }
        else{
            System.out.println("Fail to update");
        }
        /*
        Course haha = new Course("CZ3003", Course.CourseType.WITHTUT, "SCSE");
        CourseInfo hahainfo = new CourseInfo("12345", true, 80);
        hahainfo.addCompoment(new CourseCompoment("LEC", "FRI", "1200-1300"));
        hahainfo.addCompoment(new CourseCompoment("TUT", "THU", "1400-1500"));
        haha.addInfo(hahainfo);
        CourseInfo hahainfo2 = new CourseInfo("12346",true,20);
        hahainfo2.addCompoment(new CourseCompoment("LEC", "FRI", "1200-1300"));
        hahainfo2.addCompoment(new CourseCompoment("TUT", "TUE", "1200-1300"));
        haha.addInfo(hahainfo2);
        mgr.addCourse(haha);
        haha.setCourseType(Course.CourseType.WITHTUTANDLAB);
        for(int i =0; i< haha.getInfos().size();i++){
            haha.getInfos().get(i).addCompoment(new CourseCompoment("LAB","MON","1000-1200"));
        }
        //haha.getInfos().get(0).addCompoment(new CourseCompoment("LAB","MON","1000-1200"));
        //haha.setCourseType(Course.CourseType.WITHTUT);
        //haha.getInfos().get(0).getCompoments().remove(2);
        haha.setSchool("MAE");

        mgr.updateCourse(haha);
        mgr.updateCourseIndexSlot("12345",13);
        System.out.println(mgr.getCourseIDbyInfoIndex("12345"));*/
    }
}
