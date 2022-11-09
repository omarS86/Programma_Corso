package hobbyshop.view;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import hobbyshop.controller.MTG_Data_Controller;
import hobbyshop.model.MTG_Card.MTG_Card;
import hobbyshop.model.MTG_Deck.MTG_Deck;

// Classe in fase di implementazione con l'intento riunire in un unica classe i metodi piu usati

public class Generic_Methods {
	
	// Metodo per la creazione dei bordi nel pannello
		public static Border setPanelBorders(String heading, int top,int left,int bottom, int right ) {
			Border bordoInterno = BorderFactory.createTitledBorder(heading);
			Border bordoEsterno = BorderFactory.createEmptyBorder(top, left, bottom, right);
			Border bordoFinale = BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
			
			return bordoFinale;
		}
		
	// Metodo per la visualizzazione dei dati delle carte all'interno delle tabelle
	public static DefaultTableModel dataViewCards(DefaultTableModel a) {
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
	public static DefaultTableModel dataViewDecks(DefaultTableModel a) {
		int rowCount = a.getRowCount();
		for(int i = rowCount -1; i>=0; i--) {
			a.removeRow(i);
		}
		
		for(MTG_Deck c : MTG_Data_Controller.get_Decks()) {
			a.addRow(c.toArray());
		}
		return a;
	}
	
	// Metodo per la visualizzazione dele carte all'interno del deck al momento della creazione del deck
	public static DefaultTableModel dataViewCards_Decks(DefaultTableModel a) {
		int rowCount = a.getRowCount();
		for(int i = rowCount -1; i>=0; i--) {
			a.removeRow(i);
		}
		
		for(MTG_Card c : MTG_Data_Controller.get_Decks().get(MTG_Data_Controller.get_Decks().size()-1).getDeck_Cards()) {
			a.addRow(c.toArray());
		}
		return a;
	}
	
	// Metodo per la visualizzazione delle carte all'interno del deck all'indice richiesto
	public static DefaultTableModel dataViewCards_Decks(DefaultTableModel a, int n) {
		int rowCount = a.getRowCount();
		for(int i = rowCount -1; i>=0; i--) {
			a.removeRow(i);
		}
		
		for(MTG_Card c : MTG_Data_Controller.get_Decks().get(n).getDeck_Cards()) {
			a.addRow(c.toArray());
		}
		return a;
	}
	
	// Metodo per la visualizzazione delle carte all'interno di un deck commander all'indice richiesto
	public static DefaultTableModel dataViewCards_CommanderDeck(DefaultTableModel a, int n) {
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
	public static MTG_Card getCard_AtArrayIndex(int a) {
		return MTG_Data_Controller.getCards().get(a);
	}
	
	// Metodo per accedere all'indice @Parm a dell'ArrayList dei deck
	public static MTG_Deck getDeck_AtArrayIndex(int a) {
		return MTG_Data_Controller.get_Decks().get(a);
	}
	
	/* Metodo che restisce True @Param ans se all'interno della Lista dei deck @Param b 
	 * è presente la stringa a (nome della carta)	
	*/
	public static boolean cardPresence(String a, int b) {
		boolean ans = false;
		for(MTG_Card z : getDeck_AtArrayIndex(b).getDeck_Cards()) {
			boolean ans1 = z.getCard_Name().contains(a);
			if(ans1) {
				ans = ans1;
				break;
			}
		 }
		return ans;
		
	}

}
