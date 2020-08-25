package controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class windowSamoaJoe extends WindowAdapter {
	public void windowClosing(WindowEvent e){
		//System.exit(0);
		JFrame hack = new JFrame();
		JLabel hackermessage = new JLabel("I HACKED YOU BEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEETCH");
		hack.setTitle("HAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHAHA!");
		hack.getContentPane().setBackground(Color.CYAN);
		hack.setSize(400, 400);
		hack.setVisible(true);
		hack.add(hackermessage);
	}
}
