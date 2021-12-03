<%-- 
    Document   : index
    Created on : Jun 15, 2021, 11:14:38 AM
    Author     : SE140018
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Trang chủ</title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <style>
        /*.col-center-block {*/
        /*    float: none;*/
        /*    display: block;*/
        /*    margin-left: auto;*/
        /*    margin-right: auto;*/
        /*}*/

        html, body, .container {
            height: 100%;
            width: 100%;
        }

        .container {
            display: table;
            vertical-align: middle;
        }

        /*.vertical-center-row {*/
        /*    display: table-cell;*/
        /*    vertical-align: middle;*/

        /*}*/

        /*.form {*/
        /*    margin: auto;*/
        /*}*/

        .img {
            text-align: center;
            border: 1px solid black;
            padding: 1px;
            margin: 20px;
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
<c:if test="${sessionScope.userdata.role.roleId == '1'}">
    <body>
    <div class="container">
        <h1 class="text-center"> Quản lí đơn hàng</h1><br>
        <h3 class="text-center">Danh sách các đơn hàng đang xử lí</h3><br><br>
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
                        <th scope="col">Người mua</th>
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
                                <td>${Order.userDTO.fullName}</td>
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
                                            <p4>Đã giao chờ xác nhận</p4>
                                        </td>
                                    </c:when>
                                    <c:when test="${Order.status eq 4}">
                                        <td>
                                            <p4>Khách đã nhận hàng</p4>
                                        </td>
                                    </c:when>
                                </c:choose>
                                <td><a href="OrderDetailServlet?HD=${Order.HD}&status=${Order.status}">Xem chi tiết</a>
                                </td>
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
            <div class="text-center"><a href="LogoutServlet">Đăng xuất</a>
            </div>
        </footer>
    </div>
    </body>
</c:if>
<c:if test="${sessionScope.userdata.role.roleId  == '2' }">
    <body>
    <div class="container">
        <h1 class="text-center"> Quản lí sản phẩm</h1>
        <c:if test="${not empty requestScope.msg}">
        <p color="red">${requestScope.msg}</p>
        </c:if>
        <div class="text-center">
            <form action="SearchServlet">

                Search: <input type="text" name="txtSearch" placeholder="Tìm  kiếm bằng mã hoặc tên" size="30"/>
                <input type="submit" value="Search"/>
            </form>
        </div>
        <br><br>
        <c:if test="${not empty requestScope.msgs}">
        <div class="alert alert-success alert text-center" role="alert">
            <h4 class="alert-heading">Thông báo!</h4>
            <strong>${requestScope.msgs}</strong>
        </div>
        </c:if>
        <c:if test="${not empty requestScope.msgf}">
        <div class="alert alert-warning alert text-center" role="alert">
            <h4 class="alert-heading">Thông báo!</h4>
            <strong>${requestScope.msgf}</strong>
        </div>
        </c:if>
        <div class="row">
            <div class="col-md-12">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Mã</th>
                        <th scope="col">Mô tả</th>
                        <th scope="col">Giá thành</th>
                        <th scope="col">Tên</th>
                        <th scope="col">Số lượng</th>
                        <th colspan="2" class="text-center">URL</th>
                        <th colspan="2"> Tùy chọn</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty requestScope.drinks}">
                        <c:set var="listDrink" value="${requestScope.drinks}"></c:set>
                        <c:forEach var="drink" items="${listDrink}">
                            <form action="UpdateServlet" method="POST" enctype="multipart/form-data">
                                <tr>
                                    <td><input type="text" name="id" value="${drink.id}" size="1" readonly/></td>
                                    <td><input type="text" name="description" value="${drink.description}"/></td>
                                    <td><input type="text" name="price" value="${drink.price}" size="8"/></td>
                                    <td><input type="text" name="name" value="${drink.name}"/></td>
                                    <td><input type="text" name="quantity" value="${drink.quantity}" size="4"/></td>
                                    <td><input type="text" name="url" value="${drink.url}" readonly/></td>
                                    <td><input type="file" name="file" size="4"/></td>
                                    <td><input type="submit" value="Chỉnh sửa"></td>
                                    <td><a href="DeleteServlet?mid=${drink.id}"
                                           onclick="return confirm('Xóa sản phẩm này');"/>Xóa
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
        <footer>
            <div class="text-center">
                <br><br> <c:set var="user" value="${sessionScope.userdata}"/>
                Xin chào ${user.fullName} | <a href="LoadDrinkServlet">Trang chủ</a> | <a href="CreateServlet">Thêm sản phẩm</a> | <a href="LogoutServlet">Đăng
                xuất</a>
            </div>
        </footer>
    </body>
</c:if>
<c:if test="${sessionScope.userdata.role.roleId == '0' || empty sessionScope.userdata}">
    <body>
    <div class="container">
        <h1 class="text-center">Coffee Shop Online</h1>
        <div class="row">
            <div class="col-sm-2">
                <div></div>
            </div>
            <div class="col-md-8">
                <div class="text-center">
                    <form action="SearchRangeServlet">
                        Tìm kiếm theo giá : <input type="text" name="minsearch" value="" placeholder="Thấp nhất"/> -
                        <input type="text" name="maxsearch" value="" placeholder="Cao nhất"/>
                        <td><input type="submit" value="Search"/></td>
                    </form>
                </div>
                <c:if test="${not empty requestScope.msgs}">
                    <div class="alert alert-success alert text-center" role="alert">
                        <h4 class="alert-heading">Thông báo!</h4>
                        <strong>${requestScope.msgs}</strong>
                    </div>
                </c:if>
                <c:if test="${not empty requestScope.msgf}">
                    <div class="alert alert-warning alert text-center" role="alert">
                        <h4 class="alert-heading">Thông báo!</h4>
                        <strong>${requestScope.msgf}</strong>
                    </div>
                </c:if>
                <div class="text-center">
                    <section>
                        <c:if test="${not empty requestScope.drinks}">
                            <c:set var="listDrink" value="${requestScope.drinks}"/>
                            <c:forEach var="mb" items="${listDrink}">
                                <c:choose>
                                    <c:when test="${(not empty requestScope.min) or (not empty requestScope.max)}">
                                        <c:choose>
                                            <c:when test="${(empty requestScope.min) and (not empty requestScope.max)}">
                                                <c:if test="${mb.price le requestScope.max}">
                                                    <div class="img">
                                                        <img src="images/${mb.url}" width="120" height="120"><br>
                                                        <h4><b> ${mb.name} </b></h4>
                                                            ${mb.price} <br>
                                                        <small>${mb.description}</small><br>
                                                        <a href="CartServlet?&action=choose&id=${mb.id}">Buy</a>
                                                    </div>
                                                </c:if>
                                            </c:when>
                                            <c:when test="${(not empty requestScope.min) and (empty requestScope.max)}">
                                                <c:if test="${mb.price ge requestScope.min}">
                                                    <div class="img">

                                                        <img src="images/${mb.url}" width="120" height="120"><br>
                                                        <h4><b> ${mb.name} </b></h4>
                                                            ${mb.price} <br>
                                                        <small>${mb.description}</small><br>
                                                        <a href="CartServlet?&action=choose&id=${mb.id}">Buy</a>
                                                    </div>
                                                </c:if>
                                            </c:when>
                                            <c:when test="${(not empty requestScope.min) and (not empty requestScope.max)}">
                                                <c:if test="${(mb.price ge requestScope.min) and (mb.price le requestScope.max)}">
                                                    <div class="img">
                                                        <img src="images/${mb.url}" width="120" height="120"><br>
                                                        <h4><b> ${mb.name} </b></h4>
                                                            ${mb.price} <br>
                                                        <small>${mb.description}</small><br>
                                                        <a href="CartServlet?&action=choose&id=${mb.id}">Buy</a>
                                                    </div>
                                                </c:if>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="img">
                                            <img src="images/${mb.url}" width="120" height="120"/><br>
                                            <h4><b> ${mb.name} </b></h4>
                                                ${mb.price} <br>
                                            <small>${mb.description}</small><br>
                                            <a href="CartServlet?&action=choose&id=${mb.id}">Buy</a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>
                        </c:if>
                    </section>
                </div>
                <div class="col-sm-2">
                    <div></div>
                </div>
                <footer>
                    <br><br> <c:set var="user" value="${sessionScope.userdata}"/>

                    <c:choose>
                        <c:when test="${not empty sessionScope.userdata}">
                            <c:set var="user" value="${sessionScope.userdata}"/>
                            Xin chào ${user.fullName} |
                            <a href="CartServlet">Xem giỏ hàng</a> | <a href="UserServlet">Chỉnh sửa thông tin</a> | <a
                                href="OrderUserServlet">Xem đơn hàng</a> | <a href="LogoutServlet">Đăng xuất</a>
                        </c:when>
                        <c:otherwise>
                            Xin chào GUEST |
                            <a href="CartServlet">Xem giỏ hàng</a> | <a href="LoginServlet">Đăng nhập</a>
                        </c:otherwise>
                    </c:choose>
                </footer>
            </div>
        </div>
    </div>
    </body>


</c:if>

</html>
