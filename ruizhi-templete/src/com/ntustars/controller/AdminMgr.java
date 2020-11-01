package com.ntustars.controller;

import com.ntustars.entity.AccessPeriod;
import com.ntustars.entity.StudParticulars;

import java.util.Scanner;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

    
public class AdminMgr {
    private AccessPeriod accessPeriod;
    
    private TxtReaderWriter txtReaderWriter;
    
    private int vacancy;
    
    private CourseMgr cMgr;
    
    private Course course;
    

    public AdminMgr() throws IOException{

        this.txtReaderWriter = new TxtReaderWriter();  
        
        this.accessPeriod = new AccessPeriod(new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()));
    
        this.vacancy = -1;
        
        this.cMgr = new CourseMgr();
        
    
    }
 
    
    private ArrayList<List> readContentFromDB(String fileName) throws IOException{
    	ArrayList<List> contentList = new ArrayList<List>();
    	try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String student;

            while ((student = br.readLine()) != null) {
            	contentList.add(new ArrayList<String>(Arrays.asList(student.split(this.cMgr.SEPARATOR))));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
    	
        return contentList;
    }

    //Student Access Period
    public boolean updateStudAccessPeriod(String startDateTimeS, String endDateTimeS) throws IOException, ParseException{
        
        ArrayList<List> accessStartEnd = new ArrayList<List>();
        accessStartEnd = this.readContentFromDB("Student Access Period.txt");
        if (accessStartEnd.isEmpty()) {
        	System.out.println("The student access period has not yet set.\n");
        	//return success;
        }
        else {
        	String startDD = accessStartEnd.get(0).toString().substring(accessStartEnd.get(0).toString().lastIndexOf(":")+1,accessStartEnd.get(0).toString().length() - 1);
        	String endDD = accessStartEnd.get(1).toString().substring(accessStartEnd.get(1).toString().lastIndexOf(":")+1,accessStartEnd.get(1).toString().length() - 1);
        	System.out.println("Current start accessing date and time:\n"+startDD);
        	System.out.println("Current end accessing date and time:\n"+endDD);
        	this.accessPeriod.setStartAccessDate(this.accessPeriod.formatter.parse(startDD));
        	this.accessPeriod.setEndAccessDate(this.accessPeriod.formatter.parse(endDD));
        	
        }
        
        return this.setStudAccessPeriod(startDateTimeS,endDateTimeS);
    }

    public boolean setStudAccessPeriod(String startDateTimeS, String endDateTimeS) throws IOException {
    	
    	boolean success = false;
    	
    	Date start = new Date();
    	Date end = new Date();
    	
    	try{
        	start = this.accessPeriod.formatter.parse(startDateTimeS);
        }catch(ParseException e){
        System.out.println("Invalid start time.");
        return success;
        }
    	
    	try{
        	end = this.accessPeriod.formatter.parse(endDateTimeS);
        }catch(ParseException e){
        System.out.println("Invalid end time.");
        return success;
        }
            
            if(end.before(start)) {
            	System.out.println("Please enter a valid period!");
            	return success;
            }

            this.accessPeriod.setStartAccessDate(start);
            this.accessPeriod.setEndAccessDate(end);
            
            System.out.println("Student accessing period is set successfully!");
            System.out.println("Start accessing date and time:\n"+this.accessPeriod.getStartAccessDate());
            System.out.println("End accessing date and time:\n"+this.accessPeriod.getEndAccessDate());
            
            //success = true;
            
            System.out.println(this.accessPeriod.getAccessPeriod());
            
            this.writeAccessPeriodToDB(accessPeriod);
            
            return true;
    }
    
    private boolean writeAccessPeriodToDB(AccessPeriod accessPeriod) throws IOException {
    	boolean success = false;
    	if (accessPeriod.getStartAccessDate().isEmpty()||accessPeriod.getEndAccessDate().isEmpty()) {
    		System.out.print("Insufficient access period input.");
    		return success;
    	}
    	
    	ArrayList<String> accessStartEnd = new ArrayList<String>();
		
    	//CourseMgr cMgr = new CourseMgr();
    	StringBuilder builder = new StringBuilder();
    	builder.append("Start Access: ");
        builder.append(accessPeriod.getStartAccessDate());
        builder.append("\n");
        builder.append("End Access: ");
        builder.append(accessPeriod.getEndAccessDate());
        builder.append("\n");

        accessStartEnd.add(builder.toString());
        txtReaderWriter.writetxt("Student Access Period.txt", accessStartEnd);

        success = true;
        return success;
    	
    }
 
    
    //Check vacancy
    public void checkVacancy(String indexForVacancy) throws IOException {
    	this.readContentFromDB("infocompo.txt").stream().forEach((compoInfo)->{
    		if(compoInfo.get(0).toString().equals(indexForVacancy)){
    			this.vacancy = Integer.parseInt(compoInfo.get(2).toString());
    		}
    	});
    	
    	if(this.vacancy!=-1) {
    		System.out.println("The available slot of index "+indexForVacancy+": "+this.vacancy);
		}
    	else {
    		System.out.println(indexForVacancy+" is not in the database.");
    	}
    }
 
    
    //Add or update student particulars
    public void addStudInfo(String name,String matricNum,char gender,String nationality) throws IOException{
    	
        StudParticulars studentP = new StudParticulars(name.toUpperCase(),matricNum.toUpperCase(),Character.toUpperCase(gender),nationality.toUpperCase());
        
        System.out.println("Writing information to DB......");
        boolean status = writeStudentToDB(studentP);
        if(status) {
        	System.out.println("Added student information successfully.");
        }     
    }

    private boolean writeStudentToDB(StudParticulars studentP) throws IOException
    {
    	boolean success = false;
    	
    	if (studentP.getName().isEmpty()|| studentP.getMatricNum().isEmpty()||studentP.getGender()==0 ||studentP.getNationality().isEmpty()) {
    		System.out.print("Insufficient student information input.");
    	}
    	
    	
    	for(List student:readAllStudParticulars()) {
    		if(studentP.getName().equals(student.get(0))) {
    			System.out.println("The student has alreay registered!");
    			return success;}
    	}
    	
    	ArrayList studList = (ArrayList)txtReaderWriter.readtxt("Student Information.txt");
    	
    	StringBuilder builder = new StringBuilder();
        builder.append(studentP.getName());
        builder.append(this.cMgr.SEPARATOR);
        builder.append(studentP.getMatricNum());
        builder.append(this.cMgr.SEPARATOR);
        builder.append(String.valueOf(studentP.getGender()));
        builder.append(this.cMgr.SEPARATOR);
        builder.append(studentP.getNationality());

        studList.add(builder.toString());
        txtReaderWriter.writetxt("Student Information.txt", studList);

        success = true;
        return success;
    }
        
    public void updStudParticulars(String name,int choice,String update) throws IOException {
    	
    	List newStudInfo = new ArrayList<String>();
    	String updStudent;
    	StudParticulars newStudent;
    	
    	name = name.toUpperCase();
    	System.out.println(name);
    	
    	
    	int index = -1;
    	for(List student:readAllStudParticulars()) {
    		if(name.equals(student.get(0))) {
    			index = readAllStudParticulars().indexOf(student);
    		newStudInfo = student;
    		break;
    	}}

    	if(index == -1) {
    		System.out.println("The student does not exist!");
    		return;
    	}
    	else {
    		newStudent = new StudParticulars(newStudInfo.get(0).toString(),newStudInfo.get(1).toString(),newStudInfo.get(2).toString().charAt(0),newStudInfo.get(3).toString());
    		updStudent = name;
        		switch(choice) {
        		case 1:
        		{
        	    	update = update.toUpperCase();
        	    	newStudent.setName(update);
        	    	System.out.println(update);
        	    	break;
        		}
        		case 2:{
        			update = update.toUpperCase();
        			newStudent.setMatricNum(update);
        			break;
        		}
        		case 3:
        		{
        			char gender = Character.toUpperCase(update.charAt(0));
        			newStudent.setGender(gender);
        			break;
        		}
        		case 4:
        		{
        	    	update = update.toUpperCase();
        	    	newStudent.setNationality(update);
        	    	break;
        		}
    		
    		System.out.println(name);
    		//System.out.println(newStudInfo);
    		System.out.println("Updating information to DB......");
    		boolean status = UpdateStudPartToDB(name,newStudent);
            if(status) {
            	System.out.println("Updated student inforamtion successfully.");
            }
    	
    	}
    	
    }
    
    private boolean UpdateStudPartToDB(String nameToUpdate,StudParticulars newStudentInfo) throws IOException
    {
    	boolean success = false;
    	
    	File fileToUpdate = new File("Student Information.txt");
    	BufferedReader reader = null;
    	FileWriter writer = null;
  
        String oldFile = "";
        
        String oldLine = "";
        
        StringBuilder builder = new StringBuilder();
        builder.append(newStudentInfo.getName());
        builder.append(this.cMgr.SEPARATOR);
        builder.append(newStudentInfo.getMatricNum());
        builder.append(this.cMgr.SEPARATOR);
        builder.append(Character.toString(newStudentInfo.getGender()));
        builder.append(this.cMgr.SEPARATOR);
        builder.append(newStudentInfo.getNationality());
        
        String newLine = builder.toString();
        
        try
        {
            reader = new BufferedReader(new FileReader(fileToUpdate));  
            String line = reader.readLine();
            while (line != null) 
            {
                if (line.contains(nameToUpdate)) {
                	oldLine = line;
                }
            	oldFile = oldFile + line + System.lineSeparator();
                 
                line = reader.readLine();
            }

            String newFile = oldFile.replaceAll(oldLine, newLine);

            writer = new FileWriter(fileToUpdate);
             
            writer.write(newFile);
            success = true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {    
                reader.close();
                 
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    	
        
    	return success;
    	
    }
 
    private ArrayList<List> readAllStudParticulars() throws IOException{
    	return readContentFromDB("Student Information.txt");
    }

    public void printAllStudent() throws IOException {
    	readAllStudParticulars().stream().forEach((student)->{
    		System.out.print("Name:"+student.get(0)+"  ");
    		System.out.print("Matric Number:"+student.get(1)+"  ");
    		System.out.print("Gender:"+student.get(2)+"  ");
    		System.out.println("Nationality:"+student.get(3));
    	});
    }
   
    
    public void addCoursebyAdmin() throws IOException {
    }
    
    public void updateCourse() {
    }
    
    
    //Print student registration information
    private ArrayList<List> readAllStudCouRegInfo() throws IOException{
    	return readContentFromDB("Student Registration Information.txt");
    }

    public void printStudListByIndex() throws IOException {
    	
    	HashMap<String, ArrayList<List<String>>> hashMap = new HashMap<String, ArrayList<List<String>>>();

    	readAllStudCouRegInfo().stream().forEach((student) -> {
    		ArrayList<List<String>> tempList = new ArrayList<List<String>>();

			tempList.add(new ArrayList<String>(Arrays.asList(student.get(0).toString(),student.get(2).toString(),student.get(3).toString())));

    		if(!hashMap.containsKey(student.get(5).toString())) {
    			hashMap.put(student.get(5).toString(), tempList);}
    		else {

    			hashMap.get(student.get(5)).addAll(tempList);
    		}
    		
    	});
    	System.out.println(hashMap);
    }
 	
    public void printStudListByCourse() throws IOException {
    	
    	HashMap<String, ArrayList<List<String>>> hashMap = new HashMap<String, ArrayList<List<String>>>();

    	readAllStudCouRegInfo().stream().forEach((student) -> {
    		ArrayList<List<String>> tempList = new ArrayList<List<String>>();

			tempList.add(new ArrayList<String>(Arrays.asList(student.get(0).toString(),student.get(2).toString(),student.get(3).toString())));

    		if(!hashMap.containsKey(student.get(4).toString())) {
    			hashMap.put(student.get(4).toString(), tempList);}
    		else {

    			hashMap.get(student.get(4)).addAll(tempList);
    		}
    		
    	});
    	System.out.println(hashMap);
    	
    }
}
