package org.progress.web.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.progress.web.logic.Apartaments;
import org.progress.web.logic.Constants;
import org.progress.web.logic.DbFields;

public class ApartamentsDao {

    public int addApartament(final Session session, int typeOfSales, String cityName,
            String streetName, String houseNumber, String buildingNumber, String kladrId, String shortAddress,
            String apartamentLan, String apartamentLon, int rooms, int dwellingType,
            int price, int cityDistrict, int floor, int floors, int roomNumber, int material,
            BigDecimal sizeApartament, BigDecimal sizeLiving, BigDecimal sizeKitchen, int balcony,
            int loggia, int yearOfConstruction, String description,
            boolean MethodOfPurchase_PureSale, boolean MethodOfPurchase_Mortgage,
            boolean MethodOfPurchase_Exchange, boolean MethodOfPurchase_Rent,
            boolean rePplanning, int idWorkerTarget, int idCustomer, int status, int isAD, boolean IsApproved) throws SQLException {
        return (int) session.save(new Apartaments(typeOfSales, cityName, streetName,
                houseNumber, buildingNumber, kladrId, shortAddress, apartamentLan, apartamentLon,
                rooms, dwellingType, price, cityDistrict, floor,
                floors, roomNumber, material, sizeApartament, sizeLiving, sizeKitchen, balcony,
                loggia, yearOfConstruction, description, MethodOfPurchase_PureSale,
                MethodOfPurchase_Mortgage, MethodOfPurchase_Exchange, MethodOfPurchase_Rent,
                rePplanning, 1, idWorkerTarget, idCustomer, IsApproved, status, isAD));
    }

    public boolean setApproveApartamentById(final Session session, final int apartamentId, final boolean flag) throws SQLException {
        Apartaments apartament = getApartamentsById(session, apartamentId);
        apartament.setIsApproved(flag);
        session.update(apartament);
        return true;
    }

    public boolean removeApartamentById(final Session session, final int apartamentId) throws SQLException {
        Apartaments apartament = getApartamentsById(session, apartamentId);
        apartament.setDeleted(true);
        apartament.setLastModify(new Date());
        session.update(apartament);
        return true;
    }

    public boolean modifyApartament(final Session session, final Apartaments apartament) throws SQLException {
        apartament.setLastModify(new Date());
        session.update(apartament);
        return true;
    }

    public Apartaments getApartamentsById(final Session session, final int apartamentsId) throws SQLException {
        return (Apartaments) session.get(Apartaments.class, apartamentsId);
    }

    public Apartaments getApartamentsByUUID(final Session session, final String apartamentUUID) throws SQLException {
        List<Apartaments> list = session.createCriteria(Apartaments.class).add(Restrictions.eq(DbFields.APARTAMENTS.APARTAMENTUUID, apartamentUUID)).list();
        return list.get(0);
    }

    public List<Apartaments> getAllApartamentsFew(Session session, int status) throws SQLException {
        //FIXME
        Criteria cr = session.createCriteria(Apartaments.class);
        cr.add(Restrictions.eq(DbFields.APARTAMENTS.STATUS, status));
        cr.add(Restrictions.eq(DbFields.APARTAMENTS.DELETED, false));
        cr.addOrder(Order.asc(DbFields.APARTAMENTS.ROOMS));
        return cr.list();
    }

    public List<Apartaments> getAllApartamentsFull(Session session, int status) throws SQLException {
        Criteria cr = session.createCriteria(Apartaments.class);
        cr.add(Restrictions.eq(DbFields.APARTAMENTS.STATUS, status));
        cr.add(Restrictions.eq(DbFields.APARTAMENTS.DELETED, false));
        cr.addOrder(Order.asc(DbFields.APARTAMENTS.ROOMS));
        return cr.list();
    }

    public List searchByQUery(Session session, int assigned, String startDate, String endDate, String contains, String type) throws SQLException {
        List resList = new ArrayList();

//        if (assigned != -1) {
//            criteria.add(Restrictions.like(DbFields.APARTAMENTS.ASSIGNED, assigned));
//        }
//        if (idWorker != -1) {
//            criteria.add(Restrictions.like(DbFields.APARTAMENTS.IDWORKER, idWorker));
//        }
//        if ((status != -1) && (status != 0)) {
//            criteria.add(Restrictions.like(DbFields.CUSTOMERSRENT.STATUS, status));
//        }
        if (!contains.equals("")) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT Apartaments.* FROM Comments ")
                    .append("LEFT JOIN Apartaments on Comments.objectUUID=Apartaments.apartamentUUID ")
                    .append("where MATCH(text) AGAINST ('")
                    .append(contains)
                    .append("' IN NATURAL LANGUAGE MODE)");
            if (type.equals("apartamentsprepare")) {
                sql.append(" and Apartaments.status=\"0\"");
            } else {
                sql.append(" and Apartaments.status<>\"0\"");
            }
            sql.append(";");
            SQLQuery query = session.createSQLQuery(sql.toString());
            query.addEntity(Apartaments.class);
            List lApartaments = query.list();
            return lApartaments;
        } else {
            Criteria criteria = session.createCriteria(Apartaments.class);
            if (type.equals("apartamentsprepare")) {
                criteria.add(Restrictions.eq(DbFields.APARTAMENTS.STATUS, 0));
            } else {
                criteria.add(Restrictions.not(Restrictions.eq(DbFields.APARTAMENTS.STATUS, 0)));
            }
            criteria.add(Restrictions.eq(DbFields.APARTAMENTS.DELETED, false));
            resList = criteria.list();
        }
//        if ((startDate != null) && (endDate != null) && (startDate.equals(endDate))) {
//            criteria.add(Restrictions.sqlRestriction(DbFields.COMMENTS.CREATIONDATE + " >= CURDATE()"));
//        } else {
//            if (startDate != null) {
//                criteria.add(Restrictions.sqlRestriction(DbFields.COMMENTS.CREATIONDATE + " >= '" + startDate + "'"));
//            }
//            if (endDate != null) {
//                criteria.add(Restrictions.sqlRestriction(DbFields.COMMENTS.CREATIONDATE + " <= '" + endDate + "'"));
//            }
//        }

        //.add(Restrictions.between("weight", minWeight, maxWeight))
        return resList;
    }
}
