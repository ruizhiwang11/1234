package com.ntustars.entity;

import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {

    private String studentName;
    private String matricNumber;
    private IdentityType identityType;
    private String userName;
    private String password;
    private int studyYear;
    private String IDnumber;
    private String gender;
    private String nationality;
    private List<String> courseIndexRegisted;

    /**
     * Entity of Student Object
     *
     * @version 1.0
     */

    /**
     * Constructor of Student()
     */
    public Student(){

    }
    public enum IdentityType {
        PASSPORT, NRIC
    }

    public IdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType) {
        this.identityType = identityType;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public String getIDnumber() {
        return IDnumber;
    }

    public void setIDnumber(String IDnumber) {
        this.IDnumber = IDnumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<String> getCourseRegisted() {
        return courseIndexRegisted;
    }

    public void setCourseRegisted(List<String> courseIndexRegisted) {
        this.courseIndexRegisted = courseIndexRegisted;
    }

    public void printStudentInfo(){
        System.out.println(" -------------------------------------------");
        System.out.println("Student: " + this.getIDnumber());
        System.out.println("1.Name: " + this.getStudentName());
        System.out.println("2.Gender: " + this.getGender());
        System.out.println("3.Nationality: " + this.getNationality());
        System.out.println(" -------------------------------------------");
    }

}
