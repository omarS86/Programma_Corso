package hobbyshop.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import hobbyshop.controller.MTG_Data_Controller;
import hobbyshop.model.MTG_Card.Database_MTG_Card;
import hobbyshop.model.MTG_Card.MTG_Card;
import hobbyshop.model.MTG_Deck.MTG_Deck;

public  class Frame_HomePage extends New_Sample_Frame {
	
	private static final long serialVersionUID = 7961549843659265473L;
	
	// Caricamento dei dati dal DB 
//	{	if(getController_Cards().getCards().size() == 0 ){
//		try {
//			Database_MTG_Card.get_CardList();
//			dataViewCards(tableModel_Card_Menager);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	}
	
	
	// Caricamento dei dati dai file salvati usati come DB
	{if(MTG_Data_Controller.get_CardFile().isFile() && MTG_Data_Controller.getCards().size() == 0) {
		try { MTG_Data_Controller.load_From_File_CardList();			
			  dataViewCards(tableModel_Card_Menager);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}		
		}
	}
	
	{if(MTG_Data_Controller.get_DecksFile().isFile() && MTG_Data_Controller.get_Decks().size() == 0) {
		try { MTG_Data_Controller.get_Decks().clear(); 
			MTG_Data_Controller.load_From_File_DeckList();			
			dataViewDecks(tableModel_Deck_Menager);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}		
		}
	}
	
	// Menu Bar
	private JMenuBar menuBar;
	
	// File menu itemes
	private JMenu fileMenu;
	private JMenuItem exitItem;
	
	// Games Menu
	private JMenu mtgMenu, mtgDeckMenu, mtgCardsMenu, gamesMenu;
	private JMenuItem newMTGCardItem, newMTGDeckItem, importMTGDeck, saveMTGDeck; 
	
	// Oggetti del Layout
	private GridBagConstraints gbc_Tab_MTG_Panel = new GridBagConstraints();
	private GridBagConstraints gbc_MTG_Card_Menager_Panel = new GridBagConstraints();
	private GridBagConstraints gbc_MTG_Deck_Menager_Panel = new GridBagConstraints();
	
	// Tabelle, oggetti, popupMenu delle Tabelle
	private static String[] columnsCard = {"Nome Carta", "Costo Mana", "Tipo", "Qtà","Disponibili"};
	private static String[] columnsDeck = {"Id","Nome Deck","N° di Carte", "Formato"};		
	private static Object[][] objectCard = {};
	private static Object[][] objectDeck = {};
	protected static DefaultTableModel tableModel_Card_Menager = new DefaultTableModel(objectCard,columnsCard);
	protected static DefaultTableModel tableModel_Deck_Menager = new DefaultTableModel(objectDeck,columnsDeck);
	private JTable mtg_Cards_Table_Menager = new JTable(tableModel_Card_Menager) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private JTable mtg_Deck_Table_Menager = new JTable(tableModel_Deck_Menager){
		public boolean isCellEditable(int row, int column) {
			return false;
		}

	};
	
	// Oggetti PopUpMenu
	private JPopupMenu popup_Cards_Menu, popup_Decks_Menu;	
	
	public Frame_HomePage() {
		
		super();
		this.setTitle("La Locanda del Puledro Impennato");
		this.setSize(1280,720);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		// Add Menu bar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		// Adding File menu and items		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		exitItem = new JMenuItem("Esci");
		fileMenu.add(exitItem);
		exitItem.addActionListener((e)->{
			System.exit(0);
		});
		
		// Adding Games menu and items
		gamesMenu = new JMenu("Giochi");
		menuBar.add(gamesMenu);
		
		mtgMenu = new JMenu("MTG");
		gamesMenu.add(mtgMenu);
		
		// Card SubMenu
		mtgCardsMenu = new JMenu("Gestione Carte");
		
		newMTGCardItem = new JMenuItem("Aggiungi nuova carta");
		newMTGCardItem.addActionListener((e)->{
			New_MTG_Card_JDialog new_Card_Dialog = new New_MTG_Card_JDialog(Frame_HomePage.this, "Inserisci Nuova Carta");
			new_Card_Dialog.setModal(true);
			new_Card_Dialog.setVisible(true);		
		});
		mtgCardsMenu.add(newMTGCardItem);
		mtgMenu.add(mtgCardsMenu);
		
		// Deck SubMenu
		mtgDeckMenu =  new JMenu("Gestione Deck");
		newMTGDeckItem = new JMenuItem("Crea nuovo Deck");
		mtgDeckMenu.add(newMTGDeckItem);
		importMTGDeck = new JMenuItem("Importa Deck");
		mtgDeckMenu.add(importMTGDeck);
		saveMTGDeck = new JMenuItem("Salva Deck");
		mtgDeckMenu.add(saveMTGDeck);
		mtgMenu.add(mtgDeckMenu);
		
		
		
		/*
		 * Creazione, implementezione della Tabbed Pane con annessi oggetti per 
		 * la visualizzazione dei dati all'interno della Home Page
		 */
		
		/*
		 * MTG_Card_Menager_Panel
		 */
		// Panello principale Card Menager
		JPanel mtg_Card_Menager_Panel = new JPanel();
		mtg_Card_Menager_Panel.setSize(300,450);
		mtg_Card_Menager_Panel.setBorder(setPanelBorders("Magie Conosciute",0,5,5,5));
		
		// Panello con tabella visualizzazione delle carte all'interno del DB
		JPanel panel_Card_Table = new JPanel();
		panel_Card_Table.setLayout(new BorderLayout());
		panel_Card_Table.add(new JScrollPane(mtg_Cards_Table_Menager), BorderLayout.CENTER);
		mtg_Cards_Table_Menager.getTableHeader().setReorderingAllowed(false);
		mtg_Cards_Table_Menager.getTableHeader().setResizingAllowed(false);
		// Impostazioni visualizzazione dati nella tabella ( Larghezza della colonne e centratura del testo)
		mtg_Cards_Table_Menager.getColumnModel().getColumn(0).setPreferredWidth(200);
		mtg_Cards_Table_Menager.getColumnModel().getColumn(3).setPreferredWidth(10);
		mtg_Cards_Table_Menager.getColumnModel().getColumn(4).setPreferredWidth(30);
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
	    cellRenderer.setHorizontalAlignment(JLabel.CENTER);
	    mtg_Cards_Table_Menager.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
	    mtg_Cards_Table_Menager.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
	    mtg_Cards_Table_Menager.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
	    mtg_Cards_Table_Menager.getColumnModel().getColumn(4).setCellRenderer(cellRenderer);
		
		// PopupMenu e oggetti a lui connessi
	    popup_Cards_Menu = new JPopupMenu();
	    mtg_Cards_Table_Menager.addMouseListener(new MouseAdapter(){
	    	public void mousePressed(MouseEvent e) {
	    		if(e.getButton() == MouseEvent.BUTTON3) {
	    			popup_Cards_Menu.show(mtg_Cards_Table_Menager, e.getX(), e.getY());
	    		}	    		
	    	}
	    });
	    
	    // Istanza oggetto JMenuItem per cancellare le carte dal DB
		JMenuItem popup_Item_DeleteCard = new JMenuItem("Cancella Carta");
	    popup_Item_DeleteCard.addActionListener(e->{
	    	int result = JOptionPane.showConfirmDialog(this,
	    			     "Conferma l'eliminazione della carta",
	    			     "Sicuro di voler cancellare la Carta?",
	    			     JOptionPane.OK_CANCEL_OPTION,
	    			     JOptionPane.QUESTION_MESSAGE);
	    	if(result == JOptionPane.OK_OPTION) {
		    	DefaultTableModel model = (DefaultTableModel)mtg_Cards_Table_Menager.getModel();
		    	int row = mtg_Cards_Table_Menager.getSelectedRow();
		    	
		    	while(row != -1) {
		    		int modelRow = mtg_Cards_Table_Menager.convertRowIndexToModel(row);
		    		model.removeRow(modelRow);
		    		MTG_Card MTG_Card_Sample = getCard_AtArrayIndex(modelRow);		    		
//		    		try {
//						Database_MTG_Card.delete_Card(MTG_Card_Sample);
//					} catch (SQLException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}		    		
		    		MTG_Data_Controller.getCards().remove(modelRow);
		    		dataViewCards(tableModel_Card_Menager);
		    		try {
						MTG_Data_Controller.save_To_File_CardList();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		break;
		    	}
	    	} else if(result == JOptionPane.CANCEL_OPTION) {
	    		
	    	}	    	
	    });
	    
	    
	    // Istanza oggetti JMenuItem per visualizzare le info della carta dalla tebella
	    JMenuItem info_PopUp = new JMenuItem("Info Carta");
	    info_PopUp.addActionListener(e->{	    	
	    	int row = mtg_Cards_Table_Menager.getSelectedRow();
	    	int modelRow = mtg_Cards_Table_Menager.convertRowIndexToModel(row);
	    	MTG_Card MTG_Card_Sample = getCard_AtArrayIndex(modelRow);	    	  	
	    	
	    	for(MTG_Card a : MTG_Data_Controller.getCards()) {
	    		if(a.getCard_Name().contains(MTG_Card_Sample.getCard_Name())) {
	    			Info_Card_JDialog infoView = new Info_Card_JDialog(Frame_HomePage.this, "Info Carta");	    			
	    			infoView.getTextArea().append(
	    					"Nome Carta: "+ MTG_Card_Sample.getCard_Name()+ "\n"+
	    					"Tipo Carta: "+ MTG_Card_Sample.getTipe()+ "\n"+
	    					"Usata nei deck: "+ MTG_Card_Sample.getListOfDecks());	    			
	    			infoView.setModal(true);
	    			infoView.setVisible(true);
	    		}
	    	}	    	
	    });
	    
	    // Istanza oggetti JMenuItem per l'aggiornamento della quantità delle carte
	    JMenu quantity = new JMenu("Quantità");
	    
	    JMenuItem add_Qty1 = new JMenuItem("Aggiungi +1");
	    add_Qty1.addActionListener(e->{
	    	int row = mtg_Cards_Table_Menager.getSelectedRow();
	    	int modelRow = mtg_Cards_Table_Menager.convertRowIndexToModel(row);
	    	getCard_AtArrayIndex(modelRow).setQuantity(getCard_AtArrayIndex(modelRow).getQuantity()+1);
	    	getCard_AtArrayIndex(modelRow).setQty_InDeck(getCard_AtArrayIndex(modelRow).getQty_InDeck()+1);	    	
	    	dataViewCards(tableModel_Card_Menager);
	    });
	    
	    JMenuItem remove_Qty1 = new JMenuItem("Rimuivi -1");
	    remove_Qty1.addActionListener(e->{
	    	int row = mtg_Cards_Table_Menager.getSelectedRow();
	    	int modelRow = mtg_Cards_Table_Menager.convertRowIndexToModel(row);
	    	if(getCard_AtArrayIndex(modelRow).getQuantity() > 0 && getCard_AtArrayIndex(modelRow).getQty_InDeck() >0){
	    		getCard_AtArrayIndex(modelRow).setQuantity(getCard_AtArrayIndex(modelRow).getQuantity()-1);
	    		getCard_AtArrayIndex(modelRow).setQty_InDeck(getCard_AtArrayIndex(modelRow).getQty_InDeck()-1);
	    		dataViewCards(tableModel_Card_Menager);
	    	}else if(getCard_AtArrayIndex(modelRow).getQuantity() == 0 &&
	    	   getCard_AtArrayIndex(modelRow).getQuantity() == getCard_AtArrayIndex(modelRow).getQty_InDeck()) {
		    	DefaultTableModel model = (DefaultTableModel)mtg_Cards_Table_Menager.getModel();		    	
		    	while(row != -1) {		    		
		    		model.removeRow(modelRow);
//		    		MTG_Card MTG_Card_Sample = getCard_AtArrayIndex(modelRow);		    		
//		    		try {
//						Database_MTG_Card.delete_Card(MTG_Card_Sample);
//					} catch (SQLException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}		    		
		    		MTG_Data_Controller.getCards().remove(modelRow);
		    		dataViewCards(tableModel_Card_Menager);
		    		try {
						MTG_Data_Controller.save_To_File_CardList();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		break;
		    	}	    	
	    	} else if(getCard_AtArrayIndex(modelRow).getQty_InDeck() == 0) {
	    		JOptionPane.showMessageDialog(this, "Impossibile rimuovere ulteriormente la carta richiesta", "Attenzione" , JOptionPane.WARNING_MESSAGE);
	    	}
	    });
	    
	    quantity.add(add_Qty1);
	    quantity.addSeparator();
	    quantity.add(remove_Qty1);
	    
	    
	    // Aggiunta degli oggetti al menu
	    popup_Cards_Menu.add(quantity);
	    popup_Cards_Menu.add(info_PopUp);
	    popup_Cards_Menu.addSeparator();
	    popup_Cards_Menu.add(popup_Item_DeleteCard);
	    
	    
		
	    
	    //Definizione del layout e aggiunta dei componenti all'interno del panel mtg_Card_Menager_Panel
	    mtg_Card_Menager_Panel.setLayout(new GridBagLayout());
	    
	 // Tasto Aggiungi Nuova Carta
 		JButton add_New_Card = new JButton("Aggiungi Nuova Carta");
 		add_New_Card.addActionListener((e)->{
 			New_MTG_Card_JDialog new_Card_Dialog = new New_MTG_Card_JDialog(Frame_HomePage.this, "Inserisci Nuova Carta");
 			new_Card_Dialog.setModal(true);
 			new_Card_Dialog.setVisible(true);
 		});
 		
 		gbc_MTG_Card_Menager_Panel.gridx = 0;
 		gbc_MTG_Card_Menager_Panel.gridy = 0;
 		
 		gbc_MTG_Card_Menager_Panel.weightx = 0.1;
 		gbc_MTG_Card_Menager_Panel.weighty = 0.1;
 		
 		gbc_MTG_Card_Menager_Panel.anchor = GridBagConstraints.FIRST_LINE_START;
 		gbc_MTG_Card_Menager_Panel.fill = GridBagConstraints.HORIZONTAL;
 		
 		mtg_Card_Menager_Panel.add(add_New_Card,gbc_MTG_Card_Menager_Panel);
 		
 		// Tasto Cerca
 		JButton search_Button = new JButton("Prova caricamento da DB");
 		search_Button.addActionListener(e->{
 			try {
				System.out.println(Database_MTG_Card.get_CardList());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 		});
 		gbc_MTG_Card_Menager_Panel.gridx = 0;
 		gbc_MTG_Card_Menager_Panel.gridy = 1;
 		 
 		gbc_MTG_Card_Menager_Panel.weightx = 0.1;
 		gbc_MTG_Card_Menager_Panel.weighty = 0.1;
 		 
 		gbc_MTG_Card_Menager_Panel.anchor = GridBagConstraints.LAST_LINE_END;
 		 
 		mtg_Card_Menager_Panel.add(search_Button,gbc_MTG_Card_Menager_Panel);
 		
 		// Tabella visualizzazione dati
 		gbc_MTG_Card_Menager_Panel.gridx = 0;
 		gbc_MTG_Card_Menager_Panel.gridy = 2;
 		
 		gbc_MTG_Card_Menager_Panel.weightx = 1.0;
 		gbc_MTG_Card_Menager_Panel.weighty = 1.0;
 		
 		gbc_MTG_Card_Menager_Panel.gridheight = 1;
 		gbc_MTG_Card_Menager_Panel.gridwidth = 2;
 		
 		gbc_MTG_Card_Menager_Panel.anchor = GridBagConstraints.PAGE_START;
 		gbc_MTG_Card_Menager_Panel.fill = GridBagConstraints.BOTH;		
 		mtg_Card_Menager_Panel.add(panel_Card_Table, gbc_MTG_Card_Menager_Panel);
 		
 		
		/*
		 *  MTG_Deck_Menager_Panel 
		 */
 		
		// Panello principale Deck Menager
		JPanel mtg_Deck_Menager_Panel = new JPanel();
		mtg_Deck_Menager_Panel.setSize(300,450);
		mtg_Deck_Menager_Panel.setBorder(setPanelBorders("Grimori",0,5,5,5));
		
		//Panello con tabella visualizzazione dei deck all'interno del DB		
		JPanel panel_Deck_Table = new JPanel();
		panel_Deck_Table.setLayout(new BorderLayout());
		panel_Deck_Table.add(new JScrollPane(mtg_Deck_Table_Menager), BorderLayout.CENTER);
		mtg_Deck_Table_Menager.getTableHeader().setReorderingAllowed(false);
		// Impostazioni visualizzazione dati nella tabella ( Larghezza della colonne e centratura del testo)
		mtg_Deck_Table_Menager.getColumnModel().getColumn(0).setPreferredWidth(5);
		mtg_Deck_Table_Menager.getColumnModel().getColumn(1).setPreferredWidth(150);
		mtg_Deck_Table_Menager.getColumnModel().getColumn(2).setPreferredWidth(10);
		mtg_Deck_Table_Menager.getColumnModel().getColumn(3).setPreferredWidth(20);
		mtg_Deck_Table_Menager.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
	    mtg_Deck_Table_Menager.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
	    mtg_Deck_Table_Menager.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
	    mtg_Deck_Table_Menager.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
	    
	    // PopupMenu e oggetti a lui connessi
	    popup_Decks_Menu = new JPopupMenu();
	    mtg_Deck_Table_Menager.addMouseListener(new MouseAdapter(){
	    	public void mousePressed(MouseEvent e) {
	    		if(e.getButton() == MouseEvent.BUTTON3) {
	    			popup_Decks_Menu.show(mtg_Deck_Table_Menager, e.getX(), e.getY());
	    		}	    		
	    	}
	    });
	    
	 // Oggetto JMenuItem Visualizza Informazioni del deck
	    JMenuItem popup_Item_InfoDeck = new JMenuItem("Info Deck");
	    popup_Item_InfoDeck.addActionListener(e->{
	    	DefaultTableModel model = (DefaultTableModel)mtg_Deck_Table_Menager.getModel();
	    	int row = mtg_Deck_Table_Menager.getSelectedRow();
	    	int modelRow = mtg_Deck_Table_Menager.convertRowIndexToModel(row);
	    	
	    	for(MTG_Deck a : MTG_Data_Controller.get_Decks()) {
	    		if(a.getDeck_Name().contains(getDeck_AtArrayIndex(modelRow).getDeck_Name())) {
	    			Info_Deck_JDialog infoView_Deck = new Info_Deck_JDialog(Frame_HomePage.this,"Info Deck");
	    			infoView_Deck.getTextArea().append("Comandante: " + getDeck_AtArrayIndex(modelRow).getCommader().getCard_Name()+ "\n");
	    			for(MTG_Card card : getDeck_AtArrayIndex(modelRow).getDeck_Cards()) {
	    				infoView_Deck.getTextArea().append("1 x " + card.getCard_Name() +"\n");
	    				
	    			}
	    			infoView_Deck.setModal(true);
	    			infoView_Deck.setVisible(true);
	    		}
	    	}
	    	
	    	
	    });
	    
	    popup_Decks_Menu.add(popup_Item_InfoDeck);
	    
	    //Oggetto JMenuItem modica deck
	    JMenuItem popup_Item_Modification_Deck = new JMenuItem("Modifica Deck");
	    popup_Item_Modification_Deck.addActionListener(e->{
	    	DefaultTableModel model = (DefaultTableModel)mtg_Deck_Table_Menager.getModel();
	    	int row = mtg_Deck_Table_Menager.getSelectedRow();
	    	int modelRow = mtg_Deck_Table_Menager.convertRowIndexToModel(row);   			
	    	
	    	for(MTG_Deck a : MTG_Data_Controller.get_Decks()) {
	    		if(a.getDeck_Name().contains(getDeck_AtArrayIndex(modelRow).getDeck_Name())){
	    			View_Change_DeckJDialog dialog_Mod_Deck = new View_Change_DeckJDialog(Frame_HomePage.this, "Visuallizza/Modifica Deck");
	    			dialog_Mod_Deck.setString_TextDeckName(MTG_Data_Controller.get_Decks().get(modelRow).getDeck_Name());
	    			dialog_Mod_Deck.setString_TextDeckFormat(MTG_Data_Controller.get_Decks().get(modelRow).getDeck_Format().toString());
	    			dataViewCards_CommanderDeck(dialog_Mod_Deck.get_ViewChange_DFM(), modelRow);
	    			dialog_Mod_Deck.setArray_Pointer(modelRow);	    			
	    			dialog_Mod_Deck.setString_TextDeck_Qty_Card(String.valueOf(MTG_Data_Controller.get_Decks().get(modelRow).getDeck_Cards().size()));
	    	    	dialog_Mod_Deck.setString_TextCommander(modelRow);
	    	    	dialog_Mod_Deck.setColor_TextCommander();
	    			dialog_Mod_Deck.setModal(true);
	    	    	dialog_Mod_Deck.setVisible(true);
	    			
	    		}
	    	}
	    	
	    });
	    
	    popup_Decks_Menu.add(popup_Item_Modification_Deck);	    
	    
	    // Oggetto JMenuItem Rimuovi dekc
	    JMenuItem popup_Item_DeleteDeck = new JMenuItem("Cancella Deck");
	    popup_Item_DeleteDeck.addActionListener(e->{
	    	int result1 = JOptionPane.showConfirmDialog(this,
	    			     "Conferma l'eliminazione del Deck",
	    			     "Sicuro di voler cancellare il Deck?",
	    			     JOptionPane.OK_CANCEL_OPTION,
	    			     JOptionPane.QUESTION_MESSAGE);
	    	if(result1 == JOptionPane.OK_OPTION) {
		    	DefaultTableModel model = (DefaultTableModel)mtg_Deck_Table_Menager.getModel();
		    	int row = mtg_Deck_Table_Menager.getSelectedRow();
		    	
		    	while(row != -1) {
		    		int modelRow = mtg_Deck_Table_Menager.convertRowIndexToModel(row);
		    		
		    		for(MTG_Card a : getDeck_AtArrayIndex(modelRow).getDeck_Cards()) {		    			
		    			String nameString = a.getCard_Name();
		    			String deckString = getDeck_AtArrayIndex(modelRow).getDeck_Name();
		    			ArrayList<String> deckListString = a.getIn_Deck();
		    			
		    			for(MTG_Card b : MTG_Data_Controller.getCards()) {
		    				if(b.getCard_Name().contains(nameString)){
		    					int aInt = MTG_Data_Controller.getCards().indexOf(b);
		    					for(int i = 0; i<deckListString.size(); i++) {
		    						String bString = deckListString.get(i);
		    						if(bString.contains(deckString)){
		    							int bInt = getCard_AtArrayIndex(aInt).getIn_Deck().indexOf(bString);
		    							getCard_AtArrayIndex(aInt).setQty_InDeck(getCard_AtArrayIndex(aInt).getQty_InDeck()+1);
		    							getCard_AtArrayIndex(aInt).getIn_Deck().remove(bInt);		    							
		    						}
		    					}		    					
		    				}		    			
		    			}
		    		}	    		
		    		model.removeRow(modelRow);
		    		row = mtg_Deck_Table_Menager.getSelectedRow();
		    		MTG_Data_Controller.get_Decks().remove(modelRow);
		    		dataViewDecks(tableModel_Deck_Menager);
		    		dataViewCards(tableModel_Card_Menager);
		    		try {
						MTG_Data_Controller.save_To_File_DeckList();
						MTG_Data_Controller.save_To_File_CardList();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	}
	    	} else if(result1 == JOptionPane.CANCEL_OPTION) {
	    		
	    	}	    	
	    });
	    
	    popup_Decks_Menu.add(popup_Item_DeleteDeck);
		
		//Tasto aggiunta nuovo deck e actionListener
		JButton add_New_Deck = new JButton("Nuovo Deck");
		add_New_Deck.addActionListener(e->{
			New_Deck_JDialog dialog_new_Deck = new New_Deck_JDialog(Frame_HomePage.this, "Crea un nuovo deck");
			dialog_new_Deck.setModal(true);
			dialog_new_Deck.setVisible(true);
			
		});
		
		
		
		// Definizione del layout e aggiunta dei componenti all'interno del panel mtg_Deck_Menager_Panel
		mtg_Deck_Menager_Panel.setLayout(new GridBagLayout());
		
		gbc_MTG_Deck_Menager_Panel.gridx = 0;
		gbc_MTG_Deck_Menager_Panel.gridy = 0;
		
		gbc_MTG_Deck_Menager_Panel.weightx = 0.1;
		gbc_MTG_Deck_Menager_Panel.weighty = 0.1;
		
		gbc_MTG_Deck_Menager_Panel.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc_MTG_Deck_Menager_Panel.fill = GridBagConstraints.HORIZONTAL;
		
		mtg_Deck_Menager_Panel.add(add_New_Deck, gbc_MTG_Deck_Menager_Panel);
		
		// Tasto cerca deck( ora attivato per prova con i JDialog)
		JButton search_Deck = new JButton("Per ora niente");
		
		gbc_MTG_Deck_Menager_Panel.gridx = 0;
		gbc_MTG_Deck_Menager_Panel.gridy = 1;
		
		gbc_MTG_Deck_Menager_Panel.weightx = 0.1;
		gbc_MTG_Deck_Menager_Panel.weighty = 0.1;
		
		gbc_MTG_Deck_Menager_Panel.anchor = GridBagConstraints.FIRST_LINE_START  ;
		
		search_Deck.addActionListener(e->{
			
		});		
		mtg_Deck_Menager_Panel.add(search_Deck, gbc_MTG_Deck_Menager_Panel);
		
		// Tabella visualizzazione dei deck
		
		gbc_MTG_Deck_Menager_Panel.gridx = 0;
		gbc_MTG_Deck_Menager_Panel.gridy = 2;
		
		gbc_MTG_Deck_Menager_Panel.weightx = 1.0;
		gbc_MTG_Deck_Menager_Panel.weighty = 1.0;
		
		gbc_MTG_Deck_Menager_Panel.gridheight = 1;
		gbc_MTG_Deck_Menager_Panel.gridwidth = 2;
		
		gbc_MTG_Deck_Menager_Panel.anchor = GridBagConstraints.PAGE_START;
		gbc_MTG_Deck_Menager_Panel.fill = GridBagConstraints.BOTH;
		
		mtg_Deck_Menager_Panel.add(panel_Deck_Table, gbc_MTG_Deck_Menager_Panel);		
		
		/*
		 * TabbedPane e Panel che lo ospita
		 */
		
		// TabbedPanel Panel
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setLayout(new GridLayout(1,1));
		
		/* 
		 * Imposta la visualizzazione dei tab scorrevole nel caso ne vengano 
		 * aperti piu di quanti ne possano essere visualizzati
		 */
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		// aggiungere pannello
		JPanel tab_MTG_Panel = new JPanel();
		tab_MTG_Panel.setBorder(setPanelBorders("Il Laboratorio di Jace",0,5,5,5));
		tab_MTG_Panel.setLayout(new GridBagLayout());
		// Panello MTG Card Menager
		gbc_Tab_MTG_Panel.gridx = 0;
		gbc_Tab_MTG_Panel.gridy = 0;
		
		gbc_Tab_MTG_Panel.weightx = 1.0;
		gbc_Tab_MTG_Panel.weighty = 0.50;
		
		gbc_Tab_MTG_Panel.fill = GridBagConstraints.BOTH ;
		
		tab_MTG_Panel.add(mtg_Card_Menager_Panel,gbc_Tab_MTG_Panel);
		
		// Panello MTG Deck Menager
		gbc_Tab_MTG_Panel.gridx = 1;
		gbc_Tab_MTG_Panel.gridy = 0;
		
		gbc_Tab_MTG_Panel.weightx = 1.0;
		gbc_Tab_MTG_Panel.weighty = 0.50;
		
		tab_MTG_Panel.add(mtg_Deck_Menager_Panel,gbc_Tab_MTG_Panel);
		
		tabbedPane.addTab("MTG", tab_MTG_Panel);
		
		// Aggiunta del TabbedPane al conteiner della Frame
		this.add(tabbedPane);		
	}

}
