<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>详情页面</title>
<script type="text/javascript">
function save() {
	var form = document.getElementById('add_form');
	form.submit();
}
</script>
</head>
<body>
<h2>我是服务器：${pageContext.request.localPort}</h2>
<h2>当前sessionId：${pageContext.session.id}</h2>
<form action="/resume/save" method="post" id="add_form">
<h3 align="center">简历新增</h3><p align="right"><input type="button" onclick="save()" value="保存"></p>
		<table border = "1" width="300" align="center">
			<tr align="center">
			  <td>
			          序 号：<input type="text"  name="id" value="${resume.id}"/>
			  </td>
			  <td>
			          地址:<input type="text"  name="address" value="${resume.address}"/>
			  </td>
			  <td>
			          姓名:<input type="text"  name="name" value="${resume.name}"/>
			  </td>
			  <td>
			          电话:<input type="text"  name="phone" value="${resume.phone}"/>
			  </td>
			</tr>
		</table>
		</form>
</body>
</html>