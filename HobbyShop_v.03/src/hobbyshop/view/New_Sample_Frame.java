package hobbyshop.view;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import hobbyshop.controller.MTG_Data_Controller;
import hobbyshop.model.MTG_Card.MTG_Card;
import hobbyshop.model.MTG_Deck.MTG_Deck;

public abstract class New_Sample_Frame extends JFrame {
	
	private JMenuBar menusBar = new JMenuBar();
	
	private JMenuItem exitItem = new JMenuItem("Esci"),
			          save_Exit = new JMenuItem("Salva ed esci");	
	
	public New_Sample_Frame() {
		super();
		this.setSize(500,600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);		
		this.setResizable(false);
		this.setVisible(true);
		
		
		/*
		 * Aggiunta del menuBar e dei suoi oggetti
		 */
		this.setJMenuBar(menusBar);
		
		menusBar.add(exitItem);
		menusBar.add(save_Exit);
		
	}
	
	// Setter & Getter
	
	public JMenuBar getMenusBar(){
		return menusBar;
	}
	
	public JMenuItem getExitItem() {
		return exitItem;
	}
	
	public JMenuItem getSave_Exit() {
		return save_Exit;
	}
		
	/*
	 * METODI
	 */
	
	// Metodo per la creazione dei bordi nel pannello
		public Border setPanelBorders(String heading, int top,int left,int bottom, int right ) {
			Border bordoInterno = BorderFactory.createTitledBorder(heading);
			Border bordoEsterno = BorderFactory.createEmptyBorder(top, left, bottom, right);
			Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
			
			return bordoFinale;
		}
		
		// Metodo per la visualizzazione dei dati delle carte all'interno delle tabelle
		public  DefaultTableModel dataViewCards(DefaultTableModel a) {
			int rowCount = a.getRowCount();
			for(int i = rowCount-1; i>=0; i--) {
				a.removeRow(i);
			}
			
			for(MTG_Card c : MTG_Data_Controller.getCards()) {
				a.addRow(c.toArray());
			}
			return a;
				
			}
		
		// Metodo per la visualizzazione dei dati dei deck  all'interno delle tabelle
		public DefaultTableModel dataViewDecks(DefaultTableModel a) {
			int rowCount = a.getRowCount();
			for(int i = rowCount -1; i>=0; i--) {
				a.removeRow(i);
			}
			
			for(MTG_Deck c : MTG_Data_Controller.get_Decks()) {
				a.addRow(c.toArray());
			}
			return a;
		}
		
		// Metodo per la visualizzazione delle carte all'interno del deck all'indice richiesto
		public DefaultTableModel dataViewCards_Decks(DefaultTableModel a, int b) {
			int rowCount = a.getRowCount();
			for(int i = rowCount -1; i>=0; i--) {
				a.removeRow(i);
			}
			
			for(MTG_Card c : MTG_Data_Controller.get_Decks().get(b).getDeck_Cards()) {
				a.addRow(c.toArray());
			}
			return a;
		}
		
		// Metodo per la visualizzazione delle carte all'interno del deck al momento della creazione del deck
		public DefaultTableModel dataViewCards_Decks(DefaultTableModel a) {
			int rowCount = a.getRowCount();
			for(int i = rowCount -1; i>=0; i--) {
				a.removeRow(i);
			}
			
			for(MTG_Card c : MTG_Data_Controller.get_Decks().get(MTG_Data_Controller.get_Decks().size()-1).getDeck_Cards()) {
				a.addRow(c.toArray());
			}
			return a;
		}
		// Metodo per la visualizzazione delle carte all'interno di un deck commander all'indice richiesto
		public DefaultTableModel dataViewCards_CommanderDeck(DefaultTableModel a, int n) {
			int rowCount = a.getRowCount();
			for(int i = rowCount -1; i>=0; i--) {
				a.removeRow(i);
			}
			
			for(MTG_Card c : MTG_Data_Controller.get_Decks().get(n).getDeck_Cards()) {
				a.addRow(c.toArray_InDeck_Commander());
			}
			return a;
		}
		
		// Metodo per accedere all'indice @Parm a dell'ArrayList delle carte
		public MTG_Card getCard_AtArrayIndex(int a) {
			return MTG_Data_Controller.getCards().get(a);
		}
		
		// Metodo per accedere all'indice @Parm a dell'ArrayList dei deck
		public MTG_Deck getDeck_AtArrayIndex(int a) {
			return MTG_Data_Controller.get_Decks().get(a);
		}
		
	 

}
