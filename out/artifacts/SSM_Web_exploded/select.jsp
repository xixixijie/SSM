<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"   %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>在此处插入标题</title>
</head>
<body>

	<form action="selectUser.action" method="post">
		<button type="submit">查询</button>
		<div>
			<table>
				<tr>
				<th></th>
				<th>用户名</th>
				<th>年龄</th>
				<th>邮箱</th>
				<th>编辑</th>
				</tr>
				<c:forEach items="${resultList}" var="u" >
				<tr>
					<td><input type="checkbox" name="userids" value="${u.id}"></td>
					<td>${u.id }</td>
					<td>${u.username }</td>
					<td>${u.age }</td>
					<td>${u.email }</td>
					<td><a href="editUser.action?userid=${u.id}" >编辑</a></td>
					</tr>
				</c:forEach>
			</table>
			<!-- <button type="button" onclick="deleteUser()">删除</button> -->
		</div>
	</form>
</body>
</html>