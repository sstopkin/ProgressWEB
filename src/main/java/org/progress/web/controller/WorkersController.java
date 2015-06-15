package org.progress.web.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import org.hibernate.Session;
import org.progress.web.dao.DaoFactory;
import org.progress.web.exceptions.BadRequestException;
import org.progress.web.exceptions.CustomException;
import org.progress.web.exceptions.IncorrectPasswordException;
import org.progress.web.exceptions.IsNotAuthenticatedException;
import org.progress.web.logic.Constants;
import org.progress.web.logic.Workers;
import org.progress.web.util.Pair;
import org.progress.web.util.ParamName;
import org.progress.web.util.ParamUtil;
import org.progress.web.util.SHA1;

@Singleton
public class WorkersController {

    @EJB
    AuthenticationManager authManager;

    public Workers getProfileInfo(Session session, String token) throws SQLException, CustomException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        int workerId = authManager.getUserIdByToken(UUID.fromString(token));
        Pair permission = AclController.getAclCheckAccess(session, Constants.ENTITIES.WORKERS, workerId, Constants.ACL.ACCESS_VIEW);
        UUID uuid = UUID.fromString(token);
        if (authManager.isAuthentificated(uuid)) {
            Workers pr = DaoFactory.getWorkersDao().getWorkerById(session, workerId);
            if (pr == null) {
                throw new IsNotAuthenticatedException();
            }
            return pr;
        }
        throw new IsNotAuthenticatedException();
    }

    public void changePwd(Session session, String token, String oldPwd, String newPwd)
            throws NoSuchAlgorithmException, SQLException, CustomException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        if (oldPwd == null || newPwd == null) {
            throw new BadRequestException();
        }

        int userId = authManager.getUserIdByToken(UUID.fromString(token));
        Workers pr = DaoFactory.getWorkersDao().getWorkerById(session, userId);

        String oldUserPwdHash = SHA1.sha1(oldPwd);
        if (!(pr.getPwdhash().equals(oldUserPwdHash))) {
            throw new IncorrectPasswordException();
        }

        pr.setPwdhash(SHA1.sha1(newPwd));
        updateWorker(session, pr);
    }

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

    //with token, it`s admin API
    public List<Workers> getAllWorkersToAdmin(Session session, String token) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        int workerId = authManager.getUserIdByToken(UUID.fromString(token));
        //FIXME
//        Pair permission = AclController.getAclCheckAccess(session, Constants.ENTITIES.WORKERS, workerId, Constants.ACL.ACCESS_VIEW);
        List<Workers> workers = DaoFactory.getWorkersDao().getAllWorkersOrderedById(session, false);
        List list = new ArrayList();
        for (Workers ws : workers) {
            if (ws.getId() != 1) {
                List ll = new ArrayList();
                ll.add(ws.getId());
                ll.add(ws.getEmail());
                ll.add(ws.getlName());
                ll.add(ws.getfName());
                ll.add(ws.getmName());
                ll.add(ws.isDeleted());
                ll.add(ws.getIsActive());
                list.add(ll);
            }
        }
        return list;
    }

    public void setActivityUserById(Session session, String token, Map<String, String> map)
            throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        int workerId = authManager.getUserIdByToken(UUID.fromString(token));
        Pair permission = AclController.getAclCheckAccess(session, Constants.ENTITIES.NEWS, workerId, Constants.ACL.ACCESS_VIEW);
        boolean flag = ParamUtil.getBoolean(map, ParamName.STATUS);
        int userId = ParamUtil.getNotEmptyInt(map, ParamName.WORKER_ID_TARGET);
        Workers pr = DaoFactory.getWorkersDao().getWorkerById(session, userId);
        pr.setIsActive(flag);
        updateWorker(session, pr);
        if (!flag) {
            UUID userToken = authManager.getUserTokenById(userId);
            if (userToken == null) {
                return;
            }
            authManager.logOut(userToken.toString());
        }
    }

    public Workers getWorkerById(Session session, String token, String id) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        if (id == null) {
            throw new BadRequestException();
        }
        return DaoFactory.getWorkersDao().getWorkerById(session, Integer.valueOf(id));
    }

    public List getWorkerObjectsById(Session session, String token, String id) throws CustomException, SQLException {
        if (id == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        return DaoFactory.getWorkersDao().getWorkerObjectsById(session, Integer.valueOf(id));
    }

    public int addWorker(Session session, String token, Map<String, String> map) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
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
