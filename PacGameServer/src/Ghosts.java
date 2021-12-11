import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Ghosts extends Sprite {
	final static int IN_PORT = 6000;
	
	
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(IN_PORT);
		System.out.println("Waiting for clients to connect...");
		boolean a = true;
		while(a) {
			Socket s = server.accept();
			System.out.println("client connected");
			a = false;
			s.close();
		}
		
		while(true) {
			Socket s = server.accept();
			Scanner in = new Scanner(s.getInputStream());
			String ghostCmd = in.next();
			int ghostX = in.nextInt();
			int ghostY = in.nextInt();
			System.out.println("COMMAND RECIEVED\r\n   In: " + ghostCmd + "\r\n   At: X- " + ghostX + " Y- " + ghostY);
			
			
			//================SHADOW================
			if(ghostCmd.equals("shdwU")) {
				ghostY -= GameProps.CHAR_STEP/2;
			} else if(ghostCmd.equals("shdwD")) {
				ghostY += GameProps.CHAR_STEP/2;
			} else if(ghostCmd.equals("shdwL")) {
				ghostX -= GameProps.CHAR_STEP/2;
			} else if(ghostCmd.equals("shdwR")) {
				ghostX += GameProps.CHAR_STEP/2;
			}
			//======================================
			
			//==============PERIMETER===============
			if(ghostCmd.equals("prmU")) {
				ghostY -= GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("prmD")) {
				ghostY += GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("prmL")) {
				ghostX -= GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("prmR")) {
				ghostX += GameProps.CHAR_STEP;
			}
			
			if(ghostCmd.equals("prmUW")) {
				ghostY += GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("prmDW")) {
				ghostY -= GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("prmLW")) {
				ghostX += GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("prmRW")) {
				ghostX -= GameProps.CHAR_STEP;
			}
			//======================================
			
			//================NORMAL================
			if(ghostCmd.equals("nrmU")) {
				ghostY -= GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("nrmD")) {
				ghostY += GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("nrmL")) {
				ghostX -= GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("nrmR")) {
				ghostX += GameProps.CHAR_STEP;
			}
			
			if(ghostCmd.equals("nrmUW")) {
				ghostY += GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("nrmDW")) {
				ghostY -= GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("nrmLW")) {
				ghostX += GameProps.CHAR_STEP;
			} else if(ghostCmd.equals("nrmRW")) {
				ghostX -= GameProps.CHAR_STEP;
			}
			//======================================
			
			//================EATEN=================
			PrintWriter out = new PrintWriter(s.getOutputStream());
			
			if(ghostCmd.equals("eaten")) {
				out.println((GameProps.SCREEN_WIDTH - in.nextInt())/2);
				out.flush();
				out.println(GameProps.SCREEN_HEIGHT/2 - 222);
				out.flush();
			} else {
				out.println(ghostX);
				out.flush();
				out.println(ghostY);
				out.flush();
			}
			
			out.close();
			//======================================
			
			System.out.println("   To: X- " + ghostX + " Y- " + ghostY);
			
			s.close();
			
			System.out.println("END COMMAND\n======================");
		}
	}
}
