package com.bit2016.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.mysite.dao.BoardDao;
import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println( "ViewAction");
		long no = Long.parseLong(request.getParameter( "no" ));

		BoardDao dao = new BoardDao();
		BoardVo vo = dao.get( no );

		if( vo == null) {
			WebUtil.redirect( request, response, "/mysite3/board" );
			return;
		}
		
		//  조회수증가
		//{
		//}
		request.setAttribute( "list", vo);
		WebUtil.forward( request, response, "/WEB-INF/views/board/view.jsp" );
	
		
		System.out.println(vo.toString());
		System.out.println( "ViewAction끝" );
	}
	
}
