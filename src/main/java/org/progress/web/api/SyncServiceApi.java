package org.progress.web.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import static org.progress.web.api.ApiHelper.ser;
import org.progress.web.controller.SyncServiceController;
import org.progress.web.exceptions.CustomException;
import org.progress.web.util.Command;
import org.progress.web.util.TransactionService;

/**
 *
 * @author best Jun 15, 2015
 */
public class SyncServiceApi {

    @EJB
    SyncServiceController syncServiceController;

    @POST
    @Path("setapartamentsdb")
    public Response setAllApartamentsFull(@FormParam("key") final String key) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    String result = gson.toJson(syncServiceController.setAllApartaments(session));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(ApartamentsAPI.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @POST
    @Path("setnewsdb")
    public Response setAllNewsFull(@FormParam("key") final String key) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    String result = gson.toJson(syncServiceController.setAllNews(session));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(ApartamentsAPI.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @POST
    @Path("setworkersdb")
    public Response setAllWorkersFull(@FormParam("key") final String key) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    String result = gson.toJson(syncServiceController.setAllWorkers(session));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(ApartamentsAPI.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @POST
    @Path("sync")
    public Response setAllFull(@FormParam("key") final String key) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    String result = gson.toJson(syncServiceController.setAll(session));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(ApartamentsAPI.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }
}
