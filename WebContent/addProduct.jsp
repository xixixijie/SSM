<%--
  Created by IntelliJ IDEA.
  User: fouter
  Date: 2018/7/20
  Time: 12:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>添加商品</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <script src="../layui/layui.js"></script>

</head>
<body>

<form class="layui-form" action="">



    <div style="margin-top: 30px;width: 1000px">

        <div style="width: 60%">
            <div class="layui-form-item">
                <label class="layui-form-label">商品名</label>
                <div class="layui-input-block">
                    <input type="text" name="product_name" required  lay-verify="required" placeholder="25个字以内" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">商品分类</label>
                <div class="layui-input-block">
                    <select name="" lay-verify="required">
                        <option value="0">小米家居</option>
                        <option value="1">小米手机</option>
                        <option value="2">红米手机</option>
                        <option value="3">小米电视</option>
                        <option value="4">小米笔记本</option>
                    </select>
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">原价</label>
                <div class="layui-input-block">
                    <input type="number" name="original_price" required  lay-verify="required" placeholder="不超过50000元" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <label class="layui-form-label">折扣价</label>
                <div class="layui-input-block">
                    <input type="number" name="discount_price" required  lay-verify="required" placeholder="不超过50000元" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">商品概述</label>
                <div class="layui-input-block">
                    <textarea name="product_info" placeholder="请输入200字以内的商品概述" class="layui-textarea"></textarea>
                </div>
            </div>

            <div class="layui-form-item layui-upload-form">
                <label class="layui-form-label">商品封面</label>
                <div class="layui-input-block">
                    <input type="file"  name="cover_url" required  lay-verify="required" style="margin-top: 5px">
                </div>
            </div>
            <br>
            <div class="layui-form-item layui-upload-form">
                <label class="layui-form-label">商品外观图</label>
                <div class="layui-input-block">
                    <input type="file" multiple="multiple" name="aspect_url" required  lay-verify="required" style="margin-top: 5px">
                </div>
            </div>
            <br>
            <div class="layui-form-item layui-upload-form">
                <label class="layui-form-label">商品参数图</label>
                <div class="layui-input-block">
                    <input type="file" multiple="multiple" name="parameter_url" required  lay-verify="required" style="margin-top: 5px">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn " lay-submit lay-filter="formDemo">立即提交</button>
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



</script>
</body>
</html>