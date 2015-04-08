package com.example.qbit.projectredapp;

/**
 * Created by qbit on 19/03/15.
 */
public class ThreadClass {
    String title;
    String username;
    String score;
    String category;
    public ThreadClass(){
        this.title=title;
        this.username=username;
        this.score=score;
        this.category=category;
    }
    public String getScore(){return score;}
    public String getCategory(){return category;}
    public String getTitle(){
        return title;
    }
    public String getUsername(){
        return username;
    }
    public void setTitle(String urlTitle){
        this.title=urlTitle;
    }
    public void setUsername(String urlUsername){
        this.username=urlUsername;
    }
    public void setScore(String Score){this.score=Score; }
    public void setCategory(String Category){this.category=Category;}
}
