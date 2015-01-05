package com.ihsinformatics.is3web.client.composite;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.allen_sauer.gwt.log.client.Log;

public class MainComposite extends Composite {

	
	private static VerticalPanel mainPanel=new VerticalPanel();
	
	public MainComposite() {
		
		
	initWidget(mainPanel);
	//mainPanel.setWidth("100%");
	mainPanel.setSize ("80%", "80%");
	LoginComposite loginPage=new LoginComposite();	
	
	
	mainPanel.add(loginPage);
	mainPanel.setCellVerticalAlignment (loginPage, HasVerticalAlignment.ALIGN_MIDDLE);
	mainPanel.setCellHorizontalAlignment (loginPage, HasHorizontalAlignment.ALIGN_CENTER);
	// Log.debug("This is a 'DEBUG' test message");
		
	}
	
	public static void showMenuPage(){
		
		MenuComposite menuPage=new MenuComposite();
		mainPanel.clear();
		mainPanel.setSize ("80%", "80%");
		
		mainPanel.add(menuPage);
		mainPanel.setCellVerticalAlignment (menuPage, HasVerticalAlignment.ALIGN_MIDDLE);
		mainPanel.setCellHorizontalAlignment (menuPage, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	
	
}
