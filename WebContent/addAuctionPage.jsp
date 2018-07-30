<%--
  Created by IntelliJ IDEA.
  User: fouter
  Date: 2018/7/20
  Time: 12:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>添加商品</title>
    <link rel="stylesheet" href="layui/css/layui.css">
    <script src="layui/layui.js"></script>
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
</head>
<body>

<form class="layui-form" action="/addAuction.action" method="post" enctype="multipart/form-data" >



    <div style="margin-top: 30px;width: 1000px">

        <div style="width: 60%">
            <div class="layui-form-item">
                <label class="layui-form-label">拍卖品名</label>
                <div class="layui-input-block">
                    <input required lay-verify="required" type="text" maxlength="25"  name="auction_name" id="product_name"  autocomplete="off" class="layui-input">
                </div>
            </div>



            <div class="layui-form-item">
                <label class="layui-form-label">起拍价</label>
                <div class="layui-input-block">
                    <input required lay-verify="required" type="number" id="original_price" name="begin_price" step="any"   autocomplete="off" class="layui-input" max="50000" min="1">
                </div>
            </div>



            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label" >拍卖品介绍</label>
                <div class="layui-input-block">
                    <textarea required lay-verify="required" name="introduction" maxlength="200"  class="layui-textarea"></textarea>
                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label" >拍卖开始日期</label>
                <div class="layui-input-block">
                    <input required lay-verify="required" type="date"  name="begin" step="any" p  autocomplete="off" class="layui-input" max="50000" min="1">

                </div>
            </div>
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label" >拍卖结束日期</label>
                <div class="layui-input-block">
                    <input required lay-verify="required" type="date"  name="end" step="any"   autocomplete="off" class="layui-input" max="50000" min="1">

                </div>
            </div>
            <div class="layui-form-item layui-upload-form">
                <label class="layui-form-label">拍卖品封面</label>
                <div class="layui-input-block">
                    <input required lay-verify="required" type="file"  name="cover"    style="margin-top: 5px">
                </div>
            </div>




            <br>
            <div class="layui-form-item layui-upload-form">
                <label class="layui-form-label">拍卖品介绍图</label>
                <div class="layui-input-block">
                    <input required lay-verify="required" type="file"  multiple="multiple" name="files"    style="margin-top: 5px">
                </div>
            </div>
            <br>



            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button id="subtn" type="submit" class="layui-btn "  >立即提交</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>

        </div>

    </div>

</form>

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
        $("#original_price").keyup(function () {
            var originalPrice=$(this).val();
            $("#discount_price").attr("value",originalPrice);
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
</html>