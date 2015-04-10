package com.example.qbit.projectredapp;

/**
 * Created by qbit on 19/03/15.
 */
public class ThreadClass {
    String title;
    String username;
    String score;
    String category;
    String threadID;
    String link;
    public ThreadClass(){
        this.title=title;
        this.username=username;
        this.score=score;
        this.category=category;
        this.threadID=threadID;
        this.link=link;
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
}
