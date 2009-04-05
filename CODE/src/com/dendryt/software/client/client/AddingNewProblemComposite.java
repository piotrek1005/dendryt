package com.dendryt.software.client.client;

import java.util.Date;
import java.util.List;

import com.dendryt.software.client.client.tmp.Stan;
import com.dendryt.software.client.client.tmp.WagaProblemu;
import com.dendryt.software.client.client.tmp.Zgloszenie;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestionEvent;
import com.google.gwt.user.client.ui.SuggestionHandler;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author michal
 *
 */

public class AddingNewProblemComposite extends Composite {


	MultiWordSuggestOracle oracle;
	private SuggestBox produktSuggestBox;
	ListBox wersjaListBox;

	List<Produkt> produktList;
	public List<Produkt> getProduktList() {
		if(produktList == null){
			updateProductList();
		}
		return produktList;
	}

	
	ClientController clientController;
	public AddingNewProblemComposite(ClientController c){
		clientController = c;
		final FlowPanel flowPanel = new FlowPanel();
		initWidget(flowPanel);

		final HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		flowPanel.add(horizontalPanel_1);

		final Label produktLabel = new Label("Produkt:");
		horizontalPanel_1.add(produktLabel);
		horizontalPanel_1.setCellWidth(produktLabel, "130");

		oracle = new MultiWordSuggestOracle();
		// TODO here reading products name from DB
		

		updateProductList();
		
		
		

		produktSuggestBox = new SuggestBox(oracle);
		horizontalPanel_1.add(produktSuggestBox);
		produktSuggestBox.addEventHandler(new SuggestionHandler() {
			public void onSuggestionSelected(final SuggestionEvent event) {
				onProduktSuggestionSelected(event.getSelectedSuggestion().getDisplayString());
			}
		});
		produktSuggestBox.addChangeListener(new ChangeListener() {
			public void onChange(final Widget sender) {
				onProduktBoxChange();
			}

		});

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		flowPanel.add(horizontalPanel);

		final Label wersjaLabel = new Label("Wersja:");
		horizontalPanel.add(wersjaLabel);
		horizontalPanel.setCellWidth(wersjaLabel, "130");

		wersjaListBox = new ListBox();
		horizontalPanel.add(wersjaListBox);
		wersjaListBox.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {
				onWersjaBoxClick();
			}
		});

		final HorizontalPanel horizontalPanel2 = new HorizontalPanel();
		flowPanel.add(horizontalPanel2);

		final Label label2 = new Label("Waga problemu:");
		horizontalPanel2.add(label2);
		horizontalPanel2.setCellWidth(label2, "130");

		final ListBox wagaProblemuListBox = new ListBox();
		wagaProblemuListBox.addItem("normalna");
		wagaProblemuListBox.addItem("krytyczna");
		wagaProblemuListBox.addItem("niska");
		horizontalPanel2.add(wagaProblemuListBox);

		final VerticalPanel verticalPanel = new VerticalPanel();
		flowPanel.add(verticalPanel);

		final Label opisProblemuLabel = new Label("Opis problemu:");
		verticalPanel.add(opisProblemuLabel);

		final RichTextArea opisProblemuRichTextArea = new RichTextArea();
		verticalPanel.add(opisProblemuRichTextArea);

		final VerticalPanel verticalPanel_1 = new VerticalPanel();
		flowPanel.add(verticalPanel_1);
		verticalPanel_1.setWidth("300");
		verticalPanel_1
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		final Button dodajZgloszenieButton = new Button();
		verticalPanel_1.add(dodajZgloszenieButton);

		dodajZgloszenieButton.addClickListener(new ClickListener() {
			public void onClick(final Widget sender) {

				Zgloszenie z = new Zgloszenie();
				
				z.setProdukt(produktSuggestBox.getText());
				
				z.setOpisProblemu(opisProblemuRichTextArea.getText());
				
				WagaProblemu w = new WagaProblemu();
				w.setNazwaWagi(wagaProblemuListBox.getItemText(wagaProblemuListBox.getSelectedIndex()));
				z.setWagaKlienta(w);
				
				Stan s = new Stan();
				s.setNazwaStanu("ANALIZA");
				z.setStan(s);
				
				z.setDataZgloszenia(new Date());
				
				clientController.addNewZgloszenie(z, new AsyncCallback() {

					public void onFailure(Throwable caught) {
						System.out.println("failure");
					}

					public void onSuccess(Object result) {
						System.out.println("sukces");
					}

				});
			}
		});

		dodajZgloszenieButton.setText("Dodaj zgloszenie");
	}

	
	int counter = 5; //tmp
	private void updateProductList() {
		clientController.getProductList(new AsyncCallback<List<Produkt>>(){

			public void onFailure(Throwable caught) {
				System.out.println("ERROR updateProductList()");
				if(--counter > 0){
					updateProductList();
				}
			}

			public void onSuccess(List<Produkt> result) {
				produktList = result;	
				System.out.println("OK");
				fullFillOracle(oracle);
			}
			
		});
	}

	private void fullFillOracle(MultiWordSuggestOracle oracle) {
		for (Produkt p : produktList) {
			fillOracle(p.getNazwa());
		}
	}

	private void fillOracle(String s) {
		if(s != null){
			oracle.add(s);			
		}else{
			System.out.println("NULL!");
		}
	}

	
	
	/** Event occurs on product'sBox change
	 * 
	 */
	private void onProduktBoxChange() {
//		System.out.println("onProduktBoxChange:" + produktSuggestBox.getText());
		//updateWersjaBox();
		//wersjaBoxHasToBeUpdated = true;
	}
	
	private boolean wersjaBoxHasToBeUpdated = false;
	
	private void onWersjaBoxClick() {
//		System.out.println(produktSuggestBox.getText());
		
//		if(wersjaBoxHasToBeUpdated){
//			wersjaBoxHasToBeUpdated = false;
//			updateWersjaBox();
//		}
	}
	
	private void onProduktSuggestionSelected(String s){
//		System.out.println("onProduktSuggestionSelected:" + produktSuggestBox.getText());
		updateWersjaBox();
	}
	
	private void updateWersjaBox() {
		wersjaListBox.clear();
		String boxText = produktSuggestBox.getText();
//		System.out.println("updateWersja,"+boxText);
		List<Produkt> prList = getProduktList();
		if(prList != null){
			for(Produkt p : prList){
				System.out.println(p);
				if(p.getNazwa().equals(boxText)){
					for(String w : p.getWersjaList()){
						wersjaListBox.addItem(w);					
					}
				}
			}			
		}
	}
}
