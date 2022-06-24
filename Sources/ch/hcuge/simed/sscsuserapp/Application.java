package ch.hcuge.simed.sscsuserapp;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import ch.hcuge.simed.cohort.transverse.db.SimedUser;
import ch.hcuge.simed.cohort.transverse.db.WorkPosition;
import er.extensions.appserver.ERXApplication;
import er.extensions.eof.ERXEC;

public class Application extends ERXApplication {
	public static void main(String[] argv) {
		ERXApplication.main(argv, Application.class);
	}

	public Application() {
		ERXApplication.log.info("Welcome to " + name() + " !");
		/* ** put your initialization code in here ** */
		setAllowsConcurrentRequestHandling(true);		
	}

	@Override
	public void didFinishLaunching() {
		// TODO Auto-generated method stub
		super.didFinishLaunching();
		initGwt();
//		loadPatients();
	}

	private void loadPatients() {
		EOEditingContext ec = ERXEC.newEditingContext();		
		NSArray<SimedUser> users = SimedUser.fetchAllSimedUsers(ec);
		for (SimedUser user:users)
		{
			System.err.println(user.login() + " - " + user.lastName() +  " - " + user.isActive() );
			for (WorkPosition position : user.workPositions())
			{
				System.err.println("	position : " + position.publicID());
				System.err.println("	Centre : " + position.workLocation().name() + " - " + position.workLocation().code() + " - " + position.workLocation().publicID());
				System.err.println("	Role : " + position.role().name() + " - " + position.role().code() + " - " + position.role().publicID());

			}
		}
		
	}
	
	private void initGwt()
	{
		registerRequestHandler(new wogwt.server.rpc.GWTRPCRequestHandler(), wogwt.server.rpc.GWTRPCRequestHandler.KEY);

		if (wogwt.components.WOGWTScript.isHostedMode()) {
			setContextClassName(wogwt.GWTContext.class.getName());
		}

		// needed for proper class loading in GWT's Hosted Mode shell
		com.webobjects.foundation._NSUtilities.setClassForName( ch.hcuge.simed.sscsuserapp.components.Main.class, "Main" );

	}
	
	
	
}
