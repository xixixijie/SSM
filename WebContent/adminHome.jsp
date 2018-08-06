<%--
  Created by IntelliJ IDEA.
  User: fouter
  Date: 2018/7/19
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="layui/css/layui.css">
    <script src="layui/layui.js"></script>
</head>
<body style="background-color: #f2f2f2">

<ul class="layui-nav layui-bg-black">
    <li class="layui-nav-item" >小米商城后台管理</li>
</ul>

<div class="layui-row">
    <div class="layui-col-md2">
        <ul class="layui-nav layui-nav-tree layui-inline layui-bg-black" lay-filter="demo" style="margin-right: 10px;">
            <li class="layui-nav-item">
                <a href="javascript:;">团购管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="addActivity.html" target="mainFrame">发布团购</a></dd>
                    <dd><a href="generateActivity.html" target="mainFrame">管理团购</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item layui-nav-itemed">
                <a href="javascript:;">分类管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="addClassifyPage.html" target="mainFrame">添加分类</a></dd>
                    <dd><a href="showClassifyPage.html" target="mainFrame">管理分类</a></dd>
                    <dd><a href="updateModelPage.html" target="mainFrame">更新模型</a></dd>
                    <dd><a href="static.html" target="mainFrame">商品购买数量统计</a></dd>


                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">商品管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="/goToAddProduct.action" target="mainFrame">添加商品</a></dd>
                    <dd><a href="/goToSearchProduct.action?pageNumPro=1" target="mainFrame">查询商品</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">拍卖管理</a>
                <dl class="layui-nav-child">
                    <dd><a href="addAuctionPage.jsp" target="mainFrame">添加拍卖品</a></dd>
                    <%--<dd><a href="AuctionManager.html" target="mainFrame">查询拍卖品</a></dd>--%>
                </dl>
            </li>
        </ul>
    </div>
    <div class="layui-col-md10">
        <iframe name="mainFrame"  width="100%" height="1500px" frameborder="0" marginheight="0" >

        </iframe>
    </div>



</div>

<script>
    layui.use('element', function(){
        var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块

        //监听导航点击
        element.on('nav(demo)', function(elem){
            //console.log(elem)
            layer.msg(elem.text());
        });
    });
</script>

</body>
</html>