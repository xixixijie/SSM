<%--
  Created by IntelliJ IDEA.
  User: fouter
  Date: 2018/7/20
  Time: 12:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改商品信息</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="../layui/layui.js"></script>

</head>
<body>

<form class="layui-form" action="/updateProduct" method="post">



    <div class="layui-col-md8" style="margin-top: 30px">
        <div class="layui-form-item">
            <label class="layui-form-label">商品名</label>
            <div class="layui-input-block">
                <input type="text" value="${product.product_name}" name="product_name" placeholder="25个字以内" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">商品分类</label>
            <div class="layui-input-block">
                <select name="classify.classifyID" >
                    <option selected value="${product.classify.classifyID}">${product.classify.className}</option>
                    <c:forEach items="${classifyList}" var="cla">
                        <%--<c:if test="${cla.classifyID eq product.classify.classifyID}">--%>
                            <%--<option selected value="${cla.classifyID}">${cla.className}</option>--%>
                        <%--</c:if>--%>
                        <%--<c:if test="${cla.classifyID ne product.classify.classifyID}">--%>
                            <%--<option value="${cla.classifyID}">${cla.className}</option>--%>
                        <%--</c:if>--%>
                        <option value="${cla.classifyID}">${cla.className}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">原价</label>
            <div class="layui-input-block">
                <input type="number" value="${product.original_price}" name="original_price" placeholder="不超过50000元" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">折扣价</label>
            <div class="layui-input-block">
                <input type="number" value="${product.discount_price}" name="discount_price"  placeholder="不超过50000元" autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">商品概述</label>
            <div class="layui-input-block">
                <textarea name="product_info" placeholder="请输入200字以内的商品概述" class="layui-textarea">${product.product_info}</textarea>
            </div>
        </div>

        <input type="hidden" value="${product.product_id}" name="product_id">
        <input type="hidden" value="${successMsg}" id="successMsg">

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn " type="submit">立即提交</button>

            </div>
        </div>

    </div>

</form><button id="backButton" class="layui-btn layui-btn-primary" style="float: left">返回</button>
<script>
    layui.use('form', function(){
        var form = layui.form;

        //监听提交
        form.on('submit(formDemo)', function(data){
            layer.msg(JSON.stringify(data.field));
            return false;
        });


    });

    $(function () {
        var successMsg=$("#successMsg").val();
        if(successMsg!=""){
            alert(successMsg);
        }
    })

    $("#backButton").click(function () {
        window.location.href="/goToSearchProduct.action?pageNumPro="+${pageNumPro};
    });


</script>
</body>