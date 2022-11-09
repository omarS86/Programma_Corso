package hobbyshop.model.MTG_Card;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils; 

public class Database_MTG_Card {
	
	private static ArrayList<MTG_Card> cardList = new ArrayList<>();
	private static ArrayList<MTG_Card> new_Card_List = new ArrayList<>();
	private static File fileCardList = new File("cardDBList.txt");
	private ObjectInputStream ois_Card = null;
	
	// Oggetti per la connessione al DB
	private static Connection cn;
	private static Statement st;
	private static ResultSet rs;
	private static String sql;
	private static String db_Address = "jdbc:mysql://localhost:3306/hobby_shop?user=root&password=";
	
	public Database_MTG_Card() {}
	
	// Setter & Getter
	
	public ArrayList<MTG_Card> getCardList(){
		return cardList;
	}
	
	public ArrayList<MTG_Card> getNew_Card_List(){
		return new_Card_List;
	}
	
	public File getFileCardList() {
		return fileCardList;
	}
	
	/*
	 * Metodi 
	 */
	
	// Metodo per aggiungere una carta all'ArrayList
	public void addCard(MTG_Card card) {
		cardList.add(card);		
	}
	
	public void add_New_Card(MTG_Card card) {
		new_Card_List.add(card);
	}
	
	// Metodo per salvare la lista delle carte su file
	public void save_To_File_CardList(File fileCardList) throws IOException {
		ObjectOutputStream oos_Card = new ObjectOutputStream(new FileOutputStream(fileCardList));
		
		MTG_Card[] arrayCard = cardList.toArray(new MTG_Card[cardList.size()]);
		oos_Card.writeObject(arrayCard);
		
		oos_Card.close();
		
	}
	
	// Metodo per caricare la lista delle carte dal file
	public void load_From_File_CardList(File fileCardList) throws IOException , ClassNotFoundException {
		
		ois_Card = new ObjectInputStream(new FileInputStream(fileCardList));
		MTG_Card[] loadedCards = (MTG_Card[]) ois_Card.readObject();
		cardList.clear();
		cardList.addAll(Arrays.asList(loadedCards));			
		
		ois_Card.close();		
	}
	
	/*
	 * Metodi per la connessione al DB 
	 */
	
	public static String escapeSQL(String str) {
		if(str == null) {
			return null;
		}
		return StringUtils.replace(str, "'", "''");
	}
	
	// Metodo controllo presanza del corretto caricamento dei driver per la connessione
	public static void libCheck(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			System.out.println("Classe non trovata");
			System.out.println(e.getMessage());
		}
	}
	
	// Metodo per caricare i dati dalla tabella
	public static ArrayList<MTG_Card> get_CardList() throws SQLException{
		libCheck();
		cn = DriverManager.getConnection(db_Address);
		sql = "SELECT * FROM mtg_cards;";
		
		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next() == true) {				
				String tipeString = rs.getString("tipe");				
				MTG_Card a = new MTG_Card(rs.getString("nome"),rs.getString("castingcost"),MTG_Card.tipeCheck(tipeString),rs.getInt("quantity"));
				a.setQty_InDeck(rs.getInt("qty_InDeck"));
				a.setId(rs.getInt("id"));
				cardList.add(a);
				
			}
			
		}catch(SQLException e) {
			System.out.println("errore" + e.getMessage() );
		}
		cn.close();
		return cardList;
	}
	
	// Metodo per eliminare i dati dal DB mtg_cards
	public static void delete_Card(MTG_Card a) throws SQLException{
		libCheck();
		
		try {
			cn = DriverManager.getConnection(db_Address);
			int id = a.getId();
			
			sql = "delete from mtg_cards where id=" + id;
			
			st = cn.createStatement();
			st.execute(sql);
			cn.close();
		}catch(SQLException e) {
			System.out.println("errore" + e.getMessage());
		}
		
	}
	
	// Metodo inserimento nuova carta all'interno del DB mtg_cards
	public static void new_Card(MTG_Card a) {
		libCheck();
		
		try {
			cn = DriverManager.getConnection(db_Address);
			
			String name = escapeSQL(a.getCard_Name());
			String casting_Cost = escapeSQL(a.getCasting_Cost());
			String tipe = a.tipe_String(a);
			int qty = a.getQuantity();
			int qty_InDeck = a.getQty_InDeck();			
			
			sql = "insert into mtg_cards (nome, castingcost, tipe, quantity, qty_InDeck) values ('"+ name +"','"+ casting_Cost +"','"+ tipe +"','"+ qty +"','"+ qty_InDeck +"')";
			
			st = cn.createStatement();
			st.execute(sql);
			cn.close();
		} catch(SQLException e) {
			System.out.println("errore"+ e.getMessage());
		}
	}
	
	public static void change_Card(MTG_Card a) {
		libCheck();
		
		try {
			cn = DriverManager.getConnection(db_Address);
			
			int id = a.getId();
			String name = escapeSQL(a.getCard_Name());
			String casting_Cost = escapeSQL(a.getCasting_Cost());
			String tipe = a.tipe_String(a);
			int qty = a.getQuantity();
			
			sql = "update mtg_cards set nome='"+ name + "', castingcost = '"+ casting_Cost +"', tipe='" + tipe + "' where id=" + id;
			
			st = cn.createStatement();
			st.execute(sql);
			cn.close();
		}catch(SQLException e) {
			System.out.println("errore" + e.getMessage());
		}
	}

}
