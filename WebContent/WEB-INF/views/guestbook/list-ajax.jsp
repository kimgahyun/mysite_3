<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
var isEnd = false;
var page = 0;
var render = function( vo ) {
	//
	//	현업에서는 이부분을 template library ex> ejs
	//
	var htmls = "<li><br>"+
	"<table width=510 border=1> <tr> <td> "+ vo.no +"</td><td>"+vo.name+"</td><td>"+vo.regDate+"</td><td><a href=\"/mysite3/gb?a=deleteform&no=1\">삭제</a></td>"+
	"</tr><tr><td colspan=4>" +vo.content +"</td></tr>"+"</table></li>";

	$("#list-guestbook").append( htmls );

}

var fetchList = function() {
	if( isEnd == true ) {
		return;
	}
	$.ajax({
		url: "/mysite3/api/guestbook?a=ajax-list&p=" + ++page,
		type: "get",
		dataType: "json",
		success: function( response ) {
			if( response.result != "success" ) {
				console.error( response.message );
				isEnd = true;
				return;
			}
			//rendering
			$( response.data ).each( function( index, vo ){
				render( vo );
			}); 
			
			if ( response.data.length < 5 ) {
				isEnd = true;
				$("#btn-fetch").prop("disabled", true)
				return;
			}
			
		},
		error: function( jqXHR, status, e ) {
			console.error( status + ":" + e );
		}
	});
}
$(function(){
	//삭제버튼 click event (live event)
	$( document ).on( "click", "#list-guestbook li a" , function( event ){
		event.preventDefault();
		console.log( "여기서 비밀번호를 입력받는 모달 다이얼로그를 띄웁니다." );
	});
	$( "add-form" ).submit( function( event ){
		event.preventDefault();
		
		//ajax insert
		
	});
	$(window).scroll( function() {
		var $window = $(this);
		var scrollTop = $window.scrollTop();
		var windowHeight = $window.height();
		var documentHeight = $( document ).height();
		// 스크롤바가 바닥까지 왔을때
		if( scrollTop + windowHeight + 20 > documentHeight ) {
			fetchList();
		}
		console.log( scrollTop + ":" + windowHeight + ":" + documentHeight);
	});
	$( "#btn-fetch" ).click(function(){
		fetchList();
	});
	
	// 1번째 리스트 가져오기
	fetchList();
});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form id="add-form" action="<!-- /mysite3/add -->" method="post">
					<input type="hidden" name="a" value="add">
					<table>
						<tr>
							<td>이름</td>
							<td><input  type="text" name="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="pass"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul id="list-guestbook">

				</ul>
				<button style="margin-top:20px"  id="btn-fetch">가져오기</button>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="gb-ajax" />

		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>