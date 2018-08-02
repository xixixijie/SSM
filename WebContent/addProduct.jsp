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

<form class="layui-form" action="/addProduct.action" method="post" enctype="multipart/form-data" >



    <div style="margin-top: 30px;width: 1000px">

        <div style="width: 60%">
            <div class="layui-form-item">
                <label class="layui-form-label">商品名</label>
                <div class="layui-input-block">
                    <input type="text" maxlength="25" required lay-verify="required" name="product_name" id="product_name" placeholder="25个字以内" autocomplete="off" class="layui-input">
                    <span id="checkPro"></span>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">商品分类</label>
                <div class="layui-input-block">
                    <select name="classify.classifyID" lay-verify="required">
                        <c:forEach items="${classifyList}" var="cla">
                            <option value="${cla.classifyID}">${cla.className}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">原价</label>
                <div class="layui-input-block">
                    <input type="number" id="original_price" name="original_price" step="any" placeholder="不超过50000元" required lay-verify="required" autocomplete="off" class="layui-input" max="50000" min="1">
                    <span id="checkO"></span>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">折扣价</label>
                <div class="layui-input-block">
                    <input type="number" id="discount_price" name="discount_price" step="any" placeholder="不超过50000元" required lay-verify="required" autocomplete="off" class="layui-input" max="50000" min="1">
                    <span id="checkDiscount"></span>
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label" >商品概述</label>
                <div class="layui-input-block">
                    <textarea name="product_info" maxlength="200" required lay-verify="required" placeholder="请输入200字以内的商品概述" class="layui-textarea"></textarea>
                </div>
            </div>
            <div class="layui-form-item layui-upload-form">
                <label class="layui-form-label">商品封面</label>
                <div class="layui-input-block">
                    <input type="file" required lay-verify="required" name="cover"  accept="image/gif,image/jpeg,image/jpg,image/png,image/svg"  style="margin-top: 5px">
                </div>
            </div>




            <br>
            <div class="layui-form-item layui-upload-form">
                <label class="layui-form-label">商品外观图</label>
                <div class="layui-input-block">
                    <input type="file" required lay-verify="required" multiple="multiple" name="aspectPics" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg"   style="margin-top: 5px">
                </div>
            </div>
            <br>
            <div class="layui-form-item layui-upload-form">
                <label class="layui-form-label">商品参数图</label>
                <div class="layui-input-block">
                    <input type="file" required lay-verify="required" multiple="multiple" name="parameterPics"   accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" style="margin-top: 5px">
                </div>
            </div>


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


        $("#product_name").blur(function() {
            var product_name=$(this).val();
            $.ajax({
                url:"/checkProName.action",
                async:false,
                type:"POST",
                data:{"product_name":product_name},
                success:function(data){
                    if(data!=""){
                        $("#checkPro").html(data);
                        $("#checkPro").css("color","red");
                        $("#subtn").attr("disabled",true);
                    }
                    else {
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
</html>