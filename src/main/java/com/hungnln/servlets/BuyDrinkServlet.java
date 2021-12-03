/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;


import com.hungnln.daos.DrinkDAO;
import com.hungnln.daos.OrderDAO;
import com.hungnln.daos.OrderDetailDAO;
import com.hungnln.dtos.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author SE140018
 */
public class BuyDrinkServlet extends HttpServlet {

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
        List<ShoppingCartItem> cart = (List<ShoppingCartItem>) session.getAttribute("cart");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String strDate = dateFormat.format(date);
        UserDTO user = (UserDTO) session.getAttribute("userdata");
        if (cart == null) {
            response.sendRedirect("LoadDrinkServlet");
        } else {
            if (user != null) {
                if (user.getRole().getRoleId() == 0) {
                    long total = 0l;
                    for (ShoppingCartItem shoppingCartItem : cart) {
                        total = total + shoppingCartItem.getDrink().getPrice() * shoppingCartItem.getQuantity();
                    }
                    String HD = "HD_" + strDate + user.getId();
                    OrderDTO orderDTO = new OrderDTO(HD, strDate, total, user, 1);
                    OrderDAO orderDAO = new OrderDAO();
                    if (orderDAO.insertOrUpdateOrder(orderDTO)) {
                        for (ShoppingCartItem shoppingCartItem : cart) {
                            try {
                                DrinkDTO drinkDTO = shoppingCartItem.getDrink();
                                int quantity_old = drinkDTO.getQuantity();
                                if (quantity_old - shoppingCartItem.getQuantity() >= 0) {
                                    drinkDTO.setQuantity(quantity_old - shoppingCartItem.getQuantity());
                                    DrinkDAO drinkDAO = new DrinkDAO();
                                    boolean check = drinkDAO.insertOrUpdateDrink(drinkDTO);
                                    if (check) {
                                        request.setAttribute("msg", "Đặt thành công");
                                        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                                        OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderDTO, drinkDTO, shoppingCartItem.getQuantity(), shoppingCartItem.getDescription());
                                        orderDetailDAO.insertOrUpdateOrderDetail(orderDetailDTO);
                                        emptyCart(request, response);
                                    } else {
                                        drinkDTO.setQuantity(quantity_old);
                                        drinkDAO.insertOrUpdateDrink(drinkDTO);
                                        request.setAttribute("msgerror", "Đã xảy ra lõi");
                                        session.setAttribute("cart", cart);
                                        response.sendRedirect("CartServlet");
                                    }
                                } else {
                                    request.setAttribute("msgerror", "Số lượng đặt mua lớn hơn sảm phẩm còn lại");
                                    session.setAttribute("cart", cart);
                                    response.sendRedirect("CartServlet");
                                }

                            } catch (Exception e) {
                                log("BuyDrinkServlet_Exception: " + e.getMessage());
                            }
                        }
                    }
                } else if (user.getRole().getRoleId() == 1) {
                    OrderDAO orderDAO = new OrderDAO();
                    request.setAttribute("Orders", orderDAO.findAll());
                } else if (user.getRole().getRoleId() == 2) {
                    DrinkDAO drinkDAO = new DrinkDAO();
                    request.setAttribute("drinks", drinkDAO.findAll());
                }

            } else {
                request.setAttribute("msgf", "Vui lòng đăng nhập");
                request.getRequestDispatcher("LoginServlet").forward(request, response);
            }
        }
    }

    protected void emptyCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("cart");
        response.sendRedirect("CartServlet");
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
