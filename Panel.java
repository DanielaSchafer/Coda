import java.applet.Applet;

import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Panel implements Comparable<Panel> {

	private boolean isWhite;
	private int panelNum;
	private boolean isFlipped;
	private JButton b;

	public static void main(String[] args) {}

	public Panel() {}

	public Panel(boolean white, int num, boolean flipped)
	{
		isFlipped = flipped;
		this.isWhite = white;
		this.panelNum = num;
		String num1 = Integer.toString(num);
		String id;
		
		if(white)
		{
			id = "w" + num1;
		}
		else
			id = "b" + num1;
		this.b = new JButton(id);
	}

	public boolean equals(Panel p)
	{
		if(p.getIsWhite() == isWhite && panelNum == p.getValue())
			return true;
		return false;
	}
	
	public boolean isFlipped()
	{
		return isFlipped;
	}

	public JButton getButton()
	{
		return b;
	}
	
	public String toString()
	{
		String color;
		if(isWhite)
			color = "white";
		else
			color = "black";
		
		if(isFlipped())
			return (color + "/"+ "F"+panelNum);
		else
			return (color + "/"+panelNum);
	}
	
	public void flip()
	{
		isFlipped = true;
	}

	public int getValue()
	{
		return panelNum;
	}

	public boolean getIsWhite()
	{
		return isWhite;
	}

	public static boolean isDash(Panel panel)
	{
		if(panel.getValue() == -1)
		{
			return true;
		}
		else return false;
	}
	
	/*public void buttonPressed()
	{
		Scanner sc=new Scanner(System.in);  
		System.out.println("What is your guess?");
		int num = sc.nextInt();  
		if(num == panelNum)
			isFlipped = true;
		else
			guessFail();
	}*/

	public static Comparator<Panel> NameComparator = new Comparator<Panel>() {
		@Override
		public int compare(Panel p1, Panel p2) {

			return p1.compareTo(p2);
		}
	};

	public int compareTo(Panel comparePanel)
	{
		int compareValue = ((Panel) comparePanel).getValue();
		return this.panelNum - compareValue;
	}

}
