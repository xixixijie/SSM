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
    <title>查找商品</title>
    <link rel="stylesheet" href="../layui/css/layui.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="../layui/layui.js"></script>
</head>
<body style="background-color: #f2f2f2">

    <div class="layui-col-md8" style="margin-top: 30px">

        <table class="layui-table"  lay-skin="line" lay-size="lg">
            <colgroup>
                <col width="150">
                <col width="200">
                <col>
            </colgroup>
            <thead>
            <tr style="background-color: #dddddd">
                <th>商品id</th>
                <th>商品名</th>
                <th>

                    <input type="text" style="width: 150px;height: 25px">
                    <button class="layui-btn layui-btn-normal layui-btn-xs" style="height: 29px;width: 50px">搜索</button>

                </th>
                <th><button class="layui-btn layui-btn-danger layui-btn-sm" style="float: right;margin-right: 25px;">删除</button></th>

            </tr>
            </thead>
            <tbody>

            <c:forEach items="${productList}" var="pro">
                <tr>
                    <td><input type="checkbox" style="margin-left: -10px;margin-top: 8px">  ${pro.product_id}</td>
                    <td>${pro.product_name}</td>
                    <td>

                    </td>
                    <td>
                        <a href="/getProductInfo?product_id=${pro.product_id}">
                            <button class="layui-btn  layui-btn-sm" id="info">查看</button>
                        </a>
                        <a href="/editProduct?product_id=${pro.product_id}">
                            <button class="layui-btn layui-btn-warm layui-btn-sm">修改</button>
                        </a>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="row" style="margin-left: 200px">
            <ul class="pagination">
                <c:if test="${pageNumPro==1}">
                    <li class="disabled"><a href="#">&laquo;</a></li>
                </c:if>
                <c:if test="${pageNumPro!=1}">
                    <li><a href="/goToSearchProduct?pageNumPro=${pageNumPro-1}">&laquo;</a></li>
                </c:if>

                <c:forEach begin="1" end="${pageCount}" var="p">
                <c:if test="${p==pageNumPro}">
                <li class="active"><a href="#">${p}</a></li>
                </c:if>
                <c:if test="${p!=pageNumPro}">
                <li><a href="/goToSearchProduct?pageNumPro=${p}">${p} </a></li>
                </c:if>
                </c:forEach>




                <c:if test="${pageNumPro==pageCount}">
                <li class="disabled"><a href="#">&raquo;</a></li>
                </c:if>
                <c:if test="${pageNumPro!=pageCount}">
                <li><a href="/goToSearchProduct?pageNumPro=${pageNumPro+1}">&raquo;</a></li>
                </c:if>
            </ul>
        </div>


<script>
    $(function () {
        $("#info").click(function () {

        })
    })
</script>
</div>

</body>
</html>
