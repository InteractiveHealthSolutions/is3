package com.ihsinformatics.is3web.client.composite;

import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.MessageSend;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.asm.tree.IntInsnNode;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.client.ServerServiceAsync;
import com.ihsinformatics.is3web.model.Question;
import com.ihsinformatics.is3web.model.Survey;

public class MenuComposite extends Composite {

	private static VerticalPanel verticalPanel=new VerticalPanel();
	private static	VerticalPanel contentVerticalPanel=new VerticalPanel();

	private ServerServiceAsync serverService=GWT.create(ServerService.class);
	
	
	public MenuComposite() {
	initWidget(verticalPanel);

	verticalPanel.setWidth("100%");
	verticalPanel.add(getMenu());
	//verticalPanel.add(contentVerticalPanel);
	//verticalPanel.setWidth("100%");
	verticalPanel.addStyleName("demo-MenuItem");
	//contentVerticalPanel.clear();
	//Dashboard d=new Dashboard();
	//contentVerticalPanel.add(d);
	//verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	verticalPanel.add(contentVerticalPanel);
	showDashboard();
	}//end of constructor
	
	
	private MenuBar getMenu()
	{
		MenuBar setupMenuBar=new MenuBar(true);
		setupMenuBar.addItem("Survey", new Command(){

			@Override
			public void execute() {
				serverService.findSurveys(new AsyncCallback<List<Survey>>() {
					
					@Override
					public void onSuccess(List<Survey> result) {
						showSurveyForm(result);
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				//serverService.findSurveys(callback)
				System.out.println("survey form clickd");
			}});
		
		setupMenuBar.addItem("Questions/Answers", new Command(){

			@Override
			public void execute() {
				serverService.findSurveys(new AsyncCallback<List<Survey>>() {
					
					@Override
					public void onSuccess(List<Survey> result) {
						showQuestionForm(result);
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}});
		
		MenuBar formsMenuBar=new MenuBar(true);
		formsMenuBar.addItem("Participants", new Command(){

			@Override
			public void execute() {
				showParticipate();
				
			}});
		formsMenuBar.addItem("Transaction", new Command(){

			@Override
			public void execute() {
				
				Window.open("Transaction.jsp","_new", "enabled");
				showTransaction();
				
			}});		
		MenuBar reportMenuBar=new MenuBar(true);
		reportMenuBar.addItem("Reports", new Command() {
			
			@Override
			public void execute() {
				showReport();
				
			}
		});
		
		MenuBar helpMenuBar=new MenuBar(true);
		
		MenuBar mainMenuBar=new MenuBar();
		mainMenuBar.setAutoOpen(true);
		mainMenuBar.setAnimationEnabled(true);
	//	mainMenuBar.setWidth("80");
		//mainMenuBar.setWidth("50%");//gwt-MenuBar 
	//	mainMenuBar.addStyleName("demo-MenuItem");
		
		mainMenuBar.addItem("Setup", setupMenuBar);
		mainMenuBar.addItem("Forms", formsMenuBar);
		mainMenuBar.addItem("Reports", reportMenuBar);
		mainMenuBar.addItem("Help", helpMenuBar);
		
		return mainMenuBar;
	}//end of method
	
	public static void showSurveyForm(List<Survey> surveyList)
	{
		
		contentVerticalPanel.clear();
		
	SurveyFormComposite surveyPage=new SurveyFormComposite(surveyList);
	contentVerticalPanel.add(surveyPage);
	//contentVerticalPanel.setWidth("100%");
	verticalPanel.add(contentVerticalPanel);
		
	}//end of the method
	
	public static void showQuestionForm(List<Survey> list)
	{
		
		contentVerticalPanel.clear();
	QuestionFormComposite questionPage=new QuestionFormComposite(list);
	contentVerticalPanel.add(questionPage);
	//contentVerticalPanel.setWidth("100%");
	verticalPanel.add(contentVerticalPanel);
		
	}//end of method

	public static void showParticipate()
	{
		contentVerticalPanel.clear();
		ParticipateComposite participatePage=new ParticipateComposite();
		contentVerticalPanel.add(participatePage);
		//contentVerticalPanel.setWidth("100%");
		verticalPanel.add(contentVerticalPanel);
		
	}//end of method
	
	public static void showTransaction()
	{
		contentVerticalPanel.clear();
		TransactionComposite transactionPage=new TransactionComposite();
		contentVerticalPanel.add(transactionPage);
		//contentVerticalPanel.setWidth("100%");
		verticalPanel.add(contentVerticalPanel);
	}// end f method
	public static void clearContent()
	{
		contentVerticalPanel.clear();
		
	}//end of method

	public static void showReport()
	{
		contentVerticalPanel.clear();
		ReportComposite transactionPage=new ReportComposite();
		contentVerticalPanel.add(transactionPage);
		//contentVerticalPanel.setWidth("100%");
		verticalPanel.add(contentVerticalPanel);
	}// end f method
	
	
	public static void showDashboard()
	{
		contentVerticalPanel.clear();
		DashboardComposite d=new DashboardComposite();
		contentVerticalPanel.add(d);
		//contentVerticalPanel.setWidth("100%");
		verticalPanel.add(contentVerticalPanel);
	}// 
		
	
}

