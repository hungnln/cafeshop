/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.daos;


import com.hungnln.dtos.OrderDetailDTO;
import com.hungnln.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author SE140018
 */
public class OrderDetailDAO {
    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();

    public OrderDetailDAO() {
    }

    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    public boolean insertOrUpdateOrderDetail(OrderDetailDTO orderDetailDTO) {

        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                session.saveOrUpdate(orderDetailDTO);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public List<OrderDetailDTO> getOrderDetail(String HD) {
        List<OrderDetailDTO> list = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from OrderDetailDTO o where o.HD=:HD";
                Query query = session.createQuery(hql);
                query.setParameter("HD", HD);
                list = query.list();
                session.getTransaction().commit();
            }

        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return list;
    }
}
