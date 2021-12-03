/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;

import com.hungnln.daos.OrderDAO;
import com.hungnln.daos.OrderDetailDAO;
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
public class OrderDetailServlet extends HttpServlet {

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
    private final String DETAIL = "detail.jsp";
    private final String INDEX = "LoadDrinkServlet";
    private final String ERROR = "ERROR.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("userdata");
            if (user != null) {
//                if (user.getRole().getRoleId() == 1) {
//                    url = DETAIL;
//                    String HD = request.getParameter("HD");
//                    int status = Integer.parseInt(request.getParameter("status"));
//                    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//                    OrderDAO orderDAO = new OrderDAO();
//                    OrderDTO o = orderDAO.findOrder(HD);
//                    UserDTO userDTO = o.getUserDTO();
//                    request.setAttribute("Detail", orderDetailDAO.getOrderDetail(o.getHD()));
//                    request.setAttribute("user", userDTO);
//                    request.setAttribute("status", status);
//                    request.setAttribute("HD", HD);
//                } else
                    if (user.getRole().getRoleId() == 2) {
                    url = INDEX;
                    request.setAttribute("msgf", "Không thể truy cập");
                } else {
                    url = DETAIL;
                    String HD = request.getParameter("HD");
                    int status = Integer.parseInt(request.getParameter("status"));
                    OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                    OrderDAO orderDAO = new OrderDAO();
                    OrderDTO o = orderDAO.findOrder(HD);
                    UserDTO userDTO = o.getUserDTO();
                    request.setAttribute("Detail", orderDetailDAO.getOrderDetail(o.getHD()));
                    request.setAttribute("user", userDTO);
                    request.setAttribute("status", status);
                    request.setAttribute("HD", HD);
                }
            } else {
                url = LOGIN_ERROR;
                request.setAttribute("msg", "Vui lòng đăng nhập");
            }
        } catch (Exception e) {
            log("OrderDetailServlet_Exception: " + e.getMessage());
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
