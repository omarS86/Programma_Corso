package hobbyshop.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import hobbyshop.controller.MTG_Data_Controller;
import hobbyshop.model.MTG_Card.MTG_Card;

public class New_MTG_Card_Panel extends New_Sample_Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8580249653982620566L;	
		
	private final JLabel labelCardName = new JLabel("Nome Carta"),
			 			 labelCastingCost = new JLabel("Costo di Mana"),
			 			 labelTipe = new JLabel("Tipo"),
			 			 labelQuantity = new JLabel("Quantità");

	private JTextField textCardName = new JTextField(),
					   textCastingCost = new JTextField();
	
	private JList casting_Cost_List;
	
	private JSpinner spinnerQuantity;
	
	private JButton addCard;
	
	private JPanel MTG_New_Card_Panel;
	private DefaultTableModel tableModel_New_Card;
	
	

	public New_MTG_Card_Panel() {		
		super();  
		// TODO Auto-generated constructor stub
		
		// Bordi del Panello
		this.setBorder(setPanelBorders("Aggiungi una carta alla tua collezione",0,5,5,5));		
		
		// Table visualizzazione nuove carte inserite		
		String[] columnsCard = {"Nome Carta","Costo Mana","Tipo","Qtà"};
		Object[][] objectCard = {};
		
		tableModel_New_Card = new DefaultTableModel(objectCard,columnsCard);	
		JTable MTG_New_Card_Table = new JTable(tableModel_New_Card);
		MTG_New_Card_Panel = new JPanel();				
		MTG_New_Card_Panel.setLayout(new BorderLayout());
		MTG_New_Card_Panel.add(new JScrollPane(MTG_New_Card_Table), BorderLayout.CENTER);
		MTG_New_Card_Table.getColumnModel().getColumn(0).setPreferredWidth(200);
		MTG_New_Card_Table.getColumnModel().getColumn(3).setPreferredWidth(10);
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
	    cellRenderer.setHorizontalAlignment(JLabel.CENTER);
	    MTG_New_Card_Table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
	    MTG_New_Card_Table.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
	    MTG_New_Card_Table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
	
											
		/* 
		 * Impostazioni del Layout e aggiunta dei componenti al pannello.
		 * I paramentri del Layout vengono dichiarati prima dell'aggiunta di ogni componente al pannello,
		 * viene usato un unico componente GridBagConstraints e prima di aggiungere il componente vengono
		 * reimpostati i singolo paramentri che allocano lo stesso nel pannello.			 * 			 
		 */
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// Label CardName
		gbc.gridx = 0;
		gbc.gridy = 0;
					
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
					
		gbc.anchor = GridBagConstraints.LAST_LINE_END; 
					
		gbc.insets = new Insets(5,0,0,5);
					
		this.add(labelCardName, gbc);
					
		// TextField CardName
					
		gbc.gridx = 1;
		gbc.gridy = 0;
				
		gbc.weightx = 0.10;
		gbc.weighty = 0.01;
					
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
					
					
		gbc.anchor = GridBagConstraints.LAST_LINE_START;			
		gbc.insets = new Insets(5,0,0,0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		textCardName.setMinimumSize(new Dimension(100,1));
		
		this.add(textCardName, gbc);
		
		// Label Casting Cost
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
		
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;			
		gbc.insets = new Insets(5,0,0,5);
		gbc.fill = GridBagConstraints.NONE;
		
		this.add(labelCastingCost, gbc);
		
		// JList Casting Cost
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		gbc.weightx = 0.01;
		gbc.weighty = 0.01;
					
		gbc.anchor = GridBagConstraints.LAST_LINE_START; 
		gbc.insets = new Insets(5,0,0,0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		casting_Cost_List = new JList();
		DefaultListModel listmodel_Casting_Cost = new DefaultListModel();
		listmodel_Casting_Cost.addElement("X");
		listmodel_Casting_Cost.addElement("0");
		listmodel_Casting_Cost.addElement("1");
		listmodel_Casting_Cost.addElement("Rosso");
		listmodel_Casting_Cost.addElement("Verde");
		listmodel_Casting_Cost.addElement("Blu");
		listmodel_Casting_Cost.addElement("Nero");
		listmodel_Casting_Cost.addElement("Bianco");
		listmodel_Casting_Cost.addElement("Cancella");
		
		casting_Cost_List.setModel(listmodel_Casting_Cost);
		casting_Cost_List.setLayoutOrientation(JList.VERTICAL_WRAP);
		casting_Cost_List.setVisibleRowCount(3);
		
		// Mouse Listener per aggiungere gli elementi al TextField
		casting_Cost_List.addMouseListener(new MouseListener() {
			int colorless = 1;
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				String tmp = textCastingCost.getText();
				
				if(casting_Cost_List.getSelectedValue().equals("X")) {
					textCastingCost.setText(tmp.concat("X"));
				} else if (casting_Cost_List.getSelectedValue().equals("0")) {					
					textCastingCost.setText(tmp.concat("0"));
				} else if (casting_Cost_List.getSelectedValue().equals("1") && colorless==1) {						
					textCastingCost.setText("1");
					colorless++;
				} else if (casting_Cost_List.getSelectedValue().equals("1") && colorless!=1) {						
					textCastingCost.setText(Integer.toString(colorless));
					colorless++;
				} else if (casting_Cost_List.getSelectedValue().equals("Rosso")) {
					textCastingCost.setText(tmp.concat("R"));
				} else if (casting_Cost_List.getSelectedValue().equals("Verde")) {
					textCastingCost.setText(tmp.concat("G"));
				} else if (casting_Cost_List.getSelectedValue().equals("Blu")) {
					textCastingCost.setText(tmp.concat("B"));
				} else if (casting_Cost_List.getSelectedValue().equals("Nero")) {
					textCastingCost.setText(tmp.concat("BK"));
				} else if (casting_Cost_List.getSelectedValue().equals("Bianco")) {
					textCastingCost.setText(tmp.concat("W"));
				} else if(casting_Cost_List.getSelectedValue().equals("Cancella")) {
					textCastingCost.setText(null);
				}
			}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
								
			JScrollPane listScroller = new JScrollPane(casting_Cost_List);			
			this.add(listScroller, gbc);
			
			// TextField Casting Cost
			gbc.gridx = 1;
			gbc.gridy = 2;
			
			gbc.weightx = 0.01;
			gbc.weighty = 0.01;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_START; 
			gbc.insets = new Insets(5,0,0,0);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			
			textCastingCost.setEditable(false);
			this.add(textCastingCost,gbc);
			
			
			// Label Tipe (vicino ComboBox)
			gbc.gridx = 0;
			gbc.gridy = 3;
			
			gbc.weightx = 0.01;
			gbc.weighty = 0.01;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_END;
			gbc.fill = GridBagConstraints.NONE;
			
			gbc.insets = new Insets(5,0,0,5);
			
			this.add(labelTipe, gbc);
			
			// ComboBox Tipe
			String[] tipeArray = {"Creatura", "Istantaneo", "Stregoneria", "Incantesimo", "Artefatto", "Terra"};
			JComboBox tipeComboBox = new JComboBox(tipeArray);
			
			gbc.gridx = 1;
			gbc.gridy = 3;
			
			gbc.weightx = 0.01;
			gbc.weighty = 0.01;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_START;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5,0,0,0);
			
			tipeComboBox.setSize(150, 1);
			this.add(tipeComboBox, gbc);
					
			// Label Quantity
			gbc.gridx = 0;
			gbc.gridy = 4;
			
			gbc.weightx = 0.01;
			gbc.weighty = 0.01;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_END; 
			gbc.fill = GridBagConstraints.NONE;
			gbc.insets = new Insets(5,0,0,5);
			
			this.add(labelQuantity, gbc);			
			
			// Spinner
			gbc.gridx = 1;
			gbc.gridy = 4;
			
			gbc.weightx = 0.01;
			gbc.weighty = 0.01;
			
			gbc.anchor = GridBagConstraints.LAST_LINE_START;
			gbc.fill = GridBagConstraints.HORIZONTAL;			
			gbc.insets = new Insets(5,0,0,0);			
			spinnerQuantity = new JSpinner();			
			SpinnerNumberModel spinnerQModel = new SpinnerNumberModel(0,0,99,1);
			spinnerQuantity.setModel(spinnerQModel);			
			
			this.add(spinnerQuantity, gbc);
			
			// AddCard Button
			addCard = new JButton("Aggiungi nuova carta");
			addCard.addActionListener((e)->{
				MTG_Data_Controller.addCard(textCardName.getText(),textCastingCost.getText(),(String)tipeComboBox.getSelectedItem(), (int)spinnerQuantity.getValue());
				MTG_Data_Controller.add_New_Card(textCardName.getText(),textCastingCost.getText(),(String)tipeComboBox.getSelectedItem(), (int)spinnerQuantity.getValue());
				this.dataViewNew_Cards();
				textCardName.setText(null);
				textCastingCost.setText(null);
				spinnerQuantity.setValue(0);
				
												
			});
			
			gbc.gridx = 0;  			// Posizionamento sull'asse delle x
			gbc.gridy = 5;				// Posizionamento sull'asse delle y
			
			gbc.weightx = 0.01;			// Il peso relativo confronto agli altri compomenti che il componente ha
			gbc.weighty = 0.01;			// il valore deve essere compreso tra 0.1 e 1.0 	
			
			gbc.gridheight = 1;			// Numero di righe occupate dal componente
			gbc.gridwidth = 2;			// Numero di colonne occupate dal componente
			
			gbc.anchor = GridBagConstraints.PAGE_START;	// Dettermina il posizionamente all'interno della cella			
			gbc.fill = GridBagConstraints.HORIZONTAL; // Dettermina quanto spazio all'interno della cella il componente
			
			this.add(addCard, gbc);
					
			// Panel contenente  Table NewCard
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			
			gbc.weightx = 0.9;
			gbc.weighty = 0.9;
			
			gbc.gridheight = 1;
			gbc.gridwidth = 2;
			
			gbc.anchor = GridBagConstraints.PAGE_START; 
			gbc.fill = GridBagConstraints.BOTH;
			
			this.add(MTG_New_Card_Panel,gbc);
	}
	
	// Metodo per visualizzare i dati aggiornati all'intero della tabella dopo l'inserimento
		public  DefaultTableModel dataViewNew_Cards() {
			int rowCount = tableModel_New_Card.getRowCount();
			for( int i = rowCount -1; i>=0; i--) {
				tableModel_New_Card.removeRow(i);
			}		
			// Ciclo for per visualizzare dati nella Tabella
			for(MTG_Card c : MTG_Data_Controller.get_New_Card_List()) {
				tableModel_New_Card.addRow(c.toArray());
			}
			return tableModel_New_Card;		
		}
	

}
