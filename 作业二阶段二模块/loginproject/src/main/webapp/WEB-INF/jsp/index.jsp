<%@ page language="java" isELIgnored="false"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>拉勾登录页面</title>
<script type="text/javascript">
	function login() {
		
		var name = document.getElementById("name").value;
		var password = document.getElementById("password").value;
		if (name == "") {
			alert("请输入你的姓名！");
			return false;
		} else if(password == ""){
			alert("请输入你的密码！");
		} else {
			var form = document.getElementById('test_form');
			form.submit();
		}
	}
</script>
</head>
<body>
<h2>我是服务器：${pageContext.request.localPort}</h2>
<h2>当前sessionId：${pageContext.session.id}</h2>
<form action="/resume/login" method="post" id="test_form">
	<div class="content">
		<div class="form sign-in">
			<h2>请登录：</h2><p style="color:red;font-size:16px;">${errMsg}</p>
			<label> <span>用户名</span> <input id="name" name="name" type="text" value=""/>
			</label> <label> <span>密码</span> <input id="password" name="password" type="password" value=""/>
			</label>
			<button type="button" class="submit" onclick="login();">登 录</button>
		</div>
	</div>
	</form>
	<script src="../js/jquery.min.js"></script>
</body>
</html>