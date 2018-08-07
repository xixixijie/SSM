<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/25 0025
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>成功加入购物车-小米商城</title>
    <style>
        body{ margin:0;padding:0}
        .all{ width:100%; height:auto;}
        .content{ margin-left:35px; margin-right:30px; height:1000px}

        .left-content{ margin-top:120px; height:80px;  width:330px}
        .img{ width:80px; height:80px; float:left}
        .text{ width:250px;line-height:50px; float:left; padding-bottom:30px; font-size:20px; font-weight:bold}

        .right-content{width:120px; height:200px; padding-left:40px}
        .div1 a{ text-decoration:none; color:#333}
        .div1{border:1px solid #333; width:200px; line-height:30px; text-align:center; margin-bottom:15px; color:#333}
        .div2 a{ text-decoration:none; color:#FFF}
        .div2{border:1px solid #F60; width:200px; line-height:30px; text-align:center; margin-bottom:15px; font-weight:bold; background-color:#F60; color:#FFF}
    </style>
</head>

<body>
<div class="all">
    <div class="content">

        <div class="left-content">   <!--left-->
            <div class="img">
                <img src="themes/img/icon/download.jpg" width="50px">
            </div>
            <div class="text">已成功加入购物车！</div>
        </div>


        <div class="right-content">   <!--right-->
            <div class="div1"><a href="index.html">返回主页</a></div>
            <div class="div2"><a href="selectProduct.action">去购物车结算</a></div>
            </ul>
        </div>

    </div>
</div>
</body>
</html>

