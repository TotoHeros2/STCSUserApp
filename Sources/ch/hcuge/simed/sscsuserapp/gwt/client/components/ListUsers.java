package ch.hcuge.simed.sscsuserapp.gwt.client.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.gwt.ButtonCell;
import org.gwtbootstrap3.client.ui.gwt.CellTable;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import ch.hcuge.simed.sscsuserapp.gwt.client.UserApp;
import ch.hcuge.simed.sscsuserapp.gwt.client.bean.SimedUser;
import ch.hcuge.simed.sscsuserapp.gwt.client.service.RPCUsersService;




public class ListUsers extends Composite {
	
	interface Resources extends ClientBundle {
		  @Source("bille_verte.gif")
		  ImageResource getGreenLight();
		  
		  @Source("bille_rouge.gif")
		  ImageResource getRedLight();
	}


	private static ListUsersUiBinder uiBinder = GWT.create(ListUsersUiBinder.class);
	
	  
	
	
	@UiField
	Button createButton;
	
    @UiField(provided = true)
	CellTable<SimedUser> dataGrid = new CellTable<SimedUser>();
    
    private ListDataProvider<SimedUser> dataGridProvider = new ListDataProvider<SimedUser>();
    
    private ArrayList<SimedUser> users = null;




	private UserApp presenter;
 
    

	interface ListUsersUiBinder extends UiBinder<Widget, ListUsers> {
	}

	public ListUsers(UserApp presenter) {
		this.presenter = presenter;
		initWidget(uiBinder.createAndBindUi(this));

        initTable(dataGrid, dataGridProvider);


	}
	private void initTable(final AbstractCellTable<SimedUser> grid, final ListDataProvider<SimedUser> dataProvider) {
		Resources resources = GWT.create(Resources.class);
        
        Column<SimedUser, ImageResource> imageColumn =
        		new Column<SimedUser, ImageResource>(new ImageResourceCell()) {
        	@Override
        	public ImageResource getValue(SimedUser object) {
        		if (object.getIsActive())
        			return resources.getGreenLight();
        		else
        			return resources.getRedLight();        			
        	}
        };
        grid.addColumn(imageColumn, "Active");
        dataGrid.setColumnWidth(imageColumn, 10, Unit.PX);        
       		
        final TextColumn<SimedUser> col2 = new TextColumn<SimedUser>() {

            @Override
            public String getValue(final SimedUser object) {
                return String.valueOf(object.getLastName());
            }
        };
        grid.addColumn(col2, "Last Name");
        dataGrid.setColumnWidth(col2, 100, Unit.PX);
        
        final TextColumn<SimedUser> colFN = new TextColumn<SimedUser>() {

            @Override
            public String getValue(final SimedUser object) {
                return String.valueOf(object.getFirstName());
            }
        };
        grid.addColumn(colFN, "First Name");
        dataGrid.setColumnWidth(colFN, 100, Unit.PX);
        
	    final TextColumn<SimedUser> col1 = new TextColumn<SimedUser>() {

	            @Override
	            public String getValue(final SimedUser object) {
	                return String.valueOf(object.getLogin());
	            }
	        };
	        grid.addColumn(col1, "Login");

	        dataGrid.setColumnWidth(col1, 50, Unit.PX);
		    final TextColumn<SimedUser> colEmail = new TextColumn<SimedUser>() {

	            @Override
	            public String getValue(final SimedUser object) {
	                return String.valueOf(object.getEmail());
	            }
	        };
	        grid.addColumn(colEmail, "E-mail");

	        dataGrid.setColumnWidth(colEmail, 100, Unit.PX);
        
	        final Column<SimedUser, String> colDel = new Column<SimedUser, String>(new ButtonCell(ButtonType.PRIMARY, IconType.EDIT)) {
	            @Override
	            public String getValue(SimedUser object) {
	                return "Edit";
	            }
	        };
	        
	        colDel.setFieldUpdater(new FieldUpdater<SimedUser, String>() {
	            @Override
	            public void update(int index, SimedUser object, String value) {
	        		presenter.editUser(object);
//	    			boolean reply = Window.confirm("Do you want to delete SimedUser " + object.getLastName() + " ?");
//	    			if (!reply) // ok
//	    			{
//	    				return;
//	    			}
//	    			dataProvider.getList().remove(object);
//	    			dataGrid.redraw();
	    			
//	            	object.setStatus(Status.DELETED);
//	            	for (SimedUserDetailTabulation detailEditor :   editor.getEditors())
//	            	{
//	            		detailEditor.setVisible(false);
//	            	}
	            	// must be exclude from RP
					// display is ok but if Delete is only change -> no save ! 

//	            	presenter.removeSimedUser(object);
//	                dataProvider.getList().remove(index);
//	                dataProvider.flush();
	            	

	            }
	        });
	        grid.addColumn(colDel, "");	        
	        dataGrid.setColumnWidth(colDel, 40, Unit.PX);
	        
	        
	        
	        dataProvider.addDataDisplay(grid);
//			AbstractEntryPoint.initUserRights();
	        
	        RPCUsersService.Util.getInstance().users(new AsyncCallback<ArrayList<SimedUser>>() {
				
				@Override
				public void onSuccess(ArrayList<SimedUser> users) {
					ListUsers.this.updateTable(users);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
					Window.alert("Sorry, cannot get users. Error is : " + caught.getMessage());	
					
				}
			});

	    }
	private void updateTable(ArrayList<SimedUser> users)
	{
		this.users = users;
//        dataGrid.setRowCount(users.size(), true);
        dataGridProvider.setList(users);
//        dataGridProvider.flush();
        dataGrid.redraw();
        dataGrid.setVisibleRange(0,users.size());
		
	}
	@UiHandler({"createButton"})
	public void onClick(ClickEvent event) {
		presenter.editUser(new SimedUser(null, null, false, null, null, null, false, false));
	}

}
