import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.*;

import java.awt.event.*;

import javax.swing.JFrame;

public class test extends Applet implements MouseListener{
	//private mouseEventHandler eventHandler = new mouseEventHandler();
	
	static JFrame window = new JFrame();
	
	public test()
	{
		this.setPreferredSize(new Dimension(500, 500));
        addMouseListener(this);
	}
	
	public static void main(String[] args)
	{	
		test a = new test();
		
		//window.addMouseListener(eventHandler);
		//window.addMouseMotionListener(eventHandler);
		
		window.getContentPane().add(a,BorderLayout.CENTER);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(500, 500);
		window.setVisible(true);
		
		//window.addMouseListener(eventHandler);
		//window.addMouseMotionListener(eventHandler);
	}
	
	public void paint(Graphics g)
	{
		mouseEventHandler eventHandler = new mouseEventHandler();

		 window.getContentPane().addMouseListener(eventHandler);
		g.drawRect(20,20,20,20);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX()+ ", " + e.getY());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
