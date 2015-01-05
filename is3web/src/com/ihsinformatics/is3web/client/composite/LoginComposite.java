package com.ihsinformatics.is3web.client.composite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;

import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.client.ServerServiceAsync;
import com.ihsinformatics.is3web.shared.IRZ;

public class LoginComposite extends Composite {

	
	private final ServerServiceAsync serverService = GWT
			.create(ServerService.class);
	
	
	public LoginComposite() {
	initWidget(getForm());
		
	}
	
	public FormPanel getForm(){
		final FormPanel formPanel=new FormPanel();
		formPanel.addStyleName("smart-green");
		VerticalPanel verticalPanel=new VerticalPanel();
		HTML h2=new HTML("<h2 >Login Page</h2>");
		h2.setStyleName("smart-green-h2");
		verticalPanel.add(h2);
		//Label loginLabel=new Label(new HTML("<h2></h2>"));
		formPanel.setAction("/greet");
		formPanel.setMethod("POST");//smart-green-label
		//HorizontalPanel h1Panel=new HorizontalPanel();
		Label userNameLabel=new Label("User Name : ");
		userNameLabel.setStyleName("smart-green-label");
		final TextBox userNameTextBox=new TextBox();
		userNameTextBox.setName("user_get");
		userNameTextBox.setHeight("10px");
		verticalPanel.add(userNameLabel);
		verticalPanel.add(userNameTextBox);
		verticalPanel.setSpacing(10);
		userNameTextBox.setFocus(true);
		
		//HorizontalPanel h2Panel=new HorizontalPanel();
		Label passwordLabel=new Label("Password : ");
		passwordLabel.setStyleName("smart-green-label");
		final PasswordTextBox passwordTextBox=new PasswordTextBox();
		passwordTextBox.setName("password_get");
		passwordTextBox.setHeight("10px");
		//verticalPanel.setSpacing(10);
		verticalPanel.add(passwordLabel);
		verticalPanel.add(passwordTextBox);
		
		Button loginButton =new Button("Login");
		loginButton.setStyleName("smart-green-button");
		loginButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			if(userNameTextBox.getText().equalsIgnoreCase(""))
			{
				getDialog("Field missing","please enter User name!").show();
				userNameTextBox.setFocus(true);
				
			}
			else if (passwordTextBox.getText().equalsIgnoreCase("")){
				
				getDialog("Field missing","please enter password!").show();
				passwordTextBox.setFocus(true);
			}
			else
			{
				formPanel.submit();
			}
			}
		});
		formPanel.addSubmitHandler(new SubmitHandler() {
			
			@Override
			public void onSubmit(SubmitEvent event) {
	
			
				serverService.login(userNameTextBox.getText(), passwordTextBox.getText(),new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						if(result)
						{
							//getDialog("Welcome to SMS Survey","Login successfull").show();
							//System.out.println("reports :"+IRZ.getReportsDirectoryName());
							MainComposite.showMenuPage();
						}
						else {
							getDialog("Wrong Detail","Wrong User or Password ").show();
	
							
						}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						getDialog("Server response error","server not responding  error!"+caught.getMessage()).show();
						
					}
				});
			}
		});
		//verticalPanel.add(h1Panel);
	//	verticalPanel.add(h2Panel);
		verticalPanel.add(loginButton);
		formPanel.setWidget(verticalPanel);
		return formPanel;
	}
	
	public DialogBox getDialog(String title,String message){
		// Create the popup dialog box
				final DialogBox dialogBox = new DialogBox();
				dialogBox.setText(title);
				dialogBox.setAnimationEnabled(true);
	
				final Button closeButton = new Button("Close");
				VerticalPanel dialogVPanel = new VerticalPanel();
				dialogVPanel.addStyleName("dialogVPanel");
				dialogVPanel.add(new HTML("<b>"+message +"</b>"));
				
				//dialogVPanel.add(textToServerLabel);
				//dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
				//dialogVPanel.add(serverResponseLabel);
				dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
				dialogVPanel.add(closeButton);
				dialogBox.setWidget(dialogVPanel);
		
				// Add a handler to close the DialogBox
				closeButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						dialogBox.hide();
					//	sendButton.setEnabled(true);
					//	sendButton.setFocus(true);
					}
				});
				dialogBox.setPopupPosition(500, 200);
		return dialogBox;
	}
	
	
	
}
