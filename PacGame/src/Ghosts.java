import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class Ghosts extends Sprite implements Runnable{
	
	//setting up variables for various things used in through this file
	private Boolean Vis, Move;
	private JLabel GhostLbl;
	private JLabel PacLbl;
	private Thread T;
	private Pacman Pac;
	private JButton StartBttn;
	
	int GhNum = 0;
	
	//setting up getters
	public Boolean getVisible() {return Vis;}
	public Boolean getMove() {return Move;}
	
	//setting up setters
	public void setVisible(Boolean temp) {this.Vis = temp;}
	public void setMoving(Boolean temp) {this.Move = temp;}
	public void setPac(Pacman temp) {this.Pac = temp;}
	public void setGhostLbl(JLabel temp) {this.GhostLbl = temp;}
	public void setPacLbl(JLabel temp) {this.PacLbl = temp;}
	public void setStartBttn(JButton temp) {this.StartBttn = temp;}
	
	//setting default values for the Ghosts class
	public Ghosts() {
		super(50, 50, "GhostR.gif");
		this.Vis = true;
		this.Move = false;
	}
	
	//setting up a Ghosts class that allows for a variable to be entered
	public Ghosts(int i) {
		super(50, 50, "GhostR.gif");
		this.Vis = true;
		this.Move = false;
		GhNum = i;
		
		//taking the entered variable to set the right ghost's color
		switch(GhNum) {
			case 1: this.setFilename("GhostR.gif");
					break;
			case 2: this.setFilename("GhostB.gif");
					break;
			case 3: this.setFilename("GhostP.gif");
					break;
			case 4: this.setFilename("GhostO.gif");
					break;
			default: this.setFilename("");
					 break;
		}
	}
	
	public void hide() {
		this.Vis = false;
	}
	
	public void show() {
		this.Vis = true;
	}
	
	public void Display() {
		System.out.println("x, y / vis: " + this.x + ", " + this.y + " / " + this.Vis);
	}
	
	//setting up a function to start a thread for the ghosts movement
	public void moveGhost() {
		T = new Thread(this, "Ghost Thread");
		T.start();
	}
	
	//reseting the pictures
	public void resetPics() {
		GhostLbl.setIcon(new ImageIcon(getClass().getResource(getFilename())));
		PacLbl.setIcon(new ImageIcon(getClass().getResource("PacR.gif")));
	}
	
	public void eePics(int i, String a, String b, String c, String d) {
		switch(GhNum) {
			case 1: this.setFilename(a);
					break;
			case 2: this.setFilename(b);
					break;
			case 3: this.setFilename(c);
					break;
			case 4: this.setFilename(d);
					break;
			default: this.setFilename("");
					 break;
		}
	}
	
	public void run() {
		
		//starting the movement
		this.Move = true;
		
		resetPics();
		
		//looping until the a collision happens and the ghosts stop moving
		while(Move) {
			
			//collecting the ghost's and pacman's position to variables
			int GhostX = this.x;
			int GhostY = this.y;
			int PacX = Pac.getX();
			int PacY = Pac.getY();
			
			//moving the ghosts depending on the position of pacman
			if(GhostX > PacX) {
				GhostX -= GameProps.CHAR_STEP + 10;
			} else if(GhostX < PacX) {
				GhostX += GameProps.CHAR_STEP + 10;
			}
			
			if(GhostY > PacY) {
				GhostY -= GameProps.CHAR_STEP + 10;
			} else if(GhostY < PacY) {
				GhostY += GameProps.CHAR_STEP + 10;
			}
			
			if(GhostX > GameProps.SCREEN_WIDTH - this.width) {
				GhostX -= this.width - GameProps.CHAR_STEP;
			} else if(GhostX < 0) {
				GhostX += this.width + GameProps.CHAR_STEP;
			}
			
			if(GhostY > GameProps.SCREEN_HEIGHT - this.height) {
				GhostY -= this.height - GameProps.CHAR_STEP;
			} else if(GhostY < 0) {
				GhostY += this.height + GameProps.CHAR_STEP;
			}
			
			//setting the ghost's x and y
			this.setX(GhostX);
			this.setY(GhostY);
			
			//re-setting the ghost's location based on the x and y positions
			GhostLbl.setLocation(this.x, this.y);
			this.plyrCollision();
			
			try {
				Thread.sleep(150);
			} catch (Exception e) {
				
			}
		}
	}
	
	//function to check for the ghosts collision with pacman
	public boolean plyrCollision() {
		boolean temp = false;
		
		if(this.rect.intersects(Pac.getRect())) {
			this.Move = false;
			StartBttn.setText("Start");
			PacLbl.setIcon(new ImageIcon(getClass().getResource("PacDth.gif")));
			GhostLbl.setVisible(false);
			temp = true;
		}
		
		return temp;
	}
}
