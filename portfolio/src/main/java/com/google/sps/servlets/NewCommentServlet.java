package com.google.sps.servlets;

import com.google.sps.data.Comment;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/new_comment")
public class NewCommentServlet extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String comment = request.getParameter("comment");

        Entity taskEntity = new Entity("Comment");
        taskEntity.setProperty("name", name);
        taskEntity.setProperty("comment", comment);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(taskEntity);

        try {
            response.sendRedirect("/index.html");
        } catch (IOException e) {
            System.out.println("Could not redirect");
        }
    }
}
