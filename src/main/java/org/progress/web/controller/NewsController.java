package org.progress.web.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.mail.MessagingException;
import org.hibernate.Session;
import org.progress.web.dao.DaoFactory;
import org.progress.web.exceptions.BadRequestException;
import org.progress.web.exceptions.CustomException;
import org.progress.web.exceptions.IsNotAuthenticatedException;
import org.progress.web.logic.Constants;
import org.progress.web.logic.News;
import org.progress.web.util.Pair;
import org.progress.web.util.ParamName;
import org.progress.web.util.ParamUtil;

@Singleton
public class NewsController {

    @EJB
    AuthenticationManager authManager;

    public List<News> getNews(Session session) throws SQLException, CustomException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        int idWorker = authManager.getUserIdByToken(UUID.fromString(token));
        Pair<Integer, Integer> permission = AclController.getAclCheckAccess(session, Constants.ENTITIES.NEWS, idWorker, Constants.ACL.ACCESS_VIEW);
        return DaoFactory.getNewsDao().getNews(session, permission.getSecond(), idWorker);
    }

    public boolean addNews(Session session, String text, String header) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        int idWorker = authManager.getUserIdByToken(UUID.fromString(token));
        Pair permission = AclController.getAclCheckAccess(session, Constants.ENTITIES.NEWS, idWorker, Constants.ACL.ACCESS_ADD_EIDT);
        DaoFactory.getNewsDao().addNews(session, idWorker, text, header);
        try {
            JavaMail.sendMail(SettingsController.parameters.get("admin.email"), text, header);
        } catch (MessagingException ex) {
            Logger.getLogger(NewsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean deleteNewsById(Session session, String id) throws CustomException, SQLException {
        if (id == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        int idWorker = authManager.getUserIdByToken(UUID.fromString(token));
        Pair permission = AclController.getAclCheckAccess(session, Constants.ENTITIES.NEWS, idWorker, Constants.ACL.ACCESS_DELETE);
        DaoFactory.getNewsDao().removeNewsById(session, idWorker, Integer.valueOf(id));
        return true;
    }

    public boolean editNewsById(Session session, Map<String, String> map) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        int newsId = ParamUtil.getInt(map, ParamName.NEWS_ID);
        String text = map.get(ParamName.NEWS_TEXT);
        String header = map.get(ParamName.NEWS_HEADER);
        int idWorker = authManager.getUserIdByToken(UUID.fromString(token));
        Pair permission = AclController.getAclCheckAccess(session, Constants.ENTITIES.NEWS, idWorker, Constants.ACL.ACCESS_ADD_EIDT);
        DaoFactory.getNewsDao().editNewsById(session, newsId, idWorker, header, text);
        return true;
    }

    public News getNewsById(Session session, Map<String, String> param) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        int idWorker = authManager.getUserIdByToken(UUID.fromString(token));
        Pair permission = AclController.getAclCheckAccess(session, Constants.ENTITIES.NEWS, idWorker, Constants.ACL.ACCESS_VIEW);
        return DaoFactory.getNewsDao().getNewsById(session, ParamUtil.getNotEmptyInt(param, ParamName.NEWS_ID));
    }
}
