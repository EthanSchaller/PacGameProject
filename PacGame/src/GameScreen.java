import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameScreen extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 3221260907688321217L;

	//setting up variable to store pacman's info
	private Pacman Pac;
	private JLabel PacLbl;
	private ImageIcon PacImg;
	
	//setting up variable to store the pellets' info
	private Pellets[] Plt = new Pellets[530];
	private JLabel[] PltLbl = new JLabel[530];
	private ImageIcon[] PltImg = new ImageIcon[530];
	
	//setting up variable to store the ghosts' info
	private Ghosts[] Ghost = new Ghosts[5];
	private JLabel[] GhostLbl = new JLabel[5];
	private ImageIcon[] GhostImg = new ImageIcon[5];
	
	//setting up various variables to be used throughout the program
	private boolean Hit = false;
	private boolean dupeChck = false;
	public int slctIndx, slctIndx2; 
	public int posMod, posDwnShft, Score;
	
	//setting up the start button
	private JButton StartBttn;
	
	//setting up a container for the page's content
	private Container PgContent;
	
	public GameScreen() {
		super("Pac-Man");
		setSize(GameProps.SCREEN_WIDTH, GameProps.SCREEN_HEIGHT);
		
		//inputting pacman's data into the program
		PacLbl = new JLabel();
		Pac = new Pacman();
		PacImg = new ImageIcon(getClass().getResource(Pac.getFilename()));
		PacLbl.setIcon(PacImg);
		PacLbl.setSize(Pac.getWidth(), Pac.getHeight());
		
		//setting up the array of ghosts with the proper data
		for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
			GhostLbl[slctIndx] = new JLabel();
			Ghost[slctIndx] = new Ghosts(slctIndx);
			GhostImg[slctIndx] = new ImageIcon(getClass().getResource(Ghost[slctIndx].getFilename())) ;
			GhostLbl[slctIndx].setIcon(GhostImg[slctIndx]);
			GhostLbl[slctIndx].setSize(Ghost[slctIndx].getWidth(), Ghost[slctIndx].getHeight());
		}
		
		//setting up the array of pellets and power pellets with the proper data
		for(slctIndx = 1; slctIndx <= 529; slctIndx++) {
			//if the pellet is in one of the four corners then it is delared as a power pellet
			if(slctIndx == 1 || slctIndx == 23 || slctIndx == 507 || slctIndx == 529) {
				PltLbl[slctIndx] = new JLabel();
				Plt[slctIndx] = new Pellets("Power");
				PltImg[slctIndx] = new ImageIcon(getClass().getResource(Plt[slctIndx].getFilename())) ;
				PltLbl[slctIndx].setIcon(PltImg[slctIndx]);
				PltLbl[slctIndx].setSize(Plt[slctIndx].getWidth(), Plt[slctIndx].getHeight());
			
			//all other pellets are set as normal pellets
			} else {
				PltLbl[slctIndx] = new JLabel();
				Plt[slctIndx] = new Pellets();
				PltImg[slctIndx] = new ImageIcon(getClass().getResource(Plt[slctIndx].getFilename())) ;
				PltLbl[slctIndx].setIcon(PltImg[slctIndx]);
				PltLbl[slctIndx].setSize(Plt[slctIndx].getWidth(), Plt[slctIndx].getHeight());
			}
		}
		
		//loading the pages content and setting the background to black
		PgContent = getContentPane();
		PgContent.setBackground(Color.BLACK);
		
		setLayout(null);
		
		//setting pacman's starting position and adding him to the page
		Pac.setX((GameProps.SCREEN_WIDTH-Pac.getWidth())/2);
		Pac.setY(25);
		add(PacLbl);
		PacLbl.setLocation(Pac.getX(), Pac.getY());
		
		//setting the ghosts' starting positions and adding them to the page
		posMod = 0;
		
		for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
			Ghost[slctIndx].setX(25 + posMod);
			Ghost[slctIndx].setY(900);
			add(GhostLbl[slctIndx]);
			GhostLbl[slctIndx].setVisible(Ghost[slctIndx].getVisible());
			GhostLbl[slctIndx].setLocation(Ghost[slctIndx].getX(), Ghost[slctIndx].getY());
			
			posMod += 283;
		}
		
		
		//setting the pellets' starting positions and adding them to the page
		posMod = 0;
		posDwnShft = 0;
		
		for(slctIndx = 1; slctIndx <= 529; slctIndx++) {
			Plt[slctIndx].setX(25 + posMod);
			
			if(Plt[slctIndx].getX() > (967 - Plt[slctIndx].getWidth())){
				posMod = 0;
				posDwnShft += 40;
				
				Plt[slctIndx].setX(25 + posMod);
			}
			
			Plt[slctIndx].setY(25 + posDwnShft);
			add(PltLbl[slctIndx]);
			PltLbl[slctIndx].setVisible(Plt[slctIndx].getVisible());
			PltLbl[slctIndx].setLocation(Plt[slctIndx].getX(), Plt[slctIndx].getY());
			
			posMod += 40;
		}
		
		
		//setting the start button's starting position and adding it to the page
		StartBttn = new JButton("Start");
		StartBttn.setSize(100, 50);
		StartBttn.setLocation((GameProps.SCREEN_WIDTH - StartBttn.getWidth())/2, (GameProps.SCREEN_HEIGHT - StartBttn.getHeight())/2);
		add(StartBttn);
		StartBttn.addActionListener(this);
		StartBttn.setFocusable(false);
		
		//setting up the variables in the ghost's data that is set in the Ghosts java file
		for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
			Ghost[slctIndx].setGhostLbl(GhostLbl[slctIndx]);
			Ghost[slctIndx].setPac(Pac);
			Ghost[slctIndx].setPacLbl(PacLbl);
			Ghost[slctIndx].setStartBttn(StartBttn);
		}
		
		//setting pacman's data to each pellet to track collision
		for(slctIndx = 1; slctIndx <= 529; slctIndx++) {
			Plt[slctIndx].setPac(Pac);
		}
		
		//setting a listener to the page and setting it to not be focusable
		PgContent.addKeyListener(this);
		PgContent.setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		//setting up a data base to track submitted scores
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("Database Loaded");
			
			String dbURL = "JDBC:sqlite:leaderboard.db";
			conn = DriverManager.getConnection(dbURL);
			
			if(conn != null) {
				System.out.println("Database Connected");
				conn.setAutoCommit(false);
				
				stmt = conn.createStatement();
				
				String sql = "CREATE TABLE IF NOT EXISTS PLAYER" + 
							 "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
							 " NAME TEXT NOT NULL," + 
							 " SCORE INT NOT NULL)";
				stmt.executeUpdate(sql);
				conn.commit();
				System.out.println("Table Created");
				System.out.println("");
				
				System.out.println("    CURRENT LEADERBOARD");
				ResultSet rs = stmt.executeQuery("SELECT * FROM PLAYER ORDER BY SCORE DESC");
				DisplayRecords(rs);
				
				GameScreen Screen = new GameScreen();
				Screen.setVisible(true);
				
				rs.close();
				conn.close();
			}
			
		//checking for errors during the database's activities	
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void DisplayRecords(ResultSet rs) throws SQLException{
		//while loop to print out the inputed scores in the database
		int id = 1;
		while(rs.next() && id <= 5) {
			String name = rs.getString("name");
			int score = rs.getInt("score");
			
			System.out.println(" " + id + ") " + name + "  " + score);
			id++;
		}
		
		System.out.println("");
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		
		//variables to track pacman's x and y coordinates
		int PacX = Pac.getX();
		int PacY = Pac.getY();
		
		//checking if any ghost is colliding with pacman
		for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
			if(Ghost[slctIndx].plyrCollision() == true) {
				for(slctIndx2 = 1; slctIndx2 <= 4; slctIndx2++) {
					Hit = true;
					Ghost[slctIndx2].setMoving(false);
					GhostLbl[slctIndx2].setIcon(new ImageIcon(getClass().getResource("")));
				}
				
				StartBttn.setVisible(true);
				
				if(!dupeChck) {
					System.out.println(" You Lose.\r\n Your score is " + Score);
					dupeChck = true;
				}
				
				StartBttn.setVisible(true);
			}
		}
		
		//checking if any pellet is colliding with pacman
		for(slctIndx = 1; slctIndx <= 529; slctIndx++) {
			if(!Plt[slctIndx].dupe) {
				if(Plt[slctIndx].collected()) {
					Plt[slctIndx].setDupe(true);
					PltLbl[slctIndx].setVisible(false);
					Score += 100;
				}
			}
		}
		
		//making sure the game is running then allowing the player to move
		if(Hit == false && Ghost[1].getMove() == true) {
			
			//testing for the users input and moving pacman accordingly
			
			//testing for the UP and W keys
			if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
				PacY -= GameProps.CHAR_STEP;
				PacLbl.setIcon(new ImageIcon(getClass().getResource("PacU.gif")));
				if(PacY <= 25) {
					PacY = 25;
				}
			
			//testing for the DOWN and S keys
			} else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
				PacY += GameProps.CHAR_STEP;
				PacLbl.setIcon(new ImageIcon(getClass().getResource("PacD.gif")));
				if(PacY >= GameProps.SCREEN_HEIGHT - (40 + Pac.getHeight())) {
					PacY = Pac.getY();
				}
				
			//testing for the LEFT and A keys	
			} else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
				PacX -= GameProps.CHAR_STEP;
				PacLbl.setIcon(new ImageIcon(getClass().getResource("PacL.gif")));
				if(PacX <= 25) {
					PacX = 25;
				}
				
			//testing for the RIGHT and D keys	
			} else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				PacX += GameProps.CHAR_STEP;
				PacLbl.setIcon(new ImageIcon(getClass().getResource("PacR.gif")));
				if(PacX >= GameProps.SCREEN_WIDTH - (40 + Pac.getWidth())) {
					PacX = Pac.getX();
				}
			} 
		}
		
		//setting pacman's x and y
		Pac.setX(PacX);
		Pac.setY(PacY);
		
		//resetting pacman's location based on the updated x and y position
		PacLbl.setLocation(Pac.getX(), Pac.getY());
	}

	public void keyReleased(KeyEvent e) {
		
		//testing again if the user is colliding with a ghost
		if(Hit == true) {
			for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
				Ghost[slctIndx].setMoving(false);
				GhostLbl[slctIndx].setIcon(new ImageIcon(getClass().getResource("")));
			}
			
			StartBttn.setVisible(true);
		}
	}

	public void actionPerformed(ActionEvent e) {
		
		//testing if the start button was pressed
		if(e.getSource() == StartBttn) {
			StartBttn.setVisible(false);
			
			dupeChck = false;
			posMod = 0;
			Score = 0;
			Hit = false;
			
			//resetting the location of pacman at the start of the game
			Pac.setX((GameProps.SCREEN_WIDTH-Pac.getWidth())/2);
			Pac.setY(25);
			PacLbl.setLocation(Pac.getX(), Pac.getY());
			
			//resetting the location of the ghosts at the start of the game
			for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
				Ghost[slctIndx].moveGhost();
				GhostLbl[slctIndx].setVisible(true);
				Ghost[slctIndx].setX(50 + posMod);
				Ghost[slctIndx].setY(900);
				GhostLbl[slctIndx].setLocation(Ghost[slctIndx].getX(), Ghost[slctIndx].getY());
				
				posMod += 283;
			}
			
			//re-showing the pellets and resetting the check to not duplicate the points given
			for(slctIndx = 1; slctIndx <= 529; slctIndx++) {
				Plt[slctIndx].setDupe(false);
				PltLbl[slctIndx].setVisible(true);
			}
		}
	}
}