package com.example.qbit.projectredapp;


public class CommentClass {
    String username;
    String score;
    String comment;
    String commentID;

    public CommentClass(){
        this.username=username;
        this.score=score;
        this.comment=comment;
        this.commentID=commentID;
    }
    public String getUsername(){return username;}
    public String getScore(){return score;}
    public String getComment(){return comment;}
    public String getCommentID(){return commentID;}

    public void setUsername(String Username){ this.username=Username;}
    public void setScore(String Score){this.score=Score;}
    public void setComment(String Comment){this.comment=Comment;}
    public void setCommentID(String CommentID){this.commentID=CommentID;}
}




