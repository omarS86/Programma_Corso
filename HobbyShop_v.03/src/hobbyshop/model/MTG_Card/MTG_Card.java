package hobbyshop.model.MTG_Card;

import java.io.Serializable;
import java.util.ArrayList;

public class MTG_Card<E> implements Serializable {

	private int id;
	private Tipe tipe;								// Variabile per il tipo della carta
	
	private String card_Name; 						// Variabile per il nome della carte	
	private String casting_Cost;					// String per assegnare il colore del casting cost della carta
	
	private int quantity;							// Variabile per la quantità di carte possedute
	private int qty_InDeck;							// Variabile per la quantita di carte assegnate ai deck
	private int qty_InCommander;					// Variabile per la quantita all'interno di un deck commander
	
	ArrayList<String> in_Deck = new ArrayList<>();	// Array che visualizza il deck in cui viene usata la carta
	
	public MTG_Card(String cardName, String castingCost, Tipe tipe, int quantity){
			
			this.card_Name = cardName;
			this.casting_Cost = castingCost;
			this.tipe = tipe;
			this.quantity = quantity;
		}
	
	public MTG_Card() {}
	
	
	//Setter & Getter
	public String getCard_Name() {
		return card_Name;
	}
	public void setCard_Name(String card_Name) {
		this.card_Name = card_Name;
	}
	public Tipe getTipe() {
		return tipe;
	}
	public void setTipe(Tipe tipe) {
		this.tipe = tipe;
	}
	public String getCasting_Cost() {
		return casting_Cost;
	}
	public void setCasting_Cost(String casting_Cost) {
		this.casting_Cost = casting_Cost;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getQty_InDeck() {
		return qty_InDeck;
	}

	public void setQty_InDeck(int qty_InDeck) {
		this.qty_InDeck = qty_InDeck;
	}
	
	public int getQty_InCommander() {
		return qty_InCommander;
	}
	
	public void setQty_InCommander(int qty_InCommander){
		this.qty_InCommander = qty_InCommander;
	}
	
	public ArrayList<String> getIn_Deck() {
		return in_Deck;
	}
	
	public void setIn_Deck(ArrayList<String> in_Deck) {
		this.in_Deck = in_Deck;
	}

	// Metodi di utilità
	public Object[] toArray() {
		Object[] a = new Object[] {getCard_Name(), getCasting_Cost(), getTipe(), getQuantity(), getQty_InDeck()};
		return a;
	}
	
	public Object[] toArray_InDeck_Commander() {
		Object[] a = new Object[] {getCard_Name(), getCasting_Cost(), getTipe(), getQty_InCommander()};
		return a;
	}
	
	public String toString() {
		return getId()+ " "+getCard_Name()+" "+ getCasting_Cost()+" "+ getTipe()+" "+getQuantity()+" "+getQty_InDeck(); 
	}
	
	public String getListOfDecks() {
		StringBuilder sb = new StringBuilder();
		sb.append(" ");		
		for (String a : in_Deck) {
			sb.append(a +" ");
		}
		return sb.toString();
		
	}
	
	// Metodo per impostare il tipo avendo in entrata una stringa
	public static Tipe tipeCheck(String str) {
		
		Tipe tipe = null;
		if(str.equals("Creatura")) {
			tipe = Tipe.Creatura;
		} else if (str.equals("Istantaneo")) {
			tipe = Tipe.Istantaneo;
		} else if (str.equals("Stregoneria")) {
			tipe = Tipe.Stregoneria;
		} else if (str.equals("Incantesimo")) {
			tipe = Tipe.Incantesimo;
		} else if (str.equals("Artefatto")) {
			tipe = Tipe.Artefatto;
		} else if (str.equals("Terra")){
			tipe = Tipe.Terra;
		}
		return tipe;
	}
	
	// Metodo che restituisce sotto forma di stringa il tipo della carta
	public String tipe_String(MTG_Card a) {
		String aString = null;
		Tipe tipe = null;
		if(a.getTipe() == tipe.Creatura) {
			aString = "Creatura";			
		} else if (a.getTipe() == tipe.Istantaneo){
			aString = "Istantaneo";			
		} else if (a.getTipe() == tipe.Stregoneria) {
			aString = "Stregoneria";
		} else if (a.getTipe() == tipe.Incantesimo) {
			aString = "Incantesimo";
		} else if (a.getTipe() == tipe.Artefatto) {
			aString = "Artefatto";
		} else if (a.getTipe() == tipe.Terra) {
			aString = "Terra";
		}
		return aString;
			
	}

	
	
}
