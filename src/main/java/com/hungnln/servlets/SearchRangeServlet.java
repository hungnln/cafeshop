/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;


import com.hungnln.daos.DrinkDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author SE140018
 */
public class SearchRangeServlet extends HttpServlet {

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
    private final String ERROR = "ERROR.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=INDEX;
        try {
            DrinkDAO drinkDAO = new DrinkDAO();
            Float min = -1.0f, max = -1.0f;
            String msg = "";
            String minStr = request.getParameter("minsearch");
            if (minStr.trim().equals("") == false) {
                try {
                    min = Float.parseFloat(request.getParameter("minsearch"));
                    if (min < 0.0f) {
                        msg = msg + "Min >0 ";
                    } else {
                        request.setAttribute("min", min);
                    }
                } catch (Exception e) {
                    msg = msg + "Nhập lại giá thấp nhất";
                }
            }
            String maxStr = request.getParameter("maxsearch");
            if (maxStr.trim().equals("") == false) {
                try {
                    max = Float.parseFloat(request.getParameter("maxsearch"));
                    if (max < 0.0f | max < min) {
                        msg = msg + "Nhập lại giá thấp nhất và giá cao nhất ";
                    } else {
                        request.setAttribute("max", max);
                    }
                } catch (Exception e) {
                    msg = msg + " Nhập lại giá cao nhất";
                }
            }

            if (msg.equals("")) {
                request.setAttribute("drinks", drinkDAO.findAll());
                request.setAttribute("msgs", "Đã tìm thấy");
            } else {
                request.setAttribute("drinks", drinkDAO.findAll());
                request.setAttribute("msgf", msg);

            }
        } catch (Exception e) {
            log("SearchRangeServlet_Exception: " + e.getMessage());
            url=ERROR;
        }finally {
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
