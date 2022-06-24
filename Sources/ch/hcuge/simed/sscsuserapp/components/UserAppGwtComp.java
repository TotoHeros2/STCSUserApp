package ch.hcuge.simed.sscsuserapp.components;

import com.webobjects.appserver.WOContext;

import ch.hcuge.simed.cohort.transverse.interfaces.LandingPage;
import ch.hcuge.simed.foundation.interfaces.eo.GenericUser;
import er.extensions.components.ERXComponent;


public class UserAppGwtComp extends ERXComponent implements LandingPage{
    public UserAppGwtComp(WOContext context) {
        super(context);
    }

	@Override
	public void setUserToSession(GenericUser arg0) {
		// TODO Auto-generated method stub
		
	}
}