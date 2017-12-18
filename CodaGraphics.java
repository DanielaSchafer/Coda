
import java.awt.*; 


//import java.awt.event.MouseEvent;

//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;

import javax.swing.*;
import java.applet.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CodaGraphics extends JPanel implements MouseListener{

	public static void main(String args[])
	{
		//getPanelColorPreference(Graphics g)
		JFrame jp1 = new JFrame("Coda");
		CodaGraphics game = new CodaGraphics();

		jp1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jp1.setSize(new Dimension(1400,750));
		jp1.add(game);
		jp1.setVisible(true);
	}

	static CodaGame game;
	private GameState state;
	private GamePage page;
	private int i = 0;
	private ArrayList<Point> compPanelPoints = new ArrayList<Point>();
	private ArrayList<Point> userPanelPoints = new ArrayList<Point>();
	private int enteredValue;
	private int chooseValueIndex;

	private int sleepTime = 1000;

	private ArrayList<Point> valueGuessPoints = new ArrayList<Point>();

	public CodaGraphics() {
		super();
		this.addMouseListener(this);
		//game = new CodaGame();
		//state = GameState.PLR_DRAW;
		page = GamePage.HOME;
		game = new CodaGame();
		state = GameState.PLR_DRAW;
	} 

	static JFrame jp1 = new JFrame();

	/*public void paint(Graphics g)
	{
		compGuess(g);
	}*/

	//@Override
	public void paintComponent(Graphics g)
	{

		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 1400, 750);
		//System.out.println(state);
		drawBackground(g);

		//drawGame(g, game.getGame());
		//getPanelColorPreference(g);

		switch(page)
		{
		case HOME:
			homeScreen(g);
			break;
		case GAME:

			if(game.gameOverPLR() == GameState.GAME_OVER)
			{
				System.out.println("Game Over \n Computer Wins");
				state = GameState.GAME_OVER;
				page = GamePage.END_LOSE;
			}
			else if(game.gameOverComp() == GameState.GAME_OVER)
			{
				System.out.println("Game Over \n User Wins");
				state = GameState.GAME_OVER;
				page = GamePage.END_WIN;
			}

			switch(state)
			{
			case PLR_DRAW:
				drawGame(g,game.getGame());
				getPanelColorPreference(g);
				repaint();
				break;

			case PLR_CHOOSE_PANEL:
				g.setColor(Color.lightGray);
				g.fillRect(600, 50, 200, 150);
				drawGame(g,game.getGame());
				drawRanPanel(g,game.getUserRanPan());
				//state = 
				repaint();
				break;

			case PLR_CHOOSE_VALUE:
				drawGame(g,game.getGame());
				g.setColor(Color.lightGray);
				g.fillRect(600, 50, 200, 150);
				drawRanPanel(g,game.getUserRanPan());
				valuesGraphic(g,chooseValueIndex);
				//state =  game.playerGuessValue(enteredValue);
				repaint();
				break;

			case PLR_CHOOSE_GO_AGAIN:
				drawBackground(g);
				drawGame(g,game.getGame());
				g.setColor(Color.lightGray);
				g.fillRect(600, 50, 200, 150);
				drawRanPanel(g,game.getUserRanPan());
				//drawRanPanel(g,game.getUserRanPan());
				goAgainGraphic(g);
				repaint();
				break;

			case PLR_TURN_OVER:
				drawGame(g,game.getGame());
				g.setColor(Color.lightGray);
				g.fillRect(600, 50, 200, 150);
				state = game.gameOverPLR();
				repaint();
				break;

			case COMP_DRAW:
				drawGame(g,game.getGame());
				g.setColor(Color.lightGray);
				g.fillRect(600, 50, 200, 150);
				state = game.computerDrawPanel();
				drawCompRanPanel(g,game.getCompRanPan());
				repaint();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case COMP_GUESS:
				drawGame(g,game.getGame());
				drawCompRanPanel(g,game.getCompRanPan());
				state = game.computerGuess();
				compGuess(g);
				repaint();
				break;

			case COMP_CHOOSE_GO_AGAIN:
				drawGame(g,game.getGame());
				drawCompRanPanel(g,game.getCompRanPan());
				state = game.computerGoAgain();
				repaint();
				break;

			case COMP_TURN_OVER:
				drawGame(g,game.getGame());
				g.setColor(Color.lightGray);
				g.fillRect(600, 50, 200, 150);
				state = game.gameOverComp();
				repaint();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case GAME_OVER:
				System.out.println(state);
				drawGame(g,game.getGame());
				if(game.gameOverComp() == GameState.GAME_OVER)
					page = GamePage.END_WIN;

				if(game.gameOverPLR() == GameState.GAME_OVER)
					page = GamePage.END_LOSE;
				repaint();
				break;

			default:
				drawGame(g, game.getGame());
				repaint();
			}
			break;
		case END_LOSE:
			endScreenLose(g);
			game = new CodaGame();
			state = GameState.PLR_DRAW;
			repaint();
			break;
		case END_WIN:
			endScreenWin(g);
			game = new CodaGame();
			state = GameState.PLR_DRAW;
			repaint();
			break;
		case RULES:

		}
	}

	//DRAWS GAME
	public void drawGame(Graphics g, Game game)
	{
		makeComputerHand(g,game.getCompHand());
		makeStartHand(g,game.getUserHand());
	}

	//DRAWS BACKGROUND
	public static void drawBackground(Graphics g)
	{
		int[] xPos = {0,350,1050,1380};
		int[] yPos = {700,250,250,700};
		//97/26/9
		Color brown = new Color(150,136,120);
		g.setColor(brown);
		g.drawPolygon(xPos, yPos,4);
		g.fillPolygon(xPos, yPos, 4);
	}

	//CREATES USER START HAND
	public void makeStartHand(Graphics g, Code hand)
	{
		int yPos = 500;
		userPanelPoints.clear();
		int xPos = 700-((hand.getCodeLength()/2)*100);
		for(int i = 0; i<hand.getCodeLength(); i++)
		{
			Point p = new Point(xPos,yPos);
			userPanelPoints.add(p);
			makePanels(g,hand.getPanel(i),xPos);
			xPos= xPos+100; 
		}
	}

	//CREATES COMPUTER START HAND
	public void makeComputerHand(Graphics g, Code hand)
	{
		compPanelPoints.clear();
		int yPos = 300;
		int xPos = 700-((hand.getCodeLength()/2)*75);
		for(int i = 0; i<hand.getCodeLength(); i++)
		{
			Point p = new Point(xPos,yPos);
			compPanelPoints.add(p);
			drawComputerPanel(g,hand.getPanel(i),xPos);
			xPos= xPos+75; 
		}
	}

	//-------------------------------------------------------------------------------------
	//CREATES USER PANELS
	public void makePanels(Graphics g, Panel panel, int xPos)
	{
		Point startPoint = new Point(xPos,500);
		int w = 75;
		int h = 100;
		int offset = 8;
		Color sideWhite = new Color(220,250,225);
		Color mainWhite = new Color(240,250,240);
		Color outlineWhite = new Color(100,100,100);
		Color numColW = new Color(0,25,0);

		Color sideBlack = new Color(15,25,60);
		Color mainBlack = new Color(5,25,0);
		Color outlineBlack = new Color(100,100,100);
		Color numColB = mainWhite;

		if(panel.getIsWhite())
		{
			if(panel.isFlipped())
				createFlippedPanel(g,mainWhite, outlineWhite, sideWhite, numColW, w, h, panel, startPoint);

			else
				createPanel(g,mainWhite, outlineWhite, sideWhite, numColW, offset, w,  h,  panel, startPoint);
		}
		else
		{
			if(panel.isFlipped())
				createFlippedPanel(g, mainBlack, outlineBlack, sideBlack, numColB, w, h, panel, startPoint);
			else
				createPanel(g,mainBlack, outlineBlack, sideBlack, numColB, offset, w,  h,  panel, startPoint);
		}
	}

	//-------------------------------------------------------------------------------------
	//CREATES COMPUTER PANELS
	public void drawComputerPanel(Graphics g, Panel panel, int xPos)
	{
		Point startPoint = new Point(xPos,300);
		int w = 60;
		int h = 80;
		int offset = 5;
		Color sideWhite = new Color(220,250,225);
		Color mainWhite = new Color(240,250,240);
		Color outlineWhite = new Color(100,100,100);
		Color numColW = new Color(0,25,0);

		Color sideBlack = new Color(15,25,60);
		Color mainBlack = new Color(5,25,0);
		Color outlineBlack = new Color(100,100,100);
		Color numColB = mainWhite;

		String num1 = Integer.toString(panel.getValue());

		if(panel.getIsWhite())
		{
			if(panel.isFlipped())
				createComputerFilppedPanel(g,mainWhite, outlineWhite, sideWhite, numColW, w,  h,  panel, startPoint);
			else
				createComputerPanel(g,mainWhite, outlineWhite, sideWhite, numColW, offset, w,  h,  panel, startPoint);
		}
		else
		{
			if(panel.isFlipped())
				createComputerFilppedPanel(g,mainBlack, outlineBlack, sideBlack, numColB, w,  h,  panel, startPoint);
			else
				createComputerPanel(g,mainBlack, outlineBlack, sideBlack, numColB, offset, w,  h,  panel, startPoint);
		}
	}

	//------------------------------------------------------------------------------------------
	//CREATES FLIPPED COMPUTER PANEL
	public void createComputerFilppedPanel(Graphics g, Color main, Color outline, Color side, Color numCol, int w, int h, Panel panel, Point startPoint)
	{
		Font longNum = new Font("Consolas",Font.BOLD,35);
		Font dashF = new Font("Consolas",Font.BOLD, 55);
		Font smallNum = new Font("Consolas",Font.BOLD, 40);

		int topW = 50;
		int bottomH = 10;

		int[] mainPolyX = {(int)startPoint.getX()+((w-topW)/2), (int)startPoint.getX()+w-(((w-topW)/2)),(int)startPoint.getX()+w,(int)startPoint.getX()};
		int[] mainPolyY = {(int)startPoint.getY()+20, (int)startPoint.getY()+20,(int) startPoint.getY()+h-10, (int)startPoint.getY()+h-10};

		g.setColor(side);
		g.fillPolygon(mainPolyX, mainPolyY,4);
		g.setColor(outline);
		g.drawPolygon(mainPolyX, mainPolyY,4);

		g.setColor(main);
		g.fillRect((int)startPoint.getX(),(int)startPoint.getY()+h-10,w,20);
		g.setColor(outline);
		g.drawRect((int)startPoint.getX(),(int)startPoint.getY()+h-10,w,20);

		g.setColor(numCol);
		g.setFont(longNum);

		if(panel.getValue() == 10 || panel.getValue() == 11)
		{
			String num = Integer.toString(panel.getValue());
			g.drawString(num, (int)startPoint.getX()+((w/9))+6, (int)startPoint.getY()+(h-26));
		}
		else if(panel.getValue() == -1)
		{
			String num = "-";
			g.setFont(dashF);
			g.drawString(num, (int)startPoint.getX()+((w/5)-7), (int)startPoint.getY()+(h-20));
		}
		else 
		{
			String num = Integer.toString(panel.getValue());
			g.setFont(smallNum);
			g.drawString(num, (int)startPoint.getX()+((w/5)+8), (int)startPoint.getY()+(h-20));
		}
	}

	//------------------------------------------------------------------------------------------
	//CREATES FLIPPED USER PANEL
	public void createFlippedPanel(Graphics g,Color main, Color outline, Color side, Color numCol,int w, int h, Panel panel, Point startPoint)
	{
		Font longNum = new Font("Consolas",Font.BOLD,45);
		Font dashF = new Font("Consolas",Font.BOLD, 65);
		Font smallNum = new Font("Consolas",Font.BOLD, 50);

		int topW = 61;
		int bottomH = 20;

		int[] mainPolyX = {(int)startPoint.getX()+((w-topW)/2), (int)startPoint.getX()+w-(((w-topW)/2)),(int)startPoint.getX()+w,(int)startPoint.getX()};
		int[] mainPolyY = {(int)startPoint.getY()+20, (int)startPoint.getY()+20,(int) startPoint.getY()+h-10, (int)startPoint.getY()+h-10};

		g.setColor(side);
		g.fillPolygon(mainPolyX, mainPolyY,4);
		g.setColor(outline);
		g.drawPolygon(mainPolyX, mainPolyY,4);

		g.setColor(main);
		g.fillRect((int)startPoint.getX(),(int)startPoint.getY()+h-10,w,20);
		g.setColor(outline);
		g.drawRect((int)startPoint.getX(),(int)startPoint.getY()+h-10,w,20);

		g.setColor(numCol);
		g.setFont(longNum);

		if(panel.getValue() == 10 || panel.getValue() == 11)
		{
			String num = Integer.toString(panel.getValue());
			g.drawString(num, (int)startPoint.getX()+((w/9))+4, (int)startPoint.getY()+(h-30));
		}
		else if(panel.getValue() == -1)
		{
			String num = "-";
			g.setFont(dashF);
			g.drawString(num, (int)startPoint.getX()+((w/5)-7), (int)startPoint.getY()+(h-20));
		}
		else
		{
			String num = Integer.toString(panel.getValue());
			g.setFont(smallNum);
			g.drawString(num, (int)startPoint.getX()+((w/5)+9), (int)startPoint.getY()+(h-20)-5);
		}
	}

	//------------------------------------------------------------------------------------------
	//CREATES NORMAL USER PANEL
	public void createPanel(Graphics g,Color main, Color outline, Color side, Color numCol, int offset, int w, int h, Panel panel, Point startPoint)
	{
		Font longNum = new Font("Consolas",Font.BOLD,55);
		Font dashF = new Font("Consolas",Font.BOLD, 75);
		Font smallNum = new Font("Consolas",Font.BOLD, 60);

		g.setColor(main);
		g.fillRect((int)startPoint.getX(),(int)startPoint.getY(),w,h);
		g.setColor(outline);
		g.drawRect((int)startPoint.getX(),(int)startPoint.getY(),w,h);

		Point newPoint = new Point((int)startPoint.getX()-offset,(int)startPoint.getY()+offset);
		int[] TopPolygonX = {(int)startPoint.getX(),(int)newPoint.getX(),(int)newPoint.getX()+w,(int)startPoint.getX()+w};
		int[] TopPolygonY = {(int)startPoint.getY(),(int)newPoint.getY(),(int)newPoint.getY(),(int)startPoint.getY()};

		g.setColor(main);
		g.fillRect((int)newPoint.getX(),(int)newPoint.getY(),w,h);
		g.setColor(side);
		g.fillPolygon(TopPolygonX, TopPolygonY, 4);
		g.setColor(outline);
		g.drawPolygon(TopPolygonX, TopPolygonY, 4);

		int[] sideX = {(int)newPoint.getX()+w,(int)startPoint.getX()+w,(int)startPoint.getX()+w,(int)newPoint.getX()+w};
		int[] sideY = {(int)newPoint.getY(),(int)startPoint.getY(),(int)startPoint.getY()+h,(int)newPoint.getY()+h};

		g.setColor(side);
		g.fillPolygon(sideX, sideY, 4);
		g.setColor(outline);
		g.drawPolygon(sideX, sideY, 4);

		g.setColor(numCol);
		g.setFont(longNum);

		if(panel.getValue() == 10)
		{
			String num = Integer.toString(panel.getValue());
			g.drawString(num, (int)startPoint.getX()+((w/9))-9, (int)startPoint.getY()+(h-25));
		}
		else if(panel.getValue() == 11)
		{
			String num = Integer.toString(panel.getValue());
			g.drawString(num, (int)startPoint.getX()+((w/9)-8), (int)startPoint.getY()+(h-25));
		}
		else if(panel.getValue() == -1)
		{
			String num = "-";
			g.setFont(dashF);
			g.drawString(num, (int)startPoint.getX()+((w/5)-7), (int)startPoint.getY()+(h-25));
		}
		else
		{
			String num = Integer.toString(panel.getValue());
			g.setFont(smallNum);
			g.drawString(num, (int)startPoint.getX()+((w/5)-3), (int)startPoint.getY()+(h-25));
		}
	}


	//---------------------------------------------------------------------------
	//CREATES COMPUTER PANEL
	public static void createComputerPanel(Graphics g, Color main, Color outline, Color side, Color numCol, int offset, int w, int h, Panel panel, Point startPoint)
	{
		Font longNum = new Font("Consolas",Font.BOLD,55);
		Font dashF = new Font("Consolas",Font.BOLD, 75);
		Font smallNum = new Font("Consolas",Font.BOLD, 60);

		panel.getButton().setLocation((int)startPoint.getX(),(int)startPoint.getY());
		panel.getButton().setSize(w,h);

		g.setColor(main);
		g.fillRect((int)startPoint.getX(),(int)startPoint.getY(),w,h);
		g.setColor(outline);
		g.drawRect((int)startPoint.getX(),(int)startPoint.getY(),w,h);

		Point newPoint = new Point((int)startPoint.getX()-offset,(int)startPoint.getY()+offset);
		int[] TopPolygonX = {(int)startPoint.getX(),(int)newPoint.getX(),(int)newPoint.getX()+w,(int)startPoint.getX()+w};
		int[] TopPolygonY = {(int)startPoint.getY(),(int)newPoint.getY(),(int)newPoint.getY(),(int)startPoint.getY()};

		g.setColor(main);
		g.fillRect((int)newPoint.getX(),(int)newPoint.getY(),w,h);
		g.setColor(side);
		g.fillPolygon(TopPolygonX, TopPolygonY, 4);
		g.setColor(outline);
		g.drawPolygon(TopPolygonX, TopPolygonY, 4);

		int[] sideX = {(int)newPoint.getX()+w,(int)startPoint.getX()+w,(int)startPoint.getX()+w,(int)newPoint.getX()+w};
		int[] sideY = {(int)newPoint.getY(),(int)startPoint.getY(),(int)startPoint.getY()+h,(int)newPoint.getY()+h};

		g.setColor(side);
		g.fillPolygon(sideX, sideY, 4);
		g.setColor(outline);
		g.drawPolygon(sideX, sideY, 4);

		g.setColor(numCol);
		g.setFont(longNum);

		if(panel.isFlipped())
		{

			g.setColor(numCol);
			g.setFont(longNum);

			if(panel.getValue() == 10 || panel.getValue() == 11)
			{
				String num = Integer.toString(panel.getValue());
				g.drawString(num, (int)startPoint.getX()+((w/9))-2, (int)startPoint.getY()+(h-20));
			}
			else if(panel.getValue() == -1)
			{
				String num = "-";
				g.setFont(dashF);
				g.drawString(num, (int)startPoint.getX()+((w/5)-7), (int)startPoint.getY()+(h-20));
			}
			else
			{
				String num = Integer.toString(panel.getValue());
				g.setFont(smallNum);
				g.drawString(num, (int)startPoint.getX()+((w/5)-5), (int)startPoint.getY()+(h-20));
			}
		}
	}

	//--------------------------------------------------------------------------------------
	//DRAWS RANDOM PANEL
	public void drawRanPanel(Graphics g, Panel panel)
	{
		Point startPoint = new Point(1000,50);
		int w = 90;
		int h = 120;
		int offset = 10;
		Color sideWhite = new Color(220,250,225);
		Color mainWhite = new Color(240,250,240);
		Color outlineWhite = new Color(100,100,100);
		Color numColW = new Color(0,25,0);

		Color sideBlack = new Color(15,25,60);
		Color mainBlack = new Color(5,25,0);
		Color outlineBlack = new Color(100,100,100);
		Color numColB = mainWhite;


		if(panel.getIsWhite())
		{
			if(panel.isFlipped())
				createFlippedPanel(g,mainWhite, outlineWhite, sideWhite, numColW, w, h, panel, startPoint);
			else
				createPanel(g,mainWhite, outlineWhite, sideWhite, numColW, offset, w,  h,  panel, startPoint);
		}
		else
		{
			if(panel.isFlipped())
				createFlippedPanel(g, mainBlack, outlineBlack, sideBlack, numColB, w, h, panel, startPoint);
			else
				createPanel(g,mainBlack, outlineBlack, sideBlack, numColB, offset, w,  h,  panel, startPoint);
		}
	}

	//DRAWS COMPUTER RANDOM PANEL
	public void drawCompRanPanel(Graphics g, Panel panel)
	{
		Point startPoint = new Point(1000,50);
		int w = 90;
		int h = 120;
		int offset = 10;
		Color sideWhite = new Color(220,250,225);
		Color mainWhite = new Color(240,250,240);
		Color outlineWhite = new Color(100,100,100);
		Color numColW = new Color(0,25,0);

		Color sideBlack = new Color(15,25,60);
		Color mainBlack = new Color(5,25,0);
		Color outlineBlack = new Color(100,100,100);
		Color numColB = mainWhite;


		if(panel.getIsWhite())
		{
			createComputerPanel(g,mainWhite, outlineWhite, sideWhite, numColW, offset, w,  h,  panel, startPoint);
		}
		else
		{
			createComputerPanel(g,mainBlack, outlineBlack, sideBlack, numColB, offset, w,  h,  panel, startPoint);
		}
	}

	//creates text field for user to enter guess value
	public void textFieldForEnteringValues(Graphics g, int index)
	{
		int compPanelH = 80;
		int compPanelW = 60;

		JLabel enterVal = new JLabel("Enter Guess Here:");
		enterVal.setLocation((int)compPanelPoints.get(i).getX()+compPanelW/2, (int)compPanelPoints.get(i).getY()+compPanelH+5); 
		JTextField val = new JTextField(2);
		val.setLocation((int)compPanelPoints.get(i).getX()+compPanelW/2, (int)compPanelPoints.get(i).getY()+compPanelH+15);
		JButton button = new JButton("Enter");

		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try {
					int integerValue = Integer.parseInt(val.getText());
				}
				catch(NumberFormatException ex)
				{
					System.out.println("Exception : "+ex);
				}

				enteredValue = Integer.parseInt(val.getText());
				game.playerGuessValue(enteredValue);
			}
		});
	}

	public void valuesGraphic(Graphics g, int index)
	{
		valueGuessPoints.clear();
		Font smallNum = new Font("Consolas",Font.BOLD, 20);
		g.setFont(smallNum);

		int xPan = (int) compPanelPoints.get(index).getX();
		int yPan = (int) compPanelPoints.get(index).getY();

		int wPan = 60;
		int hPan = 80;

		g.drawRect(xPan, yPan, wPan, hPan);

		int wPointer = 20;
		int hPointer = 30;
		int xPosPointer = xPan + (wPan/2)-(wPointer/2);
		int yPosPointer = yPan + (hPan) + 10;

		int butt = 30; //both w and h
		int space = 10;

		int wBox = ((butt*6)+space*7);
		int hBox = (butt*2)+(space*3);
		int xBox = xPan +(wPan/2) - (wBox/2);
		int yBox = yPan + (hPointer)+ hPan;

		int Xspace = 10;
		int Yspace = 10;
		int counter = 0;

		g.setColor(Color.darkGray);
		g.fillRect(xPosPointer, yPosPointer, wPointer, hPointer);
		g.fillRect(xBox, yBox, wBox, hBox);

		for(int i = yBox+10; i<=yBox+(hBox-30); i= i+40)
		{
			for(int j = xBox+10; j<=xBox+(wBox-30); j = j+butt+space)
			{
				Point p = new Point(j,i);
				valueGuessPoints.add(p);
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(j, i, butt, butt);
				String num = Integer.toString(counter);
				g.setColor(Color.BLACK);
				if(counter<10)
					g.drawString(num, j+(butt/2)-6, i+(butt/2)+5);
				else
					g.drawString(num, j+3,i+(butt/2)+5);
				counter++;
			}
		}

	}

	public void getPanelColorPreference(Graphics g)
	{
		Font text = new Font("Consolas",Font.PLAIN,20);
		g.setFont(text);
		g.setColor(Color.darkGray);
		g.fillRect(600, 50, 200, 150);
		g.setColor(Color.WHITE);
		g.drawString("Pick a Color",635,75);

		Point whitePoint = new Point(630,100);
		Point blackPoint = new Point(720,100);
		int w = 60;
		int h = 80;
		int offset = 5;
		Color sideWhite = new Color(220,250,225);
		Color mainWhite = new Color(240,250,240);
		Color outlineWhite = new Color(100,100,100);
		Color numColW = new Color(0,25,0);

		Color sideBlack = new Color(15,25,60);
		Color mainBlack = new Color(5,25,0);
		Color outlineBlack = new Color(100,100,100);
		Color numColB = mainWhite;

		Panel whiteP = new Panel(true,0,false);
		Panel blackP = new Panel(false,0,false);

		if(game.getGame().remainingContainsColor(true))
			createComputerPanel(g,mainWhite, outlineWhite, sideWhite, numColW, offset, w,  h,  whiteP, whitePoint);

		if(game.getGame().remainingContainsColor(false))
			createComputerPanel(g,mainBlack, outlineBlack, sideBlack, numColB, offset, w,  h,  blackP, blackPoint);

	}

	public void goAgainGraphic(Graphics g) 
	{
		Font text = new Font("Consolas",Font.PLAIN,20);
		g.setFont(text);
		g.setColor(Color.darkGray);
		g.fillRect(600, 50, 200, 100);
		g.setColor(Color.WHITE);
		g.drawString("Go Again?",645,80);

		Point whitePoint = new Point(625,100);
		Point blackPoint = new Point(715,100);
		int w = 60;
		int h = 30;

		g.fillRect((int)whitePoint.getX(),(int)whitePoint.getY(),w,h);

		g.fillRect((int)blackPoint.getX(),(int)blackPoint.getY(),w,h);

		g.setColor(Color.BLACK);
		g.drawString("yes",(int)whitePoint.getX()+14,(int)whitePoint.getY()+20);
		g.drawString("no",(int)blackPoint.getX()+18,(int)blackPoint.getY()+20);
	}

	public void homeScreen(Graphics g)
	{
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 1400, 750);

		g.setColor(Color.BLACK);
		Font title = new Font("Consolas",Font.BOLD,400);
		Font button = new Font("Consolas", Font.PLAIN,40);
		g.setFont(title);
		g.drawString("CODA",250,375);

		int xPos = 600;
		int yPosPlay = 450;
		int yPosRules = 550;

		g.setFont(button);

		g.setColor(Color.green);
		g.fillRect(xPos, yPosPlay, 150, 75);
		//g.fillRect(xPos, yPosRules, 150, 75);

		g.setColor(Color.BLACK);
		g.drawString("PLAY", xPos+28, yPosPlay+50);
		//g.drawString("RULES", xPos+25, yPosRules+50);

	}

	public void endScreenWin(Graphics g)
	{
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 1400, 750);

		g.setColor(Color.BLACK);
		Font title = new Font("Consolas",Font.BOLD,200);
		Font button = new Font("Consolas", Font.PLAIN,40);
		g.setFont(title);
		g.drawString("YOU WIN",250,375);

		int xPos = 600;
		int yPosHome = 450;

		g.setFont(button);

		g.setColor(Color.green);
		g.fillRect(xPos, yPosHome, 150, 75);

		g.setColor(Color.BLACK);
		g.drawString("HOME", xPos+28, yPosHome+50);
	}

	public void endScreenLose(Graphics g)
	{
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 1400, 750);

		g.setColor(Color.BLACK);
		Font title = new Font("Consolas",Font.BOLD,200);
		Font button = new Font("Consolas", Font.PLAIN,40);
		g.setFont(title);
		g.drawString("YOU LOSE",260,375);

		int xPos = 600;
		int yPosHome = 450;

		g.setFont(button);

		g.setColor(Color.green);
		g.fillRect(xPos, yPosHome, 150, 75);

		g.setColor(Color.BLACK);
		g.drawString("HOME", xPos+28, yPosHome+50);
	}

	public void rulesScreen(Graphics g)
	{
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, 1400, 750);

		g.setColor(Color.BLACK);
		Font title = new Font("Consolas",Font.BOLD,200);
		Font button = new Font("Consolas", Font.PLAIN,40);
		g.setFont(title);
		g.drawString("YOU LOSE",260,375);
	}

	public void compGuess(Graphics g)
	{
		Font font = new Font("Consolas", Font.PLAIN,30);
		g.setFont(font);

		int guessValue = game.getComputerGuessValue();
		int guessIndex = game.getComputerGuessIndex();

		int wPan = 75;
		int hPan = 100;

		Point p = userPanelPoints.get(guessIndex);

		int xPos = (int)(p.getX()+(wPan/2))-20;
		int yPos = (int)(p.getY()+hPan+20);

		g.setColor(Color.green);
		g.fillRect(xPos,yPos,40,40);
		String num = Integer.toString(guessValue);
		g.setColor(Color.black);
		
		if(guessValue<10)
			g.drawString(num, xPos+10, yPos+30);
		else
			g.drawString(num, xPos+2, yPos+30);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int xPos = e.getX();
		int yPos = e.getY();

		int compPanelH = 80;
		int compPanelW = 60;

		//FOR END_WIN AND END_LOSE
		int xPosButton = 600;
		int yPosButton = 450;
		int wButt = 150;
		int hButt = 75;

		switch(page)
		{
		case HOME:
			int xPosButt = 600;
			int yPosPlay = 450;
			int yPosRules = 550;
			int wButton = 150;
			int hButton = 75;

			if(xPos> xPosButt && xPos<xPosButt +wButton && yPos>yPosPlay && yPos<yPosPlay+hButton)
			{
				System.out.println("game");
				page = GamePage.GAME;
				repaint();
				break;
			}
			/*else if(xPos> xPosButt && xPos<xPosButt +wButton && yPos>yPosRules && yPos<yPosRules+hButton)
			{
				System.out.println("rules");
				page = GamePage.RULES;
				break;
			}*/
			break;

		case END_WIN:

			if(xPos> xPosButton && xPos<xPosButton +wButt && yPos>yPosButton && yPos<yPosButton+hButt)
			{
				System.out.println("home");
				page = GamePage.HOME;
			}
			break;

		case END_LOSE:

			if(xPos> xPosButton && xPos<xPosButton +wButt && yPos>yPosButton && yPos<yPosButton+hButt)
			{
				System.out.println("home");
				page = GamePage.HOME;
			}
			break;

		case GAME:
			switch(state)
			{
			case PLR_CHOOSE_PANEL:
				for(int i = 0; i<compPanelPoints.size(); i++)
				{
					if(xPos>compPanelPoints.get(i).getX() && xPos<compPanelPoints.get(i).getX()+compPanelW && yPos>compPanelPoints.get(i).getY() && yPos<compPanelPoints.get(i).getY()+compPanelH)
					{
						chooseValueIndex = i;
						state = game.playerPickGuessPanel(i);

						System.out.println("index of guessed panel " + i);

						break;
					}
				}
				break;

			case PLR_DRAW:
				int blackX = 720;
				int y = 100;
				int whiteX = 630;

				if(xPos> blackX && xPos<blackX +compPanelW && yPos>y && yPos<y+compPanelH)
				{
					state = game.playerDrawPanel(false);
				}
				else if(xPos> whiteX && xPos<whiteX +compPanelW && yPos>y && yPos<y+compPanelH)
				{
					state = game.playerDrawPanel(true);
				}
				break;

			case PLR_CHOOSE_GO_AGAIN:
				int yesX = 630;
				int y2 = 100;
				int noX = 720;

				if(xPos> yesX && xPos<yesX +30 && yPos>y2 && yPos<y2+60)
				{
					state = game.playerGoAgain(true);
				}
				else if(xPos> noX && xPos<noX +60 && yPos>y2 && yPos<y2+30)
				{
					state = game.playerGoAgain(false);
				}
				System.out.println(state);
				break;

			case PLR_CHOOSE_VALUE:
				System.out.println(game.getGame().getCompHand());
				int butt = 30;
				for(int i = 0; i<valueGuessPoints.size(); i++)
				{
					if(xPos>valueGuessPoints.get(i).getX() && xPos<valueGuessPoints.get(i).getX()+butt && yPos>valueGuessPoints.get(i).getY() && yPos<valueGuessPoints.get(i).getY()+butt)
					{
						state = game.playerGuessValue(i);
						break;
						//System.out.println("Player guesses " + i);
					}
				}
				break;

			default:
				//repaint();

			}
		default:
			repaint();
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
