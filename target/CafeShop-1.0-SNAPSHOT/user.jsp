<%-- 
    Document   : user.jsp
    Created on : Jul 8, 2021, 5:04:45 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Thông tin cá nhân</title>
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

        .login-form {
            margin: auto;
        }
    </style>
</head>
<body>
<div class="container">
    <c:set var="user" value="${sessionScope.userdata}"/>
    <h1 class="text-center">Thông tin người dùng</h1>
    <div class="row">
        <div class="col-sm-3">
            <div></div>
        </div>
        <div class="col-md-6">
            <c:if test="${not empty requestScope.msgf}">
                <div class="alert alert-danger alert" role="alert">
                    <h4 class="alert-heading">Thông báo!</h4>
                    <strong>${requestScope.msgf}</strong>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.msgs}">
                <div class="alert alert-success alert" role="alert">
                    <h4 class="alert-heading">Thông báo!</h4>
                    <strong>${requestScope.msgs}</strong>
                </div>
            </c:if>
            <form action="UserServlet" method="POST">
                <div class="form">
                    <div class="form-group">
                        Tài khoản : <input class="form-control" type="text" name="id" value="${user.id}" readonly/>
                    </div>
                    <c:if test="${not empty requestScope.msgid}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msgid}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        Tên người dùng : <input class="form-control" type="text" name="name" value="${user.fullName}"/>
                    </div>
                    <c:if test="${not empty requestScope.msgname}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msgname}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        Số điện thoại : <input class="form-control" type="text" name="phone" value="${user.phone}"/>
                    </div>
                    <c:if test="${not empty requestScope.msgphone}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msgphone}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        Địa chỉ : <input class="form-control" type="text" name="address" value="${user.address}"/>
                    </div>
                    <c:if test="${not empty requestScope.msgaddress}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msgaddress}</strong>
                        </div>
                    </c:if>
                    <div class="d-flex justify-content-center mt-3 login_container text-center">
                        <input class="btn btn-info" type="submit" value="Chỉnh sửa"/>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-sm-3">
            <div></div>
        </div>
    </div>
    <div class="text-center"><a href="LoadDrinkServlet">Trang chủ</a> | <a href="LogoutServlet">Đăng xuất</a>
    </div>
</div>
</body>
</html>
