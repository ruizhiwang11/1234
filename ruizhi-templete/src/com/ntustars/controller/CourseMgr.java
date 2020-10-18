package com.ntustars.controller;
import com.ntustars.entity.Course;
import com.ntustars.entity.Course.CourseType;
import com.ntustars.entity.CourseCompoment;
import com.ntustars.entity.CourseInfo;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class CourseMgr {
    public static final String SEPARATOR = ",";
    public  ArrayList<Course> allCourseArrayList;
    private  HashMap <Course, ArrayList<String>> courseMap;
    private TxtReaderWriter txtReaderWriter;


    public CourseMgr() throws IOException {
        courseMap = new HashMap<>();
        allCourseArrayList = new ArrayList<>();
        txtReaderWriter = new TxtReaderWriter();
    }

    private void readAllCourse() throws IOException{
        ArrayList stringArray = (ArrayList)txtReaderWriter.readtxt("courseinfo.txt");
        for (int i = 0 ; i < stringArray.size() ; i++){
            ArrayList<String> indexArray = new ArrayList<>();
            String st = (String)stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st , SEPARATOR);
            String  courseID = star.nextToken().trim();
            CourseType type = strToCourseType(star.nextToken().trim());
            Course course = new Course(courseID, type);
            while (star.hasMoreTokens()){
                indexArray.add(star.nextToken().trim());
            }
            courseMap.put(course, indexArray);
        }
    }

    private  StringTokenizer addCompoment(StringTokenizer star, CourseInfo info){
        String compomentType;
        String day;
        String time;
        CourseCompoment compoment;
        compomentType = star.nextToken().trim();
        day = star.nextToken().trim();
        time = star.nextToken().trim();
        compoment = new CourseCompoment(compomentType, day, time);
        info.addCompoment(compoment);
        return star;
    }

    private  CourseType strToCourseType(String str){
        if (str.equals("ONLYLEC"))
            return CourseType.ONLYLEC;
        else if (str.equals("WITHTUT"))
            return CourseType.WITHTUT;
        else if (str.equals("WITHTUTANDLAB"))
            return CourseType.WITHTUTANDLAB;
        else throw new RuntimeException("Wrong type of course");
    }

    private CourseInfo readCourseInfo(CourseType type, String courseIndex) throws IOException{
        CourseInfo info = new CourseInfo("");
        ArrayList stringArray = (ArrayList)txtReaderWriter.readtxt("infocompo.txt");
        for(int i = 0; i< stringArray.size(); i++){
            String st = (String)stringArray.get(i);
            if(st.contains(courseIndex)){
                StringTokenizer star = new StringTokenizer(st , SEPARATOR);
                String index = star.nextToken().trim();
                info.setIndex(index);
                boolean hasslot = Boolean.parseBoolean(star.nextToken().trim());
                info.setHasVencancies(hasslot);
                int slot = Integer.parseInt(star.nextToken().trim());
                info.setSlot(slot);
                while (star.hasMoreTokens()){
                    switch (type)
                    {
                        case ONLYLEC:
                            star = addCompoment(star, info);
                            break;
                        case WITHTUT:
                            star = addCompoment(star, info);
                            star = addCompoment(star, info);
                            break;
                        case WITHTUTANDLAB:
                            star = addCompoment(star, info);
                            star = addCompoment(star, info);
                            star = addCompoment(star, info);
                            break;
                        default:
                            throw new RuntimeException("invalid database format");
                    }
                }
                break;
            }
        }
        if(info.getIndex() == ""){
            throw new RuntimeException("no course index found in the db");
        }
        return info;

    }
    public Course readCoursefromDB(String CourseID) throws  IOException {
        ArrayList stringArray = (ArrayList) txtReaderWriter.readtxt("courseinfo.txt");
        Course course = new Course();
        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            if (st.contains(CourseID)) {
                StringTokenizer star = new StringTokenizer(st, SEPARATOR);
                String ID = star.nextToken().trim();
                CourseType type = strToCourseType(star.nextToken().trim());
                course.setCourseID(ID);
                course.setCourseType(type);
                while (star.hasMoreTokens()) {
                    String courseIndex = star.nextToken().trim();
                    CourseInfo info = readCourseInfo(type, courseIndex);
                    course.addInfo(info);
                }
                break;
            }
        }
        if(course.getCourseID() == ""){
            throw new RuntimeException("No courseID found in DB");
        }
        return course;
    }

    public ArrayList buildAllCoursefromDB() throws  IOException{
        readAllCourse();
        for (Map.Entry<Course, ArrayList<String>> entry : courseMap.entrySet()) {
            Course course = entry.getKey();
            ArrayList <String> indexaAl = entry.getValue();
            for(String courseIndex : indexaAl){
                CourseInfo info = readCourseInfo(course.getType(),courseIndex);
                course.addInfo(info);
            }
            allCourseArrayList.add(course);
        }
        return  allCourseArrayList;
    }

    public boolean updateCoursetoDB(String courseIndex, Integer slot) throws  IOException{
        if(slot < 0){
            throw new RuntimeException("Course slot can't be less than 0");
        }
        ArrayList stringArray = (ArrayList)txtReaderWriter.readtxt("infocompo.txt");
        boolean success = false;
        for(int i = 0; i< stringArray.size(); i++) {
            String st = (String)stringArray.get(i);
            if(st.contains(courseIndex)){
                ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(st.split(SEPARATOR)));
                if(slot == 0){
                    elephantList.set(1, "false");
                }
                elephantList.set(2, slot.toString());
                StringBuilder updatedRow = new StringBuilder();
                for(int j = 0; j < elephantList.size(); j++){
                    updatedRow.append(elephantList.get(j));
                    updatedRow.append(SEPARATOR);
                }
                stringArray.set(i, updatedRow.toString());
                success = true;
                break;
            }
        }
        File tempDB = new File("infocompo_temp.txt");
        txtReaderWriter.writetxt("infocompo_temp.txt",stringArray);
        File DB = new File("infocompo.txt");
        DB.delete();
        tempDB.renameTo(DB);
        return success;
    }

}