package controller;

import sun.audio.*;

import java.io.*;
import java.net.URL;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

import model.disasters.Collapse;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSListener;
import model.events.simulatorListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.MedicalUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Rescuable;
import simulation.Simulator;
import view.gameView;

public class CommandCenter implements SOSListener, ActionListener {
	private gameView gameView;
	private JFrame StartMenu;
	private Simulator engine;
	private world world;
	private ArrayList<JButton>currentbuilding;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private ArrayList<Citizen> visibleCitizens;
	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;
	private JButton nxtCycle, startgame, exitgame;
	private ArrayList<JToggleButton> unitsBtn;
	private static AudioClip startmenumusic;
	static int respondunit = -1;
	private ButtonGroup magmoo3a;
	private ImageIcon buttonpicture;
	private static ResidentialBuilding curr;
	private JButton exit; 
    private JButton startagain; 
    private JFrame gameover;

	public CommandCenter() throws Exception {
		gameView = new gameView();

		StartMenu = new JFrame();
		StartMenu.setSize(new Dimension(2000, 1000));
		ImageIcon soora = new ImageIcon(getClass().getResource("/ProjectHitlerV2/Hitler.png"));
		StartMenu.setIconImage(soora.getImage());
		ImageIcon menupicture = new ImageIcon(getClass().getResource("Pictures/DisasterCity1.jpg"));
		JLabel label7elw = new JLabel(menupicture);
		label7elw.setSize(new Dimension(1000, 1000));
		StartMenu.add(label7elw);
		StartMenu.setVisible(true);
		StartMenu.setTitle("Game");
		StartMenu.setResizable(false);

		world = new world();
		gameView.add(world, BorderLayout.CENTER);
		engine = new Simulator(this);
		magmoo3a = new ButtonGroup();
		emergencyUnits = engine.getEmergencyUnits();
		unitsBtn = new ArrayList<JToggleButton>();
		for (Unit u : emergencyUnits) {
			JToggleButton zorar = new JToggleButton();
			zorar.addActionListener(this);
			ImageIcon img = new ImageIcon(getClass().getResource("Pictures/Hitler.png"));
			if (u instanceof Ambulance) {
				img = new ImageIcon(getClass().getResource("Pictures/Ambulance1.jpg"));
			} else if (u instanceof DiseaseControlUnit) {
				img = new ImageIcon(getClass().getResource("Pictures/DiseaseControlUnit1.jpg"));
			} else if (u instanceof GasControlUnit) {
				img = new ImageIcon(getClass().getResource("Pictures/GasControlUnit1.jpg"));
			} else if (u instanceof FireTruck) {
				img = new ImageIcon(getClass().getResource("Pictures/FireTruck1.jpg"));
			} else if (u instanceof Evacuator) {
				img = new ImageIcon(getClass().getResource("Pictures/Evacuator1.jpg"));
			}
			zorar.setIcon(img);
			magmoo3a.add(zorar);
			unitsBtn.add(zorar);
			gameView.changeunitstate(zorar, "idle");
			zorar.setVisible(true);
		}

		visibleBuildings = new ArrayList<ResidentialBuilding>();
		ImageIcon img = new ImageIcon(getClass().getResource("Pictures/Hitler.png"));
		startgame = new JButton("Start Game");
		exitgame = new JButton("Exit Game");
		Font F = new Font("Arial", Font.PLAIN, 40);
		startgame.setFont(F);
		exitgame.setFont(F);
		startgame.setBounds(100, 100, 500, 500);
		startgame.setSize(250, 100);
		startgame.setPreferredSize(new Dimension(100, 100));
		startgame.setVisible(true);
		exitgame.setBounds(100, 250, 500, 500);
		exitgame.setSize(250, 100);
		exitgame.setPreferredSize(new Dimension(100, 100));
		exitgame.setVisible(true);
		label7elw.add(startgame);
		startgame.addActionListener((ActionListener) this);
		label7elw.add(exitgame);
		label7elw.updateUI();
		exitgame.addActionListener((ActionListener) this);

		gameView.setIconImage(img.getImage());
		visibleCitizens = new ArrayList<Citizen>();
		nxtCycle = new JButton("Next Cycle");
		nxtCycle.setFont(F);
		nxtCycle.setBackground(Color.CYAN);
		nxtCycle.addActionListener((ActionListener) this);
		buttonpicture = new ImageIcon(getClass().getResource("Pictures/buttonbackground.jpg"));
		for (cell[] cells : controller.world.getMap()) {
			for (cell c : cells) {
				c.addActionListener(this);
				c.setVisible(true);

				c.setIcon(buttonpicture);
			}
		}
		world.getMap()[0][0].setIcon(new ImageIcon(getClass().getResource("Pictures/commandcenter1.jpg")));
		gameView.getPanel().add(nxtCycle, BorderLayout.SOUTH);
		gameView.setVisible(false);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(exit)){
            gameView.dispose();
            gameover.dispose();
            System.exit(0);
            return;
        }
        else if(e.getSource().equals(startagain)){
            gameView.dispose();
            gameover.dispose();
            try {
                new CommandCenter();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            return;
        }
		if (e.getSource().equals(startgame)) {
			startmenumusic.stop();
			URL url = CommandCenter.class.getResource("CurbYourEnthusiasm.au");
			startmenumusic = Applet.newAudioClip(url);
			startmenumusic.play();
			startmenumusic.loop();
			// StartMenu.setVisible(false);
			StartMenu.dispose();
			gameView.setVisible(true);
		} else if (e.getSource().equals(exitgame)) {
			StartMenu.setVisible(false);
			System.exit(0);
		}

		else if (e.getSource().equals(nxtCycle)) {
			if (engine.checkGameOver()) {
				exitFrame();
			}
			try {
				engine.nextCycle();
				checkIcon();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			gameView.view(engine.getview());
			changeLog();
			changeunits();

		}else if(currentbuilding!=null&&currentbuilding.contains(e.getSource())){
			if (respondunit!=-1) {
				for (int i = 0; i <curr.getOccupants().size(); i++) {
					if (e.getSource()==currentbuilding.get(i)) {
						try {
							emergencyUnits.get(respondunit).respond(curr.getOccupants().get(i));
						} catch (Exception e2) {
							frameError(e2.getMessage());
						}
						break;
					}
				}
			}
		}
		else if (e.getSource() instanceof cell) {
			cell b = (cell) e.getSource();
			if (respondunit != -1) {
				Unit curr = emergencyUnits.get(respondunit);
				if (unitsBtn.get(respondunit).isSelected()) {
					if (!(curr instanceof MedicalUnit)) {
						for (ResidentialBuilding r : visibleBuildings) {
							if (r.getLocation().getX() == b.getXp() && r.getLocation().getY() == b.getYp()) {
								try {
									emergencyUnits.get(respondunit).respond(r);
								} catch (Exception e1) {
									frameError(e1.getMessage());
								}
							}
						}
						for (Citizen c : visibleCitizens) {
							if (c.getLocation().getX() == b.getXp() && c.getLocation().getY() == b.getYp()) {
								try {
									emergencyUnits.get(respondunit).respond(c);
								} catch (Exception e1) {
									frameError(e1.getMessage());
								}
							}
						}
					} else {
						int xloc = b.getXp();
						int yloc = b.getYp();
						for (ResidentialBuilding r : visibleBuildings) {
							if (r.getLocation().getX() == b.getXp() && r.getLocation().getY() == b.getYp()) {
								try {
									emergencyUnits.get(respondunit).respond(r);
								} catch (Exception e1) {
									frameError(e1.getMessage());
									magmoo3a.clearSelection();
									respondunit = -1;
									return;
								}
							}
						}
						for (Citizen c : visibleCitizens) {
							if (c.getLocation().getX() == b.getXp() && c.getLocation().getY() == b.getYp()) {
								try {
									emergencyUnits.get(respondunit).respond(c);
								} catch (Exception e1) {
									frameError(e1.getMessage());
								}
							}
						}

					}
					magmoo3a.clearSelection();
				}
				respondunit = -1;

			}
			gameView.getCb().removeAll();
			gameView.getCb().updateUI();
			gameView.setInfo(getInformation(b.getXp(), b.getYp()));
		} else {
			JToggleButton zorar = (JToggleButton) e.getSource();
			if (respondunit != -1) {
				magmoo3a.clearSelection();
				respondunit = -1;
				gameView.setinfounit("");
				return;
			}
			for (int i = 0; i < emergencyUnits.size(); i++) {
				if (unitsBtn.get(i).equals(zorar)) {
					respondunit = i;
					break;
				}
			}
			if (zorar.isSelected()) {
				gameView.setinfounit(getunitinfo(respondunit));
			}
		}
	}

	void frameError(String s) {
		JFrame error = new JFrame();
		error.setPreferredSize(new Dimension(500, 500));
		error.setBounds(750, 250, 500, 500);
		error.setVisible(true);
		error.setResizable(false);
	//	 error.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JButton errorbutton = new JButton("Say Sorry");
		errorbutton.setPreferredSize(new Dimension(100, 100));
		JTextArea errormessage = new JTextArea(s);
		errormessage.setPreferredSize(new Dimension(300, 100));
		errormessage.setVisible(true);
		errormessage.setEditable(false);
		error.add(errorbutton, BorderLayout.SOUTH);
		error.add(errormessage, BorderLayout.NORTH);
		ImageIcon img = new ImageIcon(this.getClass().getResource("Pictures/errormessage1.jpg"));
		JLabel errorpicture = new JLabel(img);
		error.add(errorpicture, BorderLayout.CENTER);
		errorbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				error.dispose();
			}
		});
	}

	String getunitinfo(int idx) {
		Unit m = emergencyUnits.get(idx);
		String s = "";
		String type = "";
		String target = "";
		if (m instanceof Ambulance) {
			type = "Ambulance";
		}
		if (m instanceof GasControlUnit) {
			type = "GasControlUnit";
		}
		if (m instanceof DiseaseControlUnit) {
			type = "DiseaseControlUnit";
		}
		if (m instanceof FireTruck) {
			type = "FireTruck";
		}
		if (m instanceof Evacuator) {
			type = "Evacuator";
		}
		if (m.getTarget() != null) {
			if (m.getTarget() instanceof ResidentialBuilding) {
				ResidentialBuilding x = (ResidentialBuilding) m.getTarget();
				target = "Building at " + x.getLocation().getX() + " " + x.getLocation().getY();
			} else {
				Citizen x = (Citizen) m.getTarget();
				target = "Citizen " + x.getName() + " at " + x.getLocation().getX() + " " + x.getLocation().getY();
			}
		} else
			target = "No target.";

		s += "ID: " + m.getUnitID() + "\nType: " + type + "\nLocation: " + m.getLocation().getX() + " "
				+ m.getLocation().getY() + "\nSteps Per Cycle: " + m.getStepsPerCycle() + "\nTarget: " + target
				+ "\nState: " + m.getState();
		if (m instanceof Evacuator) {
			int co =1;
			s += "\nNumber of Passengers: " + ((Evacuator) m).getPassengers().size()+"\n";
			for (Citizen c : ((Evacuator) m).getPassengers()) {
				s +=(co++)+" - "+getperson(c) + "\n\n ";

			}
		}
		return s;
	}

	void checkIcon() {
		
		for (Citizen c : visibleCitizens) {
			if (c.getState()==CitizenState.DECEASED) {
				int xloc = c.getLocation().getX();
				int yloc = c.getLocation().getY();
				controller.world.getMap()[xloc][yloc]
						.setIcon(new ImageIcon(getClass().getResource("Pictures/CitizenDead1.jpg")));
				continue;
				
			}
			if (c.getState()==CitizenState.IN_TROUBLE) {
				int xloc = c.getLocation().getX();
				int yloc = c.getLocation().getY();
				controller.world.getMap()[xloc][yloc]
						.setIcon(new ImageIcon(getClass().getResource("Pictures/Citizenintrouble1.png")));
				continue;
				
			}
			if (c.getState()==CitizenState.SAFE||c.getState()==CitizenState.RESCUED) {
				int xloc = c.getLocation().getX();
				int yloc = c.getLocation().getY();
				controller.world.getMap()[xloc][yloc]
				.setIcon(new ImageIcon(getClass().getResource("Pictures/NormalCitizen1.png")));
				continue;
			}
			if (c.getDisaster() != null) {
				int xloc = c.getLocation().getX();
				int yloc = c.getLocation().getY();

				if (c.getDisaster() instanceof Injury) {
					changeIcon(xloc, yloc, "inj", c);

				} else if (c.getDisaster() instanceof Infection) {
					changeIcon(xloc, yloc, "tox", c);
				}

			}

		}
		for (ResidentialBuilding r : visibleBuildings) {
			if (r.getStructuralIntegrity()==0) {
				int xloc = r.getLocation().getX();
				int yloc = r.getLocation().getY();
				controller.world.getMap()[xloc][yloc]
				.setIcon(new ImageIcon(getClass().getResource("Pictures/destroyedbuilding1.jpg")));
			}
			
			else if (r.getDisaster() != null) {

				if (r.getDisaster() instanceof Fire) {

					changeIcon(r.getLocation().getX(), r.getLocation().getY(), "Fire", r);
				}
				if (r.getDisaster() instanceof GasLeak) {
					changeIcon(r.getLocation().getX(), r.getLocation().getY(), "Gas", r);
				}
				if (r.getDisaster() instanceof Collapse) {
					changeIcon(r.getLocation().getX(), r.getLocation().getY(), "Coll", r);
				}
			}

		}
	}

	@Override
	public void receiveSOSCall(Rescuable r) {

		if (r instanceof ResidentialBuilding) {

			if (!visibleBuildings.contains(r)) {
				visibleBuildings.add((ResidentialBuilding) r);
			}

		} else {

			if (!visibleCitizens.contains(r)) {
				visibleCitizens.add((Citizen) r);
			}
		}

	}

	public void changeview() {
		gameView.view(engine.getview());

	}

	public void changeIcon(int i, int j, String tybe, Rescuable r) {
		if (tybe.equals("people")) {
			controller.world.getMap()[i][j]
					.setIcon(new ImageIcon(getClass().getResource("Pictures/NormalCitizen1.png")));
		} else if (tybe.equals("building")) {
			controller.world.getMap()[i][j]
					.setIcon(new ImageIcon(getClass().getResource("Pictures/NormalBuilding1.jpg")));
		} else if (tybe.equals("peopleAndBuilding")) {
			controller.world.getMap()[i][j]
					.setIcon(new ImageIcon(getClass().getResource("Pictures/BuildingWithCitizens1.png")));
		} else if (tybe.equals("Fire")) {
			if (!r.getDisaster().isActive()) {
				if (((ResidentialBuilding) r).getFireDamage() > 0) {
					controller.world.getMap()[i][j]
							.setIcon(new ImageIcon(getClass().getResource("Pictures/FireTruckTreating1.png")));
				} else {
					controller.world.getMap()[i][j]
							.setIcon(new ImageIcon(getClass().getResource("Pictures/NormalBuilding1.jpg")));
				}
			} else
				controller.world.getMap()[i][j]
						.setIcon(new ImageIcon(getClass().getResource("Pictures/BuildingOnFire1.jpg")));
		} else if (tybe.equals("Coll")) {
			controller.world.getMap()[i][j]
					.setIcon(new ImageIcon(getClass().getResource("Pictures/CollapsingBuilding1.jpg")));
		} else if (tybe.equals("Gas")) {
			if (!r.getDisaster().isActive()) {
				if (((ResidentialBuilding) r).getGasLevel() > 0) {
					controller.world.getMap()[i][j]
							.setIcon(new ImageIcon(getClass().getResource("Pictures/GasLeakTreating1.jpg")));
				} else {
					controller.world.getMap()[i][j]
							.setIcon(new ImageIcon(getClass().getResource("Pictures/NormalBuilding1.jpg")));
				}
			} else
				controller.world.getMap()[i][j]
						.setIcon(new ImageIcon(getClass().getResource("Pictures/BuildingWithGasLeak1.jpg")));
		}
	}

	public static void main(String[] args) throws Exception {
	//	new CommandCenter();
		URL url = CommandCenter.class.getResource("WalterTheme1.wav");
		startmenumusic = Applet.newAudioClip(url);
		startmenumusic.play();
	}

	public void changeInfo(String s) {
		gameView.setInfo(s);
	}

	public void changeLog() {
		String s = engine.getlog();
		gameView.setLogpanel(s);

	}

	public String getInformation(int i, int j) {
		String h="";
		for (ResidentialBuilding b : visibleBuildings) {
			if (b.getLocation().getX() == i && b.getLocation().getY() == j) {
				h = " ";
				h += "Location: " + b.getLocation() + "\n StractrualIntegerity: " + b.getStructuralIntegrity()
						+ "\n FireDamage: " + b.getFireDamage() + "\n GasLevel: " + b.getGasLevel()
						+ "\n FoundationDamage: " + b.getFoundationDamage() + "\n ";
				h += "Number of Citizens: " + b.getOccupants().size() + "\n ";
				int conter = 1;
				currentbuilding= new ArrayList<JButton>();
				curr=b;
				for (Citizen c : b.getOccupants()) {
					h += (conter++) + "-" + getperson(c) + "\n ";
					JButton nn = new JButton(c.getName());
					nn.addActionListener(this);
					nn.setPreferredSize(new Dimension(150,100));
					nn.setFont(new Font(Font.DIALOG,Font.BOLD,20));
					currentbuilding.add(nn);
				}
				addtothepanel();
				if (b.getDisaster() != null) {
					if (b.getDisaster() instanceof GasLeak)
						h += " Disaster: GasLeak";
					if (b.getDisaster() instanceof Fire)
						h += " Disatser: Fire";
					if (b.getDisaster() instanceof Collapse)
						h += " Disatser: Collapse";
				}
			}
		}
		if (!h.equals("")) {
			h+="\nUnits : \n";
		for(int k =0;k<emergencyUnits.size();k++){
			Unit u = emergencyUnits.get(k);
			if (u.getLocation().getX()==i&&u.getLocation().getY()==j) {
				h+=getunitinfo(k)+"\n";
			}
		}
		}
		if (!h.equals("")) {
			return h;
		}
		h = "";
		for (Citizen c : visibleCitizens) {
			if (c.getLocation().getX() == i && c.getLocation().getY() == j) {
				h += getperson(c) + "\n ";
			}
		}
		h+="\nUnits : \n";
		for(int k =0;k<emergencyUnits.size();k++){
			Unit u = emergencyUnits.get(k);
			if (u.getLocation().getX()==i&&u.getLocation().getY()==j) {
				h+=getunitinfo(k)+"\n";
			}
		}
		if (!h.equals("\nUnits : \n")) {
			return h;
		}
		return "Location: " + i + " " + j;
	}

	public String getperson(Citizen c) {
		String CitizenInfo = "";
		CitizenInfo += "Citizen Location: " + c.getLocation() + "\n Citizen Name: " + c.getName() + "\n Citizen Age: "
				+ c.getAge() + "\n Citizen NationalID: " + c.getNationalID() + "\n Citizen HP: " + c.getHp()
				+ "\n Citizen BloodLoss: " + c.getBloodLoss() + "\n Citizen Toxicity: " + c.getToxicity()
				+ "\n Citizen State: " + c.getState();
		if (c.getDisaster() != null) {
			if (c.getDisaster() instanceof Infection)
				CitizenInfo += "\n Disatser: Infection";
			if (c.getDisaster() instanceof Injury)
				CitizenInfo += "\n Disatser: Injury";
		}
		return CitizenInfo;
	}

	void changeunits() {
		for (int i = 0; i < emergencyUnits.size(); i++) {
			String s = "";
			if (emergencyUnits.get(i).getState() == UnitState.IDLE) {
				s = "idle";
			}
			if (emergencyUnits.get(i).getState() == UnitState.RESPONDING) {
				s = "respond";
			}
			if (emergencyUnits.get(i).getState() == UnitState.TREATING) {
				s = "treat";
			}
			gameView.changeunitstate(unitsBtn.get(i), s);
		}
	}
	void addtothepanel(){
		JPanel p =gameView.getCb();
		p.removeAll();
		for(JButton b :currentbuilding)p.add(b);
		p.updateUI();
	}
	void exitFrame(){
        gameView.disable();
        gameover = new JFrame();
        gameover.setLayout(new BorderLayout());
        gameover.setPreferredSize(new Dimension(500, 500));
        gameover.setBounds(750, 250, 500, 500);
        gameover.setVisible(true);
        gameover.setResizable(false);
        exit=new JButton("end Game");
        exit.setPreferredSize(new Dimension(100, 100));
        exit.addActionListener(this);
        startagain=new JButton("Start a new game");
        startagain.setPreferredSize(new Dimension(100, 100));
        startagain.addActionListener(this);
        JTextArea endmessage =new JTextArea("Sorry but the game has ended"+" Number of Casualties: "+engine.calculateCasualties());
        endmessage.setPreferredSize(new Dimension(300, 100));
        endmessage.setVisible(true);
        endmessage.setEditable(false);
        gameover.add(exit,BorderLayout.NORTH);
        gameover.add(startagain,BorderLayout.CENTER);
        gameover.add(endmessage,BorderLayout.SOUTH);
    }

}