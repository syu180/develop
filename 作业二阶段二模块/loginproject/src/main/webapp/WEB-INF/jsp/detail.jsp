<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

       <html>
       <head>
       <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
       <title>拉勾登录页面</title>
       </head>
       <body>
       <h2>我是服务器：${pageContext.request.localPort}</h2>
		<h2>当前sessionId：${pageContext.session.id}</h2>
       <p>登录页面，请登录：
       		<table border='1'>
       			<tr>
	       			<td>
	       			  序号
	       			</td>
	       			<td>
	       			  地址
	       			</td>
	       			<td>
	       			  姓名
	       			</td>
	       			<td>
	       			  手机号码
	       			</td>
       			</tr>
       			<tr>
       			<c:foreach items="${resumes}" var ="li" varStatus="status"/>
       			</tr>
       		</table>
       </body>
       </html>