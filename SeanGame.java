import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.Color;

public class SeanGame {

	private static int cordX,cordY = 200;
	private static int difficulty = 3;
	private static int foodCount,score = 0;
	private static int foodCordX,foodCordY,barrierCordX,barrierCordY,switchCordX,switchCordY;
	private static boolean switchClicked = false;
	private static int timer = -250;
	private static Random r = new Random();
	private static boolean wLoop = true;
	public static JFrame ControlFrame = new JFrame("Controller");

	public static void main(String[] args) throws InterruptedException {
		controlScreen();
		gameScreen();
	}
	
	public static void controlScreen() {
		System.out.println("[SYSTEM]\tOpening control screen...");
		
		//initializing frame and panel
		ControlFrame.setVisible(true);
		ControlFrame.setSize(300,300);
		ControlFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		JPanel ControlPanel = new JPanel();
		ControlPanel.setLayout(null);
		ControlFrame.add(ControlPanel);
		
		//buttons
		System.out.println("[SYSTEM]\tGenerating buttons...");
		JButton ButtonUp = new JButton("Up");
		JButton ButtonDown = new JButton("Down");
		JButton ButtonRight = new JButton("Right");
		JButton ButtonLeft = new JButton("Left");
		ButtonUp.setSize(70,40);
		ButtonUp.setLocation(115,25);
		ButtonDown.setSize(70,40);
		ButtonDown.setLocation(115,135);
		ButtonRight.setSize(70,40);
		ButtonRight.setLocation(157,80);
		ButtonLeft.setSize(70,40);
		ButtonLeft.setLocation(72,80);
		
		//up action
		ButtonUp.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	if(cordY > 0)
		    		cordY-=100;
		        System.out.println("[BUTTON]\tUp button activated");
		    }
		});
		//down action
		ButtonDown.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	if(cordY < 400)
		        	cordY+=100;
		        System.out.println("[BUTTON]\tDown button activated");
		    }
		});
		//right action
		ButtonRight.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	if(cordX < 400)
		    		cordX+=100;
		        System.out.println("[BUTTON]\tRight button activated");
		    }
		});
		//left action
		ButtonLeft.addActionListener( new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	if(cordX > 0)
		        	cordX-=100;
		        System.out.println("[BUTTON]\tLeft button activated");
		    }
		});
		
		//add buttons
		ControlPanel.add(ButtonUp);
		ControlPanel.add(ButtonDown);
		ControlPanel.add(ButtonRight);
		ControlPanel.add(ButtonLeft);
		System.out.println("[SYSTEM]\tButtons successfully generated");
		System.out.println("[SYSTEM]\tControl screen successfully opened");
	}
	
	public static void gameScreen() throws InterruptedException {
		System.out.println("[SYSTEM]\tOpening game screen...");

		//make frame
		JFrame MenuFrame = new JFrame("Sean Game");
		MenuFrame.setVisible(true);
		MenuFrame.setSize(500,520);
		MenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		System.out.println("[SYSTEM]\tGame screen successfully opened");

		//gameplay
		while(wLoop) {
			timer++;
			JPanel MenuPanel = new JPanel() {
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					
					//background
					g.setColor(Color.BLACK);
	                g.fillRect(0,0,500,520);
	                validate();
	                repaint();
	                
	                //spawning items
	                if(timer == difficulty) {
	                	
	                	//food
	                	foodCount++;
	                	timer = 0;
	                	foodCordX = r.nextInt(5)*100;
	                	foodCordY = r.nextInt(5)*100;
	                	while(true) {
	                		if(foodCordX == cordX && foodCordY == cordY) {
	                			foodCordX = r.nextInt(5)*100;
	    	                	foodCordY = r.nextInt(5)*100;
	                		} else
	                			break;
	                	}
	                	System.out.println("[FOOD]\tFood spawned in X:" + foodCordX + " Y:" + foodCordY);
	                	g.setColor(Color.ORANGE);
	                	g.fillOval(foodCordX+40,foodCordY+40,20,20);
	                	
	                	//barrier
	                	barrierCordX = r.nextInt(5)*100;
	                	barrierCordY = r.nextInt(5)*100;
	                	while(true) {
	                		if((barrierCordX == cordX && barrierCordY == cordY) || (barrierCordX == foodCordX && barrierCordY == foodCordY)) {
	                			barrierCordX = r.nextInt(5)*100;
	    	                	barrierCordY = r.nextInt(5)*100;
	                		} else
	                			break;
	                	}
	                	System.out.println("[BARRIER]\tBarrier spawned in X:" + barrierCordX + " Y:" + barrierCordY);
	                	g.setColor(Color.RED);
	                	g.fillOval(barrierCordX,barrierCordY,100,100);
	                	
	                	//switch
	                	switchCordX = r.nextInt(5)*100;
	                	switchCordY = r.nextInt(5)*100;
	                	while(true) {
	                		if((switchCordX == cordX && switchCordY == cordY) || (switchCordX == foodCordX && switchCordY == foodCordY) || (switchCordX == barrierCordX && switchCordY == barrierCordY)) {
	                			switchCordX = r.nextInt(5)*100;
	    	                	switchCordY = r.nextInt(5)*100;
	                		} else
	                			break;
	                	}
	                	System.out.println("[SWITCH]\tSwitch spawned in X:" + switchCordX + " Y:" + switchCordY);
	                	g.setColor(Color.LIGHT_GRAY);
	                	g.fillRect(switchCordX+30,switchCordY+30,40,40);
	                }
	                
	                //maintaining item textures
	                if(foodCount > 0) {
	                	if(!switchClicked)
	                		g.setColor(Color.ORANGE);
	                	if(switchClicked)
	                		g.setColor(Color.GREEN);
	                	g.fillOval(foodCordX+40,foodCordY+40,20,20);
	                	g.setColor(Color.RED);
	                	g.fillOval(barrierCordX,barrierCordY,100,100);
	                	if(!switchClicked)
	                		g.setColor(Color.LIGHT_GRAY);
	                	if(switchClicked)
	                		g.setColor(Color.yellow);
	                	g.fillRect(switchCordX+30,switchCordY+30,40,40);
	                }
	                
	                //detecting if switch is on
	                if(cordX == switchCordX && cordY == switchCordY) {
	                	switchClicked = true;
	                	System.out.println("[SWITCH]\tSwitch turned on");
	                }
	                
	                //detecting if food is eaten
	                if(cordX == foodCordX && cordY == foodCordY && foodCount > 0 && switchClicked) {
	                	g.setColor(Color.BLACK);
	                	g.fillRect(foodCordX,foodCordY,100,100);
	                	g.fillRect(barrierCordX,barrierCordY,100,100);
	                	g.fillRect(switchCordX,switchCordY,100,100);
	                	foodCount--;
	                	score++;
	                	switchClicked = false;
	                	System.out.println("[FOOD]\tFood eaten");
	                }
	                
	                //sean
	            	g.setColor(Color.WHITE);
	            	g.fillOval(cordX+17,cordY+20,66,60);
	            	g.setColor(Color.BLUE);
	            	g.fillOval(cordX+25,cordY+35,20,10);
	            	g.fillOval(cordX+55,cordY+35,20,10);
	            	g.drawLine(cordX+45,cordY+42,cordX+55,cordY+42);
	            	g.setColor(Color.WHITE);
	            	g.fillOval(cordX+27,cordY+37,16,6);
	            	g.fillOval(cordX+57,cordY+37,16,6);
	            	g.setColor(Color.BLACK);
	            	g.fillRect(cordX+30,cordY+40,10,2);
	            	g.fillRect(cordX+60,cordY+40,10,2);
	            	g.setColor(Color.RED);
	            	g.fillRect(cordX+40,cordY+60,20,3);
	            	validate();
	                repaint();
	                
	                //sean eating animation
	                if(timer >= 185 && timer < 200 || timer >= 145 && timer < 160 || timer >= 115 && timer < 130 || timer >= 85 && timer < 100 || timer >= 45 && timer < 60 || timer >= 15 && timer < 30) {
		                g.setColor(Color.WHITE);
		            	g.fillOval(cordX+20,cordY+20,60,60);
		            	g.setColor(Color.BLUE);
		            	g.fillOval(cordX+25,cordY+35,20,10);
		            	g.fillOval(cordX+55,cordY+35,20,10);
		            	g.drawLine(cordX+45,cordY+42,cordX+55,cordY+42);
		            	g.setColor(Color.WHITE);
		            	g.fillOval(cordX+27,cordY+37,16,6);
		            	g.fillOval(cordX+57,cordY+37,16,6);
		            	g.setColor(Color.BLACK);
		            	g.fillRect(cordX+30,cordY+40,10,2);
		            	g.fillRect(cordX+60,cordY+40,10,2);
		            	g.setColor(Color.RED);
		            	g.fillRect(cordX+40,cordY+58,20,7);
		            	validate();
		                repaint();
	                }
	                
	                //game over conditions
	                if(foodCount > 1 || (foodCount > 0 && barrierCordX == cordX && barrierCordY == cordY)) {
	                	wLoop = false;
	                	g.setColor(Color.WHITE);
		            	g.fillOval(cordX+20,cordY+20,60,60);
		            	g.setColor(Color.BLUE);
		            	g.fillOval(cordX+25,cordY+35,20,10);
		            	g.fillOval(cordX+55,cordY+35,20,10);
		            	g.drawLine(cordX+45,cordY+42,cordX+55,cordY+42);
		            	g.setColor(Color.WHITE);
		            	g.fillOval(cordX+27,cordY+37,16,6);
		            	g.fillOval(cordX+57,cordY+37,16,6);
		            	g.setColor(Color.BLACK);
		            	g.drawLine(cordX+30,cordY+35,cordX+40,cordY+45);
		            	g.drawLine(cordX+30,cordY+45,cordX+40,cordY+35);
		            	g.drawLine(cordX+60,cordY+35,cordX+70,cordY+45);
		            	g.drawLine(cordX+60,cordY+45,cordX+70,cordY+35);
		            	g.setColor(Color.RED);
		            	g.fillRect(cordX+40,cordY+58,20,17);
		            	validate();
		                repaint();
	                }
	                
	                //stats display
	                g.setColor(Color.CYAN);
	                g.drawString("Score: " + score,10,15);
	                g.drawString("Time: " + timer + "/" + difficulty,10,30);
	                g.drawString("Level: " + (int)(score/10)+1,10,45);
	                validate();
	                repaint();
				}
			};
			MenuFrame.add(MenuPanel);
			MenuPanel.validate();
			MenuPanel.repaint();
			
			//miscellaneous
			if(timer == 100 * difficulty)
				timer = 0;
			if(foodCount > 1) {
				wLoop = false;
				break;
			}
			difficulty = 500 - ((int)(score/10))*15;
			Thread.sleep(20);
		}
		System.out.println("[SYSTEM]\tGame ended");
		ControlFrame.setVisible(false);
	}
}