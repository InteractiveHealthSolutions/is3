package com.ihsinformatics.is3web.client.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import sun.print.resources.serviceui;

import com.gargoylesoftware.htmlunit.protocol.javascript.Handler;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.gwtext.client.widgets.form.TextField;

import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.client.ServerServiceAsync;
import com.ihsinformatics.is3web.model.Answer;
import com.ihsinformatics.is3web.model.AnswerId;
import com.ihsinformatics.is3web.model.CollectionPairs;
import com.ihsinformatics.is3web.model.Question;
import com.ihsinformatics.is3web.model.QuestionId;
import com.ihsinformatics.is3web.model.Survey;

public class QuestionFormComposite extends Composite {

	
	private VerticalPanel v2Panel;
	private HorizontalPanel mainPanel=new HorizontalPanel();
	private ListBox surveyListBox,questionListBox;

	private ServerServiceAsync serverService=GWT.create(ServerService.class);
	private List<Survey> surveyList;
	private FlexTable flexTable;
	private HorizontalPanel hp;
	public QuestionFormComposite(List<Survey> surveyList) {
	
		setCompositeWithSurvey(surveyList);
	}
	

	private void setCompositeWithSurvey(List<Survey> surveyList)
	{
		this.surveyList=surveyList;
		setComposite();
		setSurveyList(surveyList);
	}

	
	private void setComposite()
	{
		initWidget(mainPanel);
		
		VerticalPanel v1Panel=new VerticalPanel();
		Label surveyLabel=new Label("Surveys");
		surveyListBox=new ListBox();
		surveyListBox.setHeight("75px");
		surveyListBox.setWidth("100px");
		surveyListBox.setVisibleItemCount(10);
		flexTable=new FlexTable();
		
		surveyListBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				int id=	Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex()));
				Survey survey=new Survey();
				survey.setSurveyId(id);
				clearQuestionListBox();
				serverService.findQuestionsBySurvey(survey, new AsyncCallback<Question[]>() {
					
					@Override
					public void onSuccess(Question[] result) {
						
						List<Question> list=new ArrayList<Question>();
						for(int i=0; i<result.length;i++){
							list.add(result[i]);
							
						}
						setQuestionList(list);
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
				
				
			}
		});
		
		Label questionsLabel=new Label("Questions");
		questionListBox=new ListBox();
		questionListBox.setHeight("75px");
		questionListBox.setWidth("100px");
		questionListBox.setVisibleItemCount(10);
		
		v1Panel.add(surveyLabel);
		v1Panel.add(surveyListBox);
		v1Panel.add(questionsLabel);
		v1Panel.add(questionListBox);
		v1Panel.setSpacing(10);
		
		v2Panel=new VerticalPanel();
		Label questionLabel=new Label("Question");
		final TextBox questionTextBox=new TextBox();

		Label pathernLabel=new Label("Answer-Pathern");
		final TextBox pathernTextBox=new TextBox();
		
		Label replyLabel=new Label("Reply");
		TextBox replyTextBox=new TextBox();
		
		Label nextQuestionLabel=new Label("Next Question");
		ListBox nextQuestionListBox=new ListBox();
		//TextBox questionTextBox=new TextBox();
		
		v2Panel.add(questionLabel);
		v2Panel.add(questionTextBox);
		v2Panel.add(pathernLabel);
		v2Panel.add(pathernTextBox);
//		v2Panel.add(replyLabel);
//		v2Panel.add(replyTextBox);
//		v2Panel.add(nextQuestionLabel);
//		v2Panel.add(nextQuestionListBox);
		//v2Panel.addStyleName("CSS_Table_Example");
		HorizontalPanel hPanel=new HorizontalPanel();
		
		final ToggleButton createToggleButton=new ToggleButton("Create question");
		Button saveButton=new Button("Save");
		Button deleteButton=new Button("delete");
		Button closeButton=new Button("close");
		final Button addAnswerButton=new Button("Add Answers");
		
		hPanel.add(createToggleButton);
		hPanel.add(saveButton);
		hPanel.add(deleteButton);
		hPanel.add(closeButton);
		hPanel.setSpacing(10);
		v2Panel.setSpacing(10);
		v2Panel.add(hPanel);
		v2Panel.add(addAnswerButton);
		addAnswerButton.setEnabled(false);
		addAnswerButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Survey survey=new Survey();
				
				survey.setSurveyId (Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex())));
				
				serverService.findQuestionsBySurvey(survey, new AsyncCallback<Question[]>() {
					
					@Override
					public void onSuccess(Question[] result) {
					List<Question> list=new ArrayList<Question>();
											
					for(int i=0;i<result.length;i++)
					{
						list.add(result[i]);
						
					}
					
					QuestionId questionId=new QuestionId();
					questionId.setSurveyId(Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex())));
					questionId.setQuestionId(Integer.parseInt(questionListBox.getValue(questionListBox.getSelectedIndex())));			
					addAnswers(questionId,list).show();
					
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});			
			}
		});
	
		mainPanel.add(v1Panel);
		mainPanel.add(v2Panel);
		mainPanel.setSpacing(10);
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(createToggleButton.isDown())
				{
					Question question=new Question();
					QuestionId id =new QuestionId();
				//int id21=	;
					if(Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex()))<0)
					{
						Window.alert("no survey is selected!");
						return;
					}
					id.setSurveyId(Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex())));
					question.setId(id);
					question.setQuestionText(questionTextBox.getText());
					question.setAnswerPattern(pathernTextBox.getText());
					serverService.saveQuestion(question, new AddCallback<Boolean>());
				}
				else {
					Question question=new Question();
					QuestionId id =new QuestionId();
				//	int id21=	;
					id.setSurveyId(Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex())));
					id.setQuestionId(Integer.parseInt(questionListBox.getValue(questionListBox.getSelectedIndex())));
					question.setId(id);
					question.setQuestionText(questionTextBox.getText());
					question.setAnswerPattern(pathernTextBox.getText());
					serverService.updateQuestion(question, new UpdateCallback());	
					
				}
				
			}
		});
questionListBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
			
			int id=	Integer.parseInt(surveyListBox.getValue(surveyListBox.getSelectedIndex()));
			int questionId=Integer.parseInt(questionListBox.getValue(questionListBox.getSelectedIndex()));
			QuestionId questionId1=new QuestionId(id, questionId);
			serverService.findQuestionById(questionId1, new AsyncCallback<Question>() {
				
				@Override
				public void onSuccess(Question result) {
					//System.out.println(result.getQuestionText());
					questionTextBox.setText(result.getQuestionText());
					pathernTextBox.setText(result.getAnswerPattern());
					//replyTextBox.setText(result.get)
					Question question=new Question();
					question.setId(new QuestionId(result.getId().getSurveyId(),result.getId().getQuestionId()));
					question.setAnswerPattern(result.getAnswerPattern());
					question.setQuestionText(result.getQuestionText());
					addAnswerButton.setEnabled(true);
					serverService.findAnswersByQuestion(question, new AsyncCallback<Answer[]>() {
						
						@Override
						public void onSuccess(Answer[] result) {
							List<Answer> ls=new ArrayList<Answer>();
							
							for(int e=0;e<result.length;e++)
							{
								ls.add(result[e]);
							}
							//System.out.println(ls);
							setTable(ls);
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}
					});
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
				
			}
		});
v2Panel.add(flexTable);

	}//end of constructor
	
	
	
	private void setSurveyList(List<Survey> list)
	{
		for(Survey s: list)
		{
			surveyListBox.addItem(s.getInitText(), s.getSurveyId()+"");
			
		}
	}//end of method
	
	private void setQuestionList(List<Question> list)
	{
		for(Question q:list)
		{
			questionListBox.addItem(q.getQuestionText(),q.getId().getQuestionId()+"");
			
		}

	}//end of method
	private void clearQuestionListBox()
	{
		questionListBox.clear();
	}//end of method
	
	private void setTable(List<Answer> answers){
		flexTable.removeStyleName("tftable");
		flexTable.addStyleName("tftable");
		//v2Panel.addAttachHandler();
	//	v2Panel.add(dataTable(answers));
		flexTable.setText(0, 0, "Answer");
		flexTable.setText(0, 1, "Reply");
		flexTable.setText(0, 2, "Next Question");
		flexTable.setText(0,3, "Resets Survey");
		flexTable.setText(0, 4, "Ends Survey");
		
		int row=1;
		for(Answer a: answers)
		{
			flexTable.setText(row, 0, a.getAnswer());
			flexTable.setText(row, 1, a.getReply());
			flexTable.setText(row, 2, a.getNextQuestionId()+"");
			flexTable.setText(row,3, a.getResetsSurvey()+"");
			flexTable.setText(row, 4, a.getEndsSurvey()+"");
			final int answerId=a.getId().getAnswerId();
			final int questionId=a.getId().getQuestionId();
			final int surveyId=a.getId().getSurveyId();
			final Answer a1=a;
			Button deleteButton =new Button("Delete");
			deleteButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					Answer answer=new Answer();
					AnswerId aId=new AnswerId();
					aId.setAnswerId(answerId);
					aId.setQuestionId(questionId);
					aId.setSurveyId(surveyId);
					answer.setId(aId);
				serverService.deleteAnswer(answer, new DeleteCallback() );
					
				}
			});
			Button updateButton =new Button("Update");
			updateButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					Survey survey=new Survey();
					survey.setSurveyId(surveyId);
serverService.findQuestionsBySurvey(survey, new AsyncCallback<Question[]>() {
	
	@Override
	public void onSuccess(Question[] result) {
		List<Question> list=new ArrayList<Question>();
		
		for(int i=0;i<result.length;i++)
		{
			list.add(result[i]);
			
		}
	updateAnswer(a1, list).show();
		
		
	}
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}
});
				}
			});
			flexTable.setWidget(row, 5, updateButton);
			flexTable.setWidget(row,6,deleteButton);
			row++;
			
		}
		
	}//end of the method
	
	
	
	  public HorizontalPanel loadTable(final List<Answer> list) {

		  if(hp!=null)
		  hp.clear();
		 
		    // Create a CellTable.
		    final CellTable<Answer> table = new CellTable<Answer>();

		    // Create name column.
			    
		    EditTextCell namecc= new EditTextCell();
		 //   namecc.onBrowserEvent(context, parent, value, event, valueUpdater(){});
		    Column<Answer, String>  answer1Column=new  Column<Answer, String> (namecc) {

				@Override
				public String getValue(Answer object) {
					// TODO Auto-generated method stub
					return object.getAnswer();
			
				
				}

	

	
			};
			
			
			//reply column
			   EditTextCell replyCell= new EditTextCell();
			    
			    Column<Answer, String>  replyColumn=new  Column<Answer, String> (replyCell) {

					@Override
					public String getValue(Answer object) {
						String result=object.getReply();
						if(object.getReply()==null)
						{
							result="  ";
						}
						return result;
					}


		
				};

				
				//next column
				   EditTextCell nextCell= new EditTextCell();
				    
				    Column<Answer, String>  nextColumn=new  Column<Answer, String> (nextCell) {

						@Override
						public String getValue(Answer object) {
							// TODO Auto-generated method stub
							return object.getNextQuestionId()+"";
						}


			
					};
					
					//reply column
					//final List<String>  list1=new ArrayList<String>();
					
					
					 EditTextCell resetCell= new EditTextCell();
				//Cell<String> resetCell= new SelectionCell(list1);
				
					    
					    Column<Answer, String>  resetColumn=new  Column<Answer, String> (resetCell) {

							@Override
							public String getValue(Answer object) {
								if(object.getResetsSurvey()==true)
								{
									return "True";
								}
								else {
							
									return "False" ;
								}
								}

					


				
						};
						

						//end column
						//final List<String>  list12=new ArrayList<String>();
						//list12.add("false");
						//list12.add("true");
						
						//SelectionCell endCell= new SelectionCell(list12);
			//			Cell<>
						  
						 EditTextCell endCell= new EditTextCell();
						    Column<Answer, String>  endColumn=new  Column<Answer, String> (endCell) {

								@Override
								public String getValue(Answer object) {
									if(object.getResetsSurvey()==true)
									{
									
										return "True";
									}
									else {
								
										
										return "False" ;
									}
								}

						


					
							};
							
						ButtonCell button=new ButtonCell();
						
						Column<Answer, String> buttonColumn=new Column<Answer,String>(button){

							@Override
							public String getValue(Answer object) {
								// TODO Auto-generated method stub
								return "Update";
							}

							@Override
							public void render(Context context, Answer object,
									SafeHtmlBuilder sb) {
								// TODO Auto-generated method stub
								super.render(context, object, sb);
							}
							
						};
							
					
						buttonColumn.setFieldUpdater(new FieldUpdater<Answer, String>() {
							
							@Override
							public void update(int index, Answer object, String value) {
								//table.redrawRow(index);
								table.redraw();
							//	object.setAnswer();
								System.out.println(table.getRowElement(index).getChildCount());
						//	table.setRowData(index, Collections.singletonList(object));
							//	System.out.println(table.getColumn(index).toString());
							 // System.out.println(table.getColumn(0).);
//							System.out.println(object.getAnswer());
//							System.out.println(object.getReply());
//							System.out.println(object.getResetsSurvey());
//							System.out.println(object.getNextQuestionId());
							System.out.println(index);	
							}
						});
		    // Make the name column sortable.
		    //nameColumn.setSortable(true);

		    // Create question column.

			    // Add a date column to show the birthday.
		
		 // Add a text column to show the address.
//		    TextColumn<CollectionPairs> villeColumn = new TextColumn<CollectionPairs>() {
		//      @Override
		  //    public String getValue(CollectionPairs object) {
		    //    return object.getTransaction().getDateAnswered();
		     // }
		    //};
		    //villeColumn.setSortable(true);
		   

		    // Add the columns. buttonColumn
		    table.addColumn(answer1Column, "Answer");
		    table.addColumn(replyColumn, "Reply");
		    table.addColumn(nextColumn, "Next Question");
		    table.addColumn(resetColumn, "Resets Survey");
			table.addColumn(endColumn, "Ends Survey");
			table.addColumn(buttonColumn, "operation");
		    // Set the total row count. You might send an RPC request to determine the
		    // total row count.
		    table.setRowCount(list.size(), true);

		    // Set the range to display. In this case, our visible range is smaller than
		    // the data set.
		    table.setVisibleRange(0, 10);

		    // Create a data provider.
		    AsyncDataProvider<Answer> dataProvider = new AsyncDataProvider<Answer>() {
		      @Override
		      protected void onRangeChanged(HasData<Answer> display) {
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
//		            Collections.sort(list, new Comparator<Answer>() {
//		              public int compare(Answer o1, Answer o2) {
//		                if (o1 == o2) {
//		                  return 0;
//		                }
//
//		                // Compare the ville columns.
//		                int diff = -1;
//		                if (o1 != null) {
//		                  diff = (o2 != null) ? o1.getAnswer().compareTo(o2.getAnswer()) : 1;
//		                }
//		                return sortList.get(0).isAscending() ? diff : -diff;
//		              }
//		            });
		            if(list.size()<10){
		            	end=list.size();
		            }
		            List<Answer> dataInRange = list.subList(start, end);

		            // Push the data back into the list.
		            table.setRowData(start, dataInRange);
		          }
		        }.schedule(10);
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
		   // table.getColumnSortList().push(namecc.toString());

		    
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
	
	
	
	private DialogBox addAnswers(QuestionId questionId,List<Question> list)
	{ // Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Add Answers!");
		dialogBox.setAnimationEnabled(true);

		final Button closeButton = new Button("Close");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
	///	dialogVPanel.add(new HTML("<b>"+message +"</b>"));
		Label answerLabel=new Label("Answer");
		final TextBox answerTextBox=new TextBox();
		
		
		
		Label replyLabel=new Label("Label");
		final TextBox replyTextBox=new TextBox();
		
		Label nQuestionLabel=new Label("Next Question ");
		final ListBox nQuestionListBox=new ListBox();
		for(Question q: list){
		nQuestionListBox.addItem(q.getQuestionText(), q.getId().getQuestionId()+""); 
		}
		nQuestionListBox.addItem("not available"); 
		Label resetsSurveyLabel=new Label("Resets Survey");
		final ListBox resetsListBox=new ListBox();
		resetsListBox.addItem("true",1+"");
		resetsListBox.addItem("false",0+"");
		
		Label endSurveyLabel=new Label("Ends Survey");
		final ListBox endsListBox=new ListBox();
		endsListBox.addItem("true",1+"");
		endsListBox.addItem("false",0+"");
		
		Button saveButton=new Button("Save");
		dialogVPanel.add(answerLabel);
		dialogVPanel.add(answerTextBox);
		dialogVPanel.add(replyLabel);
		dialogVPanel.add(replyTextBox);
		dialogVPanel.add(nQuestionLabel);
		dialogVPanel.add(nQuestionListBox);
		dialogVPanel.add(resetsSurveyLabel);
		dialogVPanel.add(resetsListBox);
		dialogVPanel.add(endSurveyLabel);
		dialogVPanel.add(endsListBox);
		
		HorizontalPanel h1Panel=new HorizontalPanel();
		h1Panel.add(saveButton);
		h1Panel.add(closeButton);
		dialogVPanel.add(h1Panel);
		//dialogVPanel.add(textToServerLabel);
		//dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		//dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		//dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		final AnswerId answerid=new AnswerId();
		answerid.setSurveyId(questionId.getSurveyId());
		answerid.setQuestionId(questionId.getQuestionId());
		// Add a handler to close the DialogBox
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Answer answer=new Answer();
				answer.setId(answerid);
				answer.setAnswer(answerTextBox.getText());
				answer.setReply(replyTextBox.getText());
				answer.setNextQuestionId(Integer.parseInt(nQuestionListBox.getValue(nQuestionListBox.getSelectedIndex())));
				answer.setEndsSurvey(Boolean.parseBoolean(endsListBox.getValue(endsListBox.getSelectedIndex())));
				answer.setResetsSurvey(Boolean.parseBoolean(resetsListBox.getValue(resetsListBox.getSelectedIndex())));
				serverService.saveAnswer(answer, new AddCallback<Boolean>());
				
			}
		});
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			//	sendButton.setEnabled(true);
			//	sendButton.setFocus(true);
			}
		});
		dialogBox.setPopupPosition(150, 200);
return dialogBox;
		
		
	}//end of method
	
	private DialogBox updateAnswer(final Answer answer,List<Question> list)
	{
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Add Answers!");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setWidth("100%");
		final Button closeButton = new Button("Close");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
	///	dialogVPanel.add(new HTML("<b>"+message +"</b>"));
		Label answerLabel=new Label("Answer");
		final TextBox answerTextBox=new TextBox();
		answerTextBox.setText(answer.getAnswer());
		
		
		Label replyLabel=new Label("Reply");
		final TextBox replyTextBox=new TextBox();
		replyTextBox.setText(answer.getReply());
		
		Label nQuestionLabel=new Label("Next Question ");
		final ListBox nQuestionListBox=new ListBox();
		for(Question q: list){
		nQuestionListBox.addItem(q.getQuestionText(), q.getId().getQuestionId()+""); 
		if(answer.getId().getQuestionId()==q.getId().getQuestionId())
		{
			nQuestionListBox.setSelectedIndex(nQuestionListBox.getItemCount()-1);	
		}
		}
		
		nQuestionListBox.addItem("not available"); 
		Label resetsSurveyLabel=new Label("Resets Survey");
		final ListBox resetsListBox=new ListBox();
		resetsListBox.addItem("true",1+"");
		resetsListBox.addItem("false",0+"");
		if(answer.getResetsSurvey()!=null)
		{
		if(answer.getResetsSurvey())
		{resetsListBox.setSelectedIndex(0);
			
		}else {resetsListBox.setSelectedIndex(1);}
		}
		Label endSurveyLabel=new Label("Ends Survey");
		final ListBox endsListBox=new ListBox();
		endsListBox.addItem("true",1+"");
		endsListBox.addItem("false",0+"");
		if(answer.getEndsSurvey()!=null)
		{
		if(answer.getEndsSurvey())
		{
			endsListBox.setSelectedIndex(0);
			
		}else
		{
			endsListBox.setSelectedIndex(1);
			
		}
		}
		Button saveButton=new Button("Update");
		dialogVPanel.add(answerLabel);
		dialogVPanel.add(answerTextBox);
		dialogVPanel.add(replyLabel);
		dialogVPanel.add(replyTextBox);
		dialogVPanel.add(nQuestionLabel);
		dialogVPanel.add(nQuestionListBox);
		dialogVPanel.add(resetsSurveyLabel);
		dialogVPanel.add(resetsListBox);
		dialogVPanel.add(endSurveyLabel);
		dialogVPanel.add(endsListBox);
		
		HorizontalPanel h1Panel=new HorizontalPanel();
		h1Panel.add(saveButton);
		h1Panel.add(closeButton);
		dialogVPanel.add(h1Panel);
		//dialogVPanel.add(textToServerLabel);
		//dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		//dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
	//	dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Answer answer1=new Answer();
				answer1.setId(answer.getId());
				answer1.setAnswer(answerTextBox.getText());
				answer1.setReply(replyTextBox.getText());
				answer1.setNextQuestionId(Integer.parseInt(nQuestionListBox.getValue(nQuestionListBox.getSelectedIndex())));
				answer1.setEndsSurvey(Boolean.parseBoolean(endsListBox.getValue(endsListBox.getSelectedIndex())));
				answer1.setResetsSurvey(Boolean.parseBoolean(resetsListBox.getValue(resetsListBox.getSelectedIndex())));
				serverService.updateAnswer(answer, new UpdateCallback());
				dialogBox.hide();
			
				//serverService.saveAnswer(answer, new AddCallback<Boolean>());
				
			}
		});
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			//	sendButton.setEnabled(true);
			//	sendButton.setFocus(true);
			}
		});
		dialogBox.setPopupPosition(150, 200);
			return dialogBox;
		
	}//end of method
	
	

	
	
}
