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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/new_comment")
public class NewCommentServlet extends HttpServlet{
    private static final Logger logger = LogManager.getLogger("Errors");
    private static final String NAME = "name";
    private static final String COMMENT = "comment";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter(NAME);
        String comment = request.getParameter(COMMENT);

        Entity taskEntity = new Entity("Comment");
        taskEntity.setProperty("name", name);
        taskEntity.setProperty("comment", comment);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(taskEntity);

        try {
            response.sendRedirect("/index.html");
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
