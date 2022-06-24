package ch.hcuge.simed.sscsuserapp.components;

import com.webobjects.appserver.WOContext;


public class Main extends BaseComponent {
	public Main(WOContext context) {
		super(context);
	}

	public String landingPageName() {
		// TODO
		return UserAppGwtComp.class.getSimpleName();

	}
}
