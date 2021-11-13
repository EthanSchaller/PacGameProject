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
	boolean Vul = false;
	boolean Eaten = false;
	int eatTimer = 1000;
	
	//making the PacMan and Ghosts filenames a variable so that it can be easily changed
	String PacDth = "MainFolder/PacDth.gif";
	
	String GhostR = "MainFolder/GhostR.gif";
	String GhostB = "MainFolder/GhostB.gif";
	String GhostP = "MainFolder/GhostP.gif";
	String GhostO = "MainFolder/GhostO.gif";
	String GhostV = "MainFolder/GhostV.gif";
	String GhostEat = "MainFolder/GhostEat.png";
	
	//setting up getters
	public Boolean getVisible() {return Vis;}
	public Boolean getMove() {return Move;}
	public Boolean getEaten() {return Eaten;}
	
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
		super(50, 50, "MainFolder/GhostR.gif");
		this.Vis = true;
		this.Move = false;
	}
	
	//setting up a Ghosts class that allows for a variable to be entered
	public Ghosts(int i) {
		super(50, 50, "MainFolder/GhostR.gif");
		this.Vis = true;
		this.Move = false;
		GhNum = i;
		
		//taking the entered variable to set the right ghost's color
		switch(GhNum) {
			case 1: this.setFilename(GhostR);
					break;
			case 2: this.setFilename(GhostB);
					break;
			case 3: this.setFilename(GhostP);
					break;
			case 4: this.setFilename(GhostO);
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
		GhostR = "MainFolder/GhostR.gif";
		GhostB = "MainFolder/GhostB.gif";
		GhostP = "MainFolder/GhostP.gif";
		GhostO = "MainFolder/GhostO.gif";
		GhostV = "MainFolder/GhostV.gif";
		GhostEat = "MainFolder/GhostEat.png";
	}
	
	public void setVulPic(int i, String a, String b, String c, String d) {
		switch(i) {
			case 1: GhostV = a;
					break;
			case 2: GhostV = b;
					break;
			case 3: GhostV = c;
					break;
			case 4: GhostV = d;
					break;
			default: GhostV = "MainFolder/GhostV.gif";
					 break;
		}
	}
	
	public void setEatPic(int i, String a, String b, String c, String d) {
		switch(i) {
		case 1: GhostEat = a;
				break;
		case 2: GhostEat = b;
				break;
		case 3: GhostEat = c;
				break;
		case 4: GhostEat = d;
				break;
		default: GhostEat = "MainFolder/GhostEat.gif";
				 break;
	}
	}
	
	public void eePics(int i, String a, String b, String c, String d, String e) {
		switch(i) {
			case 1: GhostR = a;
					GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostR)));
					break;
			case 2: GhostB = b;
					GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostB)));
					break;
			case 3: GhostP = c;
					GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostP)));
					break;
			case 4: GhostO = d;
					GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostO)));
					break;
			default: GhostLbl.setIcon(new ImageIcon(getClass().getResource(getFilename())));
					 break;
		}
		
		PacDth = e;
	}
	
	public void ghVlnrbl() {
		GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostV)));
		Vul = true;
	}
	
	public void ghEaten(int i) {
		GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostEat)));
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
	
	public int[] nrmMv(int[] GXY, String mv, int timer) {
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
				
				
				mvDir[GhNum] = mvSwp(GXY);
			}
		}
		if(timer == 0) {
			mvDir[GhNum] = mvSwp(GXY);
		}
		
		return GXY;
	}
	
	public int[] eatMv(int[] GXY) {
		
		//moving the ghosts to the center of the screen(somewhat, but kind of janky)
		if(GXY[1] > (GameProps.SCREEN_WIDTH - this.getWidth())/2) {
			GXY[1] -= GameProps.GHST_STEP * 2;
		} else if(GXY[1] < (GameProps.SCREEN_WIDTH - this.getWidth())/2) {
			GXY[1] += GameProps.GHST_STEP * 2;
		}
		
		if(GXY[2] > GameProps.SCREEN_HEIGHT/2 - 222) {
			GXY[2] -= GameProps.GHST_STEP * 2;
		} else if(GXY[2] < GameProps.SCREEN_HEIGHT/2 - 222) {
			GXY[2] += GameProps.GHST_STEP * 2;
		}
		
		return GXY;
	}
	
	public void run() {
		//starting the movement
		this.Move = true;
		
		eePics(GhNum, GhostR, GhostB, GhostP, GhostO, PacDth);
		
		mvDir[1] = "U";
		mvDir[2] = "L";
		mvDir[3] = "R";
		int intrnlTimer = 100;
		int pPelTimer = 2000;
		eatTimer = 1000;
		Eaten = false;
		
		//looping until the a collision happens and the ghosts stop moving
		while(Move) {
			//collecting the ghost's and pacman's position to variables
			int[] PacXY = new int[3];
			int[] GhostXY = new int[3];
			
			GhostXY[1] = this.x;
			GhostXY[2] = this.y;
			
			PacXY[1] = Pac.getX();
			PacXY[2] = Pac.getY();
			
			if(Vul) {
				ghVlnrbl();
			}
			
			if(Pac.addTime) {
				pPelTimer = 2000;
				Pac.addTime = false;
				if(!Eaten) {
					Vul = true;
				}
			}
			
			//checking if pacman eat a power pellet recently or not
			if(Pac.powerPac) {
				//if the pPellet timer is lower than 0 then the timer is set back to 2000, the powerPac variable is set to false, and the Vulnerable variable is set to false
				if(pPelTimer < 0) {
					pPelTimer = 2000;
					Pac.powerPac = false;
					Vul = false;
					
				//if the pPellet timer is above 0 then it is lowered by 1
				} else {
					pPelTimer--;
				}
			
			//if the ghost is not eaten and the powerPac variable is false, the ghosts are set to their original gifs
			} else if(!Eaten) {
				switch(GhNum) {
					case 1: GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostR)));
							break;
					case 2: GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostB)));
							break;
					case 3: GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostP)));
							break;
					case 4: GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostO)));
							break;
					default: GhostLbl.setIcon(new ImageIcon(getClass().getResource(getFilename())));
							 break;
				}
			}
			
			//if the ghost is eaten loop through this code
			if(Eaten) {
				Vul = false;
				//testing if the timer ran out
				if(eatTimer < 0) {
					//if the timer ran out then change the ghosts Eaten variable to false and change the images back to their default
					Eaten = false;
					
					eatTimer = 1000;
					switch(GhNum) {
						case 1: GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostR)));
								break;
						case 2: GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostB)));
								break;
						case 3: GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostP)));
								break;
						case 4: GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostO)));
								break;
						default: GhostLbl.setIcon(new ImageIcon(getClass().getResource(getFilename())));
								 break;
					}
				} else {
					eatTimer--;
				}
			}
			
			//changing the movement type for the normal pattern if it is eaten or not
			if(!Eaten) {
				switch(GhNum) {
					case 1: GhostXY = nrmMv(GhostXY, mvDir[GhNum], intrnlTimer);
							try {
								Thread.sleep(5);
							} catch (Exception e) {
								
							}
							break;
							
					case 2: GhostXY = nrmMv(GhostXY, mvDir[GhNum], intrnlTimer);
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
			} else {
				GhostXY = eatMv(GhostXY);
				try {
					Thread.sleep(4);
				} catch (Exception e) {
					
				}
			} 
			
			//lowering the internal timer for the new movement system for R and B
			intrnlTimer--;
			
			//if the timer is lower than 0 it is reset to 100
			if(intrnlTimer < 0) {
				intrnlTimer = 100;
			}
			
			//setting the ghost's x and y
			this.setX(GhostXY[1]);
			this.setY(GhostXY[2]);
			
			//re-setting the ghost's location based on the x and y positions
			GhostLbl.setLocation(this.x, this.y);
			
			//check for player collision
			this.plyrCollision();
		}
	}
	
	//function to check for the ghosts collision with pacman
	public boolean plyrCollision() {
		//temp variable is created to track if a collision is found
		boolean temp = false;
		
		//testing if the ghost and pacman intersect or not
		if(this.rect.intersects(Pac.getRect())) {
			//if pacman is not eaten and if the ghost is not vulnerable run this code
			if(Vul == false && Eaten == false) {
				//ghost is stopped from moving
				this.Move = false;
				
				//start button is set to display 'Start' on it
				StartBttn.setText("Start");
				
				//pacman is set to its death animation nad set to the center of the screen
				PacLbl.setIcon(new ImageIcon(getClass().getResource(PacDth)));
				
				//ghost is hidden and the temp variable is set to true
				GhostLbl.setVisible(false);
				temp = true;
			
			//if the ghost is vulnerable run this code
			} else if(Vul) {
				//set the ghosts image to that of its eaten form
				GhostLbl.setIcon(new ImageIcon(getClass().getResource(GhostEat)));
				
				//set the Ghost to Eaten and not Vulnerable
				Eaten = true;
				Vul = false;
			} 
		}
		
		//return the temp value that represents if a collision was found
		return temp;
	}
	
	
	//movement swapper for the updated movement system, its not the best but it works
	public String mvSwp(int[] GXY) {
		String s = "";
		int i = (int)(Math.random() * 5);
		
		if(GXY[2] < PacLbl.getY()) {
			switch(i) {
				case 0, 1, 4: s = "D";
						   	   break;
				
				case 2: s = "L";
							break;
				
				case 3: s = "R";
							break;
			}
			
		} else if(GXY[2] > PacLbl.getY()) {
			switch(i) {
				case 0, 1, 4: s = "U";
							   break; 
				
				case 2: s = "L";
							break;
				
				case 3: s = "R";
							break;
			}	
			
		} else if(GXY[1] < PacLbl.getX()) {
			switch(i) {
				case 0: s = "U";
							break;
				
				case 1: s = "D";
							break; 
							
				case 2, 3, 4: s = "L";
						   	   break;
			}
					
		}  else if(GXY[1] > PacLbl.getX()) {
			switch(i) {
				case 0: s = "U";
							break;
				
				case 1: s = "D";
							break; 
				
				case 2, 3, 4: s = "R";
						   	   break;
						   	   
			}
		}
		
		return s;
	}
}
