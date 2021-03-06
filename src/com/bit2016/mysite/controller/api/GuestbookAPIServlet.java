package com.bit2016.mysite.controller.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.guestbook.action.GuestbookActionFactory;
import com.bit2016.web.Action;
import com.bit2016.web.ActionFactory;

@WebServlet("/api/guestbook")
public class GuestbookAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestbookAPIServlet");
		request.setCharacterEncoding( "UTF-8" );
		
		String actionName = request.getParameter( "a" );
		ActionFactory af = new GuestbookActionFactory();
		Action action = af.getAction( actionName );
		
		action.execute( request, response );
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
