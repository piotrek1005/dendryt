package com.dendryt.software.client.client;

import java.util.Date;
import java.util.List;

import com.dendryt.software.client.client.tmp.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class MyCasesBrowser extends Composite {
	
	
	
	final static int MAX_TABLE_WIDTH_INDEX = 4;
	final static int SHORT_OPIS_PROBLEMU_LENGHT = 40;
	void fillTableHeader(HTMLTable table){
		table.setText(0, 0, "Data zgloszenia");
		table.setText(0, 1, "Produkt");
		table.setText(0, 2, "Krotki opis zgloszenia");
		table.setText(0, 3, "Waga klienta");
		table.setText(0, 4, "Stan");
	}
	
	
	static String getFormattedDate(Date d){
		StringBuilder s = new StringBuilder();
		s.append((d.getYear() + 1900) + "." + (d.getMonth() + 1) + "." + d.getDate() + " ");
		
		
		
		int h = d.getHours();
		if(h < 10){
			s.append("0");
		}
		s.append(h + ":");
		
		
		int m = d.getMinutes();
		if(m < 10){
			s.append("0");
		}
		s.append(m);
		
		return s.toString();
	}
	protected void fillTable(HTMLTable table, int index, Zgloszenie zgloszenie) throws Exception{
		if(index < 1){
			throw new Exception("Too low index: " + index);
		}
		
		table.setText(index, 0, getFormattedDate(zgloszenie.getDataZgloszenia()));
		
		String opis = zgloszenie.getOpisProblemu();
		String shortOpis = opis;
		if (opis.length() > SHORT_OPIS_PROBLEMU_LENGHT){
			shortOpis = zgloszenie.getOpisProblemu().substring(0, SHORT_OPIS_PROBLEMU_LENGHT);
		}
		
		table.setText(index, 1, zgloszenie.getProdukt());
		//table.setText(index, 2, opis);
		
		
		final DialogBox dialogbox = new DialogBox(false);
//	    dialogbox.setStyleName("");
	    VerticalPanel dialogBoxContents = new VerticalPanel();
	    dialogbox.setText("Pelny opis zgloszenia");
	    HTML message = new HTML(opis);
//	    message.setStyleName("");
	    ClickListener  listener = new ClickListener()
	    {
	        public void onClick(Widget sender)
	        {
	            dialogbox.hide();
	    
	        }
	    };
	    Button button = new Button("Zamknij", listener);
	    SimplePanel holder = new SimplePanel();
	    holder.add(button);
	    holder.setStyleName("demo-DialogBox-footer");
	    dialogBoxContents.add(message);
	    dialogBoxContents.add(holder);
	    dialogbox.setWidget(dialogBoxContents);

	    
	    
	    
	    
	    
//		Label lab = new Label(shortOpis);
		Hyperlink lab = new Hyperlink();
		lab.setText(shortOpis);
		lab.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
//				DialogBox d = new DialogBox(false);
//				d.setText("XXX");
//				d.show();
//				d.center();
				dialogbox.setWidth("500px");
				dialogbox.center();
			}
			
		});
		table.setWidget(index, 2, lab);
		table.setText(index, 3, zgloszenie.getWagaKlienta().getNazwaWagi());
		table.setText(index, 4, zgloszenie.getStan().getNazwaStanu());
		
		
	}
	IZgloszenieProvider clientController;
	private FlexTable table;
	public MyCasesBrowser(IZgloszenieProvider c){
		clientController = c;

		final FlowPanel flowPanel = new FlowPanel();
		initWidget(flowPanel);
		
		table = new FlexTable();
		fillTableHeader(table);

		
		fulfillTable();
		
		flowPanel.add(table);

		final FlowPanel viewPanel = new FlowPanel();
		flowPanel.add(viewPanel);
		

	}


	private void fulfillTable() {
		AsyncCallback<List<Zgloszenie>> tableFullfillingCallback = new AsyncCallback<List<Zgloszenie>>(){

			public void onFailure(Throwable caught) {	
				caught.printStackTrace();
				System.out.println("ERR");
			}

			public void onSuccess(List<Zgloszenie> result) {
				System.out.println("OK" + result.size());
				int index = 1;
				for(Zgloszenie z : result){
					try {
						fillTable(table, index++, z);
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}				
			}
			
		};
		
		clientController.getZgloszenia(tableFullfillingCallback);
	}

	
	public void refreshCaseList(){
		fulfillTable();
	}

}
