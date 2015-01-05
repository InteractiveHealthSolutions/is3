package com.ihsinformatics.is3web.client.composite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.client.ServerServiceAsync;
import com.ihsinformatics.is3web.model.Participant;

public class ParticipateComposite extends Composite {

	//class variables
	private HorizontalPanel mainPanel=new HorizontalPanel();
	private ServerServiceAsync serverService=GWT.create(ServerService.class);
	private FlexTable flexTable;
	//constructor
	public ParticipateComposite() {
	
		initWidget(mainPanel);
		
		//vertical one panel widgets
		VerticalPanel v1Panel=new VerticalPanel();
		
		Label nameLabel=new Label("Name");
		final TextBox nameTextBox=new TextBox();
		
		Label contactLabel=new Label("Contact");
		final TextBox contactTextBox=new TextBox();
		
		Button saveButton =new Button("Save");
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			Participant	participant=new Participant();
			participant.setParticipantName(nameTextBox.getText());
			participant.setContactNo(contactTextBox.getText());
			
			serverService.saveParticipant(participant, new AddCallback<Boolean>());
			}
		});
		
		v1Panel.add(nameLabel);
		v1Panel.add(nameTextBox);
		v1Panel.add(contactLabel);
		v1Panel.add(contactTextBox);
		v1Panel.add(saveButton);
		v1Panel.setSpacing(10);
		//vertical two panel widgets
		VerticalPanel v2Panel=new VerticalPanel();
		
		HorizontalPanel h2Panel=new HorizontalPanel();
		Label searchLabel=new Label("Search");
		final TextBox searchTextBox=new TextBox();
		h2Panel.add(searchLabel);
		h2Panel.add(searchTextBox);
		//searchLabel.getOffsetHeight();
		//searchTextBox.setVisibleLength(12);
		searchTextBox.setHeight("10px");

		HorizontalPanel h1Panel=new HorizontalPanel();
		final RadioButton idRadioButton=new RadioButton("radiogroup","Id");
		RadioButton nameRadioButton=new RadioButton("radiogroup","Name");
		final RadioButton contactRadioButton=new RadioButton("radiogroup","Contact");
		final RadioButton surveyRadioButton=new RadioButton("radiogroup","Survey");
	
		h1Panel.add(idRadioButton);
		h1Panel.add(nameRadioButton);
		h1Panel.add(contactRadioButton);
		h1Panel.add(surveyRadioButton);
		idRadioButton.setValue(true);
		
		
		Button searchButton=new Button("Search");
		searchButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(idRadioButton.getValue())
				{
					
					serverService.findParticipantById(Integer.parseInt(searchTextBox.getText()), new ParticipantCallback<Participant>());
				}
				else if (contactRadioButton.getValue())
				{
					serverService.findParticipantByContactNo(searchTextBox.getText(),new ParticipantCallback<Participant>() );
					
				}
				else if(surveyRadioButton.getValue())
				{
			
					serverService.findParticipantBySurvey(searchTextBox.getText(), new ParticipantCallback<List<Participant>>());
				}
			}
		});
		HorizontalPanel h3Panel=new HorizontalPanel();
		Label spaceLabel=new Label("                     ");
		h3Panel.add(spaceLabel);
		h3Panel.add(searchButton);
		
		v2Panel.add(h2Panel);
		v2Panel.add(h1Panel);
		v2Panel.add(h3Panel);
		v2Panel.setSpacing(10);
		
		//table
		 flexTable=new FlexTable();
		v2Panel.add(flexTable);
		 //add vertical panels to main panel\
		mainPanel.setSpacing(10);
		mainPanel.add(v1Panel);
		mainPanel.add(v2Panel);
		
	}//end of  constructor
	
	//method setting table
	private void setTable(List<Participant> list)
	{
		flexTable.setText(0, 1, "Name");
		flexTable.setText(0, 2, "Contact");
		flexTable.setText(0, 3, "blacklisted");
		int row=1;
		for (Participant p: list)
		{
			final TextBox nameTextBox=new TextBox();
			nameTextBox.setText(p.getParticipantName());
			flexTable.setWidget(row, 1, nameTextBox);
			final TextBox contactTextBox=new TextBox();
			contactTextBox.setText(p.getContactNo());
			flexTable.setWidget(row, 2, contactTextBox);
			final ListBox blackListBox=new ListBox();
			blackListBox.addItem("yes","1");
			blackListBox.addItem("no","0");
			if(p.getIsBlacklisted())
			{
				blackListBox.setSelectedIndex(0);
				
			}
			else {
				blackListBox.setSelectedIndex(1);
			}
			flexTable.setWidget(row, 3, blackListBox);
		final int pId=p.getParticipantId();
			Button updateButton=new Button("Update");
			updateButton.addClickHandler(new ClickHandler() {
				
					@Override
				public void onClick(ClickEvent event) {
						Participant participant=new Participant();
						participant.setParticipantId(pId);
						participant.setParticipantName(nameTextBox.getText());
						participant.setContactNo(contactTextBox.getText());
						participant.setIsBlacklisted(Boolean.parseBoolean(blackListBox.getValue(blackListBox.getSelectedIndex())));
							
						
						participant.setDateRegistered(new Date());
					
						serverService.updateParticipant(participant, new UpdateCallback());
					
				}
			});
			flexTable.setWidget(row, 4, updateButton);
		}
	}
	
	// Participant callback
	
	public  class ParticipantCallback<T> implements AsyncCallback<T>
	{

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(T result) {
			if(result==null)
			{
				Window.alert("Record not found!");
			}
			else if(result instanceof Participant)
			{
				Participant p=(Participant)result;
				List<Participant> list=new ArrayList<Participant>();
				list.add(p);
				setTable(list);
				//Window.alert(p.getParticipantName());
				
			}
			else if(result instanceof List)
			{
				List<Participant> list=(List<Participant>)result;
				setTable(list);
			}
			
		}
		
		
	}
	
	
	
}
