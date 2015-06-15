package org.progress.web.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import static org.progress.web.api.ApiHelper.ser;
import org.progress.web.controller.NewsController;
import org.progress.web.exceptions.CustomException;
import org.progress.web.util.Command;
import org.progress.web.util.ParamName;
import org.progress.web.util.TransactionService;

@Stateless
@Path("news")
public class NewsApi {

    @EJB
    NewsController newsController;

    @GET
    public Response news() throws SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson newsList = new GsonBuilder().registerTypeAdapter(Date.class, ser)
                            .create();
                    String newsJson = newsList.toJson(newsController
                            .getNews(session));
                    return ApiHelper.getResponse(newsJson);
                } catch (CustomException ex) {
                    Logger.getLogger(NewsApi.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @GET
    @Path("getnews")
    public Response getNewsById(@QueryParam("id") final String newsId) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson newsById = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    Map<String, String> map = new HashMap<>();
                    map.put(ParamName.NEWS_ID, newsId);
                    String result = newsById.toJson(newsController.getNewsById(session, map));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(ApartamentsAPI.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }
}
