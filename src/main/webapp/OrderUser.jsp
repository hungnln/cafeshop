<%-- 
    Document   : OrderUser
    Created on : Jul 9, 2021, 5:04:53 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Quản lí đơn hàng</title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <style type="text/css">
        .col-center-block {
            float: none;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }

        html, body, .container {
            height: 100%;
            width: 100%;
        }

        .container {
            display: table;
            vertical-align: middle;
        }

        .vertical-center-row {
            display: table-cell;
            vertical-align: middle;

        }

        footer {
            clear: both;
            height: 120px;
            align-items: center;
            text-align: center
        }

        p1 {
            color: orangered;
        }

        p2 {
            color: red;
        }

        p3 {
            color: blue;
        }

        p4 {
            color: greenyellow;
        }


    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center"> Quản lí đơn hàng</h1><br>
    <h3 class="text-center">Danh sách các đơn hàng của bạn</h3><br><br>
    <div class="row">
        <div class="col-sm-3">
        </div>
        <div class="col-md-6">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Hóa Đơn</th>
                    <th scope="col">Thời gian đặt</th>
                    <th scope="col">Thành tiền</th>
                    <th scope="col">Trạng thái đơn hàng</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty requestScope.Orders}">
                    <c:set var="listOrder" value="${requestScope.Orders}"></c:set>
                    <c:forEach var="Order" items="${listOrder}">
                        <tr>
                            <th scope="row">${Order.HD}</th>
                            <td>${Order.date}</td>
                            <td>${Order.total}</td>
                            <c:choose>
                                <c:when test="${Order.status eq 0}">
                                    <td>
                                        <p1>Chờ xác nhận</p1>
                                    </td>
                                </c:when>
                                <c:when test="${Order.status eq 1}">
                                    <td>
                                        <p2>Đã xác nhận</p2>
                                    </td>
                                </c:when>
                                <c:when test="${Order.status eq 2}">
                                    <td>
                                        <p3>Đang giao</p3>
                                    </td>
                                </c:when>
                                <c:when test="${Order.status eq 3}">
                                    <td>
                                        <p4>Đã Giao</p4>
                                    </td>
                                </c:when>
                                <c:when test="${Order.status eq 4}">
                                    <td>
                                        <p4>Đã hoàn thành</p4>
                                    </td>
                                </c:when>
                            </c:choose>
                            <td><a href="OrderDetailServlet?HD=${Order.HD}&status=${Order.status}">Xem chi tiết</a></td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>
        <div class="col-sm-3">
        </div>
    </div>
    <footer>
        <div class="text-center"><a href="LoadDrinkServlet">Xem danh sách sản phẩm</a> | <a href="CartServlet">Xem giỏ
            hàng</a> | <a href="UserServlet">Chỉnh sửa thông tin</a> | <a href="LogoutServlet">Đăng xuất</a>
        </div>
    </footer>
</div>
</body>
</html>
