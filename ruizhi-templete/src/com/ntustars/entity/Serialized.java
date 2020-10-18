/* package com.ntustars.entity;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
public class Serialized {
    public static List readSerializedObject(String filename) {
        List pDetails = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            pDetails = (ArrayList) in.readObject();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        // print out the size
        //System.out.println(" Details Size: " + pDetails.size());
        //System.out.println();
        return pDetails;
    }

    public static void writeSerializedObject(String filename, List list) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(list);
            out.close();
            //	System.out.println("Object Persisted");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args) throws  IOException{
        List list;
        //File myObj = new File("course.dat");
        //myObj.createNewFile();
        try	{

            // read from serialized file the list of professors
            list = (ArrayList)Serialized.readSerializedObject("course.dat");
            for (int i = 0 ; i < list.size() ; i++) {
                Course course = (Course) list.get(i);
                System.out.println("course is " + course.getCourseID() );
                System.out.println("type is  " + course.getType() );
            }

            // write to serialized file - update/insert/delete
            // example - add one more professor

            Course c = new Course("CZ2002", Course.CourseType.ONLYLEC);
            CourseInfo info = new CourseInfo(12345);
            CourseCompoment compoment = new CourseCompoment(CourseCompoment.CourseCompomentType.LEC, CourseCompoment.Day.MON, "1500-1600");
            info.addCompoment(compoment);
            c.addInfo(info);
            // add to list
            list.add(c);
            // list.remove(p);  // remove if p equals object in the list

            Serialized.writeSerializedObject("course.dat", list);

        }  catch ( Exception e ) {
            System.out.println( "Exception >> " + e.getMessage() );
        }
    }


}
*/