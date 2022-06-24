package ch.hcuge.simed.sscsuserapp.components;

import com.webobjects.appserver.WOContext;

import ch.hcuge.simed.cohort.transverse.db.SimedUser;
import er.extensions.components.ERXComponent;


public class MailChangePwdSscs extends ERXComponent {
	private String urlPrefixChangepwd;
	private SimedUser simedUser;
	
    public MailChangePwdSscs(WOContext context) {
        super(context);
    }
    
	public String getUrlPrefixChangepwd() {
		return urlPrefixChangepwd;
	}
	public void setUrlPrefixChangepwd(String urlPrefixChangepwd) {
		this.urlPrefixChangepwd = urlPrefixChangepwd;
	}

	public SimedUser getSimedUser() {
		return simedUser;
	}

	public void setSimedUser(SimedUser simedUser) {
		this.simedUser = simedUser;
	}
	public String url()
	{
		return urlPrefixChangepwd + simedUser.urlParam();
	}
	public String nomUser()
	{
		return simedUser.firstName() + " " + simedUser.lastName();
	}
}