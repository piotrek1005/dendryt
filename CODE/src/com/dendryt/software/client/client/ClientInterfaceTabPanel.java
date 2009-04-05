package com.dendryt.software.client.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;

public class ClientInterfaceTabPanel extends Composite {

	final static int MY_CASES_TAB_INDEX = 0;
	public ClientInterfaceTabPanel(ClientController designerController) {

		
		final TabPanel tabPanel = new TabPanel();
		initWidget(tabPanel);
		
			
		final MyCasesBrowser myCaseB = new MyCasesBrowser(designerController);
		tabPanel.add(myCaseB, "Moje zgloszenia");
		AddingNewProblemComposite addingNewProblemC = new AddingNewProblemComposite(designerController);
		tabPanel.add(addingNewProblemC, "Dodawanie zgloszen");
		tabPanel.selectTab(0);
		
		
		tabPanel.addTabListener(new TabListener(){

			public boolean onBeforeTabSelected(SourcesTabEvents sender,
					int tabIndex) {
				if(tabIndex == MY_CASES_TAB_INDEX){
					myCaseB.refreshCaseList();					
				}
				return true;
			}

			public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

}
