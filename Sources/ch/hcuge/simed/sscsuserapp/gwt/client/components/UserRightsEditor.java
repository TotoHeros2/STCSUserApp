package ch.hcuge.simed.sscsuserapp.gwt.client.components;

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.ButtonCell;
import org.gwtbootstrap3.client.ui.gwt.CellTable;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import ch.hcuge.simed.sscsuserapp.gwt.client.bean.CenterEnum;
import ch.hcuge.simed.sscsuserapp.gwt.client.bean.RightCenter;
import ch.hcuge.simed.sscsuserapp.gwt.client.bean.RightEnum;
import ch.hcuge.simed.sscsuserapp.gwt.client.components.ListUsers.Resources;
import ch.hcuge.simed.sscsuserapp.gwt.client.editor.ListBoxEditor;


public class UserRightsEditor extends Composite implements TakesValue<List<RightCenter>>{
	
//	final static List<ConsentType> intialConsents = Arrays.asList(ConsentType.UNANSWERED, ConsentType.CONSENT_GIVEN, ConsentType.CONSENT_UNKNOWN,ConsentType.CONSENT_REFUSED, ConsentType.CONSENT_PERMANENTLY_UNKNOWN);
	 

	private static UserRightsEditorUiBinder uiBinder = GWT.create(UserRightsEditorUiBinder.class);

	interface UserRightsEditorUiBinder extends UiBinder<Widget, UserRightsEditor> {
	}


	private boolean isDirty = false;
	
	
    @UiField(provided = true)
	CellTable<RightCenter> dataGrid = new CellTable<RightCenter>();
    
    private ListDataProvider<RightCenter> dataGridProvider = new ListDataProvider<RightCenter>();
    
//    private ArrayList<RightCenter> rightCenters = null;
	

	@UiField(provided=true)
	ListBoxEditor<RightEnum> rights = new ListBoxEditor<RightEnum>(RightEnum.list());

	@UiField(provided=true)
	ListBoxEditor<CenterEnum> centers = new ListBoxEditor<CenterEnum>(CenterEnum.list());
	
	@UiField
	Button addRight;
	
	
	@UiField
	Button register,cancel;

	public UserRightsEditor() {
		
		initWidget(uiBinder.createAndBindUi(this));
        initTable(dataGrid, dataGridProvider);

	}
	





	public boolean isDirty() {
		return isDirty;
	}
	
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}






	@UiHandler("addRight")
	public void onClick(ClickEvent event) {
		rights.setValue(RightEnum.ldm);
		centers.setValue((CenterEnum)(CenterEnum.list().get(0)));
		rights.setVisible(true);
		centers.setVisible(true);
		register.setVisible(true);
		cancel.setVisible(true);

	}
	
	
	@UiHandler("register")
	public void onClickRegister(ClickEvent event) {
		
		rights.setVisible(false);
		centers.setVisible(false);
		register.setVisible(false);
		cancel.setVisible(false);

		RightCenter rightCenter = new RightCenter();
		rightCenter.setCenter(centers.getValue());
		rightCenter.setRight(rights.getValue());
		getValue().add(rightCenter);
		updateTable(getValue());
		isDirty = true;
		
	}
	
	@UiHandler("cancel")
	public void onClickCancel(ClickEvent event) {
		
		rights.setVisible(false);
		centers.setVisible(false);
		register.setVisible(false);
		cancel.setVisible(false);
		
	}
	
	
	private void initTable(final AbstractCellTable<RightCenter> grid, final ListDataProvider<RightCenter> dataProvider) {
		Resources resources = GWT.create(Resources.class);
           
       		
        final TextColumn<RightCenter> colRight = new TextColumn<RightCenter>() {

            @Override
            public String getValue(final RightCenter object) {
                return String.valueOf(object.getRight().getDisplayValue());
            }
        };
        grid.addColumn(colRight, "Right");
        dataGrid.setColumnWidth(colRight, 100, Unit.PX);
        
        final TextColumn<RightCenter> colCenter = new TextColumn<RightCenter>() {

            @Override
            public String getValue(final RightCenter object) {
                return String.valueOf(object.getCenter().getDisplayValue());
            }
        };
        grid.addColumn(colCenter, "Centre");
        dataGrid.setColumnWidth(colCenter, 100, Unit.PX);
        
        
	    final Column<RightCenter, String> colDel = new Column<RightCenter, String>(new ButtonCell(ButtonType.PRIMARY, IconType.ERASER)) {
	            @Override
	            public String getValue(RightCenter object) {
	                return "Delete";
	            }
	        };
	        
	        colDel.setFieldUpdater(new FieldUpdater<RightCenter, String>() {
	            @Override
	            public void update(int index, RightCenter object, String value) {
	    			boolean reply = Window.confirm("Do you want to delete this right : " + object.getRight().getDisplayValue() + " for center " + object.getCenter().getDisplayValue() + " ?");
	    			if (!reply) // ok
	    			{
	    				return;
	    			}
	    			dataProvider.getList().remove(object);
	    			dataGrid.redraw();
	    			isDirty = true;
	            }
	        });
	        grid.addColumn(colDel, "Delete");	        
	        dataGrid.setColumnWidth(colDel, 40, Unit.PX);
	        
	        
	        
	        dataProvider.addDataDisplay(grid);
//			AbstractEntryPoint.initUserRights();
	        

	    }
	private void updateTable(List<RightCenter> rightCenters)
	{
		isDirty = false;
//		this.rightCenters = rightCenters;
//        dataGrid.setRowCount(users.size(), true);
        dataGridProvider.setList(rightCenters);
//        dataGridProvider.flush();
        dataGrid.redraw();
        dataGrid.setVisibleRange(0,rightCenters.size());
		
	}




	@Override
	public void setValue(List<RightCenter> value) {
		updateTable(value);
	}








	@Override
	public List<RightCenter> getValue() {
		return dataGridProvider.getList();
	}

}
