package com.bit2016.guestbook.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.guestbook.dao.GuestbookDao;
import com.bit2016.guestbook.vo.GuestbookVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("GBaddAction");
		String name = request.getParameter( "name" );
		String password = request.getParameter( "pass" );
		String content = request.getParameter( "content" );
		
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setContent(content);
		vo.setPassword(password);
		
		GuestbookDao dao = new GuestbookDao();
		dao.insert(vo);
		
		WebUtil.redirect(request, response, "/mysite3/gb" );
	}

}



