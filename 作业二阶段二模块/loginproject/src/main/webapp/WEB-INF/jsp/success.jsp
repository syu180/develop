<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>详情页面</title>
<script type="text/javascript">
	function insRow(id){
		window.location.href="/resume/update";
	}

	function updateRow(id){
		window.location.href="/resume/update?id="+id;
	}
	
	function delRow(id){
		window.location.href="/resume/delete?id="+id;
	}
</script>
</head>
<body>
<h2>我是服务器：${pageContext.request.localPort}</h2>
<h2>当前sessionId：${pageContext.session.id}</h2>
	<h3 align="center">简历详情</h3><p align="right"><input type="button" onclick="insRow()" value="新增"></p>
	<table id = "myTable" border="1" width="100%" align="center">
		<tr align="center">
			<td>序 号</td>
			<td>地址</td>
			<td>姓名</td>
			<td>电话</td>
		</tr>
		<c:forEach items="${resumes}" var="resume">
			<tr align="center">
				<td><input type="text" readonly id="id" value="${resume.id}" />
				</td>
				<td><input type="text" readonly id="address"
					value="${resume.address}" /></td>
				<td><input type="text" readonly id="name"
					value="${resume.name}" /></td>
				<td><input type="text" readonly id="phone"
					value="${resume.phone}" />
					<input type="button" onclick="updateRow(${resume.id})" value="编辑"><input type="button" onclick="delRow(${resume.id})" value="删除"></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>