package ch.hcuge.simed.sscsuserapp.gwt.client.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.webobjects.eocontrol.EOGlobalID;

import ch.hcuge.simed.cohort.transverse.db.WorkPosition;


public class SimedUser implements Serializable, IsSerializable {
	private EOGlobalID __globalID;

	
	private String email;
	
	private String firstName;
	
	private Boolean isActive;
	
	private String lastName;
	
	private String login;
	
	private String publicID;
	
	private Boolean canUseAuditTrail;
	
	
	private Boolean canUseSampleManager;
//	private YesNoEnum canUseSampleManagerEnum;

	
	
	private List<RightCenter> rightCenters = new  ArrayList<RightCenter>();


	public SimedUser() {

	}

	public SimedUser(String email, String firstName, Boolean isActive, String lastName, String login, String publicID,
			Boolean canUseAuditTrail, Boolean canUseSampleManager) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.isActive = isActive;
		this.lastName = lastName;
		this.login = login;
		this.publicID = publicID;
		this.canUseAuditTrail = canUseAuditTrail;
		this.canUseSampleManager = canUseSampleManager;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPublicID() {
		return publicID;
	}


	public void setPublicID(String publicID) {
		this.publicID = publicID;
	}


	public Boolean getCanUseAuditTrail() {
		return canUseAuditTrail;
	}


	public void setCanUseAuditTrail(Boolean canUseAuditTrail) {
		this.canUseAuditTrail = canUseAuditTrail;
	}


	public Boolean getCanUseSampleManager() {
		return canUseSampleManager;
	}


	public void setCanUseSampleManager(Boolean canUseSampleManager) {
		this.canUseSampleManager = canUseSampleManager;
	}

	public List<RightCenter> getRightCenters() {
		return rightCenters;
	}

	public void setRightCenters(List<RightCenter> rightCenters) {
		this.rightCenters = rightCenters;
	}

	public EOGlobalID get__globalID() {
		return __globalID;
	}

	public void set__globalID(EOGlobalID __globalID) {
		this.__globalID = __globalID;
	}




	
	

}
