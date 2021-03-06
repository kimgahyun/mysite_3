package com.bit2016.mysite.action.board;

import com.bit2016.mysite.action.user.JoinFormAction;
import com.bit2016.web.Action;
import com.bit2016.web.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		System.out.println("BoardActionFactory");
		Action action = null;
		
		if ( "writeform".equals( actionName ) ) {
			action = new WriteFormAction();
		}
		else if ( "write".equals( actionName ) ) {
			action = new WriteAction();
		}
		else if ( "view".equals( actionName ) ) {
			action = new ViewAction();
		}
		else if ( "delete".equals( actionName ) ) {
			action = new DeleteAction();
		}
		else {
			action = new ListAction();
		}
		return action; 
	}

}
