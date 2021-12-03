/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;


import com.hungnln.daos.DrinkDAO;
import com.hungnln.dtos.DrinkDTO;
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
public class DeleteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String INDEX = "index.jsp";
    private final String LOGIN = "login.jsp";
    private final String ERROR = "ERROR.html";
    private final String INDEX_SVLET = "LoadDrinkServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("userdata");
        DrinkDAO drinkDAO = new DrinkDAO();
        String url = LOGIN;
        try {
            if (user != null) {
                if (user.getRole().getRoleId() == 2) {
                    url = INDEX;
                    String drinkId = request.getParameter("mid");
                    DrinkDTO drinkDTO = drinkDAO.findDrink(drinkId);
                    drinkDTO.setStatus(false);
                    if (drinkDAO.insertOrUpdateDrink(drinkDTO)) {
                        request.setAttribute("msgs", "Xóa thành công!");
                        request.setAttribute("drinks", drinkDAO.findAll());
                    } else {
                        request.setAttribute("msgf", "Xảy ra lỗi hãy thử lại");
                        request.setAttribute("drinks", drinkDAO.findAll());
                    }
                } else {
                    url = INDEX_SVLET;
                }
            } else {
                request.setAttribute("msgf", "Vui lòng đăng nhập");
            }
        } catch (Exception e) {
            log("DeleteServlet_Exception: " + e.getMessage());
            url = ERROR;
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
