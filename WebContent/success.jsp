<%--
  Created by Liwenjiao.
  User: Administrator
  Date: 2018/7/29 0029
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>支付成功</title>
    <style type="text/css">
        body{margin:0; padding:0}
        #all{width:500px; height:300px;margin-left:440px; margin-top:100px;}
        #pay{ text-align:center; line-height:80px; font-size:30px; font-weight:bold}
        #return{ margin-top:20px; text-align:center; line-height:50px; font-size:24px; border:solid 2px #C00}
        a{text-decoration:none;color:#000 }
    </style>
    <script src="themes/js/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            $("#return").mouseover(function(){
                $(this).css("backgroundColor","#F00");
            })
            $("#return").mouseout(function(){
                $(this).css("backgroundColor","#FFF");
            })

        })
    </script>
</head>

<body>
<div id="all">
    <div id="pay">支付成功！</div>
    <div id="return">
        <a href="index.html">回到主页</a>
    </div>
</div>
</body>
</html>