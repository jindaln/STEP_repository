package com.google.sps.data;

public class Comment{
    private final long id;
    private final String name;
    private final String comment;
    private final String imageUrl;
    private final String email;

    public Comment(long id, String name, String comment, String imageUrl, String email){
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.imageUrl = imageUrl;
        this.email = email;
    }
}
