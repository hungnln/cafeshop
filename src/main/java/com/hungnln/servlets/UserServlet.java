/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;

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
public class UserServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect("user.jsp");
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("userdata");
        UserDAO userDAO = new UserDAO();
        String url = "user.jsp";
        try {
            if (user != null) {
                if (user.getRole().getRoleId() == 0) {
                    boolean valid = true;
                    String id = request.getParameter("id");
                    if (id.trim().isEmpty()) {
                        request.setAttribute("msgid", "Không lấy được tài khoản <br>");
                        valid = false;
                    }
                    String name = request.getParameter("name");
                    if (name.trim().isEmpty() || name.length() > 200) {
                        request.setAttribute("msgname", "Vui dùng nhập lại tên người dùng <br>");
                        valid = false;
                    }
                    String phone = request.getParameter("phone");
                    if (phone.trim().isEmpty() || isNumeric(phone) == false) {
                        request.setAttribute("msgphone", "Vui dùng nhập lại số điện thoại <br>");
                        valid = false;

                    }
                    String address = request.getParameter("address");
                    if (address.trim().isEmpty() || address.length() > 200) {
                        request.setAttribute("msgaddress", "Vui dùng nhập lại địa chỉ <br>");
                        valid = false;

                    }
                    if (valid) {
                        UserDTO userDTO = new UserDTO(id, user.getPassword(), name, user.getRole(), phone, address);
                        if (userDAO.createOrUpdateUser(userDTO)) {
                            request.setAttribute("msgs", "Đã chỉnh sửa tài khoản thành công");
                            session.removeAttribute("userdata");
                            session.setAttribute("userdata", userDTO);
                        } else {
                            request.setAttribute("msgf", "Đã xảy ra lỗi");
                        }
                    }
                }
            }
        } catch (Exception e) {
            log("UserServlet_Exception: " + e.getMessage());
            url = ERROR;
        } finally {
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

    private boolean isNumeric(String str) {
        return str.matches("([0])\\d{9}");
    }
}
