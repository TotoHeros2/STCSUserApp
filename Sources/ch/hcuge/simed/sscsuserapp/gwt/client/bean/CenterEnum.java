package ch.hcuge.simed.sscsuserapp.gwt.client.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

import com.google.gwt.text.shared.Parser;
import com.google.gwt.user.client.rpc.IsSerializable;

import ch.hcuge.simed.sscsuserapp.gwt.client.editor.BeanKeyValue;

public class CenterEnum extends BeanKeyValue implements Serializable, IsSerializable {

	private static final ArrayList<BeanKeyValue> backend = new ArrayList<BeanKeyValue>();
	


	static {
		backend.add(new CenterEnum("ZAS-SSCS_HUG_GE","Hôpitaux Universitaires de Genève"));
		backend.add(new CenterEnum("ZAS-SSCS_KS_BS","Basel Universitätsspital"));
		backend.add(new CenterEnum("ZAS-SSCS_INS_BE","Bern Inselspital"));
		backend.add(new CenterEnum("ZAS-SSCS_ZHU_ZH","Zürich Universitätsspital"));
		backend.add(new CenterEnum("ZAS-SSCS_CHUV_VD","Lausanne CHUV"));
		backend.add(new CenterEnum("ZAS-SSCS_KS_SH","Schaffhausen Kantonsspital"));
		backend.add(new CenterEnum("ZAS-SSCS_ZHI_ZH","Zürich Immunologie Zentrum"));
		backend.add(new CenterEnum("ZAS-SSCS_BH_BS","Basel Kantonsspital Bruderholz"));
		backend.add(new CenterEnum("ZAS-SSCS_KS_LU","Luzern Kantonsspital"));
		backend.add(new CenterEnum("ZAS-SSCS_HP_VS","Valais Sion CHCV"));
		backend.add(new CenterEnum("ZAS-SSCS_TLOC_TI","Ticino Lugano Ospedale Civico"));
		backend.add(new CenterEnum("ZAS-SSCS_KS_SG","St-Gallen Kantonsspital"));
		backend.add(new CenterEnum("ZAS-SSCS_USZ","USZ Rheumatologie"));
	}

	// For WO
	public static CenterEnum fromCode(String code) {
		
		for (BeanKeyValue en : backend) {
			if (en.getCode().equalsIgnoreCase(code))  {
				return (CenterEnum)en;
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
	public static  final Parser<CenterEnum> parser = new Parser() {

		@Override
		public Object parse(CharSequence text) throws ParseException {
			// TODO Auto-generated method stub
			return CenterEnum.fromCode(text.toString());
		}
		
	};	
	public CenterEnum() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CenterEnum(String code, String displayValue) {
		// TODO Auto-generated constructor stub
		super(code, displayValue);
	}

}