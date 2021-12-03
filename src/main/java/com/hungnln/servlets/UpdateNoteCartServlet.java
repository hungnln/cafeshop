/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;

import com.hungnln.daos.DrinkDAO;
import com.hungnln.daos.OrderDAO;
import com.hungnln.dtos.ShoppingCartItem;
import com.hungnln.dtos.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * @author SE140018
 */
public class UpdateNoteCartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String ERROR = "ERROR.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("userdata");
        try {
            if (user != null) {
                if (user.getRole().getRoleId() == 0) {
                    String description = (String) request.getParameter("txtDescription");
                    if (description.trim().isEmpty() == false) {
                        String id = (String) request.getParameter("txtid");
                        List<ShoppingCartItem> cart = (List<ShoppingCartItem>) session.getAttribute("cart");
                        for (ShoppingCartItem shoppingCartItem : cart) {
                            if (shoppingCartItem.getDrink().getId().equals(id)) {
                                shoppingCartItem.setDescription(description);
                            }
                        }
                        session.setAttribute("cart", cart);
                        response.sendRedirect("CartServlet");
                    } else {
                        request.setAttribute("msgf", "Nhập lại ghi chú sản phẩm");
                        request.getRequestDispatcher("CartServlet").forward(request, response);
                    }
                } else if (user.getRole().getRoleId() == 1) {
                    OrderDAO orderDAO = new OrderDAO();
                    request.setAttribute("Orders", orderDAO.findAll());
                } else if (user.getRole().getRoleId() == 2) {
                    DrinkDAO drinkDAO = new DrinkDAO();
                    request.setAttribute("drinks", drinkDAO.findAll());
                }
            } else {
                String description = (String) request.getParameter("txtDescription");
                if (description.trim().isEmpty() == false) {
                    String id = (String) request.getParameter("txtid");
                    List<ShoppingCartItem> cart = (List<ShoppingCartItem>) session.getAttribute("cart");
                    for (ShoppingCartItem shoppingCartItem : cart) {
                        if (shoppingCartItem.getDrink().getId().equals(id)) {
                            shoppingCartItem.setDescription(description);
                        }
                    }
                    session.setAttribute("cart", cart);
                    request.setAttribute("msgs", "Cập nhật ghi chú thành công");
                    request.getRequestDispatcher("CartServlet").forward(request, response);
                } else {
                    request.setAttribute("msgf", "Nhập lại ghi chú sản phẩm");
                    request.getRequestDispatcher("CartServlet").forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(ERROR);
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
