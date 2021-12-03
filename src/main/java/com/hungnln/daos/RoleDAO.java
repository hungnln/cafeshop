package com.hungnln.daos;

import com.hungnln.dtos.RoleDTO;
import com.hungnln.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class RoleDAO {
    protected Session session;
    SessionFactory factory = HibernateUtils.getSessionFactory();
    private List<RoleDTO> roles;

    public RoleDAO() {
        try {
            this.roles = getAllRoles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<RoleDTO> getAllRoles() {
        List<RoleDTO> list = null;
        try {
            session = factory.getCurrentSession();
            if (session != null) {
                session.getTransaction().begin();
                String hql = "from RoleDTO";
                Query query = session.createQuery(hql);
                list = query.list();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return list;
    }

    public RoleDTO getRoleByRoleId(int roleId) {
        RoleDAO roleDAO = new RoleDAO();
        for (RoleDTO roleDTO : roleDAO.roles) {
            if (roleDTO.getRoleId() == roleId)
                return roleDTO;
        }
        return null;
    }
}
