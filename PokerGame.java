package xyz.davidChangx.game;
import xyz.davidChangx.game.Game;
import java.net.ServerSocket;
//import xyz.davidChangx.game.Player;
import xyz.davidChangx.game.Card;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Random;
public abstract class PokerGame extends ServerSocket implements Game
{
	protected final Card[] cards;
	protected ArrayList<Socket> sockets;
	protected ArrayList<ObjectOutputStream> oos;
	protected ArrayList<ObjectInputStream> ois;
	protected int playerCount;
	protected boolean continueOrNot;
	protected PokerGame()
	{
		sockets = new ArrayList<Socket>();
		oos = new ArrayList<Socket>();
		ois = new ArrayList<Socket>();
	}
	protected abstract int[][] divideCards(int[] cards);
	protected int[] randomSequence(int n)
	{
		int[] a;
		for(int i = 0;i<n;i++)
			a[i] = i;
		Random r = new Random();
		int t,ri;
		for(i = a.length-1;i>=0;i--)
		{
			ri = r.nextInt(i+1);
			t = a[i];
			a[i] = a[ri];
			a[ri] = t;
		}
		return a;
	}
	public void load()
	{
		for(int i = 0;i<playerCount;i++)
		{
			Socket newS = this.accept();
			sockets.add(newS);
			oos.add(new ObjectOutputStream(new BufferedOutputStream(newS.getOutputStream())));
			ois.add(new ObjectInputStream(new BufferedInputStream(nenS.getInputStream())));
		}
		for(;continueOrNot;)
		{
			int[][] userCards = this.divideCards(this.randomSequence(cards.length));
			for(int i = 0;i<playerCount;i++)
			{
				oos.get(i).writeUTF("cards");
				oos.get(i).writeObject(userCards[i]);
				oos.get(i).flush();
			}
			this.fight();
			for(int j = 0;j<playerCount;j++)
			{
				oos.get(j).writeUTF("continue");
				continueOrNot = ois.get(j).readBoolean()&&continueOrNot;
			}
			String nextGame = continueOrNot?"goon":"finish";
			for(int j = 0;j<playerCount;j++)
				oos.get(j).writeUTF(nextGame);
		}
	}
	public abstract void fight();
}
