import java.io.*;
import java.net.*;
import java.util.ArrayList;

class MultiThreadServer {
	private static int player = 1;
	static int count = 1, room = 1;
	int oX1, oX2, nX1, nX2;
	static GameData data;

	@SuppressWarnings("resource")
	public static void main(String argv[]) throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(1335);
		data = new GameData();

		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			if(count > 2){
				data = new GameData();
				player = 1;
				room ++;
				count = 1;
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
				player++;
			}
			count++;
		}
	}
	public void toAllCl(ArrayList<Client> ls) {
		ls.get(0).sendData();
		ls.get(1).sendData();
	}
}