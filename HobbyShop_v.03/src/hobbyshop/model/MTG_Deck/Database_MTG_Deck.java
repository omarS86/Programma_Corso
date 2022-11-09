package hobbyshop.model.MTG_Deck;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;



public class Database_MTG_Deck {	
		
	private static ArrayList<MTG_Deck> deck_List = new ArrayList<>();	
	private static File fileDeckList = new File("deckDBList.txt");
	
	public Database_MTG_Deck() {}
	
	// Setter & Getter
	
	public ArrayList<MTG_Deck> getDeck_List(){
		return deck_List;
	}
	
	public File getFileDeckList() {
		return fileDeckList;
	}			
	// Metodi	
	
	// Metodo per aggiungere un deck alla lista
	public void addDeck(MTG_Deck deck) {
		deck_List.add(deck);
	}
	
	public void save_To_File_DeckList(File fileDeckList) throws IOException{
		ObjectOutputStream oos_Deck = new ObjectOutputStream(new FileOutputStream(fileDeckList));
		
		MTG_Deck[] deckArray = deck_List.toArray(new MTG_Deck[deck_List.size()]);
		oos_Deck.writeObject(deckArray);
		oos_Deck.close();		
	}
	
	public void load_From_File_DeckList(File fileDeckList) throws IOException, ClassNotFoundException{
		
		ObjectInputStream ois_Deck = new ObjectInputStream(new FileInputStream(fileDeckList));		
		MTG_Deck[]loadedDecks = (MTG_Deck[]) ois_Deck.readObject();
		deck_List.clear();
		deck_List.addAll(Arrays.asList(loadedDecks));		
		
		ois_Deck.close();
		
		
	}
	
}
