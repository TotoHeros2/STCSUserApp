package ch.hcuge.simed.sscsuserapp.components;

import com.webobjects.appserver.WOContext;

import ch.hcuge.simed.sscsuserapp.Application;
import ch.hcuge.simed.sscsuserapp.Session;
import er.extensions.components.ERXComponent;


public class BaseComponent extends ERXComponent {
	public BaseComponent(WOContext context) {
		super(context);
	}
	
	@Override
	public Application application() {
		return (Application)super.application();
	}
	
	@Override
	public Session session() {
		return (Session)super.session();
	}
}
