package ch.hcuge.simed.sscsuserapp.gwt.client.components;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.extras.notify.client.constants.NotifyType;
import org.gwtbootstrap3.extras.notify.client.ui.Notify;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import ch.hcuge.simed.sscsuserapp.gwt.client.UserApp;
import ch.hcuge.simed.sscsuserapp.gwt.client.bean.RightCenter;
import ch.hcuge.simed.sscsuserapp.gwt.client.bean.SimedUser;
import ch.hcuge.simed.sscsuserapp.gwt.client.editor.BeanKeyValue;
import ch.hcuge.simed.sscsuserapp.gwt.client.editor.RadioGroupEditor;
import ch.hcuge.simed.sscsuserapp.gwt.client.editor.RadioGroupEditor.RadioGroupEditorChanger;
import ch.hcuge.simed.sscsuserapp.gwt.client.editor.YesNoEnum;
import ch.hcuge.simed.sscsuserapp.gwt.client.service.RPCUsersService;


public class UserEditor extends Composite implements Editor<SimedUser>,  RadioGroupEditorChanger<BeanKeyValue> {
	private UserApp presenter;
	private SimedUser user;

	private static UserEditorUiBinder uiBinder = GWT.create(UserEditorUiBinder.class);
	
	public interface UserEditorDriver extends SimpleBeanEditorDriver<SimedUser, UserEditor> {

	}

	interface UserEditorUiBinder extends UiBinder<Widget, UserEditor> {
	}
	@Ignore
	@UiField(provided=true)
	RadioGroupEditor<YesNoEnum> canUseSampleManagerEnum = new RadioGroupEditor<YesNoEnum>(YesNoEnum.list(), "canUseSampleManagerEnum", YesNoEnum.parser, this);


	@Ignore
	@UiField(provided=true)
	RadioGroupEditor<YesNoEnum> canUseAuditTrailEnum = new RadioGroupEditor<YesNoEnum>(YesNoEnum.list(), "canUseAuditTrailEnum", YesNoEnum.parser, this);

	
	@UiField
	TextBox firstName;

	@UiField
	TextBox lastName;
	
	@UiField
	TextBox login;
	
	
	@UiField
	TextBox email;
	
	public UserEditorDriver editorDriver;
	
	
	@UiField UserRightsEditor userCenterEditor;
	
	@Ignore
	@UiField Label active;
	
	
	@UiField
	Button save,disable,sendmail,back;
	
	private boolean isRBDirdy;
	
	public UserEditor(UserApp presenter) {
		this.presenter = presenter;
		initWidget(uiBinder.createAndBindUi(this));
		editorDriver = GWT.create(UserEditorDriver.class);
		editorDriver.initialize(this);
		disable.setEnabled(false);
		sendmail.setEnabled(false);
		back.setText("< Back");
	}



	@Override
	public void onValueChanged(BeanKeyValue value, RadioGroupEditor source) {
		// TODO Auto-generated method stub
//		this.user = getUser();
//		Window.alert(" Recu : " + value + " - comp value : " + canUseSampleManagerEnum.getValue().getCode());
		isRBDirdy = true;
	}
	@UiHandler("disable")
	public void onClickDisable(ClickEvent event) {
		if (isDirty())
		{
			Notify.notify("Please save before disable", NotifyType.INFO);
			return;			
		}
		this.user = getUser();	
		List<RightCenter> rightCenters = new ArrayList<RightCenter>();
		for (RightCenter rightCenter : user.getRightCenters())
		{
			rightCenters.add(rightCenter);
		}
		user.setRightCenters(rightCenters);
		RPCUsersService.Util.getInstance().disable(user, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Notify.notify("Disabled !");
				user.setIsActive(false);
				updateActive();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Notify.notify("User cannot be Disabled. Exception : " + caught.getMessage(), NotifyType.DANGER);
			}
		});		
	}
	@UiHandler("save")
	public void onClickSave(ClickEvent event) {
		this.user = getUser();
		if (user.getRightCenters().size() == 0)
		{
			Notify.notify("User cannot be saved. No right set", NotifyType.DANGER);
			return;
		}
		if (user.getFirstName() == null || user.getLastName() == null ||
				user.getLogin() == null || user.getEmail() == null
		)
		{
			Notify.notify("User cannot be saved. All administrative data must be set", NotifyType.DANGER);
			return;
		}			
		List<RightCenter> rightCenters = new ArrayList<RightCenter>();
		for (RightCenter rightCenter : user.getRightCenters())
		{
			rightCenters.add(rightCenter);
		}
		user.setRightCenters(rightCenters);
		RPCUsersService.Util.getInstance().save(user, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				if (result != null)
				{
					Notify.notify("User cannot be saved. " + result, NotifyType.DANGER);
				}
				else
				{
					Notify.notify("Saved !");
					isRBDirdy = false;
					userCenterEditor.setDirty(false);
					editorDriver.edit(user);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Notify.notify("User cannot be saved. Exception : " + caught.getMessage(), NotifyType.DANGER);
			}
		});
		
	}

	public SimedUser getUser() {
		if (editorDriver.isDirty())
		{
			user = editorDriver.flush();
		}
		user.setCanUseSampleManager(canUseSampleManagerEnum.getValue().isActive());
		user.setCanUseAuditTrail(canUseAuditTrailEnum.getValue().isActive());
		user.setRightCenters(userCenterEditor.getValue());
		return user;
	}



	public void setUser(SimedUser user) {
		this.user = user;
		editorDriver.edit(user);
		canUseSampleManagerEnum.setValue(user.getCanUseSampleManager() == true ? YesNoEnum.YES :YesNoEnum.NO);
		canUseAuditTrailEnum.setValue(user.getCanUseAuditTrail() == true ? YesNoEnum.YES :YesNoEnum.NO);
		userCenterEditor.setValue(user.getRightCenters());
		updateActive();
		isRBDirdy = false;
	}
	
	private void updateActive()
	{
		if (user.getIsActive())
		{
			disable.setEnabled(true);
			sendmail.setEnabled(false);
			active.setText("Yes");
		}
		else
		{
			disable.setEnabled(false);
			sendmail.setEnabled(true);
			active.setText("No");
			
		}		
	}
	@UiHandler("back")
	public void onClickBack(ClickEvent event) {
		presenter.goBackToList();
	}
	@UiHandler("sendmail")
	public void onClickSendMail(ClickEvent event) {
		if (isDirty())
		{
			Notify.notify("Please save before send mail", NotifyType.INFO);
			return;			
		}
		this.user = getUser();
		if (user.getRightCenters().size() == 0)
		{
			Notify.notify("Mail cannot be send. No right set", NotifyType.DANGER);
			return;
		}
		if (user.getFirstName() == null || user.getLastName() == null ||
				user.getLogin() == null || user.getEmail() == null
		)
		{
			Notify.notify("Mail cannot be send. All administrative data must be set", NotifyType.DANGER);
			return;
		}			
		List<RightCenter> rightCenters = new ArrayList<RightCenter>();
		for (RightCenter rightCenter : user.getRightCenters())
		{
			rightCenters.add(rightCenter);
		}
		user.setRightCenters(rightCenters);
		RPCUsersService.Util.getInstance().sendMail(user, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Notify.notify("Welcome mail sent !");
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Notify.notify("Welcome mail was not sent !",NotifyType.DANGER);
				
			}
		});
	}
	private boolean isDirty()
	{
		if (isRBDirdy)
			return true;
		if (editorDriver.isDirty())
			return true;
		if (userCenterEditor.isDirty())
			return true;
		return false;
	}
}
