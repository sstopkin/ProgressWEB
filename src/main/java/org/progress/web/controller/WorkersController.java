package org.progress.web.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import org.hibernate.Session;
import org.progress.web.dao.DaoFactory;
import org.progress.web.exceptions.BadRequestException;
import org.progress.web.exceptions.CustomException;
import org.progress.web.logic.Workers;
import org.progress.web.util.ParamName;
import org.progress.web.util.SHA1;

@Singleton
public class WorkersController {

    public void updateWorker(Session session, Workers worker) throws SQLException {
        DaoFactory.getWorkersDao().updateWorker(session, worker);
    }

    //do not add token check, it`s guest api
    public List<Workers> getAllActiveWorkers(Session session) throws CustomException, SQLException {
        List<Workers> workers = DaoFactory.getWorkersDao().getAllWorkersOrderedById(session, true);
        List list = new ArrayList();
        for (Workers ws : workers) {
            List ll = new ArrayList();
            ll.add(ws.getId());
            ll.add(ws.getfName());
            ll.add(ws.getmName());
            ll.add(ws.getlName());
            list.add(ll);
        }
        return list;
    }

    public Workers getWorkerById(Session session, String id) throws CustomException, SQLException {
        if (id == null) {
            throw new BadRequestException();
        }
        return DaoFactory.getWorkersDao().getWorkerById(session, Integer.valueOf(id));
    }

    public List getWorkerObjectsById(Session session, String id) throws CustomException, SQLException {
        if (id == null) {
            throw new BadRequestException();
        }
        return DaoFactory.getWorkersDao().getWorkerObjectsById(session, Integer.valueOf(id));
    }

    public int addWorker(Session session, Map<String, String> map) throws CustomException, SQLException {
        String email = map.get(ParamName.WORKER_EMAIL);
        String workerFName = map.get(ParamName.WORKER_FIRST_NAME);
        String workerMName = map.get(ParamName.WORKER_MIDDLE_NAME);
        String workerLName = map.get(ParamName.WORKER_LAST_NAME);
        String workerPassword = null;
        try {
            workerPassword = SHA1.sha1(map.get(ParamName.WORKER_PASSWORD));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(WorkersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DaoFactory.getWorkersDao().addWorker(session, email, workerFName, workerMName, workerLName, workerPassword);
    }
}
