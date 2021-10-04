import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JFrame;

public class GameScreen extends JFrame implements ActionListener, KeyListener {
	
	private Pacman Pac;
	private Ghosts Ghost1, Ghost2, Ghost3, Ghost4;
	
	private boolean Hit = true;
	
	private JLabel PacLbl, Ghost1Lbl, Ghost2Lbl, Ghost3Lbl, Ghost4Lbl;
	private ImageIcon PacImg, Ghost1Img, Ghost2Img, Ghost3Img, Ghost4Img;
	
	private JButton StartBttn;
	
	private Container PgContent;
	
	public GameScreen() {
		super("Pac-Man");
		setSize(GameProps.SCREEN_WIDTH, GameProps.SCREEN_HEIGHT);
		
		PacLbl = new JLabel();
		Pac = new Pacman();
		PacImg = new ImageIcon(getClass().getResource(Pac.getFilename()));
		PacLbl.setIcon(PacImg);
		PacLbl.setSize(Pac.getWidth(), Pac.getHeight());
		
		Ghost1Lbl = new JLabel();
		Ghost1 = new Ghosts();
		Ghost1Img = new ImageIcon(getClass().getResource(Ghost1.getFilename()));
		Ghost1Lbl.setIcon(Ghost1Img);
		Ghost1Lbl.setSize(Ghost1.getWidth(), Ghost1.getHeight());
		
		Ghost2Lbl = new JLabel();
		Ghost2 = new Ghosts(1);
		Ghost2Img = new ImageIcon(getClass().getResource(Ghost2.getFilename()));
		Ghost2Lbl.setIcon(Ghost2Img);
		Ghost2Lbl.setSize(Ghost2.getWidth(), Ghost2.getHeight());
		
		Ghost3Lbl = new JLabel();
		Ghost3 = new Ghosts('a');
		Ghost3Img = new ImageIcon(getClass().getResource(Ghost3.getFilename()));
		Ghost3Lbl.setIcon(Ghost3Img);
		Ghost3Lbl.setSize(Ghost3.getWidth(), Ghost3.getHeight());
		
		Ghost4Lbl = new JLabel();
		Ghost4 = new Ghosts("a");
		Ghost4Img = new ImageIcon(getClass().getResource(Ghost4.getFilename()));
		Ghost4Lbl.setIcon(Ghost4Img);
		Ghost4Lbl.setSize(Ghost4.getWidth(), Ghost4.getHeight());
		
		PgContent = getContentPane();
		PgContent.setBackground(Color.BLACK);
		
		setLayout(null);
		
		Pac.setX((GameProps.SCREEN_WIDTH-Pac.getWidth())/2);
		Pac.setY((GameProps.SCREEN_HEIGHT-Pac.getHeight())/2);
		
		Ghost1.setX(50);
		Ghost1.setY(50);
		
		Ghost2.setX(50);
		Ghost2.setY(900);
		
		Ghost3.setX(900);
		Ghost3.setY(900);
		
		Ghost4.setX(900);
		Ghost4.setY(50);
		
		add(PacLbl);
		add(Ghost1Lbl);
		add(Ghost2Lbl);
		add(Ghost3Lbl);
		add(Ghost4Lbl);
		
		Ghost1Lbl.setVisible(Ghost1.getVisible());
		Ghost2Lbl.setVisible(Ghost2.getVisible());
		Ghost3Lbl.setVisible(Ghost3.getVisible());
		Ghost4Lbl.setVisible(Ghost4.getVisible());
		
		PacLbl.setLocation(Pac.getX(), Pac.getY());
		Ghost1Lbl.setLocation(Ghost1.getX(), Ghost1.getY());
		Ghost2Lbl.setLocation(Ghost2.getX(), Ghost2.getY());
		Ghost3Lbl.setLocation(Ghost3.getX(), Ghost3.getY());
		Ghost4Lbl.setLocation(Ghost4.getX(), Ghost4.getY());
		
		StartBttn = new JButton("Start");
		StartBttn.setSize(100, 50);
		StartBttn.setLocation((GameProps.SCREEN_WIDTH - StartBttn.getWidth())/2, (GameProps.SCREEN_HEIGHT - StartBttn.getHeight())/2);
		add(StartBttn);
		StartBttn.addActionListener(this);
		StartBttn.setFocusable(false);
		
		Ghost1.setGhostLbl(Ghost1Lbl);
		Ghost1.setPac(Pac);
		Ghost1.setPacLbl(PacLbl);
		Ghost1.setStartBttn(StartBttn);
		
		Ghost2.setGhostLbl(Ghost2Lbl);
		Ghost2.setPac(Pac);
		Ghost2.setPacLbl(PacLbl);
		Ghost2.setStartBttn(StartBttn);
		
		Ghost3.setGhostLbl(Ghost3Lbl);
		Ghost3.setPac(Pac);
		Ghost3.setPacLbl(PacLbl);
		Ghost3.setStartBttn(StartBttn);
		
		Ghost4.setGhostLbl(Ghost4Lbl);
		Ghost4.setPac(Pac);
		Ghost4.setPacLbl(PacLbl);
		Ghost4.setStartBttn(StartBttn);
		
		PgContent.addKeyListener(this);
		PgContent.setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		GameScreen Screen = new GameScreen();
		Screen.setVisible(true);
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		int PacX = Pac.getX();
		int PacY = Pac.getY();
		
		if(Ghost1.plyrCollision() == true || Ghost2.plyrCollision() == true || Ghost3.plyrCollision() == true || Ghost4.plyrCollision() == true) {
			Hit = true;
			Ghost1.setMoving(false);
			Ghost2.setMoving(false);
			Ghost3.setMoving(false);
			Ghost4.setMoving(false);
			
			StartBttn.setVisible(true);
		}
		
		if(Hit == false) {
			if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
				PacY -= GameProps.CHAR_STEP;
				PacLbl.setIcon(new ImageIcon(getClass().getResource("PacU.gif")));
				if(PacY <= GameProps.CHAR_STEP) {
					PacY = Pac.getY();
				}
				
			} else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
				PacY += GameProps.CHAR_STEP;
				PacLbl.setIcon(new ImageIcon(getClass().getResource("PacD.gif")));
				if(PacY >= GameProps.SCREEN_HEIGHT - GameProps.CHAR_STEP - Pac.getHeight()) {
					PacY = Pac.getY();
				}
				
			} else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
				PacX -= GameProps.CHAR_STEP;
				PacLbl.setIcon(new ImageIcon(getClass().getResource("PacL.gif")));
				if(PacX <= GameProps.CHAR_STEP) {
					PacX = Pac.getX();
				}
				
			} else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				PacX += GameProps.CHAR_STEP;
				PacLbl.setIcon(new ImageIcon(getClass().getResource("PacR.gif")));
				if(PacX >= GameProps.SCREEN_WIDTH - GameProps.CHAR_STEP - Pac.getWidth()) {
					PacX = Pac.getX();
				}
			} 
		}
		
		Pac.setX(PacX);
		Pac.setY(PacY);
		
		PacLbl.setLocation(Pac.getX(), Pac.getY());
	}

	public void keyReleased(KeyEvent e) {
		if(Hit == true) {
			Ghost1.setMoving(false);
			Ghost2.setMoving(false);
			Ghost3.setMoving(false);
			Ghost4.setMoving(false);
			
			StartBttn.setVisible(true);
		}
	}

	public void actionPerformed(ActionEvent e) {
			if(e.getSource() == StartBttn) {
				if(!Ghost1.getMove()) {
					StartBttn.setVisible(false);
					
					Ghost1.moveGhost();
					Ghost2.moveGhost();
					Ghost3.moveGhost();
					Ghost4.moveGhost();
					
					Pac.setX((GameProps.SCREEN_WIDTH-Pac.getWidth())/2);
					Pac.setY((GameProps.SCREEN_HEIGHT-Pac.getHeight())/2);
					PacLbl.setLocation(Pac.getX(), Pac.getY());
					
					Ghost1.setX(50);
					Ghost1.setY(50);
					Ghost1Lbl.setLocation(Ghost1.getX(), Ghost1.getY());
					
					Ghost2.setX(50);
					Ghost2.setY(900);
					Ghost2Lbl.setLocation(Ghost2.getX(), Ghost2.getY());
					
					Ghost3.setX(900);
					Ghost3.setY(900);
					Ghost3Lbl.setLocation(Ghost3.getX(), Ghost3.getY());
					
					Ghost4.setX(900);
					Ghost4.setY(50);
					Ghost4Lbl.setLocation(Ghost4.getX(), Ghost4.getY());
					
					Hit = false;
				}
			}
		
	}
}
