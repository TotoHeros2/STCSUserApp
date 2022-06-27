package ch.hcuge.simed.sscsuserapp.rpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import com.webobjects.appserver.WOContext;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSValidation;

import ch.hcuge.simed.cohort.transverse.db.Center;
import ch.hcuge.simed.cohort.transverse.db.Role;
import ch.hcuge.simed.cohort.transverse.db.WorkPosition;
import ch.hcuge.simed.sscsuserapp.MailSender;
import ch.hcuge.simed.sscsuserapp.gwt.client.bean.CenterEnum;
import ch.hcuge.simed.sscsuserapp.gwt.client.bean.RightCenter;
import ch.hcuge.simed.sscsuserapp.gwt.client.bean.RightEnum;
import ch.hcuge.simed.sscsuserapp.gwt.client.bean.SimedUser;
import ch.hcuge.simed.sscsuserapp.gwt.client.service.RPCUsersService;
import er.extensions.eof.ERXEC;
import er.extensions.eof.ERXEOGlobalIDUtilities;
import er.extensions.qualifiers.ERXAndQualifier;
import wogwt.server.rpc.WOGWTRpcService;

public class RPCUsersServiceImpl extends WOGWTRpcService implements RPCUsersService{
	
	static final EOEditingContext ec = ERXEC.newEditingContext();


	public RPCUsersServiceImpl(WOContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<SimedUser> users() {
		
		NSArray<ch.hcuge.simed.cohort.transverse.db.SimedUser> users = ch.hcuge.simed.cohort.transverse.db.SimedUser.fetchAllSimedUsers(ec,ch.hcuge.simed.cohort.transverse.db.SimedUser.LAST_NAME.ascs());
		ArrayList<SimedUser> toReturn = new ArrayList<SimedUser>(users.size());
		for (ch.hcuge.simed.cohort.transverse.db.SimedUser user:users)
		{
			System.err.println(user.login() + " - " + user.lastName() +  " - " + user.isActive() );
			SimedUser userClient = new SimedUser(user.email(), user.firstName(), user.isActive(), user.lastName(), user.login(), user.publicID(), user.canUseAuditTrail(), user.canUseSampleManager());
			for (WorkPosition wp: user.workPositions())
			{
				System.err.println("\t\t" + wp);
				RightCenter rightCenter = new RightCenter();
				rightCenter.setRight(RightEnum.fromCode(wp.role().publicID()));
				rightCenter.setCenter(CenterEnum.fromCode(wp.workLocation().publicID()));
				userClient.getRightCenters().add(rightCenter);
			}
			userClient.set__globalID(user.__globalID());
			toReturn.add(userClient);
		}

		return toReturn;
	}

	@Override
	public String save(SimedUser user){
		EOGlobalID gId = user.get__globalID();
		ch.hcuge.simed.cohort.transverse.db.SimedUser userDB = null;
		if (gId != null)
			userDB = (ch.hcuge.simed.cohort.transverse.db.SimedUser) ERXEOGlobalIDUtilities.fetchObjectWithGlobalID(ec, gId);
		else // new
		{
			// test if login exist
			if (user.getLogin() == null ||  user.getLogin().length() == 0)
			{
//				throw new NSValidation.ValidationException("Login cannot empty");
//				throw new Exception("Login cannot empty");
				return "Login cannot empty";		
			}
			else
			{
				NSArray<ch.hcuge.simed.cohort.transverse.db.SimedUser> users = ch.hcuge.simed.cohort.transverse.db.SimedUser.fetchSimedUsers(ec,ch.hcuge.simed.cohort.transverse.db.SimedUser.LOGIN.eq(user.getLogin()) , null);
				if (users.count() > 0)
				{
					return "Login already exists";
				}
			}
			// User must be created not actived and with a null pwd !
			userDB = ch.hcuge.simed.cohort.transverse.db.SimedUser.createSimedUser(ec, user.getEmail(), user.getFirstName(), user.getLastName(), user.getLogin(), null, ch.hcuge.simed.cohort.transverse.db.SimedUser.TO_SEND);
		}
		updateDataTo(user,userDB);
		ec.saveChanges();
		return null;
	}

	private void updateDataTo(SimedUser user, ch.hcuge.simed.cohort.transverse.db.SimedUser userDB) {
		
		userDB.setCanUseAuditTrail(user.getCanUseAuditTrail());
		userDB.setCanUseSampleManager(user.getCanUseSampleManager());
		userDB.setEmail(user.getEmail());
		userDB.setFirstName(user.getFirstName());
		userDB.setLastName(user.getLastName());
		userDB.setLogin(user.getLogin());
		userDB.setIsActive(user.getIsActive());
//		userDB.deleteAllWorkPositionsRelationships();
		Iterator<WorkPosition> it = userDB.workPositions().iterator();
		while(it.hasNext()) {
			WorkPosition wp = it.next();
			it.remove();
//			userDB.removeFromWorkPositions(wp);		
		}
		for (RightCenter rightCenter :  user.getRightCenters())
		{
			// SSCS
//			String publiId = rightCenter.getRight().getCode() + "-" + rightCenter.getCenter().getCode();
//			WorkPosition wp = WorkPosition.fetchRequiredWorkPosition(userDB.editingContext(), WorkPosition.PUBLIC_ID.eq(publiId));
//			userDB.addToWorkPositions(wp);
			// STCS
			EOQualifier rightQual = WorkPosition.ROLE.dot(Role.PUBLIC_ID.eq(rightCenter.getRight().getCode()));
			EOQualifier centerQual = WorkPosition.WORK_LOCATION.dot(Center.PUBLIC_ID.eq(rightCenter.getCenter().getCode()));
			ERXAndQualifier qualAll = new ERXAndQualifier(rightQual,centerQual);

			NSArray<WorkPosition> wps = WorkPosition.fetchWorkPositions(userDB.editingContext(),qualAll, null);
			if (wps.count() > 0)
			{
				userDB.addToWorkPositions(wps.objectAtIndex(0));
			}
			
		}
		if (userDB.currentWorkPosition() == null)
		{
			userDB.setCurrentWorkPosition(userDB.workPositions().get(0));
		}
		else 
		{
			if (!userDB.workPositions().contains(userDB.currentWorkPosition()))
			{
				userDB.setCurrentWorkPosition(userDB.workPositions().get(0));
			}
		}
	}

	@Override
	public void disable(SimedUser user) {
		EOGlobalID gId = user.get__globalID();
		ch.hcuge.simed.cohort.transverse.db.SimedUser userDB = null;
		if (gId != null)
			userDB = (ch.hcuge.simed.cohort.transverse.db.SimedUser) ERXEOGlobalIDUtilities.fetchObjectWithGlobalID(ec, gId);
		userDB.setIsActive(false);
		userDB.setPasswordHash(null);
		userDB.setPublicID(ch.hcuge.simed.cohort.transverse.db.SimedUser.TO_SEND);
		userDB.setUrlParam(UUID.randomUUID().toString() + UUID.randomUUID().toString());
		ec.saveChanges();
	}

	@Override
	public void sendMail(SimedUser user) {
		EOGlobalID gId = user.get__globalID();
		ch.hcuge.simed.cohort.transverse.db.SimedUser userDB = null;
		if (gId != null)
			userDB = (ch.hcuge.simed.cohort.transverse.db.SimedUser) ERXEOGlobalIDUtilities.fetchObjectWithGlobalID(ec, gId);	
		if (userDB != null)
		{
			MailSender.getInstance().sendWelcomeMailToUser(userDB);
		}
	}

}
