package com.ihsinformatics.is3web.client.composite;

import java.util.HashMap;
import java.util.Random;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.formatters.BarFormat.Color;
//import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
//import com.google.gwt.visualization.client.visualizations.corechart.PieChart.PieOptions;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.ihsinformatics.is3web.client.ServerService;
import com.ihsinformatics.is3web.client.ServerServiceAsync;


public class ChartComposite extends Composite {
//	com.google.gwt.visualization.client.visualizations.PieChart..
	private VerticalPanel vPanel = new VerticalPanel();
//	private FlexTable chart1Table,chart2Table,chart3Table,chart4Table;
	private ServerServiceAsync  serverService=GWT.create(ServerService.class);
	int total=0;
	 private HorizontalPanel layoutPanel;
   //  private PieChart pieChart, pieChart2;

	public ChartComposite() {
	initWidget(vPanel);
	final HorizontalPanel h1Panel=new HorizontalPanel();
	final HorizontalPanel h2Panel=new HorizontalPanel();
	final HorizontalPanel h3Panel=new HorizontalPanel();
//	chart1Table=new FlexTable();
//	chart2Table=new FlexTable();
//	chart3Table=new FlexTable();
//	chart4Table=new FlexTable();
//	
//	HorizontalPanel h1Panel=new HorizontalPanel();
//	h1Panel.add(chart1Table);
//	h1Panel.add(chart2Table);
//	
//	
//	HorizontalPanel h2Panel=new HorizontalPanel();
//	h2Panel.add(chart3Table);
//	h2Panel.add(chart4Table);
//	
//	vPanel.add(h1Panel);
//	vPanel.add(h2Panel);
	
	 // Create a callback to be called when the visualization API
    // has been loaded.
    Runnable onLoadCallback = new Runnable() {
    	
    	public void run() {
      //  Panel panel = RootPanel.get();
 
        // Create a pie chart visualization.
    	 
    	  serverService.getCompleteSurveuysNumber(new AsyncCallback<HashMap<String,Integer>>() {
    			
    			@Override
    			public void onSuccess(HashMap<String, Integer> result) {
    			  //System.out.println("Hash : "+result.size());
    				PieChart pie = new PieChart(createTable1(result), createOptions());
    			     pie.setVisible(true);
    				// pie.addSelectHandler(createSelectHandler(pie));
    				 h1Panel.add(pie);
    				 //vPanel.add(pie);
    			}
    			
    			@Override
    			public void onFailure(Throwable caught) {
    				// TODO Auto-generated method stub
    				
    			}

    		
    		});//end of anoyomous class

    	  serverService.getIncompleteSurveuysNumber(new AsyncCallback<HashMap<String,Integer>>() {
  			
  			@Override
  			public void onSuccess(HashMap<String, Integer> result) {
  			  //System.out.println("Hash : "+result.size());
  			 PieChart pie2 = new PieChart(createTable2(result), createOptions2());
  		     pie2.setVisible(true);
  			        pie2.addSelectHandler(createSelectHandler(pie2));
  			      h1Panel.add(pie2);
  			        //vPanel.add(pie2);
  			}
  			
  			@Override
  			public void onFailure(Throwable caught) {
  				// TODO Auto-generated method stub
  				
  			}

  		
  		});//end of inner class
    	  
    	  
    	  serverService.getSkippedSurveuysNumber(new AsyncCallback<HashMap<String,Integer>>() {
  			
  			@Override
  			public void onSuccess(HashMap<String, Integer> result) {
  				 PieChart pie3 = new PieChart(createTable3(result), createOptions3());
  				               pie3.setVisible(true);
			        pie3.addSelectHandler(createSelectHandler(pie3));
			        
			        h2Panel.add(pie3);
			    //    vPanel.add(pie3);
  			}
  			@Override
  			public void onFailure(Throwable caught) {
  				// TODO Auto-generated method stub
  				
  			}
  		}); 
    	  
			
    	  
    	  serverService.getIncompleteTimespan(24, new AsyncCallback<HashMap<String,Integer>>() {
  			
  			@Override
  			public void onSuccess(HashMap<String,Integer> result) {
 				 PieChart pie4 = new PieChart(createTable4(result), createOptions4());
  				
 		//		Table tble=new Table(createTable4(result), createOptions4());
 			     pie4.setVisible(true);
 				  pie4.addSelectHandler(createSelectHandler(pie4));
			        h2Panel.add(pie4);
  				
			        

  			
  			}
  			
  			

			@Override
  			public void onFailure(Throwable caught) {
  				// TODO Auto-generated method stub
  				
  			}
  		});				
  				
    	  
    	  serverService.getSkippedTimespan(new AsyncCallback<HashMap<String,Integer>>() {
  			
  			@Override
  			public void onSuccess(HashMap<String,Integer> result) {
  			
  				
  				 PieChart pie5 = new PieChart(createTable5(result), createOptions5());
   				
  		 		//		Table tble=new Table(createTable4(result), createOptions4());
  			     pie5.setVisible(true);
  		 				  pie5.addSelectHandler(createSelectHandler(pie5));
  					        h3Panel.add(pie5);
  				
  			}
  			
  			@Override
  			public void onFailure(Throwable caught) {
  				// TODO Auto-generated method stub
  				
  			}
  		});				
		     //   HorizontalPanel hPanel =new HorizontalPanel();
		       // hPanel.add(pie);hPanel.add(pie2);
		        
			
      }
    };
    // has been loaded.
//   Runnable onLoadCallback1 = new Runnable() {
//      public void run() {
//    	
//			
//      }
//    };


    // Load the visualization api, passing the onLoadCallback to be called
    // when loading is done.
   vPanel.add(h1Panel);
   vPanel.add(h2Panel);
   vPanel.add(h3Panel);
    VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
    //VisualizationUtils.loadVisualizationApi(onLoadCallback1, PieChart.PACKAGE);
  }

  private Options createOptions() {
	  Options options = Options.create();
    options.setWidth(400);
    options.setHeight(240);
    options.set3D(true);
    
    options.set("pieHole", 4.0);
    options.setPieJoinAngle(30.0);
    options.setBorderColor("000000");
    //options.setBackgroundColor(color)
    options.setPieMinimalAngle(20.0);
    options.setTitle("Complete Surveys");
    return options;
  }
  private Options createOptions2() {
	    Options options = Options.create();
	    options.setWidth(400);
	    options.setHeight(240);
	    options.setPieJoinAngle(60.0);
	    options.setBorderColor("000000");
	    //options.setBackgroundColor(color)
	    options.setPieMinimalAngle(20.0);
	    options.set3D(true);
	    options.setTitle("Incomplete Surveys");
	    return options;
	  }

  private Options createOptions3() {
	    Options options = Options.create();
	    options.setWidth(400);
	    options.setHeight(240);
	    options.set3D(true);
	    options.setBorderColor("000000");
	    //options.setBackgroundColor(color)
	    options.setPieMinimalAngle(20.0);
	   // options.setSliceVisibilityThreshold(45.00);
	    options.setPieJoinAngle(90.0);
	    options.setTitle("Skipped Surveys");
	    return options;
	  }
  
  private Options createOptions4() {
	  Options options = Options.create();
	//   Options options = Options.create();
	    options.setWidth(400);
	    options.setHeight(240);
	    options.set3D(true);
	    options.setBorderColor("000000");
	    //options.setBackgroundColor(color)
	    options.setPieMinimalAngle(20.0);
	   // options.setSliceVisibilityThreshold(45.00);
	    options.setPieJoinAngle(90.0);
	    options.setTitle("NUmber of incomplete Surveys by questions");
	    return options;

	   // return options;
	  }
  
  private Options createOptions5() {
	  Options options = Options.create();
	//   Options options = Options.create();
	    options.setWidth(400);
	    options.setHeight(240);
	    options.set3D(true);
	    options.setBorderColor("000000");
	    //options.setBackgroundColor(color)
	    options.setPieMinimalAngle(20.0);
	   // options.setSliceVisibilityThreshold(45.00);
	    options.setPieJoinAngle(90.0);
	    options.setTitle("NUmber of skipped Surveys by questions");
	    return options;

	   // return options;
	  }

  private SelectHandler createSelectHandler(final PieChart chart) {
    return new SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        String message = "";
        
        // May be multiple selections.
        JsArray<Selection> selections = chart.getSelections();

        for (int i = 0; i < selections.length(); i++) {
          // add a new line for each selection
          message += i == 0 ? "" : "\n";
          
          Selection selection = selections.get(i);

          if (selection.isCell()) {
            // isCell() returns true if a cell has been selected.
            
            // getRow() returns the row number of the selected cell.
            int row = selection.getRow();
            // getColumn() returns the column number of the selected cell.
            int column = selection.getColumn();
            message += "cell " + row + ":" + column + " selected";
          } else if (selection.isRow()) {
            // isRow() returns true if an entire row has been selected.
            
            // getRow() returns the row number of the selected row.
            int row = selection.getRow();
            message += "row " + row + " selected";
          } else {
            // unreachable
            message += "Pie chart selections should be either row selections or cell selections.";
            message += "  Other visualizations support column selections as well.";
          }
        }
        
      //  Window.alert(message);
      }
    };
  }

  private AbstractDataTable createTable1( HashMap<String , Integer> result) {
    final DataTable data = DataTable.create();
    data.addColumn(ColumnType.STRING, "Survey");
    data.addColumn(ColumnType.NUMBER, "Number of complete Surveys");
    data.addRows(result.size());
	 int row1=0;
	 boolean  flag=false;
	 boolean havedata=false;
		for(String s:result.keySet())
		{
		//System.out.println("chart ;" + result.get(s));
			if(result.get(s)==0)
			{
				
			flag=true;	
			if(havedata){
			havedata=true;
			}else {
				havedata=false;
			}
			}else {
			data.setValue(row1, 0, s);
			data.setValue(row1, 1, result.get(s));
			flag=false;
		havedata=true;
			
			}
			
			row1++;
		}
		if(flag && !havedata)
		{
		
		data.removeRows(0, result.size());	
		}
            
    
 
   
    
    return data;
  }//end of the method
  
  private AbstractDataTable createTable2(HashMap<String , Integer> result ) {
	    final DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, "Survey");
	    data.addColumn(ColumnType.NUMBER, "Number of incomplete Surveys");
	    
	    data.addRows(result.size());
		 int row=0;
		 boolean  flag=false;
		 boolean havedata=false;
			for(String s:result.keySet())
			{
			//System.out.println("chart ;" + result.get(s));
				if(result.get(s)==0)
				{
					
				flag=true;	
				if(havedata){
				havedata=true;
				}else {
					havedata=false;
				}
				}else {
				data.setValue(row, 0, s);
				data.setValue(row, 1, result.get(s));
				flag=false;
			havedata=true;
				
				}
				
				row++;
			}
			if(flag && !havedata)
			{
			
			data.removeRows(0, result.size());	
			}
	    
	    
	    return data;
	  }//end of the method

  
  private AbstractDataTable createTable3(HashMap<String , Integer> result ) {
	    final DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, "Survey");
	    data.addColumn(ColumnType.NUMBER, "Number of Skipped Surveys");
	    
	    data.addRows(result.size());
		 int row=0;
		 boolean  flag=false;
		 boolean havedata=false;
			for(String s:result.keySet())
			{
			//System.out.println("chart ;" + result.get(s));
				if(result.get(s)==0)
				{
					
				flag=true;	
				if(havedata){
				havedata=true;
				}else {
					havedata=false;
				}
				}else {
				data.setValue(row, 0, s);
				data.setValue(row, 1, result.get(s));
				flag=false;
			havedata=true;
				
				}
				
				row++;
			}
			if(flag && !havedata)
			{
			
			data.removeRows(0, result.size());	
			}
	
	    
	    
	    return data;
	  }//end of the method

  private AbstractDataTable createTable4(HashMap<String , Integer> result ) {
	    final DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, "Questions");
	    data.addColumn(ColumnType.NUMBER, "Number of incompete Surveys");
	    
	    data.addRows(result.size());
		 int row=0;
		 boolean  flag=false;
		 boolean havedata=false;
			for(String s:result.keySet())
			{
			//System.out.println("chart ;" + result.get(s));
				if(result.get(s)==0)
				{
					
				flag=true;	
				if(havedata){
				havedata=true;
				}else {
					havedata=false;
				}
				}else {
				data.setValue(row, 0, s);
				data.setValue(row, 1, result.get(s));
				flag=false;
			havedata=true;
				
				}
				
				row++;
			}
			if(flag && !havedata)
			{
			
			data.removeRows(0, result.size());	
			}
	    
	    
	    return data;
	  }//end of the method

  private AbstractDataTable createTable5(HashMap<String , Integer> result ) {
	    final DataTable data = DataTable.create();
	    data.addColumn(ColumnType.STRING, "Questions");
	    data.addColumn(ColumnType.NUMBER, "Number of incompete Surveys");
	    
	    data.addRows(result.size());
		 int row=0;
		 boolean  flag=false;
		 boolean havedata=false;
			for(String s:result.keySet())
			{
			//System.out.println("chart ;" + result.get(s));
				if(result.get(s)==0)
				{
					
				flag=true;	
				if(havedata){
				havedata=true;
				}else {
					havedata=false;
				}
				}else {
				data.setValue(row, 0, s);
				data.setValue(row, 1, result.get(s));
				flag=false;
			havedata=true;
				
				}
				
				row++;
			}
			if(flag && !havedata)
			{
			
			data.removeRows(0, result.size());	
			}
	    
	    
	    return data;
	  }//end of the method
       
  
  
  
}