import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GameScreen {
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		
		Pacman Pac = new Pacman();
		Walls[] Wall = new Walls[14];
		
		for(int slctIndx = 1; slctIndx <= 13; slctIndx++) {
			switch(slctIndx) {
				case 1, 2: Wall[slctIndx] = new Walls("vrtTall");
						   break;
				
				case 3, 4: Wall[slctIndx] = new Walls("vrtShrt");
						   break;
				
				case 5, 6: Wall[slctIndx] = new Walls("hrzWide");
						   break;
				
				case 7, 8: Wall[slctIndx] = new Walls("hrzNrrw");
						   break;
				
				case 9, 10: Wall[slctIndx] = new Walls("vrtTiny");
							break;
							
				case 11, 12: Wall[slctIndx] = new Walls("hrzTiny");
							 break;
							 
				case 13: Wall[slctIndx] = new Walls("mddlBrk");
				 		 break;
						
				default: break;
			}
		}
		
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
		Wall[7].setY(92);
		
		Wall[8].setX(165);
		Wall[8].setY(GameProps.SCREEN_HEIGHT - 155);
		
		Wall[9].setX(157);
		Wall[9].setY((GameProps.SCREEN_HEIGHT - Wall[9].getHeight() - 25)/2);
		
		Wall[10].setX(797);
		Wall[10].setY((GameProps.SCREEN_HEIGHT - Wall[10].getHeight() - 25)/2);
		
		Wall[11].setX((GameProps.SCREEN_WIDTH - Wall[11].getWidth() - 16)/2);
		Wall[11].setY(204);
		
		Wall[12].setX((GameProps.SCREEN_WIDTH - Wall[12].getWidth() - 16)/2);
		Wall[12].setY(754);
		
		Wall[13].setX((GameProps.SCREEN_WIDTH - Wall[13].getWidth() - 16)/2);
		Wall[13].setY((GameProps.SCREEN_HEIGHT - Wall[13].getHeight() - 30)/2);
		
		Pac.setX((GameProps.SCREEN_WIDTH-Pac.getWidth())/2);
		Pac.setY(25);
		
		int IN_PORT = 5001;
		ServerSocket server = new ServerSocket(IN_PORT);
		System.out.println("Waiting for clients to connect...");
		boolean a = true;
		while(a) {
			Socket s = server.accept();
			Scanner in = new Scanner(s.getInputStream());
			System.out.println("client connected");
			a = false;
			
			s.close();
			in.close();
		}
		
		
		while(true) {
			Socket s = server.accept();
			Scanner in = new Scanner(s.getInputStream());
			int PacX = 0;
			int PacY = 0;
			
			String playerCmd = in.next();
			System.out.println("======================\r\nCOMMAND RECIEVED\r\n   In: " + playerCmd);
			if(playerCmd.equals("mvU") || playerCmd.equals("mvD") || playerCmd.equals("mvL") || playerCmd.equals("mvR")) {
				Pac.setX(in.nextInt());
				Pac.setY(in.nextInt());
				
				PacX = Pac.getX();
				PacY = Pac.getY();
			}
			
			
			if(playerCmd.equals("mvU")) {
				System.out.println("   At: X- " + PacX + " Y- " + PacY);
				PacY -= GameProps.CHAR_STEP;
				System.out.println("   To: X- " + PacX + " Y- " + PacY);
				
				Pac.setX(PacX);
				Pac.setY(PacY);
				
				Pac = collision(Pac, PacX, PacY, playerCmd, Wall);
				
				PrintWriter out = new PrintWriter(s.getOutputStream());
				out.println(Pac.getX());
				out.flush();
				out.println(Pac.getY());
				out.flush();
				
				out.close();
				
			} else if(playerCmd.equals("mvD")) {
				System.out.println("   At: X- " + PacX + " Y- " + PacY);
				PacY += GameProps.CHAR_STEP;
				System.out.println("   To: X- " + PacX + " Y- " + PacY);
				
				Pac.setX(PacX);
				Pac.setY(PacY);
				
				Pac = collision(Pac, PacX, PacY, playerCmd, Wall);
				
				PrintWriter out = new PrintWriter(s.getOutputStream());
				out.println(Pac.getX());
				out.flush();
				out.println(Pac.getY());
				out.flush();
				
				out.close();
				
			} else if(playerCmd.equals("mvL")) {
				System.out.println("   At: X- " + PacX + " Y- " + PacY);
				PacX -= GameProps.CHAR_STEP;
				System.out.println("   To: X- " + PacX + " Y- " + PacY);
				
				Pac.setX(PacX);
				Pac.setY(PacY);
				
				Pac = collision(Pac, PacX, PacY, playerCmd, Wall);
				
				PrintWriter out = new PrintWriter(s.getOutputStream());
				out.println(Pac.getX());
				out.flush();
				out.println(Pac.getY());
				out.flush();
				
				out.close();
				
			} else if(playerCmd.equals("mvR")) {
				System.out.println("   At: X- " + PacX + " Y- " + PacY);
				PacX += GameProps.CHAR_STEP;
				System.out.println("   To: X- " + PacX + " Y- " + PacY);
				
				Pac.setX(PacX);
				Pac.setY(PacY);
				
				Pac = collision(Pac, PacX, PacY, playerCmd, Wall);
				
				PrintWriter out = new PrintWriter(s.getOutputStream());
				out.println(Pac.getX());
				out.flush();
				out.println(Pac.getY());
				out.flush();
				
				out.close();
				
			}
			
			if(playerCmd.equals("LBcrt")) {
				lbCreate();
				
			} else if (playerCmd.equals("LBdsp")) {
				String disp = "";
				disp = DisplayRecords();
				
				PrintWriter out = new PrintWriter(s.getOutputStream());
				out.println(disp);
				out.flush();
				
			} else if(playerCmd.equals("LBadd")) {
				lbAdd(in.next(), in.nextInt());
			}
						
			s.close();
			
			System.out.println("END COMMAND");
		}
		
	}
	
	public static Pacman collision(Pacman Pac, int PacX, int PacY, String Cmd, Walls[] Wall) {
		for(int slctIndx = 1; slctIndx <= 13; slctIndx++) {
			if(Pac.getRect().intersects(Wall[slctIndx].getRect())) {
				if(Cmd.equals("mvU")) {
					PacY += GameProps.CHAR_STEP;
				} else if(Cmd.equals("mvD")) {
					PacY -= GameProps.CHAR_STEP;
				} else if(Cmd.equals("mvL")) {
					if(slctIndx == 1) {
						PacX = (Wall[1].getX() + Wall[1].getWidth());
					} else {
						PacX += GameProps.CHAR_STEP;
					}
					
				} else if(Cmd.equals("mvR")) {
					if(slctIndx == 6) {
						PacX = GameProps.SCREEN_WIDTH - Wall[6].getWidth();
					} else {
						PacX -= GameProps.CHAR_STEP;
					}
				}
			}
		}
		
		Pac.setX(PacX);
		Pac.setY(PacY);
	
		return Pac;
	}

	public static void lbCreate() throws ClassNotFoundException, SQLException {
		//setting variables to be used for the database connection
		Connection conn = null;
		Statement stmt = null;
		
		//trying to open and connect to the leaderboard database
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:leaderboard.db";
			conn = DriverManager.getConnection(dbURL);
			
			//if the connection worked making the database if it isn't made already
			if (conn != null) {
				System.exit(0);
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				
				String sql = "CREATE TABLE IF NOT EXISTS PLAYER " +
				             "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
						     " NAME TEXT NOT NULL, " + 
				             " SCORE INT NOT NULL)";
				stmt.executeUpdate(sql);
				conn.commit();
				
				//closing the connection
				conn.close();
			}
	}
	
	public static String DisplayRecords() throws SQLException, ClassNotFoundException{
		//setting up variables to be used throughout the display function
		int id = 0;
		String FullString = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//trying to connect to the database
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:leaderboard.db";
			conn = DriverManager.getConnection(dbURL);
					
		
			//using the FullString variable to add all data to one string so that it can all be displayed in one dialog box
			FullString += "                  LeaderBoard\r\n==========================\r\n";
			
			if (conn != null) {
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				
				//sorting the data in the leaderboards and displaying the top 5 scores
				rs = stmt.executeQuery("SELECT * FROM PLAYER ORDER BY SCORE DESC LIMIT 5");
				
				//looping through the five entries
				while (rs.next()) {
					//upping the ID so that it can be displayed with each entry
					id++;
					String name = rs.getString("name");
					int score = rs.getInt("score");
					
					//adding the entry to the FullString variable
					FullString += "           " + id + ") " + name + " - " + score +"\r\n";
				}
				
				FullString += "\r\n";
				
				rs.close();
				
				//closing the connection
				conn.close();
			}
		
		return FullString;
	}
	
	public static void lbAdd(String name, int score) throws ClassNotFoundException, SQLException {
		//setting variables to be used for the database connection
		Connection conn = null;
		Statement stmt = null;
		
		//trying to connect to the database
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:leaderboard.db";
			conn = DriverManager.getConnection(dbURL);
			
			//if the connection is good then it takes the input for the name and score then adds the data to the database
			if (conn != null) {
				conn.setAutoCommit(false);
				
				stmt = conn.createStatement();
				String sql = "INSERT INTO PLAYER (NAME, SCORE) VALUES " + 
				                          "('" + name + "', " + score + ")";
				stmt.executeUpdate(sql);
				conn.commit();
				
				//closing the connection
				conn.close();
			}
	}
}
