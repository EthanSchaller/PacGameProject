import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class Ghosts extends Sprite implements Runnable{
	private Boolean Vis, Move;
	private JLabel Ghost1Lbl;
	private JLabel PacLbl;
	private Thread t1;
	private Pacman Pac;
	private JButton StartBttn;
	
	private int Score = 0;
	
	public Boolean getVisible() {return Vis;}
	public Boolean getMove() {return Move;}
	
	public void setVisible(Boolean temp) {this.Vis = temp;}
	public void setMoving(Boolean temp) {this.Move = temp;}
	
	public void setPac(Pacman temp) {this.Pac = temp;}
	public void setGhostLbl(JLabel temp) {this.Ghost1Lbl = temp;}
	public void setPacLbl(JLabel temp) {this.PacLbl = temp;}
	public void setStartBttn(JButton temp) {this.StartBttn = temp;}
	
	public Ghosts() {
		super(50, 50, "GhostR.gif");
		this.Vis = true;
		this.Move = false;
	}
	
	public Ghosts(int i) {
		super(50, 50, "GhostB.gif");
		this.Vis = true;
		this.Move = false;
	}
	
	public Ghosts(char c) {
		super(50, 50, "GhostP.gif");
		this.Vis = true;
		this.Move = false;
	}
	
	public Ghosts(String s) {
		super(50, 50, "GhostO.gif");
		this.Vis = true;
		this.Move = false;
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
	
	public void moveGhost() {
		t1 = new Thread(this, "Ghost Thread");
		t1.start();
	}
	
	public void resetPics() {
		Ghost1Lbl.setIcon(new ImageIcon(getClass().getResource(getFilename())));
		PacLbl.setIcon(new ImageIcon(getClass().getResource("PacR.gif")));
	}
	
	public void run() {
		this.Move = true;
		
		resetPics();
		
		while(Move) {
			Score += 100;
			int GhostX = this.x;
			int GhostY = this.y;
			
			int PacX = Pac.getX();
			int PacY = Pac.getY();
			
			if(GhostX > PacX) {
				GhostX -= GameProps.CHAR_STEP + 10;
			} else if(GhostX < PacX) {
				GhostX += GameProps.CHAR_STEP + 10;
			}
			
			if(GhostY > PacY) {
				GhostY -= GameProps.CHAR_STEP + 10;
			}else if(GhostY < PacY) {
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
			
			this.setX(GhostX);
			this.setY(GhostY);
			
			Ghost1Lbl.setLocation(this.x, this.y);
			this.plyrCollision();
			
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				
			}
		}
	}
	
	public boolean plyrCollision() {
		boolean temp = false;
		
		if(this.rect.intersects(Pac.getRectangle())) {
			if(Score != 0) {
				System.out.println("You lose. Your score was " + Score);
			}
			Score = 0;
			this.Move = false;
			StartBttn.setText("Start");
			Ghost1Lbl.setIcon(new ImageIcon(getClass().getResource("GhostV.gif")));
			PacLbl.setIcon(new ImageIcon(getClass().getResource("PacDth.gif")));
			temp = true;
		}
		
		return temp;
	}
}
