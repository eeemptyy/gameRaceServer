import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * 
 * @author Jompol Sermsook 5610450063
 *		        จอมพล	เสริมสุข 
 */

class MultiThreadServer {
	private static int player = 1;
	static int count = 1, room = 1;
	int oX1, oX2, nX1, nX2;
	static GameData data;

	@SuppressWarnings("resource")
	public static void main(String argv[]){
		
		try {
			new ServerFrame();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,
					"This is and Error and no one can handle it.\n" + 
			"EI EI EI", "Final call", JOptionPane.PLAIN_MESSAGE);
		}
		
	}
}