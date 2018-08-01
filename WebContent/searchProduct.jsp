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
    <style>
        .li1{
            background-color: #F0F0F0;
        }
    </style>
    <script>
        $(function () {
            $("#search_info").keyup(function () {
                var search_info=$(this).val();

                $.ajax({
                    url:"/getFullName.action",
                    async:false,
                    type:"POST",
                    data:{"search_info":search_info},
                    success:function(data){
                        if(search_info==""){
                            $("#fullName").hide();
                        }
                        else {
                            $("#fullName").show().html("");
                            if(data==""){
                                $("#fullName").text("没有相关数据！");
                            }else {
                                $("#fullName").append(data);
                                $("li").hover(function () {
                                    var full_name=$(this).text();
                                    $("#search_info").val(full_name);
                                });
                            }
                        }
                    },
                    error:function(){
                        alert("获取完整商品名失败");
                    },
                    dataType:"html"
                });
            });
        })

        function deleteProduct(){
            var chks=document.getElementsByName("chk");
            var flag=false;
            for(var i=0;i<chks.length;i++){
                if(chks[i].checked==true){
                    flag=true;
                    break;
                }
            }
            if(flag){
                //提交请求
                $("#deleteForm").submit();
            }else{
                //提示
                alert("请至少选择一个用户进行删除");
            }
        }
    </script>
</head>
<body style="background-color: #f2f2f2">

<div class="layui-col-md8" style="margin-top: 10px">

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
                <form method="post" action="/getProductByName?pageNumPro=1">
                    <input type="text" id="search_info" value="${search_info}" name="search_info" style="width: 180px;height: 25px">
                    <button type="submit" class="layui-btn layui-btn-normal layui-btn-xs" style="height: 29px;width: 50px">搜索</button>
                    <div id="fullName" style="width:180px;border:solid 1px #CCCCCC;display:none;font-size:13px;z-index:999;position: absolute;background-color: white"></div>
                </form>
            </th>
            <th>

                <button onclick="deleteProduct()" class="layui-btn layui-btn-danger layui-btn-sm" style="float: right;margin-right: 25px;">删除</button></th>

        </tr>
        </thead>
        <tbody>
        <form id="deleteForm" method="post" action="/deleteProduct">
            <c:forEach items="${productList}" var="pro">
                <tr>
                    <td>
                        <c:if test="${pro.isDelete()==true}">
                            <input name="chk" value="${pro.product_id}" type="checkbox" style="margin-left: -10px;margin-top: 8px">  ${pro.product_id}
                        </c:if>
                        <c:if test="${pro.isDelete()==false}">
                            <input disabled="disabled" name="chk" value="${pro.product_id}" type="checkbox" style="margin-left: -10px;margin-top: 8px">  ${pro.product_id}
                        </c:if>
                    </td>
                    <td>${pro.product_name}</td>
                    <td>

                    </td>
                    <td>
                        <a href="/getProductInfo?product_id=${pro.product_id}" >
                            <button type="button" class="layui-btn  layui-btn-sm" id="info">查看</button>
                        </a>
                        <a href="/editProduct?product_id=${pro.product_id}" >
                            <button type="button" class="layui-btn layui-btn-warm layui-btn-sm">修改</button>
                        </a>

                    </td>
                </tr>
            </c:forEach>
        </form>
        </tbody>
    </table>

    <div class="row" style="margin-left: 200px">
        <ul class="pagination">
            <li><a href="/getProductByName?pageNumPro=1&search_info=${search_info}">首页</a></li>
            <c:if test="${pageNumPro==1}">
                <li class="disabled"><a href="#">上一页</a></li>
            </c:if>
            <c:if test="${pageNumPro!=1}">
                <li><a href="/getProductByName?pageNumPro=${pageNumPro-1}&search_info=${search_info}">上一页</a></li>
            </c:if>

            <c:forEach begin="1" end="${pageCount}" var="p">
                <c:if test="${p==pageNumPro}">
                    <li class="active"><a href="#">${p}</a></li>
                </c:if>
                <c:if test="${p!=pageNumPro}">
                    <li><a href="/getProductByName?pageNumPro=${p}&search_info=${search_info}">${p} </a></li>
                </c:if>
            </c:forEach>




            <c:if test="${pageNumPro==pageCount}">
                <li class="disabled"><a href="#">下一页</a></li>
            </c:if>
            <c:if test="${pageNumPro!=pageCount}">
                <li><a href="/getProductByName?pageNumPro=${pageNumPro+1}&search_info=${search_info}">下一页</a></li>
            </c:if>
            <li><a href="/getProductByName?pageNumPro=${pageCount}&search_info=${search_info}">尾页</a></li>
        </ul>

    </div>

</div>

</body>
</html>
