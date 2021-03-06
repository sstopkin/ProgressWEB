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
import org.progress.web.controller.ApartamentsController;
import org.progress.web.exceptions.CustomException;
import org.progress.web.util.Command;
import org.progress.web.util.TransactionService;

@Stateless
@Path("apartament")
public class ApartamentsAPI {

    @EJB
    ApartamentsController apartamentsController;

    @GET
    @Path("getapartament")
    public Response getApartamentById(@QueryParam("id") final String id) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson apartamentById = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    String result = apartamentById.toJson(apartamentsController.getApartamentById(session, id));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(ApartamentsAPI.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @GET
    @Path("getallapartament")
    public Response getAllApartaments(@QueryParam("status") final String status) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson allApartament = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    String result = allApartament.toJson(apartamentsController.getAllApartament(session, status));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(ApartamentsAPI.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @GET
    @Path("getallapartamentfull")
    public Response getAllApartamentsFull(@QueryParam("status") final String status) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson allApartament = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();
                    String result = allApartament.toJson(apartamentsController.getAllApartamentFull(session, status));
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(ApartamentsAPI.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

}
