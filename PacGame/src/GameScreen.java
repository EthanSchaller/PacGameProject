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
import javax.swing.JTextField;

public class GameScreen extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 3221260907688321217L;

	//setting up variable to store pacman's info
	private Pacman Pac;
	private JLabel PacLbl;
	private ImageIcon PacImg;
	
	//allowing for easier switching of pacman's animations(in the form of gifs)
	private ImageIcon PacU = new ImageIcon(getClass().getResource("PacU.gif"));
	private ImageIcon PacD = new ImageIcon(getClass().getResource("PacD.gif"));
	private ImageIcon PacR = new ImageIcon(getClass().getResource("PacR.gif"));
	private ImageIcon PacL = new ImageIcon(getClass().getResource("PacL.gif"));
	
	//setting up variable to store the pellets' info
	private Pellets[] Plt = new Pellets[392];
	private JLabel[] PltLbl = new JLabel[392];
	private ImageIcon[] PltImg = new ImageIcon[392];
	
	//setting up variable to store the ghosts' info
	private Ghosts[] Ghost = new Ghosts[5];
	private JLabel[] GhostLbl = new JLabel[5];
	private ImageIcon[] GhostImg = new ImageIcon[5];
	
	//setting up variables to store the walls info
	private Walls[] Wall = new Walls[14];
	private JLabel[] WallLbl = new JLabel[14];
	private ImageIcon[] WallImg = new ImageIcon[14];
	
	//setting up various variables to be used throughout the program
	private boolean Hit = false;
	private boolean dupeChck = false;
	public int slctIndx, slctIndx2; 
	public int posMod, posDwnShft, Score;
	public String EECode;
	
	//setting up the start button
	private JButton StartBttn, ExtraBttn, ExitBttn;
	
	//TextField for codes to be entered in for secrets
	private JTextField CodeInpt;
	
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
		for(slctIndx = 1; slctIndx <= 391; slctIndx++) {
			//if the pellet is in one of the four corners then it is declared as a power pellet
			if(slctIndx == 1 || slctIndx == 23 || slctIndx == 369 || slctIndx == 391) {
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
		
		for(slctIndx = 1; slctIndx <= 13; slctIndx++) {
			switch(slctIndx) {
				case 1, 2: WallLbl[slctIndx] = new JLabel();
						   Wall[slctIndx] = new Walls("vrtTall");
						   WallImg[slctIndx] = new ImageIcon(getClass().getResource(Wall[slctIndx].getFilename())) ;
						   WallLbl[slctIndx].setIcon(WallImg[slctIndx]);
						   WallLbl[slctIndx].setSize(Wall[slctIndx].getWidth(), Wall[slctIndx].getHeight());
						   break;
				
				case 3, 4: WallLbl[slctIndx] = new JLabel();
						   Wall[slctIndx] = new Walls("vrtShrt");
						   WallImg[slctIndx] = new ImageIcon(getClass().getResource(Wall[slctIndx].getFilename())) ;
						   WallLbl[slctIndx].setIcon(WallImg[slctIndx]);
						   WallLbl[slctIndx].setSize(Wall[slctIndx].getWidth(), Wall[slctIndx].getHeight());
						   break;
				
				case 5, 6: WallLbl[slctIndx] = new JLabel();
						   Wall[slctIndx] = new Walls("hrzWide");
						   WallImg[slctIndx] = new ImageIcon(getClass().getResource(Wall[slctIndx].getFilename())) ;
						   WallLbl[slctIndx].setIcon(WallImg[slctIndx]);
						   WallLbl[slctIndx].setSize(Wall[slctIndx].getWidth(), Wall[slctIndx].getHeight());
						   break;
				
				case 7, 8: WallLbl[slctIndx] = new JLabel();
						   Wall[slctIndx] = new Walls("hrzNrrw");
						   WallImg[slctIndx] = new ImageIcon(getClass().getResource(Wall[slctIndx].getFilename())) ;
						   WallLbl[slctIndx].setIcon(WallImg[slctIndx]);
						   WallLbl[slctIndx].setSize(Wall[slctIndx].getWidth(), Wall[slctIndx].getHeight());
						   break;
				
				case 9, 10: WallLbl[slctIndx] = new JLabel();
							Wall[slctIndx] = new Walls("vrtTiny");
							WallImg[slctIndx] = new ImageIcon(getClass().getResource(Wall[slctIndx].getFilename())) ;
							WallLbl[slctIndx].setIcon(WallImg[slctIndx]);
							WallLbl[slctIndx].setSize(Wall[slctIndx].getWidth(), Wall[slctIndx].getHeight());
							break;
							
				case 11, 12: WallLbl[slctIndx] = new JLabel();
							 Wall[slctIndx] = new Walls("hrzTiny");
							 WallImg[slctIndx] = new ImageIcon(getClass().getResource(Wall[slctIndx].getFilename())) ;
							 WallLbl[slctIndx].setIcon(WallImg[slctIndx]);
							 WallLbl[slctIndx].setSize(Wall[slctIndx].getWidth(), Wall[slctIndx].getHeight());
							 break;
							 
				case 13: WallLbl[slctIndx] = new JLabel();
				 		 Wall[slctIndx] = new Walls("mddlBrk");
				 		 WallImg[slctIndx] = new ImageIcon(getClass().getResource(Wall[slctIndx].getFilename())) ;
				 		 WallLbl[slctIndx].setIcon(WallImg[slctIndx]);
				 		 WallLbl[slctIndx].setSize(Wall[slctIndx].getWidth(), Wall[slctIndx].getHeight());
				 		 break;
						
				default: break;
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
		
		//setting the walls in their positions and adding them to the page
			Wall[1].setX(-2);
			Wall[1].setY(0);
			
			Wall[2].setX(957);
			Wall[2].setY(0);
			
			Wall[3].setX(876);
			Wall[3].setY((GameProps.SCREEN_HEIGHT - Wall[3].getHeight() - 25)/2);
			
			Wall[4].setX(76);
			Wall[4].setY((GameProps.SCREEN_HEIGHT - Wall[4].getHeight() - 25)/2);
			
			Wall[5].setX(-2);
			Wall[5].setY(0);
			
			Wall[6].setX(0);
			Wall[6].setY(957);
			
			Wall[7].setX(165);
			Wall[7].setY(95);
			
			Wall[8].setX(165);
			Wall[8].setY(GameProps.SCREEN_HEIGHT - 150);
			
			Wall[9].setX(157);
			Wall[9].setY((GameProps.SCREEN_HEIGHT - Wall[9].getHeight() - 25)/2);
			
			Wall[10].setX(797);
			Wall[10].setY((GameProps.SCREEN_HEIGHT - Wall[10].getHeight() - 25)/2);
			
			Wall[11].setX((GameProps.SCREEN_WIDTH - Wall[11].getWidth() - 16)/2);
			Wall[11].setY(200);
			
			Wall[12].setX((GameProps.SCREEN_WIDTH - Wall[12].getWidth() - 16)/2);
			Wall[12].setY(754);
			
			Wall[13].setX((GameProps.SCREEN_WIDTH - Wall[13].getWidth() - 16)/2);
			Wall[13].setY((GameProps.SCREEN_HEIGHT - Wall[13].getHeight() - 30)/2);
			
		for(slctIndx = 1; slctIndx <= 13; slctIndx++) {
			add(WallLbl[slctIndx]);
			WallLbl[slctIndx].setVisible(true);
			WallLbl[slctIndx].setLocation(Wall[slctIndx].getX(), Wall[slctIndx].getY());
		}
		
		//setting the pellets' starting positions and adding them to the page
		posMod = 0;
		posDwnShft = 0;
		int tempInt = 1;
		boolean tempCollision = false;
		
		for(slctIndx = 1; slctIndx <= 391; slctIndx++) {
			Plt[slctIndx].setX(40 + posMod);
			
			if(Plt[slctIndx].getX() > (967 - Plt[slctIndx].getWidth())){
				posMod = 0;
				posDwnShft += 55;
				
				Plt[slctIndx].setX(40 + posMod);
				
			}
	
			Plt[slctIndx].setY(40 + posDwnShft);
			
			for(tempInt = 1; tempInt <= 13; tempInt++) {
				if(Plt[slctIndx].rect.intersects(Wall[tempInt].getRect())) {
					tempCollision = true;
					break;
				} else {
					tempCollision = false;
				}
			}
			
			if(!tempCollision) {
				add(PltLbl[slctIndx]);
				PltLbl[slctIndx].setVisible(Plt[slctIndx].getVisible());
				PltLbl[slctIndx].setLocation(Plt[slctIndx].getX(), Plt[slctIndx].getY());
			}
			
			posMod += 40;
		}
		
		//setting the start button's starting position and adding it to the page
		StartBttn = new JButton("Start");
		StartBttn.setSize(100, 50);
		StartBttn.setLocation((GameProps.SCREEN_WIDTH - StartBttn.getWidth())/2, ((GameProps.SCREEN_HEIGHT - StartBttn.getHeight())/2) - 75);
		add(StartBttn);
		StartBttn.addActionListener(this);
		StartBttn.setFocusable(false);
		
		//button to go to a screen to enter codes
		ExtraBttn = new JButton("Extras");
		ExtraBttn.setSize(100, 50);
		ExtraBttn.setLocation((GameProps.SCREEN_WIDTH - ExtraBttn.getWidth())/2, (GameProps.SCREEN_HEIGHT - ExtraBttn.getHeight())/2);
		add(ExtraBttn);
		ExtraBttn.addActionListener(this);
		ExtraBttn.setFocusable(false);
		
		//button to exit the program
		ExitBttn = new JButton("Exit");
		ExitBttn.setSize(100, 50);
		ExitBttn.setLocation((GameProps.SCREEN_WIDTH - ExitBttn.getWidth())/2, ((GameProps.SCREEN_HEIGHT - ExitBttn.getHeight())/2) + 75);
		add(ExitBttn);
		ExitBttn.addActionListener(this);
		ExitBttn.setFocusable(false);
		
		//input field to take the users inputed code
		CodeInpt = new JTextField();
		CodeInpt.setSize(100, 50);
		CodeInpt.setLocation((GameProps.SCREEN_WIDTH - StartBttn.getWidth())/2, ((GameProps.SCREEN_HEIGHT - StartBttn.getHeight())/2) - 75);
		add(CodeInpt);
		CodeInpt.setVisible(false);
				
		//setting up the variables in the ghost's data that is set in the Ghosts java file
		for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
			Ghost[slctIndx].setGhostLbl(GhostLbl[slctIndx]);
			Ghost[slctIndx].setPac(Pac);
			Ghost[slctIndx].setPacLbl(PacLbl);
			Ghost[slctIndx].setStartBttn(StartBttn);
		}
		
		//setting pacman's data to each pellet to track collision
		for(slctIndx = 1; slctIndx <= 391; slctIndx++) {
			Plt[slctIndx].setPac(Pac);
		}
		
		startScreen();
		
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
				System.out.println("");
				
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
				}
				
				if(!dupeChck) {
					System.out.println(" You Lose.\r\n Your score is " + Score);
					dupeChck = true;
				}
				
				StartBttn.setVisible(true);
				ExtraBttn.setVisible(true);
				ExitBttn.setVisible(true);
				startScreen();
			}
		}
		
		//checking if any pellet is colliding with pacman
		for(slctIndx = 1; slctIndx <= 391; slctIndx++) {
			if(!Plt[slctIndx].dupe) {
				if(Plt[slctIndx].collected()) {
					Plt[slctIndx].setDupe(true);
					PltLbl[slctIndx].setVisible(false);
					Score += 1000;
				}
			}
		}
		
		//making sure the game is running then allowing the player to move
		if(Hit == false && Ghost[1].getMove() == true) {
			//testing for the users input and moving pacman accordingly
			
			//testing for the UP and W keys
			if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
				PacY -= GameProps.CHAR_STEP;
				PacLbl.setIcon(PacU);
			
			//testing for the DOWN and S keys
			} else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
				PacY += GameProps.CHAR_STEP;
				PacLbl.setIcon(PacD);
				
			//testing for the LEFT and A keys	
			} else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
				PacX -= GameProps.CHAR_STEP;
				PacLbl.setIcon(PacL);
				
			//testing for the RIGHT and D keys	
			} else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				PacX += GameProps.CHAR_STEP;
				PacLbl.setIcon(PacR);
			} 
		} else {
			for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
				Ghost[slctIndx].setMoving(false);
				GhostLbl[slctIndx].setIcon(new ImageIcon(getClass().getResource("")));
			}
			
			StartBttn.setVisible(true);
		}
		
		//setting pacman's x and y
		Pac.setX(PacX);
		Pac.setY(PacY);
		
		for(slctIndx = 1; slctIndx <= 13; slctIndx++) {
			if(Pac.getRect().intersects(Wall[slctIndx].getRect())) {
				if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
					PacY += GameProps.CHAR_STEP;
				} else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
					PacY -= GameProps.CHAR_STEP;
				} else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
					if(slctIndx == 1) {
						PacX = (Wall[1].getX() + Wall[1].getWidth());
					} else {
						PacX += GameProps.CHAR_STEP;
					}
					
				} else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if(slctIndx == 6) {
						PacX = GameProps.SCREEN_WIDTH - Wall[6].getWidth();
					} else {
						PacX -= GameProps.CHAR_STEP;
					}
				}
				
				Pac.setX(PacX);
				Pac.setY(PacY);
			}
		}
		
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
			ExtraBttn.setVisible(false);
			ExitBttn.setVisible(false);
			startScreen();
			
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
			for(slctIndx = 1; slctIndx <= 391; slctIndx++) {
				Plt[slctIndx].setDupe(false);
				PltLbl[slctIndx].setVisible(true);
			}
			
		} else if(e.getSource() == ExtraBttn) {
			if(StartBttn.isVisible()) {
				//setting the current screen state to the code screen
				codeScreen();
			
			} else if(!StartBttn.isVisible()) {
				
				//collecting the users input and testing it against the desired easter egg code
				EECode = CodeInpt.getText();
				if(EECode.equalsIgnoreCase("Billie")) {
					PacU = new ImageIcon(getClass().getResource("PacD.gif"));
					PacD = new ImageIcon(getClass().getResource("PacU.gif"));
					PacR = new ImageIcon(getClass().getResource("PacL.gif"));
					PacL = new ImageIcon(getClass().getResource("PacR.gif"));
					
					System.out.println("Code " + EECode.toUpperCase() + " was applied \r\nPacman is now moonwalking whereever he goes");
				
				} else {
					//testing if nothing or something that doesn't match a code is entered and reseting animations to normal
					if(EECode.equalsIgnoreCase("")) {
						PacU = new ImageIcon(getClass().getResource("PacU.gif"));
						PacD = new ImageIcon(getClass().getResource("PacD.gif"));
						PacR = new ImageIcon(getClass().getResource("PacR.gif"));
						PacL = new ImageIcon(getClass().getResource("PacL.gif"));
						
						for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
							Ghost[slctIndx].resetPics();
						}
						
						System.out.println("No code was entered, resetting to game default");
						
					} else {
						PacU = new ImageIcon(getClass().getResource("PacU.gif"));
						PacD = new ImageIcon(getClass().getResource("PacD.gif"));
						PacR = new ImageIcon(getClass().getResource("PacR.gif"));
						PacL = new ImageIcon(getClass().getResource("PacL.gif"));
						
						for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
							Ghost[slctIndx].eePics(slctIndx, "GhostR.gif", "GhostB.gif", "GhostP.gif", "GhostO.gif");
						}
						
						System.out.println("Invalid code was entered, resetting to game default");
					}
				}
				
				CodeInpt.setText("");
			}
			
		} else if(e.getSource() == ExitBttn) {
			
			//exiting the program if on the main menu
			if(StartBttn.isVisible()) {
				System.out.println("\r\nThank you for playing");
				System.exit(0);
			} else if(!StartBttn.isVisible()) {
				
				//exiting back to the main menu
				codeScreen();
			}
		}
	}
	
	public void startScreen() {
		if(StartBttn.isVisible()) {
			PacLbl.setVisible(false);
			
			for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
				GhostLbl[slctIndx].setVisible(false);
			}
			
			//setting pacman's data to each pellet to track collision
			for(slctIndx = 1; slctIndx <= 391; slctIndx++) {
				PltLbl[slctIndx].setVisible(false);
			} 
			
			for(slctIndx = 1; slctIndx <= 13; slctIndx++) {
				WallLbl[slctIndx].setVisible(false);
			} 
			
		} else if(!StartBttn.isVisible()) {
			PgContent.setBackground(Color.BLACK);
			PacLbl.setVisible(true);
			
			for(slctIndx = 1; slctIndx <= 4; slctIndx++) {
				GhostLbl[slctIndx].setVisible(true);
			}
			
			//setting pacman's data to each pellet to track collision
			for(slctIndx = 1; slctIndx <= 391; slctIndx++) {
				PltLbl[slctIndx].setVisible(true);
			} 
			
			for(slctIndx = 1; slctIndx <= 13; slctIndx++) {
				WallLbl[slctIndx].setVisible(true);
			} 
		}
	}
	
	public void codeScreen() {
		//if the start button is visible it sends the user to the code menu
		if(StartBttn.isVisible()) {
			StartBttn.setVisible(false);
			ExtraBttn.setText("Enter");
			CodeInpt.setVisible(true);
			CodeInpt.setFocusable(true);
			
		//if the start button is not visible it sends the user to the main menu
		} else if(!StartBttn.isVisible()) {
			StartBttn.setVisible(true);
			ExtraBttn.setText("Extras");
			CodeInpt.setVisible(false);
			CodeInpt.setFocusable(false);
			
		}
		
	}
}