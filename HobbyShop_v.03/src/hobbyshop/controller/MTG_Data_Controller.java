package hobbyshop.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import hobbyshop.model.MTG_Card.Database_MTG_Card;
import hobbyshop.model.MTG_Card.MTG_Card;
import hobbyshop.model.MTG_Card.Tipe;
import hobbyshop.model.MTG_Deck.Database_MTG_Deck;
import hobbyshop.model.MTG_Deck.Deck_Format;
import hobbyshop.model.MTG_Deck.MTG_Deck;

public class MTG_Data_Controller {
	
	private static Database_MTG_Deck dbDeck = new Database_MTG_Deck();
	private static Database_MTG_Card dbCard = new Database_MTG_Card();
	
	
	// Gestione del DB delle Carte
	
	// Metodo per l'aggiunta della carta nell'ArrayList principale
		public static void addCard(String card_Name, String casting_Cost, String tipeString, int quantity) {
			
			
			// Conversione Tipe
			Tipe tipe = null;
			if(tipeString.equals("Creatura")) {
				tipe = Tipe.Creatura;
			} else if (tipeString.equals("Istantaneo")) {
				tipe = Tipe.Istantaneo;
			} else if (tipeString.equals("Stregoneria")) {
				tipe = Tipe.Stregoneria;
			} else if (tipeString.equals("Incantesimo")) {
				tipe = Tipe.Incantesimo;
			} else if (tipeString.equals("Artefatto")) {
				tipe = Tipe.Artefatto;
			} else if (tipeString.equals("Terra")){
				tipe = Tipe.Terra;
			}
			
			// Creazione della nuova carta
			MTG_Card newCard = new MTG_Card(card_Name, casting_Cost, tipe, quantity );
			newCard.setQty_InDeck(quantity);
			// Aggiunta della nuova carta al DB
			//Database_MTG_Card.new_Card(newCard);
			dbCard.addCard(newCard);
		}
		
		// Metodo per l'aggiunta delle carte nell'ArrayList di visualizzazione temporanea nella pagina dell'inserimento delle nuove carte
		public static void add_New_Card(String card_Name, String casting_Cost, String tipeString, int quantity) {
				
				
				// Conversione Tipe
				Tipe tipe = null;
				if(tipeString.equals("Creatura")) {
					tipe = Tipe.Creatura;
				} else if (tipeString.equals("Istantaneo")) {
					tipe = Tipe.Istantaneo;
				} else if (tipeString.equals("Stregoneria")) {
					tipe = Tipe.Stregoneria;
				} else if (tipeString.equals("Incantesimo")) {
					tipe = Tipe.Incantesimo;
				} else if (tipeString.equals("Artefatto")) {
					tipe = Tipe.Artefatto;
				} else if (tipeString.equals("Terra")){
					tipe = Tipe.Terra;
				}
				
				// Creazione della nuova carta
				MTG_Card newCard = new MTG_Card(card_Name, casting_Cost, tipe, quantity );
				
				// Aggiunta della nuova carta al DB			
				dbCard.add_New_Card(newCard);
			}
		
		// metodo che estrapola la lista delle carte dal DB
		public static ArrayList<MTG_Card> getCards(){
			return dbCard.getCardList();
		}
		
		public static ArrayList<MTG_Card> get_New_Card_List(){
			return dbCard.getNew_Card_List();
		}
		
		// Metodo che restituisce il file dove sono salvate le carte
		public static File get_CardFile() {
			return dbCard.getFileCardList();
		}
		
		public static void save_To_File_CardList() throws IOException {
			dbCard.save_To_File_CardList(get_CardFile());
		}
		
		public static void load_From_File_CardList() throws IOException, ClassNotFoundException {
			dbCard.load_From_File_CardList(get_CardFile());
		}
		
	// Gestione del DB dei Deck
	
	/*	 
	 * Metodo per L'aggiunta di un deck all'interno dell'ArrayList
	 */

	public static void addDeck(String deck_Name, String deckFormatString) {
		
		//Conversione da stringa a tipo Deck_Format
		Deck_Format deck_Format = null;
		if(deckFormatString.equals("Commander")) {
			deck_Format = Deck_Format.Commander;
		}else if(deckFormatString.equals("Modern")) {
			deck_Format = Deck_Format.Modern;			
		}
		
		// Creazione del deck inserire nel db
		MTG_Deck new_Deck = new MTG_Deck(deck_Name,deck_Format);
				
		// Aggiunta del deck creato al DB
		dbDeck.addDeck(new_Deck);
	}
	
	// Metoto per estrapolare la liste dei deck	
	public static ArrayList<MTG_Deck> get_Decks(){
		return dbDeck.getDeck_List();
	}
	
	// Metodo che restituisce il file dove viene salvata la lista dei deck
	public static File get_DecksFile() {
		return dbDeck.getFileDeckList();
	}
	
	public static void save_To_File_DeckList() throws IOException{
		dbDeck.save_To_File_DeckList(get_DecksFile());
	}
	
	public static void load_From_File_DeckList() throws ClassNotFoundException, IOException{
		dbDeck.load_From_File_DeckList(get_DecksFile());
	}
	
	
	// Metodo che restituisce il comandante all'indice richiesto (metodo da migliorare)
	public static String getCommander_Decks(int c) {		
		String b = null;
		MTG_Card aCard = dbDeck.getDeck_List().get(c).getCommader();
		Deck_Format deck_Format = dbDeck.getDeck_List().get(c).getDeck_Format() ;
		if(deck_Format.equals(Deck_Format.Commander)) {
			b = aCard.getCard_Name();
		}else if (deck_Format.equals(Deck_Format.Modern)) {
			b = "Modern";
		}
		return b;
	}
}
