package com.google.sps.data;

public class Comment{
    private long id;
    private String name;
    private String comment;
    private String imageUpload;

    public Comment(long id, String name, String comment, String imageUpload){
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.imageUpload = imageUpload;
    }
}
