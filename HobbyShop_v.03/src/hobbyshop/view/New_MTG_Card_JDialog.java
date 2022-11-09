package hobbyshop.view;

import java.awt.Container;
import java.awt.Frame;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import hobbyshop.controller.MTG_Data_Controller;

public class New_MTG_Card_JDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8702365005453745676L;
		
	private New_MTG_Card_Panel panelTable_New_Card = new New_MTG_Card_Panel();
	// Oggetti della barra menu
	private JMenuBar menuBar_NC = new JMenuBar();
	private JMenuItem exit_Item = new JMenuItem("Esci"),
					  save_Exit = new JMenuItem("Salva ed Esci");
	
	
	public New_MTG_Card_JDialog(Frame owner, String title) {
		
		super(owner,title);
		this.setSize(800,600);
		Container dialogContent = getContentPane();
		this.setLocationRelativeTo(null);
		
		
		
		
		/*
		 * Adding NewCardPanel
		 */
		
		this.add(panelTable_New_Card);
		
		/*
		 * Adding menu objects
		 */
		
		this.setJMenuBar(menuBar_NC);
		
		menuBar_NC.add(exit_Item);
		this.exit_Item.addActionListener((e)->{
			MTG_Data_Controller.get_New_Card_List().clear();
			this.dispose();
		});
		
		menuBar_NC.add(save_Exit);
		this.save_Exit.addActionListener((e)->{
			System.out.println(MTG_Data_Controller.getCards());
			try {
				MTG_Data_Controller.save_To_File_CardList();
				panelTable_New_Card.dataViewCards(Frame_HomePage.tableModel_Card_Menager);
				MTG_Data_Controller.get_New_Card_List().clear();				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			this.dispose();
			
		});		
		this.pack();
		
	} 
	
	
	
	
		
		 

	

}
