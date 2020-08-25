package controller;

import java.awt.Color;

import javax.swing.*;
import javax.imageio.*;
public class cell extends JButton {
	private int xp;
	private int yp;
public cell(int x,int y){
	super();
	setVisible(true);
	setBackground(Color.MAGENTA);
	this.xp=x;
	this.yp=y;
}
public int getXp(){
	return xp;
}
public int getYp(){
	return yp;
}
}
