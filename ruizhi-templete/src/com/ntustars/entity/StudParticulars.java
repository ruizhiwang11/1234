package com.ntustars.entity;


public class StudParticulars {
    private String name;
	private String matricNum;
    private char gender;
    private String nationality;

    public StudParticulars(String name,String matricNum, char gender, String nationality){
        this.name = name;
    	this.matricNum = matricNum;
        this.gender = gender;
        this.nationality = nationality;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
    	return this.name;
    }

    public void setMatricNum(String matricNum){
        this.matricNum = matricNum;
    }

    public String getMatricNum(){
        return this.matricNum;
    }

    public void setGender(char gender){
        this.gender = gender;
    }

    public char getGender(){
        return this.gender;
    }

    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    public String getNationality(){
        return this.nationality;
    }

    public void printStudentInfo(){
            System.out.println("Name: "+this.name+" Matric Number: "+this.matricNum+" Gender: "+this.gender+" Nationality: "+this.nationality);
    }
}
