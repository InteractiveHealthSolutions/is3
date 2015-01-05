package com.ihsinformatics.is3web.client.composite;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UpdateCallback implements AsyncCallback<Boolean> {

	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(Boolean result) {
		if(result)
		{
			getDialog("Record", "Record updates successfully!").show();
			MenuComposite.clearContent();
			
		}
		else
		{
			getDialog("Record", "Record not updated !").show();
			
		}

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
