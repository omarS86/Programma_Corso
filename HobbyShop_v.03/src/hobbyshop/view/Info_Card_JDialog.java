package hobbyshop.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Info_Card_JDialog extends JDialog {
	
	private JTextArea textArea;
	
	// Istanza oggetti del menu
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem exit_Item = new JMenuItem("Esci");
	
	
	
	public Info_Card_JDialog(Frame owner, String title) {
		super(owner, title);
		this.setSize(new Dimension(600,600));
		Container dialogContent = getContentPane();
		dialogContent.setLayout(new GridLayout());
		this.setLocationRelativeTo(null);	
	
		// Aggiunta della barra menu
		this.setJMenuBar(menuBar);
		
		exit_Item.addActionListener(e->{
			this.dispose();
		});
		
		menuBar.add(exit_Item);
		
		dialogContent.add(new Info_Panel());
		
		this.pack();
	}
	
	public class Info_Panel extends New_Sample_Panel{
		
		
		public Info_Panel() {
			super();
			// Impostazione bordi del panello
			this.setBorder(setPanelBorders("Informazioni Carta",0,5,5,5));			
			
			// Istanza oggetto JTextArea e ScrollPane
			textArea = new JTextArea();
			
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setPreferredSize(new Dimension(300,300));
			
			
			 
			// Impostazione del Layout e aggiunta dei componenti al panello
			
			this.setLayout(new GridBagLayout());			
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			
			gbc.gridheight = 1;
			gbc.gridwidth  = 1;
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.insets = new Insets(5,5,5,5);
			
			this.add(scrollPane, gbc);
		
		}
		
		
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}

}
