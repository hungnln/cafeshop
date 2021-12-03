/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;


import com.hungnln.daos.DrinkDAO;
import com.hungnln.daos.OrderDAO;
import com.hungnln.daos.UserDAO;
import com.hungnln.dtos.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author SE140018
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String LOGIN_ERROR = "login.jsp";
    private final String LOGIN_SUCCESS = "LoadDrinkServlet";
    private final String ERROR = "ERROR.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_ERROR;
        try {
            HttpSession session = request.getSession(false);
            UserDTO user = (UserDTO) session.getAttribute("userdata");
            if (user != null) {
                url = LOGIN_SUCCESS;
                if (user.getRole().getRoleId() == 1) {
                    OrderDAO orderDAO = new OrderDAO();
                    request.setAttribute("Orders", orderDAO.findAll());

                } else if (user.getRole().getRoleId() == 2) {
                    DrinkDAO drinkDAO = new DrinkDAO();
                    request.setAttribute("drinks", drinkDAO.findAll());


                } else if (user.getRole().getRoleId() == 0) {
                    DrinkDAO drinkDAO = new DrinkDAO();
                    request.setAttribute("drinks", drinkDAO.findAll());

                }
            }
        } catch (Exception e) {
            log("LoginServlet_Exception: " + e.getMessage());
            url = ERROR;
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_ERROR;
        try {
            HttpSession session = request.getSession(false);
            String u = request.getParameter("name");
            String p = request.getParameter("password");
            if (u.length() <= 10) {
                UserDAO dao = new UserDAO();
                UserDTO user = dao.checkLogin(u, p);
                if (user != null) {
                    url = LOGIN_SUCCESS;
                    session.setAttribute("userdata", user);
                } else {
                    request.setAttribute("msgf", "Sai tài khoản hoặc mật khẩu");
                }
            } else {
                request.setAttribute("msgf", "Nhập lại tài khoản");
            }
        } catch (Exception e) {
            log("LoginServlet_Exception: " + e.getMessage());
            url=ERROR;
        }finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
