/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.daos;

import com.hungnln.dtos.DrinkDTO;
import com.hungnln.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SE140018
 */
public class DrinkDAO {

    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();
    private List<DrinkDTO> drinks;
    private List<DrinkDTO> searchList;

    public DrinkDAO() {
        try {
            this.drinks = getAllDrinks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<DrinkDTO> getSearchList() {
        return searchList;
    }

    private void closeConnection() {
        if (session != null) {
            session.close();
        }
    }

    public List<DrinkDTO> findAll() {
        return this.drinks;
    }

    public void searchDrink(String x) {
        for (DrinkDTO drinkDTO : this.drinks) {
            if (drinkDTO.getId().equalsIgnoreCase(x) || drinkDTO.getName().contains(x)) {
                if (searchList == null) {
                    searchList = new ArrayList<>();
                }
                searchList.add(drinkDTO);
            }
        }
    }

    public DrinkDTO findDrink(String drinkId) {
        for (DrinkDTO drinkDTO : this.drinks) {
            if (drinkDTO.getId().equalsIgnoreCase(drinkId)) {
                return drinkDTO;
            }
        }
        for (DrinkDTO drinkDTO : this.drinks) {
            if (drinkDTO.getName().contains(drinkId)) {
                return drinkDTO;
            }
        }
        return null;
    }

    public boolean checkID(String id) {
        boolean check = true;
        for (DrinkDTO drinkDTO : this.drinks) {
            if (drinkDTO.getId().equals(id)) {
                check = false;
            }
        }
        return check;
    }

    public List<DrinkDTO> getAllDrinks() {
        List<DrinkDTO> list = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from DrinkDTO";
                Query query = session.createQuery(hql);
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

    public boolean insertOrUpdateDrink(DrinkDTO drinkDTO) {
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                session.saveOrUpdate(drinkDTO);
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
