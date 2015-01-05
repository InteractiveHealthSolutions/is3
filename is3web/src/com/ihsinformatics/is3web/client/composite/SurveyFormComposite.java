package com.ihsinformatics.is3web.client.composite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.client.ServerServiceAsync;
import com.ihsinformatics.is3web.model.Survey;

public class SurveyFormComposite extends Composite {

	private HorizontalPanel hSplitPanel =new  HorizontalPanel();
	private ListBox surveyListBox;
	int _id=0;
	//private GreetingServiceAsync messageService = GWT.create(GreetingService.class);
	private ServerServiceAsync serverService=GWT.create(ServerService.class);
	private  TextBox surveyerNameTextBox,surveyNameTextBox,initTextBox;
	private DateBox startDateBox,endDateBox;
	public SurveyFormComposite(List<Survey> list) {
	      initWidget(hSplitPanel);
	      
	      hSplitPanel.setSpacing(20);
	      
		surveyListBox=new ListBox();
		surveyListBox.setHeight("75px");
		surveyListBox.setWidth("100px");
		surveyListBox.setVisibleItemCount(10);
		setList(list);
	
		Label surveysLabel=new Label("Surveys");
		
		VerticalPanel verticalPanel1=new VerticalPanel();

		verticalPanel1.add(surveysLabel);
		
		verticalPanel1.add(surveyListBox);
		verticalPanel1.setSpacing(10);
		
		VerticalPanel verticalPanel2=new VerticalPanel();
		Label surveyerLabel=new Label("Surveyer ");
		 surveyerNameTextBox=new TextBox();
		verticalPanel2.add(surveyerLabel);
		verticalPanel2.add(surveyerNameTextBox);
		
		
		Label surveyLabel=new Label("Survey Name");
		surveyNameTextBox=new TextBox();
		verticalPanel2.add(surveyLabel);
		verticalPanel2.add(surveyNameTextBox);
		
		Label startDateLabel=new Label("Start Date : ");
		// Create a DateBox
		DateTimeFormat dateFormat =DateTimeFormat.getFormat("dd-MM-yyyy");
		 startDateBox =new DateBox(); 
		//startDateBox.setWidth("25");
		startDateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		verticalPanel2.add(startDateLabel);
		verticalPanel2.add(startDateBox);
		
		Label endDateLabel=new Label("End Date: ");
		// Create a DateBox
		//DateTimeFormat dateFormat =DateTimeFormat.getFormat("dd-MM-yyyy");
		endDateBox =new DateBox(); 
		//startDateBox.setWidth("25");
		endDateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		Label initialText =new Label("initial Text");
		 initTextBox=new TextBox();
		
		verticalPanel2.add(endDateLabel);
		verticalPanel2.add(endDateBox);
		verticalPanel2.add(initialText);
		verticalPanel2.add(initTextBox);
		
		
		HorizontalPanel hPanel=new HorizontalPanel();
		final ToggleButton toggleButton =new ToggleButton("Create Survey");
	
		Button saveButton=new Button("Save");
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
			if(toggleButton.isDown())
			{
//				if(surveyNameTextBox.getText().equalsIgnoreCase("") || initTextBox.getText().equalsIgnoreCase(""))
//				{
//				Window.alert("survey name and init text cannot be null");	
//				return ;	
//				}
			//startDateBox.getValue();
				Survey survey=new Survey();
				survey.setSurveyName(surveyNameTextBox.getText());
				survey.setDateStart(startDateBox.getValue());
				survey.setDateExpire(endDateBox.getValue());
				survey.setIsExpired(false);
				survey.setInitText( initTextBox.getText());
				if(validate().equals(""))
				{
					//System.out.println(validate());
				
					serverService.saveSurvey(survey, new AddCallback<Boolean>());
				}
		
				else{
					getDialog("Fields missing", validate()+"are missing fields").show()
					;
					
				}
			}
			
			else
			{
				int id=	Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex()));
				Survey survey=new Survey();
				survey.setSurveyId(id);
				survey.setSurveyName(surveyNameTextBox.getText());
				
				//System.out.println(startDateBox.getValue());
				survey.setDateStart(startDateBox.getValue());
				survey.setDateExpire(endDateBox.getValue());
				//System.out.println(endDateBox.getValue());
				survey.setIsExpired(false);
				survey.setInitText( initTextBox.getText());
			
				if(surveyNameTextBox.getText().equalsIgnoreCase("") || initTextBox.getText().equalsIgnoreCase(""))
					{
					Window.alert("survey name and init text cannot be null");	
					return ;	
					}
			serverService.updateSurvey(survey, new AsyncCallback<Boolean>(
					) {
				
				@Override
				public void onSuccess(Boolean result) {
					if (result)
					{
						Window.alert("Record updated");
						
					}
					else{
						
						Window.alert("Record not updated");
					}
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
				
			}
				
			}
		});
		Button deleteButton =new Button("Delete");
		deleteButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int id=	Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex()));
				Survey survey=new Survey();
				survey.setSurveyId(id);
				survey.setSurveyName(surveyNameTextBox.getText());
				survey.setDateStart(startDateBox.getValue());
				survey.setDateExpire(endDateBox.getValue());
				survey.setIsExpired(false);
				survey.setInitText( initTextBox.getText());
				serverService.deleteSurvey(survey, new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
					if(result){
						Window.alert("record deleted");
					}else{
						
						Window.alert("record not deleted");
					}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						//caught.
						
					}
				});
				
			}
		});
		 
		Button closeButton =new Button("Close");
		deleteButton.addClickHandler(new ClickHandler() {
			
			@Override
			
			public void onClick(ClickEvent event) {
				MenuComposite.clearContent();
				
			}
		});
		
		verticalPanel2.setSpacing(10);
		hPanel.add(toggleButton);
		hPanel.add(saveButton);
		hPanel.add(deleteButton);
		hPanel.add(closeButton);
		hPanel.setSpacing(10);
		verticalPanel2.add(hPanel);
		hSplitPanel.add(verticalPanel1);
		hSplitPanel.add(verticalPanel2);
		hSplitPanel.setSpacing(10);
	surveyListBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
		int id=	Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex()));
		serverService.findSurveyById(id, new AsyncCallback<Survey>() {
			
			@Override
			public void onSuccess(Survey result) {
				surveyerNameTextBox.setText(result.getSurveyer());
				surveyNameTextBox.setText(result.getSurveyName());
				startDateBox.setValue(result.getDateStart());
				endDateBox.setValue(result.getDateExpire());
				initTextBox.setText(result.getInitText());
				_id=result.getSurveyId();
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Server response error");
				
			}
		});
			
			}
		});
	}
	private void setList(List<Survey> list)
	{
	 for(Survey s: list)
	 {
		 surveyListBox.addItem(s.getInitText(), s.getSurveyId()+"");
		 
	 }
		
	}
	
	private String validate()
	{
		List<String> fields = new ArrayList<>();
		
		if(surveyerNameTextBox.getText().isEmpty())	
		{
			fields.add("surveyer name field ");
			
		}
		if(surveyNameTextBox.getText().isEmpty())	
		{
			fields.add("survey name field ");
		
		}
		//System.out.println(startDateBox.getValue().toString());
		if(startDateBox.getValue()==null)	
		{
			fields.add("start date field ");
			
		}
		if(endDateBox.getValue()==null)	
		{
			fields.add("end date field ");
			
		}
		if(initTextBox.getText().isEmpty())
		{
			fields.add("init field ");
		}
		//System.out.println(fields.toString());
		StringBuilder ss=new StringBuilder();
		
		for(int i=0;i<fields.size()-1;i++)
		{
			ss.append(fields.get(i)+",");
		}
	return	ss.toString();
	}
	
	private DialogBox getDialog(String title,String message){
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
