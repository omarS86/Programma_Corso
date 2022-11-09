package hobbyshop.model.MTG_Deck;

import java.io.Serializable;
import java.util.ArrayList;

import hobbyshop.model.MTG_Card.MTG_Card;

public class MTG_Deck implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4922182166029190256L;
		
	private String deck_Name; 										// Variabile per il nome del deck	
	private static int contatore = 0;
	private int id;
	private int number_Of_Cards;									// Variabile del numero di carte all'interno di un deck
	private Deck_Format deck_Format;								// Variabile che definisce il formato del mazzo
	
	private ArrayList<MTG_Card>deck_Cards = new ArrayList<>();		// ArrayList dove viene salvata la lista dei deck
	private MTG_Card commader = new MTG_Card();						// Oggetto dove viene salvato il comandate del deck nel caso il formato sia Commander
	
	
	public MTG_Deck() {}
	
	public MTG_Deck(String deck_Name, Deck_Format deck_Format) {
		
		this.id = contatore;
		this.deck_Name = deck_Name;
		this.deck_Format = deck_Format;
		
		contatore++;
	}
	
	
	// Setter & Getter
	public void setDeck_Name(String deck_Name) {
		this.deck_Name = deck_Name;
	}

	public String getDeck_Name() {
		return deck_Name;
	}


	public int getNumber_Of_Cards() {
		return number_Of_Cards;
	}


	public void setNumber_Of_Cards(int number_Of_Cards) {
		this.number_Of_Cards = number_Of_Cards;
	}


	public Deck_Format getDeck_Format() {
		return deck_Format;
	}


	public void setDeck_Format(Deck_Format deck_Format) {
		this.deck_Format = deck_Format;
	}


	public ArrayList<MTG_Card> getDeck_Cards() {
		return deck_Cards;
	}


	public void setDeck_Cards(ArrayList<MTG_Card> deck_Cards) {
		this.deck_Cards = deck_Cards;
	}


	public MTG_Card getCommader() {
		return commader;
	}


	public void setCommader(MTG_Card commader) {
		this.commader = commader;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	// Metodi
	
	

	public Object[] toArray() {
		Object[] a = new Object[] {getId(), getDeck_Name(), getNumber_Of_Cards(), getDeck_Format()};
		return a;		
	}
	
	// Metodo per aggiungere una carta all'interno del deck
	public void addCard(MTG_Card card) {
		deck_Cards.add(card);
	}
	

}
