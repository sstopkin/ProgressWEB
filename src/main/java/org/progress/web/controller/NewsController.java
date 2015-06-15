package org.progress.web.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import org.hibernate.Session;
import org.progress.web.dao.DaoFactory;
import org.progress.web.exceptions.BadRequestException;
import org.progress.web.exceptions.CustomException;
import org.progress.web.logic.News;
import org.progress.web.util.ParamName;
import org.progress.web.util.ParamUtil;

@Singleton
public class NewsController {

    public List<News> getNews(Session session) throws SQLException, CustomException {
        return DaoFactory.getNewsDao().getNews(session);
    }

    public boolean addNews(Session session, String text, String header) throws CustomException, SQLException {
        DaoFactory.getNewsDao().addNews(session, text, header);
        return true;
    }

    public boolean deleteNewsById(Session session, String id) throws CustomException, SQLException {
        if (id == null) {
            throw new BadRequestException();
        }
        DaoFactory.getNewsDao().removeNewsById(session, Integer.valueOf(id));
        return true;
    }

    public boolean editNewsById(Session session, Map<String, String> map) throws CustomException, SQLException {
        int newsId = ParamUtil.getInt(map, ParamName.NEWS_ID);
        String text = map.get(ParamName.NEWS_TEXT);
        String header = map.get(ParamName.NEWS_HEADER);
        DaoFactory.getNewsDao().editNewsById(session, newsId, header, text);
        return true;
    }

    public News getNewsById(Session session, Map<String, String> param) throws CustomException, SQLException {
        return DaoFactory.getNewsDao().getNewsById(session, ParamUtil.getNotEmptyInt(param, ParamName.NEWS_ID));
    }

}
