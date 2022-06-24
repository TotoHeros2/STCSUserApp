package ch.hcuge.simed.sscsuserapp.gwt.client.editor;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.RadioButton;
import org.gwtbootstrap3.client.ui.base.RadioGroupBase;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.uibinder.client.UiConstructor;

public class RadioGroupEditor<T extends BeanKeyValue> extends RadioGroupBase<T> implements ClickHandler {

	// standard notif dont no work all the time !
	public interface RadioGroupEditorChanger<BeanKeyValue>{
		void onValueChanged(BeanKeyValue value,RadioGroupEditor source);
	}
	RadioGroupEditorChanger<BeanKeyValue> valueChanger;

    @UiConstructor
	public RadioGroupEditor(ArrayList<BeanKeyValue> list,String name, Parser<T> parser,RadioGroupEditorChanger<BeanKeyValue> valueChanger) {
		super(name  + Document.get().createUniqueId(), parser); // must have a different name if many RBG
		this.valueChanger = valueChanger;
		for (BeanKeyValue e: list)
		{
			RadioButton rb = new RadioButton(name + "_" + this.getName() , e.getDisplayValue());
			rb.setFormValue(e.getCode());
            rb.setSize(ButtonSize.EXTRA_SMALL);
			this.add(rb);
            rb.addClickHandler(this); // standard notif dont no work all the time !
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if (valueChanger != null)
		{
			valueChanger.onValueChanged(this.getValue(),this);
		}
	}


	
	
	
/* to do
	public boolean isActive()
	{
		if (getValue())
	}
*/
}
