package org.progress.web.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.progress.web.logic.Constants;
import org.progress.web.logic.DbFields;
import org.progress.web.logic.News;

public class NewsDao {

    public List<News> getNews(Session session) throws SQLException {
        Criteria cr = session.createCriteria(News.class);
        cr.add(Restrictions.eq(DbFields.NEWS.DELETED, false));
        cr.addOrder(Order.desc(DbFields.NEWS.LASTMODIFY));
        return cr.list();
    }

    public void addNews(final Session session, final String header, final String text) throws SQLException {
        session.save(new News(1, header, text));
    }

    public News getNewsById(final Session session, final int newsId) throws SQLException {
        return (News) session.get(News.class, newsId);
    }

    public void editNewsById(final Session session, final int newsId, final String header, final String text) throws SQLException {
        News news = getNewsById(session, newsId);
        news.setHeader(header);
        news.setIdWorker(1);
        news.setLastModify(new Date());
        news.setText(text);
        session.update(news);
    }

    public void removeNewsById(Session session, int newsId) throws SQLException {
        News news = DaoFactory.getNewsDao().getNewsById(session, newsId);
        news.setLastModify(new Date());
        news.setDeleted(true);
        session.update(news);
    }
}
