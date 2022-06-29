package ch.hcuge.simed.sscsuserapp;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.webobjects.appserver.WORequest;
import com.webobjects.foundation.NSArray;

import ch.hcuge.simed.cohort.transverse.db.SimedUser;
import ch.hcuge.simed.sscsuserapp.components.MailChangePwdSciCore;
import ch.hcuge.simed.sscsuserapp.components.MailChangePwdSscs;
//import ch.hcuge.simed.sscsuserapp.gwt.client.bean.SimedUser;
import er.extensions.appserver.ERXApplication;
import er.extensions.appserver.ERXWOContext;
import er.extensions.foundation.ERXProperties;
import er.javamail.ERMailDeliveryHTML;

public class MailSender {
	
    private static MailSender instance;

	private MailSender()
    {

    }
    
    // Static method to create instance of Singleton class
    public static MailSender getInstance()
    {
        if (instance == null)
        {
        	instance = new MailSender();
        }
        return instance;
    }
    
    public void sendWelcomeMailToUser(SimedUser user)
    {
		String urlPrefixChangepwd = ERXProperties.stringForKey("prodUrlPrefix");




			// pegn must be done with DTW then restart tomcat			
			if (! SimedUser.TO_SEND.equalsIgnoreCase(user.publicID())) // add condition for not send to all
			{
				return;
			}
			//		simedUser.setIsActive(false);
			//		simedUser.setPasswordHash(null);
			//		simedUser.setUrlParam(UUID.randomUUID().toString() + UUID.randomUUID().toString());
			//		ec.saveChanges();


			ERXWOContext mailContext = new ERXWOContext( new WORequest("GET", "/", "HTTP/1.1", null, null, null));
//			MailChangePwdSscs mailUser =  (MailChangePwdSscs)Application.application().pageWithName("MailChangePwdSscs",mailContext);
			MailChangePwdSciCore mailUser =  (MailChangePwdSciCore)Application.application().pageWithName("MailChangePwdSciCore",mailContext);

			mailUser.setUrlPrefixChangepwd(urlPrefixChangepwd);
			mailUser.setSimedUser(user);

			// tls encrypt for citycable
			//    	Session session = ERJavaMail.sharedInstance().defaultSession();
			//    	session.getProperties().put("mail.smtp.starttls.enable", "true");
			//    	session = Session.getDefaultInstance(session.getProperties(), new javax.mail.Authenticator() {
			//    		protected PasswordAuthentication getPasswordAuthentication() {
			//    			return new PasswordAuthentication(ERXProperties.stringForKey("er.javamail.smtpUser"), ERXProperties.stringForKey("er.javamail.smtpPassword"));
			//    			}
			//    	});
			//    	ERJavaMail.sharedInstance().setDefaultSession(session);

			ERMailDeliveryHTML message = new ERMailDeliveryHTML();
			message.setComponent(mailUser);
			message.setCharset("utf-8");
			try {
				message.newMail();
				message.setToAddress(user.email(),user.firstName() + " " + user.lastName());
				//			message.setFromAddress("pgilquin@bluewin.ch","Simed HUG" );
				message.setFromAddress(ERXProperties.stringForKey("er.javamail.smtpUser"),"Simed HUG" );
				message.setReplyToAddress(ERXProperties.stringForKey("er.javamail.smtpUser"));

				message.setSubject("Welcome to SSCS V2 Application");
				//			message.setSubject("Welcome to STCS V2.1 Application");

				//			message.setCCAddresses(new NSArray<String>("Camillo.Ribi@chuv.ch","Christophe.Gaudet-Blavignac@hcuge.ch") );


				message.sendMail(true); // block until done
				ERXApplication.log.info("SSCS : Send change pwd message to  " +  user.email() + " : " + user.firstName() + " " + user.lastName());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		  	
    	
    }

}
