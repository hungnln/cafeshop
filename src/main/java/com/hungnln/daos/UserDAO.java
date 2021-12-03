/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.daos;


import com.hungnln.dtos.UserDTO;
import com.hungnln.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author SE140018
 */
public class UserDAO {
    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();
    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }
    public List<UserDTO> getAll(){
        List<UserDTO> list =null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from UserDTO";
                Query query = session.createQuery(hql);
                list = query.getResultList();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }

    public UserDTO checkLogin(String userId, String password) {
        UserDTO user = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from UserDTO u where u.id=:userId and u.password=:password";
                Query query = session.createQuery(hql);
                query.setParameter("userId", userId);
                query.setParameter("password", password);
                user = (UserDTO) query.getSingleResult();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return user;
    }

    public UserDTO findUser(String userId) {
        UserDTO user = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from UserDTO u where u.id=:userId";
                Query query = session.createQuery(hql);
                query.setParameter("userId", userId);
                user = (UserDTO) query.getSingleResult();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            closeConnection();
        }
        return user;
    }

    public boolean createOrUpdateUser(UserDTO userDTO) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                session.saveOrUpdate(userDTO);
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
