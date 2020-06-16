package com.google.sps.data;

public class Login{
    private final boolean loggedIn;
    private final String loginUrl;
    private final String logoutUrl;

    public Login(boolean loggedIn, String loginUrl, String logoutUrl){
        this.loggedIn = loggedIn;
        this.loginUrl = loginUrl;
        this.logoutUrl = logoutUrl;
    }
}
