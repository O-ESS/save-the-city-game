package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.*;

import controller.world;

public class gameView extends JFrame {
	private JTextArea txt;
	private JPanel panel;
	private JPanel infoPanel, units, respond, treat, idle,cb;
	private JTextArea infotxt, unitinfo;
	private JScrollPane logpanel;
	private ArrayList<JToggleButton> responders, treaters, idles;
	private JTextArea logtxt;

	public gameView() {
		super();
		Font F20 = new Font("Arial", Font.PLAIN, 20);
		Font F40 = new Font("Arial", Font.PLAIN, 40);
		setTitle("game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 1950, 1000);
		setResizable(false);
		setLayout(new BorderLayout());
		panel = new JPanel();
		infoPanel = new JPanel();
		// world.getMap()[0][0].setIcon(new
		// ImageIcon(getClass().getResource("im/people.png")));
		// units view
		units = new JPanel();
		units.setLayout(new FlowLayout());
		units.setPreferredSize(new Dimension(500, 500));
		unitspanels();
		unitinfo = new JTextArea();
		unitinfo.setPreferredSize(new Dimension(800,1500));
		unitinfo.setEditable(false);
		//unitinfo.setText("fassa");
		add(units, BorderLayout.EAST);
		// end units view
		logtxt = new JTextArea();
		// logtxt.setPreferredSize(new Dimension(2000, 500));
		logtxt.setEditable(false);
		logtxt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		panel.setLayout(new GridLayout());
		txt = new JTextArea();
		txt.setPreferredSize(new Dimension(200, 200));
		txt.setEditable(false);
		txt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		panel.add(txt, BorderLayout.SOUTH);
		infotxt = new JTextArea();
		infotxt.setPreferredSize(new Dimension(450, 1500));
		infotxt.setEditable(false);
		infotxt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		infoPanel.add(infotxt);
		add(panel, BorderLayout.NORTH);
		// add(infoPanel,BorderLayout.SOUTH);
		setMaximumSize(new Dimension(1000, 1000));
		setMinimumSize(new Dimension(1000, 1000));
		logpanel = new JScrollPane(logtxt);
		logpanel.setPreferredSize(new Dimension(650, 200));
		JScrollPane infoscroll = new JScrollPane(infotxt);
		infoscroll.setPreferredSize(new Dimension(350, 1000));
		add(infoscroll, BorderLayout.WEST);
		JPanel south= new JPanel();
		JScrollPane unitscroll = new JScrollPane(unitinfo);
		unitscroll.setPreferredSize(new Dimension(600, 200)); //Don't mess here
		
		south.setPreferredSize(new Dimension(2000,250));
		south.setVisible(true);
		cb = new JPanel();
		cb.setPreferredSize(new Dimension(600,200));
		cb.setBackground(Color.white);
		cb.setVisible(true);	
		south.add(logpanel,BorderLayout.WEST);
		south.add(cb,BorderLayout.CENTER);
		south.add(unitscroll,BorderLayout.EAST);
		add(south, BorderLayout.SOUTH);
		infotxt.setFont(F20);
		logtxt.setFont(F20);
		unitinfo.setFont(F20);
		txt.setFont(F40);
		setInfo("");
		repaint();
		revalidate();
		view("CurrentCycle: 0" + "\nnumber of casualties: 0");
//		JLabel background = new JLabel();
//		background.setBounds(0,0,1950,1000);
//		ImageIcon backgroundimage = new ImageIcon(getClass().getResource(
//				"gamebackground1.jpg"));
//		background.setIcon(backgroundimage);
//		add(background);
//		background.setVisible(true);

	}
	public JPanel getCb() {
		return cb;
	}

	void unitspanels() {
		JTabbedPane jtp = new JTabbedPane();
		treaters = new ArrayList<JToggleButton>();
		responders = new ArrayList<JToggleButton>();
		idles = new ArrayList<JToggleButton>();
		treat = new JPanel();
		respond = new JPanel();
		idle = new JPanel();
		treat.setVisible(true);
		respond.setVisible(true);
		idle.setVisible(true);
		JScrollPane idlescroll = new JScrollPane(idle);
		JScrollPane treatscroll = new JScrollPane(treat);
		JScrollPane respondscroll = new JScrollPane(respond);
		idlescroll.setVisible(true);
		treatscroll.setVisible(true);
		respondscroll.setVisible(true);
		// jtp.s
		treat.setPreferredSize(units.getPreferredSize());
		respond.setPreferredSize(units.getPreferredSize());
		idle.setPreferredSize(units.getPreferredSize());
		idle.setBackground(Color.ORANGE);
		respond.setBackground(Color.BLUE);
		treat.setBackground(Color.GREEN);
		jtp.addTab("Idle Units", idlescroll);
		jtp.setMnemonicAt(0, KeyEvent.VK_1);
		jtp.addTab("Responding Units", respondscroll);
		jtp.setMnemonicAt(1, KeyEvent.VK_2);
		jtp.addTab("Treating Units", treatscroll);
		jtp.setMnemonicAt(2, KeyEvent.VK_3);
		jtp.setVisible(true);
		units.add(jtp);
	}

	public void changeunitstate(JToggleButton g, String s) {
		if (s.equals("treat")) {
			if (!treaters.contains(g)) {
				treaters.add(g);
				treat.add(g);
			}
			ArrayList<JToggleButton> remove = new ArrayList<JToggleButton>();
			for (JToggleButton z : idles) {
				if (z.equals(g)) {
					remove.add(z);
				}
			}
			idles.removeAll(remove);
			remove.clear();
			for (JToggleButton z : responders) {
				if (z.equals(g)) {
					remove.add(z);
				}
			}
			responders.removeAll(remove);
		} else if (s.equals("respond")) {
			if (!responders.contains(g)) {
				responders.add(g);
				respond.add(g);
			}
			ArrayList<JToggleButton> remove = new ArrayList<JToggleButton>();
			for (JToggleButton z : idles) {
				if (z.equals(g)) {
					remove.add(z);
				}
			}
			idles.removeAll(remove);
			remove.clear();
			for (JToggleButton z : treaters) {
				if (z.equals(g)) {
					remove.add(z);
				}
			}
			treaters.removeAll(remove);
			remove.clear();
		} else if (s.equals("idle")) {
			if (!idles.contains(g)) {
				idles.add(g);
				idle.add(g);
				g.setVisible(true);
			}
			ArrayList<JToggleButton> remove = new ArrayList<JToggleButton>();
			for (JToggleButton z : treaters) {
				if (z.equals(g)) {
					remove.add(z);
				}
			}
			treaters.removeAll(remove);
			remove.clear();
			for (JToggleButton z : responders) {
				if (z.equals(g)) {
					remove.add(z);
				}
			}
			responders.removeAll(remove);
			remove.clear();
		}
		treat.updateUI();
		respond.updateUI();
		idle.updateUI();
	}

	public JPanel getPanel() {
		return panel;
	}

	public void view(String display) {

		txt.setText(display);
	}

	public void setInfo(String h) {
		infotxt.setText("infoPanel: " + "\n" + h);
	}

	public void Icons(int i, int j, String tybe) {
		if (tybe.equals("people")) {
			world.getMap()[i][j].setIcon(new ImageIcon(getClass().getResource(
					"Pictures/NormalCitizen.png")));
		} else if (tybe.equals("building")) {
			world.getMap()[i][j].setIcon(new ImageIcon(getClass().getResource(
					"Pictures/NormalBuilding1.jpg")));
		} else if (tybe.equals("peopleAndBuilding")) {
			world.getMap()[i][j].setIcon(new ImageIcon(getClass().getResource(
					"Pictures/BuildingWithCitizens1.png")));
		}
	}

	public void setLogpanel(String h) {
		logtxt.setText(h);
	}
	public void setinfounit(String h){
		unitinfo.setText(h);
	}
}
