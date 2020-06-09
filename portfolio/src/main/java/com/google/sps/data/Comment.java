package com.google.sps.data;

public class Comment{
    private long id;
    private String name;
    private String comment;
    private String imageUrl;

    public Comment(long id, String name, String comment, String imageUrl){
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.imageUrl = imageUrl;
    }
}
