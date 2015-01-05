package com.ihsinformatics.is3web.client.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.client.ServerServiceAsync;
import com.ihsinformatics.is3web.model.CollectionPairs;
import com.ihsinformatics.is3web.model.Survey;

public class DashboardComposite extends Composite {

	private VerticalPanel vPanel = new VerticalPanel();
	private FlexTable chart1Table,chart2Table,chart3Table,chart4Table , chart5Table, chart6Table,chart7Table,chart8Table;
	private ServerServiceAsync  serverService=GWT.create(ServerService.class);
	int total=0;
	
	public DashboardComposite() {
	initWidget(vPanel);
	chart1Table=new FlexTable();
	chart2Table=new FlexTable();
	chart3Table=new FlexTable();
	chart4Table=new FlexTable();
	chart5Table=new FlexTable();
	chart6Table=new FlexTable();
	chart7Table=new FlexTable();
	chart8Table=new FlexTable();
	
	HorizontalPanel h1Panel=new HorizontalPanel();
	//h1Panel.addStyleName("");
	h1Panel.add(chart1Table);
	h1Panel.add(chart2Table);
	h1Panel.setSpacing(10);
	
	
	HorizontalPanel h2Panel=new HorizontalPanel();
	h2Panel.add(chart3Table);
	h2Panel.add(chart4Table);
	h2Panel.setSpacing(10);
	
	HorizontalPanel h3Panel=new HorizontalPanel();
	h3Panel.add(chart5Table);
	h3Panel.add(chart6Table);
	h3Panel.setSpacing(10);
	
	
	HorizontalPanel h4Panel=new HorizontalPanel();
	h4Panel.add(chart7Table);
	h4Panel.add(chart8Table);
	h4Panel.setSpacing(10);
	
	vPanel.add(h2Panel);
	vPanel.add(h1Panel);
	vPanel.add(h3Panel);
	vPanel.add(h4Panel);
	vPanel.setSpacing(10);
//	setChart1Table();
//	setChart2Table();
//	setChart3Table();
//	setChart4Table();
//	setChart5Table();
//	setChart6Table();
//	setChart7Table();
//	setChart8Table();
	ChartComposite s=new ChartComposite();
	vPanel.add(s);
	//chart1Table.setText(0,1, "its working");
	
	}

	
	private void setChart1Table()
	{  chart1Table.clear();
	chart1Table.removeAllRows();
//chart1Table.addStyleName("gridtable");
chart1Table.setStylePrimaryName("tftable");
		chart1Table.setTitle("Table of Incomplete % Surveys");
		chart1Table.setText(0, 0, "Table of Incomplete % Surveys");	
		serverService.getIncompleteSurveuysPercentage(new AsyncCallback<HashMap<String,Double>>() {
			
			@Override
			public void onSuccess(HashMap<String, Double> result) {
				if(result==null || result.size()==0)
				{
					chart1Table.setText(1, 1, "There is no INcomplete survey");
				return;
				}
				int row=1;
				
			for(String s:result.keySet())
			{
				chart1Table.setText(row, 0, "Percentage of incomplete surveys of "+ s+" are ");
				chart1Table.setText(row, 1, result.get(s)+"");
				row++;
			}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
	}//end of method


	private void setChart2Table()
	{  chart2Table.clear();
	chart2Table.removeAllRows();
	//chart2Table.addStyleName("tftable");
	chart2Table.setStylePrimaryName("tftable");	
	chart2Table.setTitle("Table of Complete % Surveys");
	chart2Table.setText(0, 0, "Table of Complete % Surveys");	
		serverService.getCompleteSurveuysPercentage(new AsyncCallback<HashMap<String,Double>>() {
			
			@Override
			public void onSuccess(HashMap<String, Double> result) {
				if(result==null || result.size()==0)
				{
					chart2Table.setText(1, 1, "There is no complete survey");
				return;
				}
				int row=1;
				
			for(String s:result.keySet())
			{
				chart2Table.setText(row, 0, "percentage of complete surveys of "+ s+" are ");
				chart2Table.setText(row, 1, result.get(s)+"");
			//	System.out.println("afdasd");
				row++;
			}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
	}//end of method
	
	
	
	private void setChart3Table()
	{  chart3Table.clear();
	chart3Table.removeAllRows();
	//chart2Table.addStyleName("tftable");
	chart3Table.setStylePrimaryName("tftable");	
	chart3Table.setTitle("Table of Complete no of  Surveys");
	chart3Table.setText(0, 0, "Table of Complete no of  Surveys");	
		serverService.getCompleteSurveuysNumber(new AsyncCallback<HashMap<String,Integer>>() {
			
			@Override
			public void onSuccess(HashMap<String, Integer> result) {
				if(result==null || result.size()==0)
				{
					chart3Table.setText(1, 1, "There is no complete survey");
				return;
				}
				int row=1;
				
			for(String s:result.keySet())
			{
				chart3Table.setText(row, 0, "no of complete surveys of "+s+" are ");
				chart3Table.setText(row, 1,  result.get(s)+"");
			//	System.out.println("afdasd");
				row++;
			}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

		
		});
	
		
	}//end of method
	
	

	private void setChart4Table()
	{  chart4Table.clear();
	chart4Table.removeAllRows();
//chart1Table.addStyleName("gridtable");
chart4Table.setStylePrimaryName("tftable");
		chart4Table.setTitle("Table of Incomplete no of  Surveys");
		chart4Table.setText(0, 0, "Table of Incomplete no of  Surveys");	
		serverService.getIncompleteSurveuysNumber(new AsyncCallback<HashMap<String,Integer>>() {
			
			@Override
			public void onSuccess(HashMap<String, Integer> result) {
				if(result==null || result.size()==0)
				{
					chart4Table.setText(1, 1, "There is no incomplete survey");
				return;
				}
				int row=1;
				
			for(String s:result.keySet())
			{
				chart4Table.setText(row, 0, "number of incomplete surveys of "+s+" are ");
				chart4Table.setText(row, 1, result.get(s)+"");
				row++;
			}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
	}//end of method
	
	private void setChart5Table()
	{  chart5Table.clear();
	chart5Table.removeAllRows();
//chart1Table.addStyleName("gridtable");
chart5Table.setStylePrimaryName("tftable");
		chart5Table.setTitle("Skipped surveys Table");
		chart5Table.setText(0, 0, "Skipped surveys Table in Numbers");	
		serverService.getSkippedSurveuysNumber(new AsyncCallback<HashMap<String,Integer>>() {
			
			@Override
			public void onSuccess(HashMap<String, Integer> result) {
				if(result==null || result.size()==0)
				{
					chart5Table.setText(1, 1, "There is no skipped survey");
				return;
				}
				int row=1;
				
			for(String s:result.keySet())
			{
				chart5Table.setText(row, 0, "number of Skipped surveys of "+s+" are ");
				chart5Table.setText(row, 1, result.get(s)+"");
				row++;
			}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
	}//end of method
	
	
	private void setChart6Table()
	{  chart6Table.clear();
	chart6Table.removeAllRows();
//chart1Table.addStyleName("gridtable");
chart6Table.setStylePrimaryName("tftable");
		chart6Table.setTitle("Skipped surveys Table");
		chart6Table.setText(0, 0, "Skipped surveys Table in  Percentage");	
		serverService.getSkippedSurveuysPercentage(new AsyncCallback<HashMap<String,Double>>() {
			
			@Override
			public void onSuccess(HashMap<String, Double> result) {
				
				if(result==null || result.size()==0)
				{
					chart6Table.setText(1, 1, "There is no skipped survey");
				return;
				}
				int row=1;
				
			for(String s:result.keySet())
			{
				chart6Table.setText(row, 0, "Percentage of Skipped surveys of "+s+" are ");
				chart6Table.setText(row, 1, result.get(s)+"");
				row++;
			}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
	}//end of method

	private void setChart7Table()
	{  chart7Table.clear();
	chart7Table.removeAllRows();
//chart1Table.addStyleName("gridtable");
chart7Table.setStylePrimaryName("tftable");
		chart7Table.setTitle("Incomplete surveys Table");
		chart7Table.setText(0, 1, "Incomplete surveys Table ");	
		serverService.getIncompleteTimespan(24, new AsyncCallback<HashMap<String,Integer>>() {
			
			@Override
			public void onSuccess(HashMap<String,Integer> result) {
			
				if(result==null || result.size()==0)
				{
					chart7Table.setText(1, 1, "There is no Incomplete survey");
				return;
				}
				int row=1;
				
			for(String s:result.keySet())
			{
				chart7Table.setText(row, 0, s );
				chart7Table.setText(row, 1, "Incomplete surveys are ");
				chart7Table.setText(row, 2, result.get(s)+"");
				row++;
			}
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});				
				
	
		
	}//end of method
	
	
	private void setChart8Table()
	{  chart8Table.clear();
	chart8Table.removeAllRows();
//chart1Table.addStyleName("gridtable");
chart8Table.setStylePrimaryName("tftable");
		chart8Table.setTitle("Skipped surveys Table");
		chart8Table.setText(0, 1, "Skipped surveys Table ");	
		serverService.getSkippedTimespan(new AsyncCallback<HashMap<String,Integer>>() {
			
			@Override
			public void onSuccess(HashMap<String,Integer> result) {
			
				if(result==null || result.size()==0)
				{
					chart8Table.setText(1, 1, "There is no Incomplete survey");
				return;
				}
				int row=1;
				
			for(String s:result.keySet())
			{
				chart8Table.setText(row, 0, s );
				chart8Table.setText(row, 1, "Incomplete surveys are ");
				chart8Table.setText(row, 2, result.get(s)+"");
				row++;
			}
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});				
				
	
		
	}//end of method
	
	
}
