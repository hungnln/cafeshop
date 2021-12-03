/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;


import com.hungnln.daos.DrinkDAO;
import com.hungnln.dtos.ShoppingCartItem;
import com.hungnln.dtos.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SE140018
 */
public class CartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String CART = "cart.jsp";
    private final String CART_SVL = "CartServlet";

    public CartServlet() {
        super();
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CartServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("userdata");
        if (user != null) {
            if (user.getRole().getRoleId() == 0) {
                if (action == null) {
                    displayCart(request, response);
                } else if (action.equalsIgnoreCase("choose")) {
                    buyItem(request, response);
                } else if (action.equalsIgnoreCase("remove")) {
                    removeItem(request, response);
                } else if (action.equalsIgnoreCase("empty")) {
                    emptyCart(request, response);
                }
            } else {
                request.getRequestDispatcher("LoadDrinkServlet");
            }
        } else {
            if (action == null) {
                displayCart(request, response);
            } else if (action.equalsIgnoreCase("choose")) {
                buyItem(request, response);
            } else if (action.equalsIgnoreCase("remove")) {
                removeItem(request, response);
            } else if (action.equalsIgnoreCase("empty")) {
                emptyCart(request, response);
            }
        }
    }

    protected void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(CART).forward(request, response);
    }

    protected void removeItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<ShoppingCartItem> cart = (List<ShoppingCartItem>) session.getAttribute("cart");
        int index = isExisting(request.getParameter("id"), cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        response.sendRedirect(CART_SVL);
    }

    protected void emptyCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("cart");
        response.sendRedirect(CART_SVL);
    }

    protected void buyItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DrinkDAO drinkDAO = new DrinkDAO();
        HttpSession session = request.getSession();
        String description = (String) request.getParameter("description");

        if (session.getAttribute("cart") == null) {
            List<ShoppingCartItem> cart = new ArrayList<ShoppingCartItem>();
            cart.add(new ShoppingCartItem(drinkDAO.findDrink(request.getParameter("id")), 1, description));
            session.setAttribute("cart", cart);
        } else {
            List<ShoppingCartItem> cart = (List<ShoppingCartItem>) session.getAttribute("cart");
            int index = isExisting(request.getParameter("id"), cart);
            if (index == -1) {
                cart.add(new ShoppingCartItem(drinkDAO.findDrink(request.getParameter("id")), 1, description));

            } else {
                int quantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(quantity);
            }
            session.setAttribute("cart", cart);
        }
        response.sendRedirect(CART_SVL);
    }

    private int isExisting(String id, List<ShoppingCartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getDrink().getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
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
