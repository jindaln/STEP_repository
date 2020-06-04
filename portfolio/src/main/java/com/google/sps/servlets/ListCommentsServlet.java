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
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@WebServlet("/list_comments")
public class ListCommentsServlet extends HttpServlet{

    private static final String NAME = "name";
    private static final String COMMENT = "comment";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Translate translate = TranslateOptions.getDefaultInstance().getService();
        String lang = request.getParameter("language");
        Query query = new Query("Comment");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> results = datastore.prepare(query).
            asList(FetchOptions.Builder.withLimit(
            Integer.parseInt(request.getParameter("max_comments"))));
    
        List<Comment> comments = new ArrayList<>();
        for (Entity entity : results) {
            long id = entity.getKey().getId();

            String name = (String) entity.getProperty(NAME);
            Translation translation_name =
            translate.translate(name, Translate.TranslateOption.targetLanguage(lang));
            name = translation_name.getTranslatedText();
            System.out.println(name);

            String comment = (String) entity.getProperty(COMMENT);
            Translation translation_comment =
            translate.translate(comment, Translate.TranslateOption.targetLanguage(lang));
            comment = translation_comment.getTranslatedText();
            System.out.println(comment);

            Comment newComment = new Comment(id, name, comment);
            comments.add(newComment);
        }
        Gson gson = new Gson();
        String json = gson.toJson(comments);
        response.setContentType("application/json;");
        response.getWriter().println(json);
    }
}
