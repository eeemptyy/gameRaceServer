import java.io.*;
import java.net.*;

/**
 * 
 * @author Jompol Sermsook 5610450063
 *		        จอมพล	เสริมสุข 
 */

class Client extends Thread {
	private Socket connectionSocket;
	private String fromClient, out;;
	private BufferedReader inFromClient;
	private PrintWriter outToClient;
	private GameData data;
	private int p, oX1, oX2, room;

	public Client(Socket c, GameData data, int player, int room) throws IOException {
		connectionSocket = c;
		inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);
		this.data = data;
		this.room = room;
		out = data.getX1() + "," + data.getY1() + "," + data.getX2() + "," + data.getY2();
		p = player;
	}

	public void run() {
		try {
			while (connectionSocket.isConnected()) {
				oX1 = data.getX1();
				oX2 = data.getX2();
				System.out.println("Before from");
				fromClient = inFromClient.readLine();
				System.out.println("After from");
				if (!(fromClient.equals("start") || (fromClient.equals("move")))) {
					outToClient.println("4000"+" "+"Client Mismatch");
					connectionSocket.close();
					System.out.println("Close Sockets");
				} else {
					if (fromClient.equals("start")) {
						if (p == 1) {
							System.out.println("Start player 1");
							out = data.getX1() + "," + data.getY1() + "," + data.getX2() + "," + data.getY2() + "," + p
									+ "," + room + "," + "1150" + "," + "GameFrame Started";
							System.out.println(out);
						} else if (p == 2) {
							System.out.println("Start player 2");
							out = data.getX1() + "," + data.getY1() + "," + data.getX2() + "," + data.getY2() + "," + p
									+ "," + room + "," + "1150" + "," + "GameFrame Started";
						}
						System.out.println(out + "2");
						outToClient.println(out);
						outToClient.flush();
					} else if (fromClient.equals("move")) {
						if (p == 1) {
							data.setX1(data.getX1() + 5);
						} else if (p == 2) {
							data.setX2(data.getX2() + 5);
						}
					}
				}
				if ((data.getX1() != oX1) || (data.getX2() != oX2)) {
					if (data.threadList.size() == 2) {
						System.out.println(out + "3");
						data.toAllClient();
					} else if (data.threadList.size() == 1) {
						System.out.println("ASDASD");
						data.toAllClient("0001");
					}
				}
				if (data.getWelcomeSocket().isClosed()) {
					connectionSocket.close();

				}
			}
		} catch (IOException e) {
			System.out.println("Errore: " + e);
		}
	}

	public void sendData() {
		out = data.getX1() + "," + data.getY1() + "," + data.getX2() + "," + data.getY2() + "," + "1112" + "," + "Fine";
		outToClient.println(out);
		outToClient.flush();
	}

	public void sendDataNotNor(String statusCode, String statusPhrase) {
		if (statusCode.equals("1000") || (statusCode.equals("1001"))) {
			out = data.getX1() + "," + data.getY1() + "," + data.getX2() + "," + data.getY2() + "," + p + "," + room
					+ "," + statusCode + "," + statusPhrase;
		}else {
			out = data.getX1() + "," + data.getY1() + "," + data.getX2() + "," 
		+ data.getY2() + "," + statusCode + "," + statusPhrase;
		}
		outToClient.println(out);
		outToClient.flush();
	}
}