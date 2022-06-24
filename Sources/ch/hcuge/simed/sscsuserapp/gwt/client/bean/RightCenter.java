package ch.hcuge.simed.sscsuserapp.gwt.client.bean;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RightCenter implements Serializable, IsSerializable{
	
	
	private RightEnum right;
	private CenterEnum center;
	
	public RightEnum getRight() {
		return right;
	}
	public void setRight(RightEnum right) {
		this.right = right;
	}
	public CenterEnum getCenter() {
		return center;
	}
	public void setCenter(CenterEnum center) {
		this.center = center;
	}

}
