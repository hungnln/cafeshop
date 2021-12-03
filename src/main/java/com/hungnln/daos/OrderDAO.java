/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.daos;


import com.hungnln.dtos.OrderDTO;
import com.hungnln.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author SE140018
 */
public class OrderDAO {
    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();
    private List<OrderDTO> orders;

    public OrderDAO() {
        try {
            this.orders = getAllOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    public List<OrderDTO> findAll() {
        return this.orders;
    }

    public List<OrderDTO> getAllOrderByUserID(String userID) throws Exception {
        List<OrderDTO> list = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from OrderDTO o where o.HD=:userID";
                Query query = session.createQuery(hql);
                query.setParameter("userID", userID);
                list = query.getResultList();
                session.getTransaction().commit();

            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<OrderDTO> getAllOrder() {
        List<OrderDTO> list = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from OrderDTO";
                Query query = session.createQuery(hql);
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

    public OrderDTO findOrder(String HD) {
        OrderDTO orderDTO = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = " from OrderDTO o where o.HD =:HD";
                Query query = session.createQuery(hql);
                query.setParameter("HD", HD);
                orderDTO = (OrderDTO) query.getSingleResult();
                session.getTransaction().commit();
                return orderDTO;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return null;
    }

    public boolean insertOrUpdateOrder(OrderDTO orderDTO) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                session.saveOrUpdate(orderDTO);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return false;
    }

}
