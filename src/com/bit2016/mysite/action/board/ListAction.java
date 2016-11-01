package com.bit2016.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.mysite.dao.BoardDao;
import com.bit2016.mysite.vo.BoardVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class ListAction implements Action {
	private static final int LIST_SIZE = 5;
	private static final int PAGE_SIZE = 5;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		System.out.println("ListAction");
		
		int page = WebUtil.checkIntParam( request.getParameter( "p" ), 1);
		BoardDao dao = new BoardDao();
		
		int totalCount = dao.getTotalCount();
		int pageCount = (int) Math.ceil( totalCount / LIST_SIZE );
		
		if( page > pageCount ) {
			page = 1;
		}
		List<BoardVo> list = dao.getList( page, LIST_SIZE );
		request.setAttribute( "list", list );
		request.setAttribute( "totalCount",	totalCount );
		request.setAttribute( "currentpage", page );
		request.setAttribute( "listSize", LIST_SIZE);
		
		
		WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp" );
	}

}
