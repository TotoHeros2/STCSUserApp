package ch.hcuge.simed.sscsuserapp.gwt.client.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

import com.google.gwt.text.shared.Parser;
import com.google.gwt.user.client.rpc.IsSerializable;

import ch.hcuge.simed.sscsuserapp.gwt.client.editor.BeanKeyValue;
// from Role
public class RightEnum extends BeanKeyValue implements Serializable, IsSerializable {

	private static final ArrayList<BeanKeyValue> backend = new ArrayList<BeanKeyValue>();
	
	public  static final RightEnum ldm = new RightEnum("ldm","Local Data Manager");
	public  static final RightEnum validate = new RightEnum("validate","Local validator");
	public  static final RightEnum ro = new RightEnum("ro","Read only");


	static {
		backend.add(ldm);
		backend.add(validate);
		backend.add(ro);
	}

	// For WO
	public static RightEnum fromCode(String code) {
		
		for (BeanKeyValue en : backend) {
			if (en.getCode().equalsIgnoreCase(code))  {
				return (RightEnum)en;
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
	public static  final Parser<RightEnum> parser = new Parser() {

		@Override
		public Object parse(CharSequence text) throws ParseException {
			// TODO Auto-generated method stub
			return RightEnum.fromCode(text.toString());
		}
		
	};	
	public RightEnum() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RightEnum(String code, String displayValue) {
		// TODO Auto-generated constructor stub
		super(code, displayValue);
	}

}