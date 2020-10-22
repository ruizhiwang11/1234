package com.ntustars.entity;

import java.util.ArrayList;
import java.util.List;

public class CourseInfo{
    private String index;
    private int slot;
    private  boolean hasVencancies = true;
    private List<CourseCompoment> compoments;
    public CourseInfo(String index){
        this.index = index;
        compoments = new ArrayList<>();
    }
    public CourseInfo(String index, boolean hasVencancies, int slot){
        this.index = index;
        this.hasVencancies = hasVencancies;
        this.slot = slot;
        compoments = new ArrayList<>();
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getSlot() {
        return slot;
    }

    public boolean getHasVencancies() {
        return hasVencancies;
    }

    public void setHasVencancies(boolean hasVencancies) {
        this.hasVencancies = hasVencancies;
    }
    public  void setSlot(int slot) {
        this.slot = slot;
    }
    public List<CourseCompoment> getCompoments() {
        return compoments;
    }
    public void addCompoment(CourseCompoment compoment){
        compoments.add(compoment);
    }
    public void deleteCompoment(int num){
        compoments.remove(num);
    }
    public void updateCompoments(int index, CourseCompoment compoment){
        compoments.set(index, compoment);
    }
    public void printInfo(){
        System.out.println(index + ":" + " HasVencancies:"+ hasVencancies + " slot:" + slot);
        for(int i = 0; i< compoments.size(); i++){
            compoments.get(i).printCompoment();
        }
    }

}
