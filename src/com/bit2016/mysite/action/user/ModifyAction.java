package com.bit2016.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit2016.mysite.dao.UserDao;
import com.bit2016.mysite.vo.UserVo;
import com.bit2016.web.Action;
import com.bit2016.web.util.WebUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("modifyAction");
		String name = request.getParameter( "name" );
		String email = request.getParameter( "email" );
		String password = request.getParameter( "password" );
		String gender = request.getParameter( "gender" );

		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		//
		
		UserVo vo = new UserVo();
		vo.setName( name );
		vo.setPassword( password );
		vo.setGender( gender );
		vo.setNo(authUser.getNo());
		System.out.println(vo.toString());
		UserDao dao = new UserDao();
		dao.update( vo );
		
		authUser.setName( name );
		session.setAttribute( "authUser", authUser );
		
		WebUtil.redirect( request, response, "/mysite3/main" );

	}

}
