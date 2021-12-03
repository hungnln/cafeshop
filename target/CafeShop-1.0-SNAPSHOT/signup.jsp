<%-- 
    Document   : signup
    Created on : Jul 6, 2021, 4:12:32 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Đăng kí</title>
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

        .signup-form {
            margin: auto;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="text-center">Đăng kí</h1>
    <div class="row">
        <div class="col-sm-3">
            <div></div>
        </div>
        <div class="col-md-6">
            <c:if test="${not empty requestScope.msgerror}">
                <div class="alert alert-warning alert" role="alert">
                    <h4 class="alert-heading">Thông báo!</h4>
                    <strong>${requestScope.msgerror}</strong>
                </div>
            </c:if>
            <form action="SignUpServlet" method="POST">
                <div class="signup-form">
                    <div class="form-group">
                        <input class="form-control" type="text" name="id" value="${param.id}" placeholder="Tài khoản"/>
                    </div>
                    <c:if test="${not empty requestScope.msgid}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msgid}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <input class="form-control" type="text" name="name" value="${param.name}"
                               placeholder="Tên người dùng"/>
                    </div>
                    <c:if test="${not empty requestScope.msgname}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msgname}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <input class="form-control" type="text" name="phone" value="${param.phone}"
                               placeholder="Số điện thoại"/>
                    </div>
                    <c:if test="${not empty requestScope.msgphone}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msgphone}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <input class="form-control" type="text" name="address" value="${param.address}"
                               placeholder="Địa chỉ"/>
                    </div>
                    <c:if test="${not empty requestScope.msgaddress}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msgaddress}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <input class="form-control" type="password" name="password" value="" placeholder="Mật khẩu"/>
                    </div>
                    <div class="form-group">
                        <input class="form-control" type="password" name="repassword" value=""
                               placeholder="Nhập lại mật khẩu"/>
                    </div>
                    <c:if test="${not empty requestScope.msgpassword}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msgpassword}</strong>
                        </div>
                    </c:if>
                    <div class="d-flex justify-content-center mt-3 signup-container text-center">
                        <input class="btn btn-info" type="submit" value="Đăng kí"/>
                    </div>
                    <br>
                    <div class="text-center"><small>Đã có tài khoản : </small> <a href="LoginServlet"><small>Đăng
                        nhập</small></a>
                    </div>
                    <br>
                    <div class="text-center"><a href="LoadDrinkServlet">Trang chủ</a>
                    </div>
                </div>
            </form>
        </div>

    </div>
    <div class="row">
        <div class="col-sm-3">
            <div></div>
        </div>
    </div>
</div>

<script src="js/jquery-1.11.1.min.js"></script>
</body>
</html>
