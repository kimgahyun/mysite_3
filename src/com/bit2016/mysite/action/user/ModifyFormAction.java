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

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ModifyFromAction");
		HttpSession session = request.getSession();
		//현재 로긴된 사용자정보
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		
		UserVo userVo = new UserDao().get( authUser.getNo() );

		request.setAttribute( "userVo", userVo );
		WebUtil.forward( request, response, "/WEB-INF/views/user/modifyform.jsp" );
	}

}
