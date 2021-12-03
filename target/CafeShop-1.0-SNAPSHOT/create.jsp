<%-- 
    Document   : staff.create
    Created on : Jun 22, 2021, 11:39:21 PM
    Author     : SE140018
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tạo mới sản phẩm</title>
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

        .create-form {
            margin: auto;
        }
    </style>
</head>
<body>

<div class="container">
    <h1 class="text-center">Tạo sản phẩm</h1>
    <div class="row">
        <div class="col-sm-3">
            <div></div>
        </div>
        <div class="col-md-6">
            <c:if test="${not empty requestScope.msgs}">
                <div class="alert alert-success alert" role="alert">
                    <h4 class="alert-heading">Thông báo!</h4>
                    <strong>${requestScope.msgs}</strong>

                </div>

            </c:if>
            <c:if test="${not empty requestScope.msgf}">
                <div class="alert alert-danger alert" role="alert">
                    <h4 class="alert-heading">Thông báo!</h4>
                    <strong>${requestScope.msgf}</strong>

                </div>
            </c:if>


            <form action="CreateServlet" method="POST" enctype="multipart/form-data">
                <div class="create-form">
                    <div class="form-group">
                        Mã sản phẩm : <input class="form-control" type="text" name="txtID" value="">
                    </div>
                    <c:if test="${not empty requestScope.msg_id}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msg_id}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        Mô tả : <input class="form-control" type="text" name="txtDescription" value="">
                    </div>
                    <c:if test="${not empty requestScope.msg_description}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msg_description}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        Tên sản phẩm : <input class="form-control" type="text" name="txtName" value="">
                    </div>
                    <c:if test="${not empty requestScope.msg_name}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msg_name}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        Giá thành : <input class="form-control" type="text" name="txtPrice" value="">
                    </div>
                    <c:if test="${not empty requestScope.msg_price}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msg_price}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        Số lượng : <input class="form-control" type="text" name="txtQuantity" value="">
                    </div>
                    <c:if test="${not empty requestScope.msg_quantity}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msg_quantity}</strong>
                        </div>
                    </c:if>
                    <div class="form-group">
                        Chọn ảnh : <input class="form-control" type="file" name="file">
                    </div>
                    <c:if test="${not empty requestScope.msg_url}">
                        <div class="alert alert-warning alert" role="alert">
                            <strong>${requestScope.msg_url}</strong>
                        </div>
                    </c:if>

                    <div class="d-flex justify-content-center mt-3 login_container text-center">
                        <input class="btn btn-info" type="submit" value="Tạo"/> <input class="btn btn-info" type="reset"
                                                                                       value="Reset"/>
                    </div>
                </div>

            </form>

        </div>
        <div class="col-sm-3">
        </div>
    </div>
</div>
<script src="js/jquery-1.11.1.min.js"></script>

<footer>
    <div class="text-center">
        <br><br> <c:set var="user" value="${sessionScope.userdata}"/>
        Xin chào ${user.fullName} | <a href="LoadDrinkServlet">Trang chủ</a> | <a href="LogoutServlet">Đăng xuất</a>
    </div>
</footer>
</body>


</html>
