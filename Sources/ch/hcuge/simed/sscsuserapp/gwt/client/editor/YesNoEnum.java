package ch.hcuge.simed.sscsuserapp.gwt.client.editor;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

import com.google.gwt.text.shared.Parser;
import com.google.gwt.user.client.rpc.IsSerializable;




public class YesNoEnum extends BeanKeyValue implements Serializable, IsSerializable {

	private static final ArrayList<BeanKeyValue> backend = new ArrayList<BeanKeyValue>();
	
	
	private  static int  i = 0;
	public  static final YesNoEnum YES = new YesNoEnum(BeanKeyValue.YES_VALUE,BeanKeyValue.YES_VALUE);
	public  static final YesNoEnum NO = new YesNoEnum(BeanKeyValue.NO_VALUE,BeanKeyValue.NO_VALUE);


	static {
		backend.add(YES);
		backend.add(NO);

	}

	// For WO
	public static YesNoEnum fromCode(String code) {
		
		for (BeanKeyValue en : backend) {
			if (en.getCode().equalsIgnoreCase(code))  {
				return (YesNoEnum)en;
			}
		}
		return null;
	}

	// For  gwt Editor
	public static ArrayList<BeanKeyValue> list()
	{
		return backend;
	}
	// for rb group
	public static  final Parser<YesNoEnum> parser = new Parser() {

		@Override
		public Object parse(CharSequence text) throws ParseException {
			// TODO Auto-generated method stub
			return YesNoEnum.fromCode(text.toString());
		}
		
	};	
	public YesNoEnum() {
		super();
		// TODO Auto-generated constructor stub
	}
	public YesNoEnum(String code, String displayValue) {
		// TODO Auto-generated constructor stub
		super(code, displayValue);
	}

	
	public boolean isActive()
	{
		if (this.getCode().equals(YES.getCode()))
			return true;
		return false;
	}
}
