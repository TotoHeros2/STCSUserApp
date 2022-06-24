package ch.hcuge.simed.sscsuserapp.gwt.client.service;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ch.hcuge.simed.sscsuserapp.gwt.client.bean.SimedUser;


public interface RPCUsersServiceAsync {
	
	void users(AsyncCallback<ArrayList<SimedUser>> callback);

	void save(SimedUser user, AsyncCallback<String> callback);

	void disable(SimedUser user, AsyncCallback<Void> callback);

	void sendMail(SimedUser user, AsyncCallback<Void> callback);

}
