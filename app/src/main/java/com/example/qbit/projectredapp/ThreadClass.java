package com.example.qbit.projectredapp;


public class ThreadClass {
    String title;
    String username;
    String score;
    String category;
    String threadID;
    String link;
    String type;
    public ThreadClass(){
        this.title=title;
        this.username=username;
        this.score=score;
        this.category=category;
        this.threadID=threadID;
        this.link=link;
        this.type=type;
    }
    public String getScore(){return score;}
    public String getCategory(){return category;}
    public String getTitle(){
        return title;
    }
    public String getUsername(){
        return username;
    }
    public String getThreadID(){return threadID;}
    public String getLink(){return link;}
    public String getType(){return type;}
    public void setTitle(String urlTitle){
        this.title=urlTitle;
    }
    public void setUsername(String urlUsername){
        this.username=urlUsername;
    }
    public void setScore(String Score){this.score=Score; }
    public void setCategory(String Category){this.category=Category;}
    public void setThreadID(String ThreadID){this.threadID=ThreadID;}
    public void setLink(String Link){this.link=Link;}
    public void setType(String Type){this.type=Type;}
}
