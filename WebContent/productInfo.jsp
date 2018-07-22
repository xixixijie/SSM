<%--
  Created by IntelliJ IDEA.
  User: fouter
  Date: 2018/7/20
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>查看商品信息</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <script src="../layui/layui.js"></script>
</head>
<body>
<div style="width: 80%;margin-top: 20px">


    <div class="layui-card " style="width: 400px;float: left">
        <div class="layui-card-header">商品名</div>
        <div class="layui-card-body">
            ${product.product_name}
        </div>
    </div>


    <div class="layui-card " style="width: 400px;float: left;margin-left: 20px">
        <div class="layui-card-header">商品种类</div>
        <div class="layui-card-body">
            ${product.classify.className}
        </div>
    </div>

    <div class="layui-card " style="width: 400px;float: left">
        <div class="layui-card-header">原价</div>
        <div class="layui-card-body">
            ${product.original_price}
        </div>
    </div>

    <div class="layui-card " style="width: 400px;float: left;margin-left: 20px">
        <div class="layui-card-header">折扣价</div>
        <div class="layui-card-body">
            ${product.discount_price}
        </div>
    </div>

    <div class="layui-card " style="width: 820px;float: left">
        <div class="layui-card-header">商品概述</div>
        <div class="layui-card-body">
           ${product.product_info}
        </div>
    </div>

    <button onclick="history.go(-1)" class="layui-btn " lay-submit lay-filter="formDemo" style="margin-top: 10px;margin-left: 380px">返回</button>
</div>






</body>
</html>
