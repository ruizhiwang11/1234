package com.ntustars.controller;
import com.ntustars.entity.Course;
import com.ntustars.entity.Course.CourseType;
import com.ntustars.entity.CourseCompoment;
import com.ntustars.entity.CourseInfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Map;


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
            String school = star.nextToken().trim();
            Course course = new Course(courseID, type, school);
            while (star.hasMoreTokens()){
                indexArray.add(star.nextToken().trim());
            }
            courseMap.put(course, indexArray);
        }
    }

    private StringTokenizer readCompoment(StringTokenizer star, CourseInfo info){
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
    private ArrayList readAllcourseIndex() throws IOException{
        ArrayList stringArray = (ArrayList)txtReaderWriter.readtxt("infocompo.txt");
        ArrayList<String> courseIndexList = new ArrayList<>();
        for(int i =0; i< stringArray.size();i++){
            String st = (String) stringArray.get(i);
            StringTokenizer star = new StringTokenizer(st,SEPARATOR);
            courseIndexList.add(star.nextToken().trim());
        }
        return courseIndexList;
    }
    private CourseType strToCourseType(String str){
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
        for(int i = 0; i< stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            if (st.contains(courseIndex)) {
                StringTokenizer star = new StringTokenizer(st, SEPARATOR);
                String index = star.nextToken().trim();
                info.setIndex(index);
                boolean hasslot = Boolean.parseBoolean(star.nextToken().trim());
                info.setHasVencancies(hasslot);
                int slot = Integer.parseInt(star.nextToken().trim());
                info.setSlot(slot);
                while (star.hasMoreTokens()) {
                    switch (type) {
                        case ONLYLEC:
                            star = readCompoment(star, info);
                            break;
                        case WITHTUT:
                            star = readCompoment(star, info);
                            star = readCompoment(star, info);
                            break;
                        case WITHTUTANDLAB:
                            star = readCompoment(star, info);
                            star = readCompoment(star, info);
                            star = readCompoment(star, info);
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
    private void writeCourseInfo(CourseInfo info, CourseType type)throws IOException{
        if(info.getIndex() == "" || info.getCompoments().size() == 0){
            throw new RuntimeException("empty course index");
        }
        ArrayList stringArray = (ArrayList)txtReaderWriter.readtxt("infocompo.txt");
        StringBuilder builder = new StringBuilder();
        builder.append(info.getIndex());
        builder.append(SEPARATOR);
        builder.append(info.getHasVencancies());
        builder.append(SEPARATOR);
        builder.append(info.getSlot());
        builder.append(SEPARATOR);
        for (CourseCompoment compo : info.getCompoments()){
            builder.append(compo.getCourseCompomentType());
            builder.append(SEPARATOR);
            builder.append(compo.getDay());
            builder.append(SEPARATOR);
            builder.append(compo.getTime());
            builder.append(SEPARATOR);
        }
        stringArray.add(builder.toString());
        txtReaderWriter.writetxt("infocompo.txt",stringArray);
    }
    private void updateCourseInfoDB (Course course)throws IOException{
        ArrayList stringArray = (ArrayList) txtReaderWriter.readtxt("courseinfo.txt");
        for(int i = 0; i< stringArray.size(); i++) {
            String st = (String)stringArray.get(i);
            if(st.contains(course.getCourseID())){
                StringBuilder builder = new StringBuilder();
                builder.append(course.getCourseID());
                builder.append(SEPARATOR);
                builder.append(course.getCourseType());
                builder.append(SEPARATOR);
                builder.append(course.getSchool());
                builder.append(SEPARATOR);
                for(int j =0; j < course.getInfos().size(); j++){
                    builder.append(course.getInfos().get(j).getIndex());
                    builder.append(SEPARATOR);
                }
                stringArray.set(i,builder.toString());
            }
        }
        txtReaderWriter.writetxt("courseinfo.txt", stringArray);
    }
    private void updateExistInfoCompoDB(CourseInfo info) throws  IOException{
        ArrayList stringArray = (ArrayList) txtReaderWriter.readtxt("infocompo.txt");
            for (int j = 0; j < stringArray.size(); j++) {
                String st = (String) stringArray.get(j);
                if (st.contains(info.getIndex())) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(info.getIndex());
                    builder.append(SEPARATOR);
                    builder.append(info.getHasVencancies());
                    builder.append(SEPARATOR);
                    builder.append(info.getSlot());
                    builder.append(SEPARATOR);
                    for (int k = 0; k < info.getCompoments().size(); k++) {
                        builder.append(info.getCompoments().get(k).getCourseCompomentType());
                        builder.append(SEPARATOR);
                        builder.append(info.getCompoments().get(k).getDay());
                        builder.append(SEPARATOR);
                        builder.append(info.getCompoments().get(k).getTime());
                        builder.append(SEPARATOR);
                    }
                    stringArray.set(j, builder.toString());
                }
            }

        txtReaderWriter.writetxt("infocompo.txt", stringArray);
    }
    private void addNewInfoCompoDB(CourseInfo info) throws  IOException{
        ArrayList stringArray = (ArrayList) txtReaderWriter.readtxt("infocompo.txt");
        StringBuilder builder = new StringBuilder();
        builder.append(info.getIndex());
        builder.append(SEPARATOR);
        builder.append(info.getHasVencancies());
        builder.append(SEPARATOR);
        builder.append(info.getSlot());
        builder.append(SEPARATOR);
        for (int k = 0; k < info.getCompoments().size(); k++) {
            builder.append(info.getCompoments().get(k).getCourseCompomentType());
            builder.append(SEPARATOR);
            builder.append(info.getCompoments().get(k).getDay());
            builder.append(SEPARATOR);
            builder.append(info.getCompoments().get(k).getTime());
            builder.append(SEPARATOR);
        }
        stringArray.add(builder.toString());
        txtReaderWriter.writetxt("infocompo.txt",stringArray);
    }

    public String getCourseIDbyInfoIndex(String index) throws  IOException{
        ArrayList stringArray = (ArrayList) txtReaderWriter.readtxt("courseinfo.txt");
        for (int i = 0; i < stringArray.size(); i++){
            String st = (String) stringArray.get(i);
            if(st.contains(index)){
                StringTokenizer star = new StringTokenizer(st, SEPARATOR);
                return  (star.nextToken()).trim();
            }
        }
        throw new RuntimeException("No such a index in the database: "+ index);
    }

    public Course readCourseFromDB(String CourseID) throws  IOException {
        ArrayList stringArray = (ArrayList) txtReaderWriter.readtxt("courseinfo.txt");
        Course course = new Course();
        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            if (st.contains(CourseID)) {
                StringTokenizer star = new StringTokenizer(st, SEPARATOR);
                String ID = star.nextToken().trim();
                CourseType type = strToCourseType(star.nextToken().trim());
                String school = star.nextToken().trim();
                course.setCourseID(ID);
                course.setCourseType(type);
                course.setSchool(school);
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

    public ArrayList buildAllCourseFromDB() throws  IOException{
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

    public boolean updateCourseIndexSlot(String courseIndex, Integer slot) throws IOException{
        boolean success = false;
        if(slot < 0){
            System.out.println("Course slot can't be less than 0");
            return success;
        }
        ArrayList stringArray = (ArrayList)txtReaderWriter.readtxt("infocompo.txt");

        for(int i = 0; i< stringArray.size(); i++) {
            String st = (String)stringArray.get(i);
            if(st.contains(courseIndex)) {
                ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(st.split(SEPARATOR)));
                if (slot == 0) {
                    elephantList.set(1, "false");
                }
                elephantList.set(2, slot.toString());
                StringBuilder updatedRow = new StringBuilder();
                for (int j = 0; j < elephantList.size(); j++) {
                    updatedRow.append(elephantList.get(j));
                    updatedRow.append(SEPARATOR);
                }
                stringArray.set(i, updatedRow.toString());
                success = true;
                break;
            }
        }
        txtReaderWriter.writetxt("infocompo.txt",stringArray);
        return success;
    }
    public boolean addCourse(Course course) throws  IOException{
        boolean success = false;
        if(course.getCourseID().isEmpty() || course.getSchool().isEmpty()||course.getInfos().isEmpty()){
            System.out.println("Course is empty");
            return success;
        }
        ArrayList stringArray = (ArrayList) txtReaderWriter.readtxt("courseinfo.txt");
        for(int i = 0; i< stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            if (st.contains(course.getCourseID())) {
                System.out.println("Data base already have the course");
                return success;
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append(course.getCourseID());
        builder.append(SEPARATOR);
        builder.append(course.getType());
        builder.append(SEPARATOR);
        builder.append(course.getSchool());
        builder.append(SEPARATOR);
        for(CourseInfo info : course.getInfos()){
            builder.append(info.getIndex());
            builder.append(SEPARATOR);
            writeCourseInfo(info, course.getType());
        }
        stringArray.add(builder.toString());
        txtReaderWriter.writetxt("courseinfo.txt", stringArray);

        success = true;
        return success;
    }
    public boolean updateCourse(Course course) throws  IOException{
        boolean success = false;
        if(course.getCourseID().isEmpty() || course.getSchool().isEmpty()||course.getInfos().isEmpty()){
            System.out.println("Course is empty");
            return success;
        }
        ArrayList DBinfoList = readAllcourseIndex();
        updateCourseInfoDB(course);
        for(CourseInfo info : course.getInfos()){
            if(DBinfoList.contains(info.getIndex())){
                updateExistInfoCompoDB(info);
            }
            else{
                addNewInfoCompoDB(info);
            }
        }
        success = true;
        // Finish update the courseinfo db
        return  success;
    }
}