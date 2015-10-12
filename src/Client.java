import java.io.*;
import java.net.*;

class Client extends Thread {
	private Socket connectionSocket;
	private String fromClient;
	private BufferedReader inFromClient;
	private PrintWriter outToClient;
	private GameData data;
	private int p;
	private String out;
	int oX1, oX2;
	private MultiThreadServer sev;
	private int room;

	public Client(Socket c, GameData data, int player, int room) throws IOException {
		connectionSocket = c;
		inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);
		this.data = data;
		this.room = room;
		out = data.getX1() + "," + data.getY1() + "," + data.getX2() + "," + data.getY2();
		p = player;
		sev = new MultiThreadServer();
	}

	public void run() {
		try {
			while (connectionSocket.isConnected()) {
				oX1 = data.getX1();
				oX2 = data.getX2();
				System.out.println("Before from");
				fromClient = inFromClient.readLine();
				System.out.println("After from");
				if (fromClient.equals("start")) {
					if (p == 1) {
						out = data.getX1() + "," + data.getY1() + "," + 
						data.getX2() + "," + data.getY2() + "," + p + "," + room;
					} else if (p == 2) {
						out = data.getX1() + "," + data.getY1() + "," + 
						data.getX2() + "," + data.getY2() + "," + p + "," + room;
					}
					outToClient.println(out);
					outToClient.flush();
				} else if ((fromClient.equals("move")) && (p == 1)) {
					data.setX1(data.getX1() + 5);
				} else if ((fromClient.equals("move")) && (p == 2)) {
					data.setX2(data.getX2() + 5);
				}
				if ((data.getX1() != oX1) || (data.getX2() != oX2)) {
					sev.toAllCl(data.threadList);
				}
			}
		} catch (IOException e) {
			System.out.println("Errore: " + e);
		}
	}

	public void sendData() {
		out = data.getX1() + "," + data.getY1() + "," + data.getX2() + "," + data.getY2();
		outToClient.println(out);
		outToClient.flush();
	}
}