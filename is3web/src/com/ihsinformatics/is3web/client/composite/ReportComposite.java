package com.ihsinformatics.is3web.client.composite;

import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.client.ServerServiceAsync;
import com.ihsinformatics.is3web.shared.Parameter;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.chart.yui.PieChart;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.CellMetadata;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.Renderer;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.layout.VerticalLayout;

public class ReportComposite extends Composite {

	
	private VerticalPanel mainContent=new VerticalPanel();
	private HorizontalPanel horizontalPanel=new HorizontalPanel();
	private ServerServiceAsync serverService= GWT.create(ServerService.class);
	
	// constructor
	public ReportComposite() {
	
		initWidget(mainContent);
		
		mainContent.setSpacing(10);
		final ListBox mainList=new ListBox();
		mainList.addItem("Reports", "reports");
		mainList.addItem("Charts", "charts");
		
		Button openButton =new Button ("Open");
		openButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String value=mainList.getValue(mainList.getSelectedIndex());
				if(value.equalsIgnoreCase("reports"))
				{
					showReports();
					
				}else if (value.equalsIgnoreCase("charts")) {
					
					showCharts();
				}
				
			}
		});
		
		HorizontalPanel h1Panel =new HorizontalPanel();
		h1Panel.add(mainList);
		h1Panel.add(openButton);
		
		mainContent.add(h1Panel);
		//mainContent.add(horizontalPanel);
	}//end of constructor
	
	
	private  void showCharts()
	{
		horizontalPanel.clear();
		ChartComposite chartsPanel=new ChartComposite();
		horizontalPanel.add(chartsPanel);
		mainContent.add(horizontalPanel);
		
		
	}//end of method
	
	private void showReports()
	{
		horizontalPanel.clear();
		
		VerticalPanel v1Panel=new VerticalPanel();
		final ListBox reportList=new ListBox();
		reportList.addItem("Average Time ", "timediff");
		reportList.addItem("Above 24hour Transactions ", "timecheck");
		Button generateButton=new Button("Generate");
		horizontalPanel.add(reportList);
		horizontalPanel.add(generateButton);
		horizontalPanel.setSpacing(10);
		generateButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String value=reportList.getValue(reportList.getSelectedIndex());
				if(value.equalsIgnoreCase("timediff"))
				{
					//Parameter[] params =new Parameter[5];
					serverService.generateReport("averagetime", new Parameter[] {}, false, new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(String result) {
							Window.open(result, "survey average", "_BLANK");
							
						}
					});
					
				}else if (value.equalsIgnoreCase("timecheck")) {
					
					//Parameter[] params =new Parameter[5];
					serverService.generateReport("transactionTimecheck", new Parameter[] {}, false, new AsyncCallback<String>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(String result) {
							Window.open(result, "above 24hour Transactions", "_BLANK");
							
						}
					});
				}
				
				
			}
		});
		mainContent.add(horizontalPanel);
		
	}
	
	
	
}
