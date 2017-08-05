package xyz.davidChangx.game;
import java.util.ArrayList;
import java.net.Socket;
import javax.swing.JFrame;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
public abstract class Player extends JFrame
{
	protected final Card[] cards;
	protected Socket socket;
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
	protected int[] myCards;
	protected abstract void check(ArrayList<Integer> cards);
	protected Player(String hostIP)
	{
		socket = new Socket(hostIP,3000);
		oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
	}
	protected void listen()
	{
		for(;;)
		{
			String command = ois.readUTF();
			this.reflect(command);
		}
	}
	protected void reflect(String command);
}
