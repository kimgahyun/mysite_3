<%@page import="com.bit2016.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	UserVo userVo = (UserVo) request.getAttribute("userVo");
	//System.out.print(userVo);
%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite3/assets/css/user.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp"></jsp:include>
		<div id="content">
			<div id="user">

				<form id="join-form" name="joinForm" method="post"
					action="/mysite3/user?a=modify">
					<type ="hidden" name="no" value="<%=userVo.getNo()%>" /> <label
						class="block-label" for="name">이름</label> <input id="name"
						name="name" type="text" value="<%=userVo.getName()%>"> <label
						class="block-label" for="email">이메일</label> <strong><%=userVo.getEmail()%></strong>

					<label class="block-label">패스워드</label> <input name="password"
						type="password" value="<%=userVo.getPassword()%>">

					<fieldset>
						<legend>성별</legend>
						<%
							if ("mail".equals(userVo.getGender())) {
						%>
						<label>여</label> <input type="radio" name="gender" value="female"
							checked=> <label>남</label> <input type="radio"
							name="gender" value="male" checked="checked">
						<%
							} else {
						%>
						<label>여</label> <input type="radio" name="gender" value="female"
							checked="checked"> <label>남</label> <input type="radio"
							name="gender" value="male" checked=>
						<%
							}
						%>
					</fieldset>

					<input type="submit" value="수정하기">

				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"></jsp:include>
	</div>
</body>
</html>