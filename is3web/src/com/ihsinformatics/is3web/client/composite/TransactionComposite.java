package com.ihsinformatics.is3web.client.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

import com.google.gwt.aria.client.RadiogroupRole;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
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
import com.gwtext.client.widgets.form.TextField;
import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.client.ServerServiceAsync;
import com.ihsinformatics.is3web.model.CollectionPairs;
import com.ihsinformatics.is3web.model.Participant;
import com.ihsinformatics.is3web.model.Transaction;
import com.ihsinformatics.is3web.shared.RegexUtil;

public class TransactionComposite extends Composite {

	//class variables
	private VerticalPanel mainPanel=new VerticalPanel();
	private RadioButton surveyRadioButton,participantRadioButton;
	private ListBox searchByListBox;
	private TextBox searchTextBox;
	private ServerServiceAsync serverService=GWT.create(ServerService.class);
	private FlexTable flexTable;
	private ListBox typeListBox;
	private VerticalPanel v1Panel=new VerticalPanel();
	private HorizontalPanel hp ;
	
	
	//constructor
	public TransactionComposite() {
	//initialing mainPanel
		initWidget(mainPanel);
	
	Label searchLabel=new Label ("Search");
	searchTextBox=new TextBox();
	
	flexTable=new FlexTable();
	 //first horizontal panel
	HorizontalPanel h1Panel=new HorizontalPanel();
	 
	surveyRadioButton =new RadioButton("searchtype","Survey");
	participantRadioButton =new RadioButton("searchtype","Participant");
	surveyRadioButton.setValue(true);
	h1Panel.add(surveyRadioButton);
	h1Panel.add(participantRadioButton);
	
	surveyRadioButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
		
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			setSearchByListBox();
			
		}
	});// end of surveyradioButton annnooumousy class
	
	participantRadioButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
		
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			setSearchByListBox();
			
		}
	});//end of participantradiobutton annouymous class
	
	Label searchByLabel=new Label("Search by");
	searchByListBox=new ListBox();
		
	Label typeLabel=new Label("Type");
	typeListBox=new ListBox();
	typeListBox.addItem("Complete", "C");
	typeListBox.addItem("InComplete", "I");
	typeListBox.addItem("Pending", "P");
	
	Button searchButton=new Button("Search");
	searchButton.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
		if(validator())
		{
			if(surveyRadioButton.getValue())
			{
				if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("sname"))
				{
					//String value=.matches(RegexUtil.);
					if(typeListBox.getValue(typeListBox.getSelectedIndex()).equalsIgnoreCase("C")){
						
						serverService.findCompleteTransactionsBySurveyName(searchTextBox.getText(),  new  CollectionCallback());
						}
						else if(typeListBox.getValue(typeListBox.getSelectedIndex()).equalsIgnoreCase("I"))
						{
							
						}
						else if(typeListBox.getValue(typeListBox.getSelectedIndex()).equalsIgnoreCase("P"))
						{
							serverService.findPendingTransactionsBySurveyName(searchTextBox.getText(), new  CollectionCallback());
						}
						
					
				}
				else if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("surveyer"))
				{
					
				}
				else if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("sid"))
				{
					if(typeListBox.getValue(typeListBox.getSelectedIndex()).equalsIgnoreCase("C")){
						
					serverService.findCompleteTransactionsBySurveyId(Integer.parseInt(searchTextBox.getText()), new  CollectionCallback());
					}
					else if(typeListBox.getValue(typeListBox.getSelectedIndex()).equalsIgnoreCase("I"))
					{
						serverService.findIncompleteTransactionBySurveyId(Integer.parseInt(searchTextBox.getText()), new  CollectionCallback());
					}
					else if(typeListBox.getValue(typeListBox.getSelectedIndex()).equalsIgnoreCase("P"))
					{
						serverService.findPendingTransactionBySurveyId(Integer.parseInt(searchTextBox.getText()), new  CollectionCallback());
					}
					
				}
				
			}
			else if(participantRadioButton.getValue())
			{
				if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("pname"))
				{
					serverService.findTransactionByParticipantName(searchTextBox.getText(),new TransactionCallback<List<CollectionPairs>>() );
				}
				else if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("contact"))
				{
				serverService.findTransactionByParticipantContact(searchTextBox.getText(),new TransactionCallback<List<CollectionPairs>>() );
					
				}
				else if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("pid"))
				{
					Participant participant=new Participant();
					participant.setParticipantId(Integer.parseInt(searchTextBox.getText()));
					//serverService.findTransactionsByParticipant(participant, new TransactionCallback<Transaction[]>());
					serverService.findTransactionByParticipant(participant.getParticipantId(), new TransactionCallback<List<CollectionPairs>>() );
				}
			}//end of outer if-else control
			
		}
			
		}
	});//end of searchbutton annouymous class
	
	//add widget to the mainPanel
	
	mainPanel.add(h1Panel);
	mainPanel.add(searchByLabel);
	mainPanel.add(searchByListBox);
	mainPanel.add(typeLabel);
	mainPanel.add(typeListBox);
	mainPanel.add(searchLabel);
	mainPanel.add(searchTextBox);
	mainPanel.add(searchButton);
	
	setSearchByListBox();
	}//end of constructor

	private void setSearchByListBox()
	{
		searchByListBox.clear();
		if(surveyRadioButton.getValue())
		{
			searchByListBox.addItem("name","sname");
			//searchByListBox.addItem("surveyer","surveyer");
			searchByListBox.addItem("Id","sid");
			typeListBox.setVisible(true);
		}
		else if(participantRadioButton.getValue())
		{
			searchByListBox.addItem("name","pname");
			searchByListBox.addItem("contact","contact");
			searchByListBox.addItem("Id","pid");
			typeListBox.setVisible(false);
			
		}
	}//end of method
	
	private boolean validator()
	{ boolean check =false;
		if(surveyRadioButton.getValue())
		{
			if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("sname"))
			{
				//String value=.matches(RegexUtil.);
				check=RegexUtil.isName(searchTextBox.getText());
				if(!check)
				{
					Window.alert("plx correct enter a name");
				}
				
			}
			else if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("surveyer"))
			{
				check= RegexUtil.isWord(searchTextBox.getText());
				if(!check)
				{
					Window.alert("plx correct enter a name");
				}
			}
			else if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("sid"))
			{
				check= RegexUtil.isNumeric(searchTextBox.getText(), false);
				if(!check)
				{
					Window.alert("plx number");
				}
			}
		}
		else if(participantRadioButton.getValue())
		{
			if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("pname"))
			{
				//String value=.matches(RegexUtil.);
				check= RegexUtil.isName(searchTextBox.getText());
				if(!check)
				{
					Window.alert("plx correct enter a name");
				}
			}
			else if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("contact"))
			{
				check= RegexUtil.isContactNumber(searchTextBox.getText());
				if(!check)
				{
					Window.alert("plx correct contact");
				}
				
			}
			else if(searchByListBox.getValue(searchByListBox.getSelectedIndex()).equalsIgnoreCase("pid"))
			{
				check= RegexUtil.isNumeric(searchTextBox.getText(), false);
				if(!check)
				{
					Window.alert("plx number");
				}
				
			}
		}//end of outer if-else control
		
		
		
		return check;
	}//end of method

	private void setTableForParticipant(List<CollectionPairs> list)
	{
		flexTable.clear();
		flexTable.removeAllRows();
		v1Panel.clear();
		//flexTable.addStyleName("CSS_Table_Example ");
		if(list.size()<=0)
		{
			Window.alert("No record found!");
			return;
		}
		
		HorizontalPanel h1Panel=new HorizontalPanel();
		Label nameLabel=new Label("Name : " +list.get(0).getParticipant().getParticipantName());
		Label contactLabel=new Label ("Contact : "+ list.get(0).getParticipant().getContactNo());
		h1Panel.add(nameLabel);
		h1Panel.add(contactLabel);
		v1Panel.add(h1Panel);
		mainPanel.add(v1Panel);
		
//		flexTable.setText(0, 0, "Survey");
//		flexTable.setText(0, 1, "Question");
//		flexTable.setText(0, 2, "Answered");
//		flexTable.setText(0, 3, "Date_created");
//		flexTable.setText(0, 4, "Date_Answered");
//		int row=1;
//		for(CollectionPairs p:list)
//		{
//			flexTable.setText(row, 0, p.getSurvey().getSurveyName());
//			flexTable.setText(row, 1, p.getQuestion().getQuestionText());
//			flexTable.setText(row, 2, p.getTransaction().getAnswer());
//			flexTable.setText(row, 3, p.getTransaction().getDateCreated()+"");
//			if(p.getTransaction().getDateAnswered()==null)
//			{
//				flexTable.setText(row, 4, "Pending");
//			}
//			else
//			{
//			flexTable.setText(row, 4, p.getTransaction().getDateAnswered()+"");
//			}
//			row++;
//		}
		mainPanel.add(loadTableForParticipant(list));
		
	}//end of method
	
	
	//set table for Survey
	private void setTableForSurvey(List<CollectionPairs> list)
	{
//		flexTable.clear();
//		flexTable.removeAllRows();
//		flexTable.addStyleName("CSS_Table_Example ");
		v1Panel.clear();
		if(list.size()<=0)
		{
			Window.alert("No record found!");
			return;
		}
		System.out.println("Size ="+list.size());
		String name=null;
		if(list.get(0).getSurvey().getSurveyName().equalsIgnoreCase(""))
		{
		name ="not defined"	;
		}
		else{
			
			name=list.get(0).getSurvey().getSurveyName();
		}
		HorizontalPanel h1Panel=new HorizontalPanel();
		Label nameLabel=new Label("Survey Name : " +name);
		Label contactLabel=new Label ("    Initials : "+ list.get(0).getSurvey().getInitText());
		h1Panel.add(nameLabel);
		h1Panel.add(contactLabel);
		h1Panel.setSpacing(10);
		HorizontalPanel h2Panel=new HorizontalPanel();
		Label sDateLabel=new Label("Start Date : " +list.get(0).getSurvey().getDateStart()+"");
		Label eDateLabel=new Label ("    End Date : "+ list.get(0).getSurvey().getDateExpire()+"");
		h2Panel.add(sDateLabel);
		h2Panel.add(eDateLabel);
		h2Panel.setSpacing(10);
	
		v1Panel.add(h1Panel);
		v1Panel.add(h2Panel);
		mainPanel.add(v1Panel);
		
//		flexTable.setText(0, 0, "Participant");
//		flexTable.setText(0, 1, "Question");
//		flexTable.setText(0, 2, "Answered");
//		flexTable.setText(0, 3, "Date_created");
//		flexTable.setText(0, 4, "Date_Answered");
//		int row=1;
//		for(CollectionPairs p:list)
//		{
//			flexTable.setText(row, 0, p.getParticipant().getParticipantName());
//			flexTable.setText(row, 1, p.getQuestion().getQuestionText());
//			flexTable.setText(row, 2, p.getTransaction().getAnswer());
//			flexTable.setText(row, 3, p.getTransaction().getDateCreated()+"");
//		
//			
//			flexTable.setText(row, 4, p.getTransaction().getDateAnswered()+"");
//			
//			row++;
//		}
		mainPanel.add(loadTable(list));
		
		
	}//end of method
	
	  public HorizontalPanel loadTable(final List<CollectionPairs> list) {

		  if(hp!=null)
		  hp.clear();
		  
		    // Create a CellTable.
		    final CellTable<CollectionPairs> table = new CellTable<CollectionPairs>();

		    // Create name column.
		    TextColumn<CollectionPairs> nameColumn = new TextColumn<CollectionPairs>() {
		      @Override
		      public String getValue(CollectionPairs c) {
		        return c.getParticipant().getContactNo();
		      }
		    };

		    // Make the name column sortable.
		    //nameColumn.setSortable(true);

		    // Create question column.
		    TextColumn<CollectionPairs> questionColumn = new TextColumn<CollectionPairs>() {
		      @Override
		      public String getValue(CollectionPairs c) {
		        return c.getQuestion().getQuestionText();
		      }
		    };
			
			// Create answer column.
		    TextColumn<CollectionPairs> answerColumn = new TextColumn<CollectionPairs>() {
		      @Override
		      public String getValue(CollectionPairs c) {
		        return c.getTransaction().getAnswer();
		      }
		    };
		    DateTimeFormat dateFormat=DateTimeFormat.getFormat("dd-MMM-yyyy hh:mm");
		    // Add a date column to show the birthday.
		    DateCell dateCell = new DateCell(dateFormat);
		    
		    Column<CollectionPairs, Date> dateCreatedColumn = new Column<CollectionPairs, Date>(dateCell) {
		      @Override
		      public Date getValue(CollectionPairs object) {
		        return object.getTransaction().getDateCreated();
		      }
		    };
			
			    // Add a date column to show the birthday.
		    DateCell dateCell1 = new DateCell(dateFormat);
		    Column<CollectionPairs, Date> dateAnsweredColumn = new Column<CollectionPairs, Date>(dateCell1) {
		      @Override
		      public Date getValue(CollectionPairs object) {
		        return object.getTransaction().getDateAnswered();
		      }
		    };
		   
		 // Add a text column to show the address.
//		    TextColumn<CollectionPairs> villeColumn = new TextColumn<CollectionPairs>() {
		//      @Override
		  //    public String getValue(CollectionPairs object) {
		    //    return object.getTransaction().getDateAnswered();
		     // }
		    //};
		    //villeColumn.setSortable(true);
		   

		    // Add the columns.
		    table.addColumn(nameColumn, "Participant");
		    table.addColumn(questionColumn, "Question");
		    table.addColumn(answerColumn, "Answered");
		    table.addColumn(dateCreatedColumn, "Date created");
			table.addColumn(dateAnsweredColumn, "Date answered");

		    // Set the total row count. You might send an RPC request to determine the
		    // total row count.
		    table.setRowCount(list.size(), true);

		    // Set the range to display. In this case, our visible range is smaller than
		    // the data set.
		    table.setVisibleRange(0, 10);

		    // Create a data provider.
		    AsyncDataProvider<CollectionPairs> dataProvider = new AsyncDataProvider<CollectionPairs>() {
		      @Override
		      protected void onRangeChanged(HasData<CollectionPairs> display) {
		        final Range range = display.getVisibleRange();

		        // Get the ColumnSortInfo from the table.
		        final ColumnSortList sortList = table.getColumnSortList();

		        // This timer is here to illustrate the asynchronous nature of this data
		        // provider. In practice, you would use an asynchronous RPC call to
		        // request data in the specified range.
		        new Timer() {
		          @Override
		          public void run() {
		            int start = range.getStart();
		            int end = start + range.getLength();
		            // This sorting code is here so the example works. In practice, you
		            // would sort on the server.
		            Collections.sort(list, new Comparator<CollectionPairs>() {
		              public int compare(CollectionPairs o1, CollectionPairs o2) {
		                if (o1 == o2) {
		                  return 0;
		                }

		                // Compare the ville columns.
		                int diff = -1;
		                if (o1 != null) {
		                  diff = (o2 != null) ? o1.getParticipant().getParticipantId().compareTo(o2.getParticipant().getParticipantId()) : 1;
		                }
		                return sortList.get(0).isAscending() ? diff : -diff;
		              }
		            });
		            if(list.size()<10){
		            	end=list.size();
		            }
		            List<CollectionPairs> dataInRange = list.subList(start, end);

		            // Push the data back into the list.
		            table.setRowData(start, dataInRange);
		          }
		        }.schedule(10);
		      }
		    };

		    // Connect the list to the data provider.
		    dataProvider.addDataDisplay(table);

		    
		    	
		    TextBox searchTextBox =new TextBox();
		    
		    
		    SimplePager pager = new SimplePager();
		    pager.setDisplay(table);
		    
		    // Add a ColumnSortEvent.AsyncHandler to connect sorting to the
		    // AsyncDataPRrovider.
		    AsyncHandler columnSortHandler = new AsyncHandler(table);
		    table.addColumnSortHandler(columnSortHandler);

		    // We know that the data is sorted alphabetically by default.
		    table.getColumnSortList().push(nameColumn);

		    
		     hp = new HorizontalPanel();
		    
		    VerticalPanel vp = new VerticalPanel();
		    vp.add(table);
		    vp.add(pager);	 
		    
		    hp.setWidth("100%");
		    hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		    hp.add(vp);
		  
		  return hp;
		    // Add it to the root panel.
		   // RootPanel.get().add(hp);
		  }//end of methiod
	
	  
	  
	  public HorizontalPanel loadTableForParticipant(final List<CollectionPairs> list) {

		  if(hp!=null)
		  hp.clear();
		  
		    // Create a CellTable.
		    final CellTable<CollectionPairs> table = new CellTable<CollectionPairs>();

		    // Create name column.
		    TextColumn<CollectionPairs> nameColumn = new TextColumn<CollectionPairs>() {
		      @Override
		      public String getValue(CollectionPairs c) {
		        return c.getSurvey().getSurveyName();
		      }
		    };

		    // Make the name column sortable.
		    //nameColumn.setSortable(true);

		    // Create question column.
		    TextColumn<CollectionPairs> questionColumn = new TextColumn<CollectionPairs>() {
		      @Override
		      public String getValue(CollectionPairs c) {
		        return c.getQuestion().getQuestionText();
		      }
		    };
			
			// Create answer column.
		    TextColumn<CollectionPairs> answerColumn = new TextColumn<CollectionPairs>() {
		      @Override
		      public String getValue(CollectionPairs c) {
		        return c.getTransaction().getAnswer();
		      }
		    };
		    
		    // Add a date column to show the birthday.
		    DateCell dateCell = new DateCell();
		    Column<CollectionPairs, Date> dateCreatedColumn = new Column<CollectionPairs, Date>(dateCell) {
		      @Override
		      public Date getValue(CollectionPairs object) {
		        return object.getTransaction().getDateCreated();
		      }
		    };
			
			    // Add a date column to show the birthday.
		    DateCell dateCell1 = new DateCell();
		    Column<CollectionPairs, Date> dateAnsweredColumn = new Column<CollectionPairs, Date>(dateCell1) {
		      @Override
		      public Date getValue(CollectionPairs object) {
		        return object.getTransaction().getDateAnswered();
		      }
		    };
		   
		 // Add a text column to show the address.
//		    TextColumn<CollectionPairs> villeColumn = new TextColumn<CollectionPairs>() {
		//      @Override
		  //    public String getValue(CollectionPairs object) {
		    //    return object.getTransaction().getDateAnswered();
		     // }
		    //};
		    //villeColumn.setSortable(true);
		   

		    // Add the columns.
		    table.addColumn(nameColumn, "Survey");
		    table.addColumn(questionColumn, "Question");
		    table.addColumn(answerColumn, "Answered");
		    table.addColumn(dateCreatedColumn, "Date created");
			table.addColumn(dateAnsweredColumn, "Date answered");

		    // Set the total row count. You might send an RPC request to determine the
		    // total row count.
		    table.setRowCount(list.size(), true);

		    // Set the range to display. In this case, our visible range is smaller than
		    // the data set.
		    table.setVisibleRange(0, 10);

		    // Create a data provider.
		    AsyncDataProvider<CollectionPairs> dataProvider = new AsyncDataProvider<CollectionPairs>() {
		      @Override
		      protected void onRangeChanged(HasData<CollectionPairs> display) {
		        final Range range = display.getVisibleRange();

		        // Get the ColumnSortInfo from the table.
		        final ColumnSortList sortList = table.getColumnSortList();

		        // This timer is here to illustrate the asynchronous nature of this data
		        // provider. In practice, you would use an asynchronous RPC call to
		        // request data in the specified range.
		        new Timer() {
		          @Override
		          public void run() {
		            int start = range.getStart();
		            int end = start + range.getLength();
		            // This sorting code is here so the example works. In practice, you
		            // would sort on the server.
		            Collections.sort(list, new Comparator<CollectionPairs>() {
		              public int compare(CollectionPairs o1, CollectionPairs o2) {
		                if (o1 == o2) {
		                  return 0;
		                }

		                // Compare the ville columns.
		                int diff = -1;
		                if (o1 != null) {
		                  diff = (o2 != null) ? o1.getParticipant().getParticipantId().compareTo(o2.getParticipant().getParticipantId()) : 1;
		                }
		                return sortList.get(0).isAscending() ? diff : -diff;
		              }
		            });
		            if(list.size()<10){
		            	end=list.size();
		            }
		            List<CollectionPairs> dataInRange = list.subList(start, end);

		            // Push the data back into the list.
		            table.setRowData(start, dataInRange);
		          }
		        }.schedule(1000);
		      }
		    };

		    // Connect the list to the data provider.
		    dataProvider.addDataDisplay(table);

		    
		    SimplePager pager = new SimplePager();
		    pager.setDisplay(table);
		    
		    // Add a ColumnSortEvent.AsyncHandler to connect sorting to the
		    // AsyncDataPRrovider.
		    AsyncHandler columnSortHandler = new AsyncHandler(table);
		    table.addColumnSortHandler(columnSortHandler);

		    // We know that the data is sorted alphabetically by default.
		    table.getColumnSortList().push(nameColumn);

		    
		     hp = new HorizontalPanel();
		    
		    VerticalPanel vp = new VerticalPanel();
		    vp.add(table);
		    vp.add(pager);	 
		    
		    hp.setWidth("100%");
		    hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		    hp.add(vp);
		  
		  return hp;
		    // Add it to the root panel.
		   // RootPanel.get().add(hp);
		  }//end of methiod
	//inner async class
  class	TransactionCallback<T> implements AsyncCallback<T>
	{

	@Override
	public void onFailure(Throwable caught) {
		Window.alert("Record not found!");
		
	}

	@Override
	public void onSuccess(T result) {
		if(result==null)
		{
			Window.alert("No record Found !");
			return;
		}
		if(result instanceof List)
		{
			List l=(List) result;

			if(l.get(0) instanceof CollectionPairs)
			{
				setTableForParticipant((List<CollectionPairs>)result);
			}
			//setTable((List<Transaction>)result);
			
		}
		else if (result instanceof Transaction[])
		{
			
		}
	
		
	}
	}//end of the inner class
	
	//callback for survey search 
		class CollectionCallback implements AsyncCallback<List<CollectionPairs>>
		{

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Record not found!");
				
				
			}

			@Override
			public void onSuccess(List<CollectionPairs> result) {
				if(result==null)
				{
					Window.alert("No record Found !");
					return;
				}
				setTableForSurvey(result);
				
			}
			
		

			
	}//end of the inner class
	
	
	
  }
