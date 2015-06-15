package org.progress.web.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.Date;
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
import org.progress.web.controller.WorkersController;
import org.progress.web.exceptions.CustomException;
import org.progress.web.util.Command;
import org.progress.web.util.TransactionService;

@Stateless
@Path("workers")
public class WorkersApi {

    @EJB
    WorkersController workersController;

    @GET
    @Path("getallworkers")
    public Response getAllWorkers() throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson apartamentById = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Date.class, ser).create();
                    String result = apartamentById.toJson(workersController.getAllActiveWorkers(session));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(WorkersApi.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @GET
    @Path("getworker")
    public Response getWorkerById(@QueryParam("id") final String id) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson apartamentById = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    String result = apartamentById.toJson(workersController.getWorkerById(session, id));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(WorkersApi.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @GET
    @Path("getworkerobjects")
    public Response getWorkerObjectsById(@QueryParam("id") final String id) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson apartamentById = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    String result = apartamentById.toJson(workersController.getWorkerObjectsById(session, id));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(WorkersApi.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }
}
