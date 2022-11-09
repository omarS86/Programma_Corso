package hobbyshop.view;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import hobbyshop.controller.MTG_Data_Controller;

/*
 * Classe in fase di costruzione per ora messa da parte perchè non ha un utilità ben precisa
 */

public class View_Change_Card_JDialog extends JDialog {
	
	// Oggetti del menu
	private JMenuBar cc_MenuBar = new JMenuBar();
	private JMenuItem save_Exit_Item = new JMenuItem("Salve ed Esci"),
					  exit_Item = new JMenuItem("Esci");

	/*
	 * Costruttore dove la classe madre
	 *  @Param owner è di tipo Frame
	 */
	View_Change_Card_JDialog(Frame owner, String title){
		super(owner,title);
		this.setSize(800,600);
		Container dialogContent = getContentPane();
		dialogContent.setLayout(new GridLayout());
		this.setLocationRelativeTo(null);
		
		// Aggiunta barra menu
		this.setJMenuBar(cc_MenuBar);
		
		save_Exit_Item.addActionListener(e->{
			try {
				MTG_Data_Controller.save_To_File_CardList();
				
			}catch (IOException e1) {
				e1.printStackTrace();
			}
			this.dispose();
		});
		cc_MenuBar.add(save_Exit_Item);
		
		exit_Item.addActionListener(e->{
			dispose();
		});
		cc_MenuBar.add(exit_Item);	
	}
	
	/*
	 * Costruttore dove la classe madre
	 *  @Param owner è di tipo Dialog
	 */
	View_Change_Card_JDialog(Dialog owner, String title){
		super(owner,title);
		this.setSize(800,600);
		Container dialogContent = getContentPane();
		dialogContent.setLayout(new GridLayout());
		this.setLocationRelativeTo(null);
		
		// Aggiunta barra menu
		this.setJMenuBar(cc_MenuBar);
		
		save_Exit_Item.addActionListener(e->{
			try {
				MTG_Data_Controller.save_To_File_CardList();
				
			}catch (IOException e1) {
				e1.printStackTrace();
			}
			this.dispose();
		});
		cc_MenuBar.add(save_Exit_Item);
		
		exit_Item.addActionListener(e->{
			dispose();
		});
		cc_MenuBar.add(exit_Item);	
	}
	
	public class View_Change_Card_Panel extends New_Sample_Panel{
		
		private int Card_Array_Pointer; // Variabile usata per estrapolare l'indice a cui far riferimento nell'array delle carte
		
		private final JLabel labelCardName = new JLabel("Nome Carta"),
							 labelCastingCost = new JLabel("Costo di Mana"),
							 labelTipe = new JLabel("Tipo"),
							 labelQuantity = new JLabel("Quantità");
		
		private JTextField textCardName = new JTextField(),
						   textCastingCost = new JTextField();
		
	}
}
