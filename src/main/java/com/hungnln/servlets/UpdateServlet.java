/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;


import com.hungnln.daos.DrinkDAO;
import com.hungnln.dtos.DrinkDTO;
import com.hungnln.dtos.UserDTO;
import com.hungnln.utils.UploadFileUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author SE140018
 */

public class UpdateServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect(INDEX);
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
        DrinkDAO drinkDAO = new DrinkDAO();
        if (user != null) {
            if (user.getRole().getRoleId() == 2) {
                String msg_checkString = "";
                String url = request.getParameter("url");
                if (url.trim().isEmpty()) {
                    msg_checkString = msg_checkString + "Không lấy được đường dẫn url <br>";
                } else {
                    String url_new = null;
                    try {
                        url_new = UploadFileUtils.uploadFile(request);
                    } catch (URISyntaxException e) {
                        msg_checkString=msg_checkString+"Không lưu được ảnh mới <br>";
                    }
                    if (url_new.trim().isEmpty() == false) {
                        url = url_new;
                    }
                }

                String id = request.getParameter("id");
                if (id.trim().isEmpty()) {
                    msg_checkString = msg_checkString + "Không lấy được mã sản phẩm <br>";
                }
                String description = request.getParameter("description");
                if (description.trim().isEmpty() || description.trim().length() > 200) {
                    msg_checkString = msg_checkString + "Không lấy được mô tả sản phẩm <br>";
                }
                long price = -1l;
                try {
                    price = Long.parseLong(request.getParameter("price"));
                    if (price < 0.0f) {
                        msg_checkString = msg_checkString + "Không lấy được đơn giá sản phẩm <br>";
                    }
                } catch (Exception e) {
                    msg_checkString = msg_checkString + "Vui lòng nhập giá sản phẩm >=0 <br>";
                }
                String name = request.getParameter("name");
                if (name.trim().isEmpty() || name.trim().length() > 50) {
                    msg_checkString = msg_checkString + "Không lấy được tên sản phẩm <br>";
                }

                int quantity = -1;
                try {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                    if (quantity < 0) {
                        msg_checkString = msg_checkString + "Không lấy được số lượng sản phẩm <br>";
                    }
                } catch (Exception e) {
                    msg_checkString = msg_checkString + "Vui lòng nhập giá sản phẩm >=0 <br>";
                }

                if (msg_checkString.equals("")) {
                    try {
                        DrinkDTO drinkDTO = new DrinkDTO(id, url, name, description, price, quantity, true);
                        boolean check = drinkDAO.insertOrUpdateDrink(drinkDTO);
                        if (check) {
                            request.setAttribute("msgs", "Cập nhật thành công");
                            DrinkDAO drinkDAO2 = new DrinkDAO();
                            request.setAttribute("drinks", drinkDAO2.findAll());
                            request.getRequestDispatcher(INDEX).forward(request, response);
                        } else {
                            request.setAttribute("msgf", "Cập nhật thất bại");
                            request.setAttribute("drinks", drinkDAO.findAll());
                            request.getRequestDispatcher(INDEX).forward(request, response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        response.sendRedirect(ERROR);

                    }

                } else {
                    request.setAttribute("msgf", "Xãy ra lỗi:<br> " + msg_checkString);
                    request.setAttribute("drinks", drinkDAO.findAll());
                    request.getRequestDispatcher(INDEX).forward(request, response);
                }
            } else {
                response.sendRedirect("LoadDrinkServlet");
            }
        } else {
            request.setAttribute("errormessage", "Please login with ADMIN account");
            request.getRequestDispatcher(LOGIN).forward(request, response);
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
