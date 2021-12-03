<%-- 
    Document   : cart
    Created on : Jun 15, 2021, 11:16:06 AM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Giỏ hàng</title>
    <style>

        header {
            height: 120px;
            align-items: center;
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
        }

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

        .form {
            margin: auto;
        }
    </style>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="text-center"> Quản lí đơn hàng</h1><br>
    <div class="row">
        <div class="col-md-12">
            <c:if test="${not empty requestScope.msgs}">
                <div class="alert alert-success alert text-center" role="alert">
                    <strong>${requestScope.msgs}</strong>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.msgf}">
                <div class="alert alert-warning alert text-center" role="alert">
                    <strong>${requestScope.msgf}</strong>
                </div>
            </c:if>
            <div class="form">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Mã</th>
                        <th scope="col">Tên sản phẩm</th>
                        <th scope="col">Hình ảnh</th>
                        <th scope="col">Giá</th>
                        <th scope="col">Số lượng</th>
                        <th scope="col">Ghi chú</th>
                        <th scope="col">Thành tiền</th>
                        <th scope="col">Tùy chọn</th>
                    </tr>
                    </thead>
                    <tbody>


                    <c:choose>
                    <c:when test="${not empty sessionScope.cart}">
                        <c:set var="total" value="0"></c:set>
                    <c:forEach var="drinklist" items="${sessionScope.cart}">
                        <c:set var="total" value="${pageScope.total+ drinklist.drink.price*drinklist.quantity}"></c:set>
                    <form action="UpdateNoteCartServlet">
                        <tr>
                            <td scope="row">
                                <label>${drinklist.drink.id}</label>
                                <input type="hidden" name="txtid" value="${drinklist.drink.id}" readonly/>
                            </td>
                            <td>${drinklist.drink.name}</td>
                            <td align="center">
                                <img src="images/${drinklist.drink.url}" width="100">
                            </td>
                            <td align="center">${drinklist.drink.price}</td>
                            <td align="center">${drinklist.quantity}</td>
                            <td><input type="text" name="txtDescription" value="${drinklist.description}"/>
                                <input type="submit" value="OK"/>
                            </td>

                            <td align="center">${drinklist.drink.price*drinklist.quantity}</td>
                            <td align="center">
                                <c:url value="CartServlet" var="removeItem">
                                    <c:param name="action" value="remove"></c:param>
                                    <c:param name="id" value="${drinklist.drink.id}"></c:param>
                                </c:url>
                                <a href="${removeItem}" onclick="return confirm('Xóa sản phẩm này?');">Xóa</a>
                            </td>
                        </tr>
                    </form>
                    </c:forEach>
                    </c:when>
                    <c:otherwise>
                    <tr>
                        <th colspan="12"><br/> Giỏ hàng trống!</th>
                    </tr>
                    </c:otherwise>
                    </c:choose>
                    <tr>
                        <td colspan="6" align="right">Tổng cộng</td>
                        <td colspan="2">${pageScope.total}</td>
                    </tr>
            </div>
            </tbody>
            </table>
            <div class="text-center">
                <button><a href="BuyDrinkServlet" onclick="return confirm('Click OK to buy items')">Đặt hàng</a>
                </button>
            </div>
            <br><br><br>
            <div class="text-center">
                <footer>
                    <c:choose>
                        <c:when test="${not empty sessionScope.userdata}">
                            <c:set var="user" value="${sessionScope.userdata}"/>
                            Xin chào ${user.fullName} |
                            <a href="LoadDrinkServlet">Xem danh sách sản phẩm</a> | <a href="CartServlet?action=empty">Xóa
                            giỏ hàng</a> | <a href="OrderUserServlet">Xem đơn hàng</a> | <a href="LogoutServlet">Đăng
                            xuất</a>
                        </c:when>
                        <c:otherwise>
                            Xin chào GUEST |
                            <a href="LoadDrinkServlet">Xem danh sách sản phẩm</a> | <a href="CartServlet?action=empty">Xóa
                            giỏ hàng</a> | <a href="LoginServlet">Đăng nhập</a>
                        </c:otherwise>
                    </c:choose>
                </footer>
            </div>
        </div>

    </div>
</div>
</div>

</body>
</html>
