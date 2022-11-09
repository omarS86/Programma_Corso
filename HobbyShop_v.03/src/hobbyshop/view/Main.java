 package hobbyshop.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main {

	static private Frame_HomePage frameHP;
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					frameHP = new Frame_HomePage();
					frameHP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frameHP.setResizable(true);				
				} catch(Exception e) {
					e.printStackTrace();					
				}
				
			}
			
		});
		

	}

}
