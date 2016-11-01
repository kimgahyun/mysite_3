<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">

<!-- <script>
$(window).scroll(function( ){  //스크롤이 움직일때마다 이벤트 발생 
    var position = $(window).scrollTop(); // 현재 스크롤바의 위치값을 반환합니다.
    $( "#navigation" ).stop().animate({top:position+"px"}, 1); //해당 오브젝트 위치값 재설정
});

</script> -->

<div id="navigation" style="position: relative">
	<ul id="ulid" style="position: absolute; left: 0px; top: ">
		<c:choose>
			<c:when test="${param.menu == 'main' }">
				<li class="selected"><a href="/mysite3/main">안대혁</a></li>
				<li><a href="/mysite3/gb">방명록</a></li>
				<li><a href="/mysite3/gb?a=ajax">방명록(AJAX)</a></li>
				<li><a href="/mysite3/board">게시판</a></li>
			</c:when>
			<c:when test="${param.menu == 'gb' }">
				<li><a href="/mysite3/main">안대혁</a></li>
				<li class="selected"><a href="/mysite3/gb">방명록</a></li>
				<li><a href="/mysite3/gb?a=ajax">방명록(AJAX)</a></li>
				<li><a href="/mysite3/board">게시판</a></li>
			</c:when>
			<c:when test="${param.menu == 'gb-ajax' }">
				<li><a href="/mysite3/main">안대혁</a></li>
				<li><a href="/mysite3/gb">방명록</a></li>
				<li class="selected"><a href="/mysite3/gb?a=ajax">방명록(AJAX)</a></li>
				<li><a href="/mysite3/board">게시판</a></li>
			</c:when>
			<c:when test="${param.menu == 'board' }">
				<li><a href="/mysite3/main">안대혁</a></li>
				<li><a href="/mysite3/gb">방명록</a></li>
				<li><a href="/mysite3/gb?a=ajax">방명록(AJAX)</a></li>
				<li class="selected"><a href="/mysite3/board">게시판</a></li>
			</c:when>
		</c:choose>

	</ul>
</div>