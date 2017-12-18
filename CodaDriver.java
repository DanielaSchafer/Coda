import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class CodaDriver {

	public static void main(String[] args) {
		
		JFrame jp1 = new JFrame("Coda");
		CodaGraphics game = new CodaGraphics();
	
		jp1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jp1.setSize(new Dimension(1400,750));
		jp1.add(game);
		jp1.setBackground(Color.lightGray);
		jp1.setVisible(true);

	}
}