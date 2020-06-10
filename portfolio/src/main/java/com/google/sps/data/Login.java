package com.google.sps.data;

public class Login{
    private boolean loggedIn;
    private String loginUrl;
    private String logoutUrl;

    public Login(boolean loggedIn, String loginUrl, String logoutUrl){
        this.loggedIn = loggedIn;
        this.loginUrl = loginUrl;
        this.logoutUrl = logoutUrl;
    }
}
