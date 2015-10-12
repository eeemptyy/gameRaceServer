import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author Jompol Sermsook 5610450063
 *		        จอมพล	เสริมสุข 
 */

public class ServerFrame extends JFrame {
	private JPanel contentPane;
	private Server server;
	private Thread connectionListener;
	String ServState;
	private JLabel serverStatus;
	private JLabel numberOfRoom;
	private int again = 1;

	public ServerFrame() throws IOException {
		server = new Server();
		ServState = "Offline";
		setTitle("Game Server @ IP: " + "158.108.30.141:1112");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		connectionListener = new Thread(server);

		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		panel1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Server info.", TitledBorder.LEFT,
				TitledBorder.TOP, null, null));
		panel1.setLayout(null);

		JButton endServerButton = new JButton("End Server");
		endServerButton.setBounds(286, 26, 113, 23);
		endServerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				connectionListener.stop();
				try {
					server.stopServer();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ServState = "Offline...";
				again = 2;
				serverStatus.setText("Server status: " + ServState);
				JOptionPane.showMessageDialog(null, "Server closed.",
						"", JOptionPane.PLAIN_MESSAGE);
			}
		});
		panel1.add(endServerButton);

		JButton startSevButton = new JButton("Start Server");
		startSevButton.setBounds(163, 26, 113, 23);
		startSevButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (ServState.equals("Online...")) {
					JOptionPane.showMessageDialog(null, "Server in Running",
							"", JOptionPane.PLAIN_MESSAGE);
				} else {
					if (again == 2) {
						try {
							server = new Server();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						connectionListener = new Thread(server);
					}
					connectionListener.start();
					ServState = "Online...";
					serverStatus.setText("Server status: " + ServState);
					again = 3;
				}
			}
		});
		panel1.add(startSevButton);

		serverStatus = new JLabel("Server status: " + ServState);
		serverStatus.setBounds(10, 30, 143, 14);
		panel1.add(serverStatus);
		
		int roomNo = server.getRoom();
		numberOfRoom = new JLabel("Total room in server: " + roomNo);
		numberOfRoom.setBounds(10, 59, 143, 14);
		panel1.add(numberOfRoom);
		setVisible(true);
	}
}
