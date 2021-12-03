<%-- 
    Document   : login
    Created on : Jun 15, 2021, 11:15:35 AM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Đăng nhập</title>
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
    <h1 class="text-center">Đăng nhập</h1>
    <br><br>
    <div class="row">
        <div class="col-sm-3">
            <div></div>
        </div>
        <div class="col-md-6">
            <c:if test="${not empty requestScope.msgf}">
                <div class="alert alert-warning alert text-center" role="alert">
                    <h4 class="alert-heading">Thông báo!</h4>
                    <strong>${requestScope.msgf}</strong>
                </div>
            </c:if>
            <form action="LoginServlet" method="POST">
                <div class="login-form">
                    <div class="form-group">
                        <input class="form-control" type="text" name="name" value="" placeholder="Tài khoản"/>
                    </div>
                    <br><br>
                    <div class="form-group">
                        <input class="form-control" type="password" name="password" value="" placeholder="Mật khẩu"/>
                    </div>
                    <br><br>
                    <div class="d-flex justify-content-center mt-3 login_container text-center">
                        <input class="btn btn-info" type="submit" value="Đăng nhập"/>
                    </div>
                    <br>
                    <br>
                    <div class="text-center"><small>Chưa có tài khoản : </small> <a href="signup.jsp"><small>Tạo tài
                        khoản</small></a>
                    </div>
                    <br>
                    <div class="text-center"><a href="LoadDrinkServlet">Trang chủ</a>
                    </div>
                </div>
            </form>

        </div>
        <div class="col-sm-3">
        </div>
    </div>
</div>
<script src="js/jquery-1.11.1.min.js"></script>
</body>
</html>