<%--
  Created by IntelliJ IDEA.
  User: WANGKING
  Date: 2018-07-26
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!--There is no configured/running web-servers found! Please, run any web-configuration and hit the Refresh button -->
    <form  action="test.action" method="post">



        <p>hi</p>
        <p>${result}</p>
        <input type="text" value="123" id="keyname">


        <button type="submit">提交</button>

    </form>

</body>
</html>
