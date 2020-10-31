package com.ntustars.entity;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class AccessPeriod {
	    private Date startDateTime;
	    private Date endDateTime;
	    public SimpleDateFormat formatter;
	    private ArrayList<String> accessPeriod;

	    public AccessPeriod(Date startDateTime, Date endDateTime){
	        this.formatter = new SimpleDateFormat("yyyy MM dd HH mm");
	        this.startDateTime = startDateTime;
	        this.endDateTime = endDateTime;	        
	        this.accessPeriod = new ArrayList<String>();
	    }

	    public void setStartAccessDate(Date startDateTime){
	        this.startDateTime = startDateTime;
	    }

	    public String getStartAccessDate(){
	        return this.formatter.format(this.startDateTime);
	    }

	    public void setEndAccessDate(Date endDateTime){
	        this.endDateTime = endDateTime;
	    }

	    public String getEndAccessDate(){
	        return this.formatter.format(this.endDateTime);
	    }
	    
	    public ArrayList<String> getAccessPeriod() {
		this.accessPeriod.clear();
	    	this.accessPeriod.add(this.getStartAccessDate());
	    	this.accessPeriod.add(this.getEndAccessDate());
	    	return this.accessPeriod;
	    }
	 
}
