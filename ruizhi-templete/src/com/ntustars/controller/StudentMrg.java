package com.ntustars.controller;
import com.ntustars.entity.Course;
import com.ntustars.entity.Course.CourseType;
import com.ntustars.entity.CourseCompoment;
import com.ntustars.entity.CourseInfo;
import com.ntustars.entity.Student;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Map;

// student course table: StID, courseID, courseID, courseID, courseID, etc ...


public class StudentMrg {

    private TxtReaderWriter txtReaderWriter;
    private int vacancy;
    private StudentMrg studentMrg;
    public HashMap<String, ArrayList<String >> studnetCoursetable;
    private ArrayList studentCourse;

    //private HashMap<String, String, Boolean> returnData;
    public StudentMrg() throws IOException{
        txtReaderWriter = new TxtReaderWriter();

    }
    private readStudentCourseTable( String studentid){
        HashMap studentCourseMap = (HashMap)txtReaderWriter.readtxt("Student Course Table.txt");
        if (studentCourseMap.containsKey(studentid) == false){
            studentCourseMap.put(studentid, "");
            return studentCourseMap.value(studentid);
        }
        else {
            return studentCourseMap.value(studentid);
            
        }
    }


    public boolean studentAddCourse(String StudenId, int courseId){
        boolean success = false;
        studentCourse = new ArrayList<>();
        if ()
        //HashMap returnData = new HashMap<String, String, Boolean>(StudenId, courseId, success);

        //call addCourse
        //check success of addCourse if success success

        return success;
    }
    public boolean studentDropCourse(String StudenId, int courseId){
        boolean success = false;
        //HashMap returnData = new HashMap<String, String, Boolean>(StudenId, courseId, success);

        //call addCourse
        //check success of addCourse if success success

        return success;
    }
    public boolean studentCheckCourse(String StudenId, int courseId){
        boolean success = false;
        //HashMap returnData = new HashMap<String, String, Boolean>(StudenId, courseId, success);

        //call addCourse
        //check success of addCourse if success success

        return success;
    }
    public boolean studentCheckVacanCourse(String StudenId, int courseId){
        boolean success = false;
        //HashMap returnData = new HashMap<String, String, Boolean>(StudenId, courseId, success);

        //call addCourse
        //check success of addCourse if success success

        return success;
    }
    public boolean studentCourseIndex(String StudenId, int courseId){
        boolean success = false;
        //HashMap returnData = new HashMap<String, String, Boolean>(StudenId, courseId, success);

        //call addCourse
        //check success of addCourse if success success

        return success;
    }
    public boolean studentSwopCourse(String StudenId, int courseId){
        boolean success = false;
        //HashMap returnData = new HashMap<String, String, Boolean>(StudenId, courseId, success);

        //call addCourse
        //check success of addCourse if success success

        return success;
    }

}
