package ch.hcuge.simed.sscsuserapp.gwt.client;


import org.gwtbootstrap3.client.ui.Label;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import ch.hcuge.simed.sscsuserapp.gwt.client.bean.SimedUser;
import ch.hcuge.simed.sscsuserapp.gwt.client.components.ListUsers;
import ch.hcuge.simed.sscsuserapp.gwt.client.components.UserEditor;
import wogwt.translatable.WOGWTClientUtil;


public class UserApp implements EntryPoint {
	
//	private ListUsers listUsers = null;
	private UserEditor userEditor = null;
	
	SimplePanel dynamicPanel;


	@Override
	public void onModuleLoad() {
		if (!WOGWTClientUtil.hostPageNameEquals("UserAppGwtComp")) {
			return;
		}
		userEditor = new UserEditor(this);
		// test 
		dynamicPanel = new SimplePanel();
//		userEditor.setVisible(true);
//		listUsers.setVisible(false);
		
		dynamicPanel.setWidget(new ListUsers(this));
		
 // Add it to the root panel.
	    RootPanel.get("app").add(dynamicPanel);
//		RootPanel.get("app").add(new Label("test"));
		userEditor.setUser(new SimedUser("toto@toto.ch", "Toto", true, "Le Héros", "tohe", "XXXX", false, true));

	}
	
	public void  goBackToList()
	{
		dynamicPanel.clear();
		dynamicPanel.setWidget(new ListUsers(this));
	}
	
	public void  editUser(SimedUser user)
	{
		dynamicPanel.clear();
		userEditor.setUser(user);
		dynamicPanel.setWidget(userEditor);
	}

}