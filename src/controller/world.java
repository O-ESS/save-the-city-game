package controller;

import java.awt.GridLayout;
import javax.swing.*;

public class world extends JPanel {
	private static cell[][] map = new cell[10][10];

	public world() {
		super();
		setLayout(new GridLayout(10,10,10, 10));
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 10; j++) {
				map[i][j] = new cell(i, j);
				this.add(map[i][j]);
			}

	}

	public static cell[][] getMap() {
		return map;
	}
}
