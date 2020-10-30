package com.ntustars.controller;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class AdminMgr {
    private Date startDateTime;
    private Date endDateTime;
    private SimpleDateFormat formatter;
    private HashMap<String,StudParticulars> students;
    
    private TxtReaderWriter txtReaderWriter;
    
public class AdminMgr {
    private Date startDateTime;
    private Date endDateTime;
    private SimpleDateFormat formatter;
    private AccessPeriod accessPeriod;
    private HashMap<String,StudParticulars> students;
    
    private TxtReaderWriter txtReaderWriter;
    

public AdminMgr(){
        this.students =new HashMap<String,StudParticulars>();
        this.txtReaderWriter = new TxtReaderWriter();  
        this.accessPeriod = new AccessPeriod(new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()));
    }
 
    public void editStudAccessPeriod(){
        Date start = new Date();
    	Date end = new Date();
        Scanner sc = new Scanner(System.in);      
        System.out.println("Current start accessing date and time:\n"+this.accessPeriod.getStartAccessDate());
        System.out.println("Current end accessing date and time:\n"+this.accessPeriod.getEndAccessDate());
        while(true) {
        while(true){
            System.out.println("Enter start time in yyyy MM dd HH mm format:");
            String startDateTimeS = sc.nextLine();
            try{
            	start = this.accessPeriod.formatter.parse(startDateTimeS);
            }catch(ParseException e){
            System.out.println("Invalid input. Please try again!");
            continue;
        }
        break;
    }

        while(true){
            System.out.println("Enter end time in yyyy MM dd HH mm format:");
            String endDateTimeS = sc.nextLine();
            try{
            	end = this.accessPeriod.formatter.parse(endDateTimeS);
            }catch(ParseException e){
            System.out.println("Invalid input. Please try again!");
            continue;
        }
        break;      
	}
        if(end.after(start)) {
        	break;
        } 
        System.out.println("Please enter a valid period!");
    }
        
        this.accessPeriod.setStartAccessDate(start);
        this.accessPeriod.setEndAccessDate(end);
        
        System.out.println("Student accessing period is updated successfully!");
        System.out.println("Updated start accessing date and time:\n"+this.accessPeriod.getStartAccessDate());
        System.out.println("Updated end accessing date and time:\n"+this.accessPeriod.getEndAccessDate());
    }
    
    public void addStudInfo() throws IOException {
    	
    	Scanner sc = new Scanner(System.in);
        
        System.out.println("Add a new student:");
        
        System.out.println("Please enter the student's name:");
    	String name = sc.nextLine();
    	while(!name.matches("^[a-zA-Z]*$"))
    	{
    		System.out.println("Invalid input! Please enter again!");
    		System.out.println("Please enter the student's name:");
        	name = sc.nextLine();        	
    	}
    	name = name.toUpperCase();
    	
    	System.out.println("Please input matric number of "+name+":");
		String matricNum = sc.nextLine();
		while(!matricNum.matches("[a-zA-Z]\\d{7}[a-zA-Z]"))
    	{
    		System.out.println("Invalid input! Please enter again!");
    		System.out.println("Please input matric number of "+name+":");
    		matricNum = sc.nextLine();        	
    	}
		matricNum = matricNum.toUpperCase();
		//System.out.println(matricNum);
		
		System.out.println("Please enter gender of "+name+", M or F?");
		char gender = sc.next().charAt(0);
		//System.out.println(gender);
		
		while((Character.toUpperCase(gender)!='M')&&(Character.toUpperCase(gender)!='F'))
    	{
    		System.out.println("Invalid input! Please enter again!");
    		System.out.println("Please enter gender of "+name+", M or F?");
        	gender = sc.next().charAt(0); 

    	}
		sc.nextLine();
		gender = Character.toUpperCase(gender);

		System.out.println("Please enter the nationality of "+name+":");
    	String nationality = sc.nextLine();
    	while(!nationality.matches("^[a-zA-Z]*$"))
    	{
    		System.out.println("Invalid input! Please enter again!");
    		System.out.println("Please enter the nationality of "+name+":");
    		nationality = sc.nextLine();        	
    	}
    	
    	nationality = nationality.toUpperCase();
    	
        StudParticulars studentP = new StudParticulars(name,matricNum,gender,nationality);
        
        System.out.println("Writing information to DB......");
        boolean status = writeStudent(studentP);
        if(status) {
        	System.out.println("Added student inforamtion successfully.");
        }
    }
	
 
    private boolean writeStudent(StudParticulars studentP) throws IOException
    {
    	boolean success = false;
    	
    	if (studentP.getName().isEmpty()|| studentP.getMatricNum().isEmpty()||studentP.getGender()==0 ||studentP.getNationality().isEmpty()) {
    		System.out.print("Insufficient student information input.");
    	}
    	
    	for(List student:readAllStudents()) {
    		if(studentP.getName().equals(student.get(0))) {
    			System.out.println("The student has alreay registered!");
    			return success;}
    	}
    	
    	ArrayList studList = (ArrayList)txtReaderWriter.readtxt("Student Information.txt");
    		
    	CourseMgr cMgr = new CourseMgr();
    	StringBuilder builder = new StringBuilder();
        builder.append(studentP.getName());
        builder.append(cMgr.SEPARATOR);
        builder.append(studentP.getMatricNum());
        builder.append(cMgr.SEPARATOR);
        builder.append(String.valueOf(studentP.getGender()));
        builder.append(cMgr.SEPARATOR);
        builder.append(studentP.getNationality());

        studList.add(builder.toString());
        txtReaderWriter.writetxt("Student Information.txt", studList);

        success = true;
        return success;
    }
        
    public void updStudParticulars() throws IOException {
    	
    	List newStudInfo = new ArrayList<String>();
    	String updStudent;
    	StudParticulars newStudent;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("\nWhich student's information would you like to update? Please enter the name:");
        
    	String name = sc.nextLine();
    	while(!name.matches("^[a-zA-Z]*$"))
    	{
    		System.out.println("Invalid input! Please enter again!");
    		System.out.println("Please enter the student's name:");
        	name = sc.nextLine();        	
    	}

    	name = name.toUpperCase();
    	System.out.println(name);
    	
    	int index = -1;
    	for(List student:readAllStudents()) {
    		if(name.equals(student.get(0))) {
    			index = readAllStudents().indexOf(student);
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
    		System.out.println("Which information would you like to update?");
    		System.out.println("1.Name   2.Matric Number   3.Gender   4.Nationality 5.Quit");
    		int choice;
    		do{
    			choice = sc.nextInt();
        		switch(choice) {
        		case 1:
        		{
        			sc.nextLine();
        			System.out.println("Please enter the student's new name:");
        	    	String newName = sc.nextLine();
        	    	while(!newName.matches("^[a-zA-Z]*$"))
        	    	{
        	    		System.out.println("Invalid input! Please enter again!");
        	    		System.out.println("Please enter the student's new name:");
        	    		newName = sc.nextLine();        	
        	    	}
        	    	newName = newName.toUpperCase();
        	    	newStudent.setName(newName);
        	    	System.out.println(newName);
        	    	break;
        		}
        		case 2:
        		{
        			sc.nextLine();
        			System.out.println("Please input new matric number:");
        			String matricNum = sc.nextLine();
        			while(!matricNum.matches("[a-zA-Z]\\d{7}[a-zA-Z]"))
        	    	{
        	    		System.out.println("Invalid input! Please enter again!");
        	    		System.out.println("Please input new matric number:");
        	    		matricNum = sc.nextLine();        	
        	    	}
        			matricNum = matricNum.toUpperCase();
        			newStudent.setMatricNum(matricNum);
        			break;
        		}
        		case 3:
        		{
        			System.out.println("Please enter new gender, M or F?");
        			char gender = sc.next().charAt(0);
        			
        			while((Character.toUpperCase(gender)!='M')&&(Character.toUpperCase(gender)!='F'))
        	    	{
        	    		System.out.println("Invalid input! Please enter again!");
        	    		System.out.println("Please enter new gender, M or F?");
        	        	gender = sc.next().charAt(0); 
     
        	    	}
        			sc.nextLine();
        			gender = Character.toUpperCase(gender);
        			newStudent.setGender(gender);
        			break;
        		}
        		case 4:
        		{
        			sc.nextLine();
        			System.out.println("Please enter the new nationality :");
        	    	String nationality = sc.nextLine();
        	    	while(!nationality.matches("^[a-zA-Z]*$"))
        	    	{
        	    		System.out.println("Invalid input! Please enter again!");
        	    		System.out.println("Please enter the new nationality: ");
        	    		nationality = sc.nextLine();        	
        	    	}
        	    	
        	    	nationality = nationality.toUpperCase();
        	    	newStudent.setNationality(nationality);
        	    	break;
        		}
        		case 5:
        		{
        			System.out.println("Terminating...");
        		}
        		sc.nextLine();
        		}
        		
        		System.out.println("Would you like to continue update?");
        		System.out.println("1.Name   2.Matric Number   3.Gender   4.Nationality 5.Quit");
    		}while(choice<5);
    		
    		System.out.println(name);

    		System.out.println("Updating information to DB......");
    		boolean status = writeStudParUpdate(name,newStudent);
            if(status) {
            	System.out.println("Updated student inforamtion successfully.");
            }
    	
    	}
    	
    }
    
    private boolean writeStudParUpdate(String nameToUpdate,StudParticulars newStudentInfo) throws IOException
    {
    	boolean success = false;
    	
    	File fileToUpdate = new File("Student Information.txt");
    	BufferedReader reader = null;
    	FileWriter writer = null;
    	CourseMgr cMgr = new CourseMgr();
  
        String oldFile = "";
        String oldLine = "";
        
        StringBuilder builder = new StringBuilder();
        builder.append(newStudentInfo.getName());
        builder.append(cMgr.SEPARATOR);
        builder.append(newStudentInfo.getMatricNum());
        builder.append(cMgr.SEPARATOR);
        builder.append(Character.toString(newStudentInfo.getGender()));
        builder.append(cMgr.SEPARATOR);
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

    private ArrayList<List> readAllStudents() throws IOException{
    	CourseMgr cMgr = new CourseMgr();
    	ArrayList<List> studentList = new ArrayList<List>();
        try (BufferedReader br = new BufferedReader(new FileReader("Student Information.txt")))
        {
            String student;

            while ((student = br.readLine()) != null) {
            	studentList.add(new ArrayList<String>(Arrays.asList(student.split(cMgr.SEPARATOR))));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 

        return studentList;
    }
    
    public void printAllStudent() throws IOException {
    	readAllStudents().stream().forEach((student)->{
    		System.out.print("Name:"+student.get(0)+"  ");
    		System.out.print("Matric Number:"+student.get(1)+"  ");
    		System.out.print("Gender:"+student.get(2)+"  ");
    		System.out.println("Nationality:"+student.get(3));
    	});
    }
    
    private ArrayList<List> readAllStudCouRegInfo() throws IOException{
    	CourseMgr cMgr = new CourseMgr();
    	ArrayList<List> stRegList = new ArrayList<List>();
        try (BufferedReader br = new BufferedReader(new FileReader("Student Registration Information.txt")))
        {
            String student;

            while ((student = br.readLine()) != null) {
            	stRegList.add(new ArrayList<String>(Arrays.asList(student.split(cMgr.SEPARATOR))));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 

        return stRegList;
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
