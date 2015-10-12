import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Jompol Sermsook 5610450063
 *		        จอมพล	เสริมสุข 
 */

public class Server implements Runnable {

	private static int player = 1;
	private static int count = 1, room = 1;
	private static GameData data;
	private ServerSocket welcomeSocket;
	private Socket connectionSocket;
	
	private static int PORT = 1112;

	public int getRoom() {
		return room;
	}

	public Server() throws IOException {
		welcomeSocket = new ServerSocket(PORT);
		data = new GameData();
	}

	public void getConnected() throws IOException {
		connectionSocket = welcomeSocket.accept();
		if (count > 2) {
			data = new GameData();
			player = 1;
			count = 1;
			room++;
		}
		if (connectionSocket != null) {
			if (player > 2) {
				player = 1;
			}
			System.out.print("Get connected from: " + connectionSocket.getRemoteSocketAddress());
			System.out.println("  as player: " + player);
			Client client = new Client(connectionSocket, data, player, room);
			client.start();
			data.threadList.add(client);
			if (player == 1) {
				client.sendDataNotNor("1000", "Connected waiting for another player.");
			}else if(player == 2){
				client.sendDataNotNor("1001", "Connected");
			}
			data.setWelcomeSocket(welcomeSocket);
			player++;
		}
		count++;
	}

	public void stopServer() throws IOException {
		// connectionSocket.close();
		welcomeSocket.close();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				getConnected();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public ServerSocket getWelcomeSocket() {
		return welcomeSocket;
	}

	public void setWelcomeSocket(ServerSocket welcomeSocket) {
		this.welcomeSocket = welcomeSocket;
	}

	public Socket getConnectionSocket() {
		return connectionSocket;
	}

	public void setConnectionSocket(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}

}
