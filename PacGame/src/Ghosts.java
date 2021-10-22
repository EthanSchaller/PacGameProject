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
	private Walls[] Wall;
	private JButton StartBttn;
	
	int GhNum = 0;
	int wallNum = 0;
	String[] mvDir = new String[4];
	
	//setting up getters
	public Boolean getVisible() {return Vis;}
	public Boolean getMove() {return Move;}
	
	//setting up setters
	public void setVisible(Boolean temp) {this.Vis = temp;}
	public void setMoving(Boolean temp) {this.Move = temp;}
	public void setPac(Pacman temp) {this.Pac = temp;}
	public void setWalls(Walls[] temp) {this.Wall = temp;}
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
	
	public int[] shdwMv(int[] GXY, int[] PXY) {
		
		//moving the ghosts depending on the position of pacman
		if(GXY[1] > PXY[1]) {
			GXY[1] -= GameProps.GHST_STEP /2;
		} else if(GXY[1] < PXY[1]) {
			GXY[1] += GameProps.GHST_STEP /2;
		}
		
		if(GXY[2] > PXY[2]) {
			GXY[2] -= 1;
		} else if(GXY[2] < PXY[2]) {
			GXY[2] += GameProps.GHST_STEP /2;
		}
		
		if(GXY[1] > GameProps.SCREEN_WIDTH - this.width) {
			GXY[1] -= this.width - GameProps.GHST_STEP /2;
		} else if(GXY[1] < 0) {
			GXY[1] += this.width + GameProps.GHST_STEP /2;
		}
		
		if(GXY[2] > GameProps.SCREEN_HEIGHT - this.height) {
			GXY[2] -= this.height - GameProps.GHST_STEP /2;
		} else if(GXY[2] < 0) {
			GXY[2] += this.height + GameProps.GHST_STEP /2;
		}
		
		return GXY;
	}
	
	public int[] prmMv(int[] GXY, String mv) {
		if(mv == "U") {
			GXY[2] -= GameProps.GHST_STEP;
		} else if(mv == "D") {
			GXY[2] += GameProps.GHST_STEP;
		} else if(mv == "L") {
			GXY[1] -= GameProps.GHST_STEP;
		} else if(mv == "R") {
			GXY[1] += GameProps.GHST_STEP;
		}
		
		for(int i = 1; i <= 13; i++) {
			if(this.getRect().intersects(Wall[i].getRect())) {
				if(mv == "U") {
					GXY[2] += GameProps.GHST_STEP * 2;
					mvDir[GhNum] = "L";
				} else if(mv == "D") {
					GXY[2] -= GameProps.GHST_STEP * 2;
					mvDir[GhNum] = "R";
				} else if(mv == "L") {
					if(i == 1) {
						GXY[1] = (Wall[i].getX() + Wall[i].getWidth());
					} else {
						GXY[1] += GameProps.GHST_STEP * 2;
					}
					mvDir[GhNum] = "D";
					
				} else if(mv == "R") {
					if(i == 6) {
						GXY[1] = GameProps.SCREEN_WIDTH - Wall[6].getWidth();
					} else {
						GXY[1] -= GameProps.GHST_STEP * 2;
					}
					mvDir[GhNum] = "U";
				}
			}
		}
		
		return GXY;
	}
	
	public int[] nrmMv(int[] GXY, String mv) {
		if(mv == "U") {
			GXY[2] -= GameProps.GHST_STEP;
		} else if(mv == "D") {
			GXY[2] += GameProps.GHST_STEP;
		} else if(mv == "L") {
			GXY[1] -= GameProps.GHST_STEP;
		} else if(mv == "R") {
			GXY[1] += GameProps.GHST_STEP;
		}
		
		for(int i = 1; i <= 13; i++) {
			if(this.getRect().intersects(Wall[i].getRect())) {
				if(mv == "U") {
					GXY[2] += GameProps.GHST_STEP * 2;
				} else if(mv == "D") {
					GXY[2] -= GameProps.GHST_STEP * 2;
				} else if(mv == "L") {
					if(i == 1) {
						GXY[1] = (Wall[i].getX() + Wall[i].getWidth());
					} else {
						GXY[1] += GameProps.GHST_STEP * 2;
					}
					
				} else if(mv == "R") {
					if(i == 6) {
						GXY[1] = GameProps.SCREEN_WIDTH - Wall[6].getWidth();
					} else {
						GXY[1] -= GameProps.GHST_STEP * 2;
					}
				}
				
				mvDir[GhNum] = mvSwp();
			}
		}
		
		return GXY;
	}
	
	public void run() {
		
		//starting the movement
		this.Move = true;
		
		resetPics();
		
		mvDir[1] = "U";
		mvDir[2] = "L";
		mvDir[3] = "R";
		
		//looping until the a collision happens and the ghosts stop moving
		while(Move) {
			
			//collecting the ghost's and pacman's position to variables
			int[] PacXY = new int[3];
			int[] GhostXY = new int[3];
			
			GhostXY[1] = this.x;
			GhostXY[2] = this.y;
			
			PacXY[1] = Pac.getX();
			PacXY[2] = Pac.getY();
			
			switch(GhNum) {
				case 1: GhostXY = nrmMv(GhostXY, mvDir[GhNum]);
						try {
							Thread.sleep(5);
						} catch (Exception e) {
							
						}
						break;
						
				case 2: GhostXY = nrmMv(GhostXY, mvDir[GhNum]);
						try {
							Thread.sleep(5);
						} catch (Exception e) {
							
						}
						break;
						
				case 3: GhostXY = prmMv(GhostXY, mvDir[GhNum]);
						try {
							Thread.sleep(4);
						} catch (Exception e) {
							
						}
						break;
				
				case 4:	GhostXY = shdwMv(GhostXY, PacXY);
						try {
							Thread.sleep(4);
						} catch (Exception e) {
							
						}
						break;
						
				default: break;
			}
			//setting the ghost's x and y
			this.setX(GhostXY[1]);
			this.setY(GhostXY[2]);
			
			//re-setting the ghost's location based on the x and y positions
			GhostLbl.setLocation(this.x, this.y);
			this.plyrCollision();
		}
	}
	
	//function to check for the ghosts collision with pacman
	public boolean plyrCollision() {
		boolean temp = false;
		
		if(this.rect.intersects(Pac.getRect())) {
			this.Move = false;
			StartBttn.setText("Start");
			PacLbl.setIcon(new ImageIcon(getClass().getResource("PacDth.gif")));
			PacLbl.setLocation((GameProps.SCREEN_WIDTH - Pac.getWidth())/2, (GameProps.SCREEN_HEIGHT - Pac.getHeight())/2);
			GhostLbl.setVisible(false);
			temp = true;
		}
		
		return temp;
	}
	
	public String mvSwp() {
		String s = "";
		int i = (int)(Math.random() * 6);
		
		if(this.getY() < PacLbl.getY()) {
			switch(i) {
				case 0, 4, 5: s = "U";
						   	  break;
				
				case 1: s = "D";
						break; 
				
				case 2: s = "L";
						break;
				
				case 3: s = "R";
						break;
			}
			
		} else  if(this.getX() < PacLbl.getX()) {
			switch(i) {
				case 0: s = "U";
						break;
				
				case 1: s = "D";
						break; 
				
				case 2: s = "L";
						break;
				
				case 3, 4, 5: s = "R";
						   	  break;
			}
					
		} else if(this.getY() > PacLbl.getY()) {
			switch(i) {
				case 0: s = "U";
						break;
				
				case 1, 4, 5: s = "D";
							  break; 
				
				case 2: s = "L";
						break;
				
				case 3: s = "R";
						break;
			}	
			
		} else if(this.getX() > PacLbl.getX()) {
			switch(i) {
				case 0: s = "U";
						break;
				
				case 1: s = "D";
						break; 
				
				case 2, 4, 5: s = "L";
						   	  break;
						
				case 3: s = "R";
						break;
			}
		}
		
		return s;
	}
}
