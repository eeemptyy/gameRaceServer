import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Jompol Sermsook 5610450063
 *		        จอมพล	เสริมสุข 
 */

public class GameData {
	private int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
	ArrayList<Client> threadList = new ArrayList<Client>();
	private ServerSocket welcomeSocket;
	private String statusPhrase;
	HashMap<String, String> status = new HashMap<String, String>();
	
	public void setStatusMap(){
		
		status.put("0001", "No another player.");
		status.put("1000", "Connected waiting for another player.");
		status.put("1001", "Connected Ready to Play.");
		status.put("1112", "Fine.");
		status.put("1150", "GameFrame Started.");
		status.put("4000", "Client Mismatch");
		status.put("5555", "Connection lost.");
		
	}
	
	public void toAllClient() {
		threadList.get(0).sendData();
		threadList.get(1).sendData();
	}

	public void toAllClient(String statusCode) {
		statusPhrase = "NULL";
		if (statusCode.equals("0001")) {
			threadList.get(0).sendDataNotNor(statusCode, statusPhrase);
		} else {
			threadList.get(0).sendDataNotNor(statusCode, statusPhrase);
			threadList.get(1).sendDataNotNor(statusCode, statusPhrase);
		}
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public ServerSocket getWelcomeSocket() {
		return welcomeSocket;
	}

	public void setWelcomeSocket(ServerSocket welcomeSocket) {
		this.welcomeSocket = welcomeSocket;
	}
}
