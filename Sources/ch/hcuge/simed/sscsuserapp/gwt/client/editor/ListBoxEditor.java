package ch.hcuge.simed.sscsuserapp.gwt.client.editor;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.ValueListBox;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.SimpleKeyProvider;

public class ListBoxEditor<T extends BeanKeyValue> extends Composite implements LeafValueEditor<T> {
	
	ValueListBox<T> listBox;
	@SuppressWarnings("unchecked")
	@UiConstructor
	public ListBoxEditor(ArrayList<BeanKeyValue> list) {
		ArrayList<T> values = (ArrayList<T>)list;
		SimpleKeyProvider<T> keyProvider = new SimpleKeyProvider<T>() { 

	        @Override 
	        public Object getKey(T item) { 
	           return item == null ? null : item.getCode(); 
	        } 
	    }; 
	    
	    Renderer<T> renderer = new AbstractRenderer<T>() {

			@Override
			public String render(T object) {
				// TODO Auto-generated method stub
				return  object == null ? "" : object.getDisplayValue() ;
			}
			
		};
		listBox = new ValueListBox<T>(renderer,keyProvider);

		listBox.setValue(values.get(0));
		listBox.setAcceptableValues(values);
//		listBox.setHeight("26px");
		initWidget(listBox);
		listBox.addValueChangeHandler(new ValueChangeHandler<T>() {

			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				// TODO Auto-generated method stub
//				IfrEventBus.EVENT_BUS.fireEvent(new RedPointEvent());// only rp never used for setvisible In IFR
			}
		});
	}

	@Override
	public void setValue(T value) {
		if (value == null) return;
		listBox.setValue(value);
	}

	@Override
	public T getValue() {
		return listBox.getValue();
	}

}
