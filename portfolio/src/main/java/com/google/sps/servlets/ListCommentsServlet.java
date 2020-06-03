package com.google.sps.servlets;

import com.google.sps.data.Comment;
import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;
import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/list_comments")
public class ListCommentsServlet extends HttpServlet{

    private static final String NAME = "name";
    private static final String COMMENT = "comment";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Query query = new Query("Comment");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> results = datastore.prepare(query).
            asList(FetchOptions.Builder.withLimit(
            Integer.parseInt(request.getParameter("max_comments"))));

        List<Comment> comments = new ArrayList<>();
        for (Entity entity : results) {
            long id = entity.getKey().getId();
            String name = (String) entity.getProperty(NAME);
            String comment = (String) entity.getProperty(COMMENT);

            Comment newComment = new Comment(id, name, comment);
            comments.add(newComment);
        }
        Gson gson = new Gson();
        String json = gson.toJson(comments);
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }
}
