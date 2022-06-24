package ch.hcuge.simed.sscsuserapp.gwt.client.editor;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BeanKeyValue implements Serializable, IsSerializable{
	private String code;
	private String displayValue;

	public BeanKeyValue(String code, String displayValue) {
		this.code = code;
		this.displayValue = displayValue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	public static final String UNANSWERED = "-2";
	public static final String UNANSWERED_VALUE = "--";
	public static final String UNKNOWN = "-1";
	public static final String UNKNOWN_VALUE = "Unknown";
	public static final String KNOWN = "-3";
	public static final String KNOWN_VALUE = "Known";
	public static final String OTHER = "-10";
	public static final String OTHER_VALUE = "Other";
	
	public static final String YES = "-5";
	public static final String YES_VALUE = "Yes";
	
	public static final String NO = "-6";
	public static final String NO_VALUE = "No";
	
	public static final String APPROXIMATIVE = "-4";
	public static final String APPROXIMATIVE_VALUE = "Approximative";
	
	public static final String DONE = "-11";
	public static final String DONE_VALUE = "Done";
	
	public static final String NOT_DONE = "-12";
	public static final String NOT_DONE_VALUE = "Not done";	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		BeanKeyValue other = (BeanKeyValue)obj;
		if (other.getCode().equalsIgnoreCase(getCode()))
		{
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return getDisplayValue();
	}

	// obligatoire pour Serializable
	public BeanKeyValue()
	{
		
	}
	
	public boolean isUnsanswered()
	{
		if (getCode().equalsIgnoreCase(UNANSWERED))
		{
			return true;
		}
		return false;
	}

}
