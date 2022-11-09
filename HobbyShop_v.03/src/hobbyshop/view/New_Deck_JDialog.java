package hobbyshop.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import hobbyshop.controller.MTG_Data_Controller;



public class New_Deck_JDialog extends JDialog {
	
	private JMenuBar menuBar01;
	private JMenu fileMenu;
	private JMenuItem exitItem;
	
	
	public New_Deck_JDialog(Frame owner, String title) {
		
		super(owner,title);
		this.setSize(400,300);			
		Container DialogContent = getContentPane();
		DialogContent.setLayout(new GridLayout());
		this.setLocationRelativeTo(null);
		
		// Menu Bar
		menuBar01 = new JMenuBar();
		this.setJMenuBar(menuBar01);
		fileMenu = new JMenu("File");
		menuBar01.add(fileMenu);
		
		exitItem = new JMenuItem("Cosa Guardi?");
		fileMenu.add(exitItem);
		
		New_MTG_Deck_Panel new_Deck_Panel = new New_MTG_Deck_Panel();
		DialogContent.add(new_Deck_Panel);
	}
	
	public class New_MTG_Deck_Panel extends New_Sample_Panel {
			
			private final JLabel labelDeckName = new JLabel("Nome del Deck"),
					 			 labelFormat = new JLabel("Formato");
			
			private  JTextField textDeckName = new JTextField();
			
				
	
			private String[] comboBoxItems = {"Commander","Modern"};
			private JComboBox comboBoxFormat = new JComboBox(comboBoxItems);
			
			private JButton confirm_Button = new JButton("Crea Deck"),
							cancel_Button = new JButton("Cancella");
					
			New_MTG_Deck_Panel(){
				
				super();
				// Impostazione Bordi
				this.setBorder(setPanelBorders("Crea un nuovo deck",0,5,5,5));
				
				// Impostazione del Layout
				this.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				
				// Label DeckName
				gbc.gridx = 0;
				gbc.gridy = 0;
				
				gbc.weightx = 0.1;
				gbc.weighty = 0.1;
				
				gbc.anchor = GridBagConstraints.LAST_LINE_END;
				gbc.insets = new Insets(5,0,0,5);
				
				this.add(labelDeckName,gbc);
				
				// TextField DeckName
				
				gbc.gridx = 1;
				gbc.gridy = 0;
				
				gbc.weightx = 0.1;
				gbc.weighty = 0.1;
				
				gbc.anchor = GridBagConstraints.LAST_LINE_START;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.insets = new Insets(5,0,0,0);
				
				textDeckName.setMinimumSize(new Dimension(100,1));
				
				this.add(textDeckName, gbc);
				
				// Label Format
				
				gbc.gridx = 0;
				gbc.gridy = 1;
				
				gbc.weightx = 0.1;
				gbc.weightx = 0.1;
				
				gbc.anchor = GridBagConstraints.LAST_LINE_END;
				gbc.fill = GridBagConstraints.NONE;
				gbc.insets = new Insets(5,0,0,5);
				
				this.add(labelFormat, gbc);
				
				// JComboBox
				
				gbc.gridx = 1;
				gbc.gridy = 1;
				
				gbc.weightx = 0.1;
				gbc.weighty = 0.1;
				
				gbc.anchor = GridBagConstraints.LAST_LINE_START;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.insets = new Insets(5,0,0,0);
				
				this.add(comboBoxFormat, gbc);
				
				// Tasto Conferma
				
				gbc.gridx = 0;
				gbc.gridy = 2;
				
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				
				gbc.anchor = GridBagConstraints.LAST_LINE_END;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				
				/* Crea una nuova finesta di dialogo che prende il nome ed il formato
				 * del deck appena inserito e li passa ai campi JTextField della nuova
				 * finestra 
				 */
				
				confirm_Button.addActionListener(e->{
					New_Deck_Building_JDialog ndm_JDialog = new New_Deck_Building_JDialog(New_Deck_JDialog.this,"Nuovo Deck Porca la miseria");
					ndm_JDialog.setModal(true);				
					MTG_Data_Controller.addDeck(textDeckName.getText(), comboBoxFormat.getSelectedItem().toString());					
					ndm_JDialog.getNdb_Panel().setString_TextDeckName(textDeckName.getText());
					ndm_JDialog.getNdb_Panel().setString_TextDeckFormat(comboBoxFormat.getSelectedItem().toString());
					dispose();
					ndm_JDialog.setVisible(true);
					
					
					
				});
				
				this.add(confirm_Button,gbc);
				
				// Tasto Cancella
				
				gbc.gridx = 1;
				gbc.gridy = 2;
				
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				
				gbc.anchor = GridBagConstraints.LAST_LINE_END;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				
				cancel_Button.addActionListener(e-> dispose());
				
				this.add(cancel_Button,gbc);
				
				
			}		
			
	
		}
	
	

}
