/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hungnln.servlets;

import com.hungnln.daos.DrinkDAO;
import com.hungnln.daos.OrderDAO;
import com.hungnln.dtos.DrinkDTO;
import com.hungnln.dtos.UserDTO;
import com.hungnln.utils.UploadFileUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

import java.io.*;

/**
 * @author SE140018
 */
@MultipartConfig()
public class CreateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String CREATE = "create.jsp";
    private final String INDEX = "index.jsp";
    private final String LOGIN = "login.jsp";
    private final String ERROR = "ERROR.html";

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
        HttpSession session = request.getSession();
        DrinkDAO drinkDAO = new DrinkDAO();
        OrderDAO orderDAO = new OrderDAO();
        UserDTO user = (UserDTO) session.getAttribute("userdata");
        String url = LOGIN;
        try {
            if (user != null) {
                if (user.getRole().getRoleId() == 2) {
                    url = CREATE;
                } else if (user.getRole().getRoleId() == 1) {
                    request.setAttribute("msgf", "Chỉ tài khoản ADMIN được phép truy cập");
                    request.setAttribute("Orders", orderDAO.findAll());
                    url = INDEX;

                } else if (user.getRole().getRoleId() == 0) {
                    request.setAttribute("msgf", "Chỉ tài khoản ADMIN được phép truy cập");
                    request.setAttribute("drinks", drinkDAO.findAll());
                    url = INDEX;

                }
            } else {
                request.setAttribute("msgf", "Vui lòng đăng nhập");
                url = LOGIN;
            }
        } catch (Exception e) {
            url = ERROR;
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

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
        Boolean valid = true;
        String msg_id = "", msg_Description = "", msg_price = "", msg_name = "", msg_quantity = "", msg_url = "";
        String url = INDEX;
        try {
            DrinkDAO drinkDAO = new DrinkDAO();
            UserDTO user = (UserDTO) session.getAttribute("userdata");
            if (user != null) {
                if (user.getRole().getRoleId() == 2) {
                    url = CREATE;
                    String id = request.getParameter("txtID");
                    if (drinkDAO.checkID(id)) {
                        if (id.trim().isEmpty() || id.trim().length() > 10) {
                            msg_id = "Vui lòng nhập mã sản phẩm. "
                                    + "Độ dài là 10 kí tự";
                            valid = false;
                        }
                    } else {
                        msg_id = "Mã sản phẩm đã tồn tại";
                        valid = false;
                    }

                    String description = request.getParameter("txtDescription");
                    if (description.trim().isEmpty() || description.trim().length() > 200) {
                        msg_Description = "Vui lòng nhập mô tả. "
                                + "Độ dài là 200 kí tự";
                        valid = false;
                    }
                    long price = (long) -1.0;
                    try {
                        price = Long.parseLong(request.getParameter("txtPrice"));
                        if (price < 0.0f) {
                            msg_price = "Vui lòng nhập giá sản phẩm. "
                                    + "Giá sản phẩm lớn hơn 0";
                            valid = false;
                        }
                    } catch (Exception e) {
                        msg_price = "Vui lòng nhập giá sản phẩm. "
                                + "Giá sản phẩm lớn hơn 0";
                        valid = false;
                    }
                    String name = request.getParameter("txtName");
                    if (name.trim().isEmpty() || name.trim().length() > 50) {
                        msg_name = "Vui lòng nhập tên sản phẩm. "
                                + "Độ dài là 50 kí tự";
                        valid = false;
                    }
                    int quantity = -1;
                    try {
                        quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                        if (quantity < 0) {
                            msg_quantity = "Vui lòng nhập số lượng. "
                                    + "Số lượng lớn hơn 0";
                            valid = false;
                        }
                    } catch (Exception e) {
                        msg_quantity = "Vui lòng nhập số lượng. "
                                + "Số lượng lớn hơn 0";
                        valid = false;
                    }
                    String imageurl = null;
                            UploadFileUtils.uploadFile(request);
                    if (imageurl.trim().isEmpty()) {
                        msg_url = "Vui lòng thêm hình ảnh ";
                        valid = false;
                    }
                    if (valid) {
                        DrinkDTO drinkDTO = new DrinkDTO(id, imageurl, name, description, price, quantity, true);
                        boolean kq = drinkDAO.insertOrUpdateDrink(drinkDTO);
                        if (kq == true) {
                            DrinkDAO drinkDAO2 = new DrinkDAO();
                            request.setAttribute("msgs", "Thêm sản phẩm thành công");
                            url = CREATE;
                            request.setAttribute("drinks", drinkDAO2.findAll());
                        } else {
                            url = CREATE;
                            request.setAttribute("msgf", "Lỗi khi thêm sản phẩm");
                            request.setAttribute("drinks", drinkDAO.findAll());
                        }
                    } else {
                        url = CREATE;
                        request.setAttribute("msg_id", msg_id);
                        request.setAttribute("msg_description", msg_Description);
                        request.setAttribute("msg_price", msg_price);
                        request.setAttribute("msg_name", msg_name);
                        request.setAttribute("msg_quantity", msg_quantity);
                        request.setAttribute("msg_url", msg_url);
                    }


                } else if (user.getRole().getRoleId() == 1) {
                    OrderDAO orderDAO = new OrderDAO();
                    request.setAttribute("Orders", orderDAO.findAll());
                } else if (user.getRole().getRoleId() == 0) {
                    request.setAttribute("drinks", drinkDAO.findAll());
                }

            } else {
                request.setAttribute("drinks", drinkDAO.findAll());
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            log("CreateServlet_Exception: " + e.getMessage());
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

}
