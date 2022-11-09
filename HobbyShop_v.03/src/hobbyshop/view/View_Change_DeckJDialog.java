package hobbyshop.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import hobbyshop.controller.MTG_Data_Controller;
import hobbyshop.model.MTG_Card.MTG_Card;
import hobbyshop.model.MTG_Deck.Deck_Format;

public class View_Change_DeckJDialog extends JDialog {	
	
	// Istanza dell'oggetto New_Deck_Building_Panel 
	private View_Change_Panel ViewChange = new View_Change_Panel();
	
	// Oggetti del menu	
	private JMenuBar ndb_MenuBar = new JMenuBar();	
	private JMenuItem exit_Item = new JMenuItem("Esci"),
					  save_Exit_Item = new JMenuItem("Salva ed Esci");
	
	// Costruttore dove la classe madre è di tipo Frame
	View_Change_DeckJDialog(Frame owner,String title){
		super(owner,title);
		this.setSize(1000,600); 
		Container dialogContent = getContentPane();
		dialogContent.setLayout(new GridLayout());
		this.setLocationRelativeTo(null);
		
		// Aggiunta della barra menu
		this.setJMenuBar(ndb_MenuBar);
		
		save_Exit_Item.addActionListener(e->{
			try {
				MTG_Data_Controller.save_To_File_DeckList();
				MTG_Data_Controller.save_To_File_CardList();
				ViewChange.dataViewCards(Frame_HomePage.tableModel_Card_Menager);
				ViewChange.dataViewDecks(Frame_HomePage.tableModel_Deck_Menager);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.dispose();
			
		});
		ndb_MenuBar.add(save_Exit_Item);
		exit_Item.addActionListener(e->{
			dispose();
		});
		ndb_MenuBar.add(exit_Item);
		
		dialogContent.add(ViewChange);
		
		this.pack();
	}
	
	// Costruttore dove la classe madre è di tipo Dialog
	View_Change_DeckJDialog(Dialog owner,String title){
		super(owner,title);
		this.setSize(1000,600); 
		Container dialogContent = getContentPane();
		dialogContent.setLayout(new GridLayout());
		this.setLocationRelativeTo(null);
		
		// Aggiunta della barra menu
		this.setJMenuBar(ndb_MenuBar);
		
		save_Exit_Item.addActionListener(e->{
			try {
				MTG_Data_Controller.save_To_File_DeckList();
				MTG_Data_Controller.save_To_File_CardList();
				ViewChange.dataViewCards(Frame_HomePage.tableModel_Card_Menager);
				ViewChange.dataViewDecks(Frame_HomePage.tableModel_Deck_Menager);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.dispose();
			
		});
		ndb_MenuBar.add(save_Exit_Item);
		exit_Item.addActionListener(e->{
			dispose();
		});
		ndb_MenuBar.add(exit_Item);
		
		dialogContent.add(new View_Change_Panel());
		
		this.pack();
	}
	
	
	public class View_Change_Panel extends New_Sample_Panel{
		
		private int Array_Pointer; // Variabile usata per estrapolare l'indice a cui far riferimento nell'array dei deck
		
		private final JLabel labelDeckName = new JLabel("Nome del Deck"),
				             labelFormat = new JLabel("Formato"),
				             labelDeck_Qty_Card = new JLabel("N° di Carte"),
				             labelCommander = new JLabel("Comandante");

		private JTextField textDeckName = new JTextField(),
					       textDeckFormat = new JTextField(),
					       textDeck_Qty_Card = new JTextField(),
					       textCommander = new JTextField();
		
		// Tabelle, oggetti, popupMenu delle Tabelle
		private String[] columnsCard = {"Nome Carta","Costo Mana","Tipo","Qtà","Disponibili"};
		private String[] columnsCard_Deck = {"Nome Carta","Costo Mana","Tipo","Qtà"};
		private Object[][] objectCard = {};
		private JPanel MTG_Cards_Panel, MTG_DeckCards_Panel;
		private DefaultTableModel tableModel_Cards = new DefaultTableModel(objectCard,columnsCard),
						          tableModel_DeckCards = new DefaultTableModel(objectCard,columnsCard_Deck);
		
		// Oggetti del Layout
		private GridBagConstraints gbc = new GridBagConstraints();
		
		// Oggetti di servizio
		
		
		
		// Oggetti PopupMenu
		private JPopupMenu popupMenu_Card_Data = new JPopupMenu(),
				   		   popupMenu_DeckCards = new JPopupMenu();
		
		
		public View_Change_Panel() {
			super();
			// Impostazione bordi del panello
			this.setBorder(setPanelBorders("Crea un nuovo deck",0,5,5,5));
			
			
			
			// Oggetti comuni ad entrabe le tabelle			
			DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		    cellRenderer.setHorizontalAlignment(JLabel.CENTER);
			
		 // Tabella visualizzazione dell carte all'interno della finestra gestione deck
			JTable MTG_Cards_Table = new JTable(tableModel_Cards) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			MTG_Cards_Table.getTableHeader().setReorderingAllowed(false);
			
			// Aggiunta Listener per menu popup alla tabella carte
			MTG_Cards_Table.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3) {
						popupMenu_Card_Data.show(MTG_Cards_Table, e.getX(), e.getY());
					}
				}
			});
			
			// Istanza oggetto JMenuItem per l'aggiunta delle carte e suo listener
			JMenuItem menu_Card_AddCard = new JMenuItem("Aggiungi carta al deck");
			menu_Card_AddCard.addActionListener((e)->{
				DefaultTableModel model = (DefaultTableModel)MTG_Cards_Table.getModel();
				int row = MTG_Cards_Table.getSelectedRow();
				
				while(row !=-1) {
					 int modelRow = MTG_Cards_Table.convertRowIndexToModel(row);
					 MTG_Card MTG_Card_Sample = MTG_Data_Controller.getCards().get(modelRow);					 					 
					 String aString = MTG_Card_Sample.getCard_Name();					 
					 if(getDeck_AtArrayIndex(Array_Pointer).getDeck_Format() == Deck_Format.Commander) {
						 cardPresence(aString,Array_Pointer);
						 System.out.println(cardPresence(aString,Array_Pointer));						 
						 if(cardPresence(aString,Array_Pointer)){
							 JOptionPane.showMessageDialog(this, "Carta già presente nel deck", "Attenzione" , JOptionPane.WARNING_MESSAGE);								 
						 }else {
							 if(getCard_AtArrayIndex(modelRow).getQty_InDeck() == 0) {
								 JOptionPane.showMessageDialog(this, "Nessuna carta disponibile nella collezione", "Attenzione" , JOptionPane.WARNING_MESSAGE);						 
							 }else{
								 MTG_Card_Sample.setQty_InCommander(1);
								 getDeck_AtArrayIndex(Array_Pointer).addCard(MTG_Card_Sample);
								 getDeck_AtArrayIndex(Array_Pointer).setNumber_Of_Cards(getDeck_AtArrayIndex(Array_Pointer).getDeck_Cards().size());
								 getCard_AtArrayIndex(modelRow).setQty_InDeck(getCard_AtArrayIndex(modelRow).getQty_InDeck()-1);								 
								 getCard_AtArrayIndex(modelRow).getIn_Deck().add(getDeck_AtArrayIndex(Array_Pointer).getDeck_Name());
								 setString_TextDeck_Qty_Card(String.valueOf(getDeck_AtArrayIndex(Array_Pointer).getDeck_Cards().size()));					 
								 this.dataViewCards(tableModel_Cards);
								 this.dataViewCards_CommanderDeck(tableModel_DeckCards, Array_Pointer);								 
								 }
						 	}
						 
					 }					 
					 break;
					 
				}
			});
			popupMenu_Card_Data.add(menu_Card_AddCard);
			
			// Istanza oggetto JMenuItem per assegnare il comandate nel caso il formato sia Commander
			JMenuItem setDeck_Commander = new JMenuItem("Usa come Comandante");
			setDeck_Commander.addActionListener(e->{
				DefaultTableModel model = (DefaultTableModel)MTG_Cards_Table.getModel();
				int row = MTG_Cards_Table.getSelectedRow();
			
				while(row !=-1) {
					int modelRow = MTG_Cards_Table.convertRowIndexToModel(row);
					MTG_Card MTG_Cards_Sample = getCard_AtArrayIndex(modelRow);
					getDeck_AtArrayIndex(Array_Pointer).setCommader(MTG_Cards_Sample);
					setString_TextCommander(MTG_Cards_Sample.getCard_Name());
					textCommander.setBackground(Color.GREEN);
					break;
				}
				
			});			
			popupMenu_Card_Data.add(setDeck_Commander);
			
			
			
			MTG_Cards_Panel = new JPanel();				
			MTG_Cards_Panel.setLayout(new BorderLayout());
			MTG_Cards_Panel.add(new JScrollPane(MTG_Cards_Table), BorderLayout.CENTER);
			MTG_Cards_Panel.setBorder(setPanelBorders("Carte Possedute",0,5,5,5));
			MTG_Cards_Table.getColumnModel().getColumn(0).setPreferredWidth(200);
			MTG_Cards_Table.getColumnModel().getColumn(3).setPreferredWidth(10);		
		    MTG_Cards_Table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
		    MTG_Cards_Table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
		    MTG_Cards_Table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
		    this.dataViewCards(tableModel_Cards); // Visualizzazione dati delle carte
		    
		    // Seconda tabella dove vengono visualizzate le carte che vengono aggiunte al deck	    
		    JTable MTG_DeckCards_Table = new JTable(tableModel_DeckCards){
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			MTG_DeckCards_Table.getTableHeader().setReorderingAllowed(false);
			
		    MTG_DeckCards_Panel = new JPanel();
		    MTG_DeckCards_Panel.setLayout(new BorderLayout());
		    MTG_DeckCards_Panel.add(new JScrollPane(MTG_DeckCards_Table), BorderLayout.CENTER);
		    MTG_DeckCards_Panel.setBorder(setPanelBorders("Carte presenti nel deck",0,5,5,5));
		    MTG_DeckCards_Table.getColumnModel().getColumn(0).setPreferredWidth(200);
		    MTG_DeckCards_Table.getColumnModel().getColumn(3).setPreferredWidth(10);		
		    MTG_DeckCards_Table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
		    MTG_DeckCards_Table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
		    MTG_DeckCards_Table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
		    
		    // Aggiunta Listener per menu popup alla tabella delle carte contenute nel deck
		    MTG_DeckCards_Table.addMouseListener(new MouseAdapter() {
		    	public void mousePressed(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3) {
						popupMenu_DeckCards.show(MTG_DeckCards_Table, e.getX(), e.getY());
					}
				}
		    	
		    });
		    
		    // Istanza oggetto JMenuItem aggiunto al PopupMenu per opzione rimozzione carte
		    JMenuItem popup_Item_RemoveCard = new JMenuItem("Rimuovi Carta");
		    popup_Item_RemoveCard.addActionListener(e->{
		    	DefaultTableModel model = (DefaultTableModel)MTG_DeckCards_Table.getModel();
		    	int row = MTG_DeckCards_Table.getSelectedRow();
		    	
		    	while(row != -1) {
		    		int modelRow = MTG_DeckCards_Table.convertRowIndexToModel(row);
		    		MTG_Card MTG_Card_Sample = getDeck_AtArrayIndex(Array_Pointer).getDeck_Cards().get(modelRow);		    		
		    		String nameString = MTG_Card_Sample.getCard_Name();
		    		String deckNameString = getDeck_AtArrayIndex(Array_Pointer).getDeck_Name();
		    		ArrayList<String> deckList = MTG_Card_Sample.getIn_Deck();
		    		for(MTG_Card a : MTG_Data_Controller.getCards()) {
		    			if(a.getCard_Name().contains(nameString)){
		    				int aInt = MTG_Data_Controller.getCards().indexOf(a);		    				
				    		for(int i=0; i<deckList.size(); i++){
				    			String b = deckList.get(i);
				    			if(b.contains(deckNameString)) {
				    				int cInt = getCard_AtArrayIndex(aInt).getIn_Deck().indexOf(b);
				    				getCard_AtArrayIndex(aInt).setQty_InDeck(getCard_AtArrayIndex(aInt).getQty_InDeck()+1);
				    				System.out.println(cInt);
				    				getCard_AtArrayIndex(aInt).getIn_Deck().remove(cInt);				    				
				    			}
				    		}
		    			}
		    		}		    		
		    		model.removeRow(modelRow);		    		
		    		getDeck_AtArrayIndex(Array_Pointer).getDeck_Cards().remove(modelRow);
		    		getDeck_AtArrayIndex(Array_Pointer).setNumber_Of_Cards(getDeck_AtArrayIndex(Array_Pointer).getDeck_Cards().size());
		    		setString_TextDeck_Qty_Card(String.valueOf(getDeck_AtArrayIndex(Array_Pointer).getDeck_Cards().size()));
		    		this.dataViewCards(tableModel_Cards);
		    		this.dataViewCards_CommanderDeck(tableModel_DeckCards,Array_Pointer);
		    		break;
		    	}
		    });
		    popupMenu_DeckCards.add(popup_Item_RemoveCard);
		    
		    
			/* 
			 * Impostazioni del Layout e aggiunta dei componenti al pannello.
			 * I paramentri del Layout vengono dichiarati prima dell'aggiunta di ogni componente al pannello,
			 * viene usato un unico componente GridBagConstraints e prima di aggiungere il componente vengono
			 * reimpostati i singolo paramentri che allocano lo stesso nel pannello.			 * 			 
			 */
			this.setLayout(new GridBagLayout());
			
			
			// Label DeckName
			gbc.gridx = 0;
			gbc.gridy = 0;
			
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_END;		
			gbc.insets = new Insets(5,0,0,5);
			
			this.add(labelDeckName, gbc);
			
			// TextField DeckName
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_START;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5,0,0,0);
			
			textDeckName.setMinimumSize(new Dimension(100,1));
			textDeckName.setBackground(Color.WHITE);
			textDeckName.setEditable(false);
			
			this.add(textDeckName, gbc);
			
			// Label Format
			
			gbc.gridx = 2;
			gbc.gridy = 0;
			
			gbc.weightx = 0.1;
			gbc.weightx = 0.1;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_END;
			gbc.fill = GridBagConstraints.NONE;
			gbc.insets = new Insets(5,0,0,5);
			
			this.add(labelFormat, gbc);
			
			// TextField Format
			
			gbc.gridx = 3;
			gbc.gridy = 0;
			
			gbc.weightx = 0.1;
			gbc.weighty = 0.1;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_START;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5,0,0,0);
			
			textDeckFormat.setBackground(Color.WHITE);
			textDeckFormat.setEditable(false);
			
			this.add(textDeckFormat, gbc);
			
			// Label Comandante			
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			
			gbc.weightx = 0.01;
			gbc.weighty = 0.01;
			
			
			
			gbc.anchor = GridBagConstraints.LAST_LINE_END;
			gbc.fill = GridBagConstraints.NONE;
			gbc.insets = new Insets(5,0,0,5);
			
			this.add(labelCommander, gbc);
			
			// JTextField Commander
			
			gbc.gridx = 1;
			gbc.gridy = 1;
			
			gbc.weightx = 0.01;
			gbc.weighty = 0.01;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_START;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5,0,0,0);
			
			textCommander.setBackground(Color.WHITE);
			textCommander.setEditable(false);
			
			this.add(textCommander, gbc);
			
			// Label Qty Card
			gbc.gridx = 2;
			gbc.gridy = 1;
			
			gbc.weightx = 0.01;
			gbc.weighty = 0.01;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_END;
			gbc.fill = GridBagConstraints.NONE;
			gbc.insets = new Insets(5,0,0,5);
			
			this.add(labelDeck_Qty_Card, gbc);
			
			// TextField Qty Card
			gbc.gridx = 3;
			gbc.gridy = 1;
			
			gbc.weightx = 0.01;
			gbc.weighty = 0.01;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_START;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5,0,0,5);
			
			textDeck_Qty_Card.setBackground(Color.WHITE);
			textDeck_Qty_Card.setEditable(false);
			
			this.add(textDeck_Qty_Card,gbc);
			
			// Panel contenente la Tabella dove vengono visualizzate le carte gia presenti nel DB
			gbc.gridx = 0;
			gbc.gridy = 3;
			
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			
			gbc.gridheight = 1;
			gbc.gridwidth = 2;
			
			gbc.anchor = GridBagConstraints.PAGE_START;
			gbc.fill = GridBagConstraints.BOTH;
			
			this.add(MTG_Cards_Panel, gbc);
			
			// Panel contenente la Tabella delle carte che vengono aggiunte al deck
			gbc.gridx = 2;
			gbc.gridy = 3;
			
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			
			gbc.gridheight = 1;
			gbc.gridwidth = 2;
			
			this.add(MTG_DeckCards_Panel, gbc);
		}
		
		// Metodi interni al pannello
		public void setArray_Pointer(int Array_Pointer) {
			this.Array_Pointer = Array_Pointer;
		}
		
		public int getArray_Pointer() {
			return Array_Pointer;
		}
		
		public void setString_TextDeckName(String a) {
			  this.textDeckName.setText(a);
			}
		
		public void setString_TextDeckFormat(String a) {
			this.textDeckFormat.setText(a);
		}
		
		public void setString_TextDeck_Qty_Card(String a) {
			this.textDeck_Qty_Card.setText(a);
		}
		
		public void setString_TextCommander(String a) {
			this.textCommander.setText(a);
		}
		
		public DefaultTableModel get_TableModel_DeckCards() {
			return  tableModel_DeckCards;
		}
		
		public JTextField getTextCommander() {
			return textCommander;
		}
		
	}
	
	// Metodi della finistra JDialog
	
	// Metodo che restituisce il pannello
	public View_Change_Panel getViewChange_Panel() {
		return ViewChange;
	}
	
	public DefaultTableModel get_ViewChange_DFM() {
		return ViewChange.get_TableModel_DeckCards();
	}
	
	// Assegna un valore alla variabile Array_Pointer
	public void setArray_Pointer(int a) {
		this.ViewChange.setArray_Pointer(a);
	}
	
	public void setString_TextDeckName(String a) {
		this.ViewChange.setString_TextDeckName(a);
	}
	
	public void setString_TextDeckFormat(String a) {
		this.ViewChange.setString_TextDeckFormat(a);
	}
	
	public void setString_TextDeck_Qty_Card(String a) {
		this.ViewChange.setString_TextDeck_Qty_Card(a);
	}
	
	public void setString_TextCommander(String a) {
		this.ViewChange.setString_TextCommander(a);
	}
	
	public void setString_TextCommander(int a) {
		this.ViewChange.setString_TextCommander(MTG_Data_Controller.getCommander_Decks(a));
		
	}
	
	public void setColor_TextCommander() {
		if(this.ViewChange.getTextCommander().getText().isEmpty()) {
			this.ViewChange.getTextCommander().setBackground(Color.RED);
		}else {
			this.ViewChange.getTextCommander().setBackground(Color.GREEN);
		}
		
	}

}

