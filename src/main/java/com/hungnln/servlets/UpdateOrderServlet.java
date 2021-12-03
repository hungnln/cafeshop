/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;

import com.hungnln.daos.OrderDAO;
import com.hungnln.dtos.OrderDTO;
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
public class UpdateOrderServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("userdata");
        if (user != null) {
            if (user.getRole().getRoleId() == 1 || user.getRole().getRoleId() == 0) {
                try {
                    String HD = request.getParameter("HD");
                    int status = Integer.parseInt(request.getParameter("status"));
                    OrderDAO orderDAO = new OrderDAO();
                    OrderDTO orderDTO = orderDAO.findOrder(HD);
                    orderDTO.setStatus(status);
                    boolean check = orderDAO.insertOrUpdateOrder(orderDTO);
                    if (check) {
                        if (user.getRole().getRoleId() == 1) {
                            request.setAttribute("HD", HD);
                            request.setAttribute("status", orderDTO.getStatus());
                            request.getRequestDispatcher("OrderDetailServlet").forward(request, response);
                        } else {
                            response.sendRedirect("OrderUserServlet");
                        }
                    } else {
                        request.setAttribute("HD", HD);
                        request.setAttribute("status", status);
                        request.setAttribute("msgf", "Xảy ra lỗi");
                        request.getRequestDispatcher("OrderDetailServlet").forward(request, response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                response.sendRedirect("LoadDrinkServlet");
            }
        } else {
            response.sendRedirect("LoadDrinkServlet");
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
    }
}
// </editor-fold>

