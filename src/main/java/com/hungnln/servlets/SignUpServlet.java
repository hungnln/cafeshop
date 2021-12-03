/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;

import com.hungnln.daos.RoleDAO;
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
public class SignUpServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String SUCCESS = "login.jsp";
    private final String FAIL = "signup.jsp";
    private final String ERROR = "ERROR.html";
    private final String INDEX = "LoadDrinkServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        UserDTO user = (UserDTO) session.getAttribute("userdata");
        UserDAO userDAO = new UserDAO();
        boolean valid = true;
        String url = FAIL;
        try {
            if (user == null) {
                String userId = request.getParameter("id");
                if (userId.trim().isEmpty()) {
                    request.setAttribute("msgid", "Vui lòng nhập tài khoản");
                    valid = false;
                } else {
                    if (userDAO.findUser(userId) != null) {
                        request.setAttribute("msgid", "Tài khoản đã bị trùng");
                        valid = false;
                    }
                    if (userId.length() > 10) {
                        request.setAttribute("msgid", "Độ dài tài khoản là 10");
                        valid = false;
                    }
                }
                String password = request.getParameter("password");
                String repassword = request.getParameter("repassword");
                if (password.trim().isEmpty() || repassword.trim().isEmpty()) {
                    request.setAttribute("msgpassword", "Vui lòng nhập mật khẩu");
                    valid = false;
                } else {
                    if (password.equals(repassword) == false) {
                        request.setAttribute("msgpassword", "Vui lòng nhập đúng mật khẩu");
                        valid = false;
                    }
                    if (password.length() > 50) {
                        request.setAttribute("msgpassword", "Độ dài mật khẩu là 50");
                        valid = false;
                    }
                }
                String fullName = request.getParameter("name");
                if (fullName.trim().isEmpty()) {
                    request.setAttribute("msgname", "Vui lòng nhập tên người dùng");
                    valid = false;
                } else {
                    if (fullName.length() > 200) {
                        request.setAttribute("msgname", "Độ dài tài khoản là 10");
                        valid = false;
                    }
                }
                String phoneNumber = request.getParameter("phone");
                if (phoneNumber.trim().isEmpty()) {
                    request.setAttribute("msgname", "Vui lòng nhập số điện thoại");
                    valid = false;
                } else {
                    if (phoneNumber.length() > 10 || isNumeric(phoneNumber) == false) {
                        request.setAttribute("msgname", "Sai định dạng số điện thoại");
                        valid = false;
                    }
                }
                String address = request.getParameter("address");
                if (address.trim().isEmpty()) {
                    request.setAttribute("msgname", "Vui lòng nhập địa chỉ");
                    valid = false;
                } else {
                    if (address.length() > 200) {
                        request.setAttribute("msgname", "Độ dài địa chỉ là 200");
                        valid = false;
                    }
                }
                if (valid) {
                    RoleDAO roleDAO = new RoleDAO();
                    UserDTO userDTO = new UserDTO(userId,password,fullName,roleDAO.getRoleByRoleId(1),phoneNumber, address);
                    if (userDAO.createOrUpdateUser(userDTO)) {
                        url = SUCCESS;
                        request.setAttribute("msgsuccess", "Đã tạo tài khoản thành công");
                    } else {
                        request.setAttribute("msgerror", "Đã xảy ra lỗi");
                    }
                }
            } else {
                url = INDEX;
            }
        } catch (Exception e) {
            log("SignUpServlet_Exception: " + e.getMessage());
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


    private boolean isNumeric(String str) {
        return str.matches("([0])\\d{9}");
    }
}
