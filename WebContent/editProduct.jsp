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





<div class="layui-col-md8" style="margin-top: 30px">
    <form class="layui-form" action="/updateProduct" method="post">
        <div class="layui-form-item">
            <label class="layui-form-label">商品名</label>
            <div class="layui-input-block">
                <input type="text" maxlength="25" required lay-verify="required" value="${product.product_name}" name="product_name" id="product_name" placeholder="25个字以内" autocomplete="off" class="layui-input">
                <span id="checkPro"></span>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">商品分类</label>
            <div class="layui-input-block">
                <select name="classify.classifyID" lay-verify="required">
                    <option selected="selected" value="${product.classify.classifyID}">${product.classify.className}</option>
                    <c:forEach items="${classifyList}" var="cla">

                        <c:if test="${cla.classifyID ne product.classify.classifyID}">
                            <option value="${cla.classifyID}">${cla.className}</option>
                        </c:if>

                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">原价</label>
            <div class="layui-input-block">
                <input id="original_price" step="any" type="number" required lay-verify="required" value="${product.original_price}" name="original_price" placeholder="不超过50000元" autocomplete="off" class="layui-input" max="50000" min="1">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">折扣价</label>
            <div class="layui-input-block">
                <input id="discount_price" step="any" type="number" required lay-verify="required" value="${product.discount_price}" name="discount_price"  placeholder="不超过50000元" autocomplete="off" class="layui-input" max="50000" min="1">
                <span id="checkDiscount"></span>
            </div>
        </div>

        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">商品概述</label>
            <div class="layui-input-block">
                <textarea name="product_info" required lay-verify="required" placeholder="请输入200字以内的商品概述" class="layui-textarea" maxlength="200">${product.product_info}</textarea>
            </div>
        </div>

        <input type="hidden" value="${product.product_id}" name="product_id">

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button id="subtn" class="layui-btn" type="submit" >确认修改</button>
                <button id="backButton" class="layui-btn layui-btn-primary" >返回</button>
            </div>
        </div>
    </form>
</div>



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

        $("#product_name").blur(function() {
            var product_name=$(this).val();
            $.ajax({
                url:"/checkProName.action",
                type:"POST",
                data:{"product_name":product_name},
                success:function(data){
                    if(data!=""){
                        $("#checkPro").html(data);
                        $("#checkPro").css("color","red");
                        $("#subtn").attr("disabled",true);
                    }
                    else {
                        $("#checkPro").html(data);
                        $("#subtn").attr("disabled",false);
                    }
                },
                error:function(){
                    alert("查询商品名失败");},
                dataType:"text"
            });


        });

        $("#discount_price").blur(function () {
            var originalPrice=$("#original_price").val();
            var discountPrice=$("#discount_price").val();
            if(discountPrice>originalPrice){
                $("#checkDiscount").css("color","red");
                $("#checkDiscount").html("折扣价不能超过原价");
                $("#subtn").attr("disabled",true);
            }else {
                $("#checkDiscount").html("");
                $("#subtn").attr("disabled",false);
            }
        })
    })




</script>
</body>