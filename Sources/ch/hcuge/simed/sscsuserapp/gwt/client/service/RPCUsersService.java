package ch.hcuge.simed.sscsuserapp.gwt.client.service;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.RemoteService;

import ch.hcuge.simed.sscsuserapp.gwt.client.bean.SimedUser;
import wogwt.translatable.WOGWTClientUtil;

public interface RPCUsersService extends RemoteService {
	
	public ArrayList<SimedUser> users();
	public String save(SimedUser user);
	public void disable(SimedUser user);
	public void sendMail(SimedUser user);


	
	
    public static class Util {
        public static RPCUsersServiceAsync getInstance() {
        	RPCUsersServiceAsync service  = (RPCUsersServiceAsync)GWT.create(RPCUsersService.class);
        	((ServiceDefTarget)service).setServiceEntryPoint(WOGWTClientUtil.rpcUrl());
        	return service;
        }
    }


}
