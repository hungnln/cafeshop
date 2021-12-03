<%-- 
    Document   : Orderdetail
    Created on : Jul 7, 2021, 11:27:46 AM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <title>Quản lý đơn hàng</title>
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

        .login-form {
            margin: auto;
        }

        .img {
            text-align: center;
            border: 1px solid green;
            padding: 5px;
            margin: 5px;
            height: 250px;
            width: 200px;
            float: left;
        }

        footer {
            clear: both;
            height: 120px;
            align-items: center;
            text-align: center
        }

    </style>
</head>
<c:choose>
    <c:when test="${sessionScope.userdata.role eq 1}">
        <body>
        <div class="container">
            <h1 class="text-center">Chi tiết đơn hàng </h1>
            <br><br>
            <div class="row">
                <div class="col-md-8">
                    <c:set var="hd" value="${requestScope.hd}">
                    </c:set>
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Sản phẩm</th>
                            <th scope="col">Số lượng</th>
                            <th scope="col">Ghi chú</th>
                            <th scope="col">Đơn giá</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${not empty requestScope.Detail}">
                            <c:set var="listDetail" value="${requestScope.Detail}"></c:set>
                            <c:forEach var="detail" items="${listDetail}">
                                <tr>
                                    <td>${detail.drinkDTO.name}</td>
                                    <td>${detail.quantity}</td>
                                    <td>${detail.description}</td>
                                    <td>${detail.quantity*detail.drinkDTO.price}</td>
                                    <td></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                    <div class="text-center">
                        <c:choose>
                            <c:when test="${requestScope.status eq 0}">
                                <button><a href="UpdateOrderServlet?HD=${requestScope.HD}&status=1">Xác nhận đơn
                                    hàng</a></button>
                            </c:when>
                            <c:when test="${requestScope.status eq 1}">
                                <button><a href="UpdateOrderServlet?HD=${requestScope.HD}&status=2">Giao đơn</a>
                                </button>
                            </c:when>
                            <c:when test="${requestScope.status eq 2}">
                                <button><a href="UpdateOrderServlet?HD=${requestScope.HD}&status=3">Đã giao</a></button>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
                <div class="col-md-4">
                    <h1 class="text-center">Thông tin khách hàng</h1>
                    <br>
                    <h4> Tên khách hàng : ${user.fullName} <br/></h4>
                    <br/>
                    <h4> Số điện thoại : ${user.phone} <br/></h4>
                    <br/>
                    <h4> Địa chỉ liên hệ : ${user.address} <br/></h4>
                </div>
            </div>
            <footer>
                <div class="text-center"><a href="LoadDrinkServlet">Trang chủ</a> | <a href="LogoutServlet">Đăng
                    xuất</a>
                </div>
            </footer>
        </div>
        </body>
    </c:when>
    <c:when test="${sessionScope.userdata.role eq 0}">
        <body>
        <div class="container">
            <h1 class="text-center">Chi tiết đơn hàng </h1>
            <br><br>
            <div class="row">
                <div class="col-sm-3">
                </div>
                <div class="col-md-6">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Sản phẩm</th>
                            <th scope="col">Số lượng</th>
                            <th scope="col">Ghi chú</th>
                            <th scope="col">Đơn giá</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${not empty requestScope.Detail}">
                            <c:set var="listDetail" value="${requestScope.Detail}"></c:set>
                            <c:forEach var="detail" items="${listDetail}">
                                <tr>
                                    <td>${detail.drinkDTO.name}</td>
                                    <td>${detail.quantity}</td>
                                    <td>${detail.description}</td>
                                    <td>${detail.quantity*detail.drinkDTO.price}</td>
                                    <td></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                    <div class="text-center">

                        <c:if test="${requestScope.status eq 3}">
                            <button><a href="UpdateOrderServlet?HD=${requestScope.HD}&status=4">Đã nhận được hàng</a>
                            </button>
                        </c:if>

                    </div>
                </div>
                <div class="col-md-3">
                </div>
            </div>
            <footer>

                <c:set var="user" value="${sessionScope.userdata}"/>
                Xin chào ${user.fullName} |
                <a href="CartServlet">Xem giỏ hàng</a> | <a href="UserServlet">Chỉnh sửa thông tin</a> | <a
                    href="OrderUserServlet">Xem đơn hàng</a> | <a href="LogoutServlet">Đăng xuất</a>

            </footer>
        </div>

        </body>
    </c:when>
</c:choose>
</html>
