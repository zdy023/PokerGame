package xyz.davidChangx.game.pokergame;
import xyz.davidChangx.game.PokerGame;
import java.util.ArrayList;
import java.net.Socket();
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Random;
public class FightLandlord extends PokerGame
{
	private int rate,lordIndex;
	private final int BASE_RATE = 1;
	private int[] goals;
	public FightLandlord()
	{
		super();
		playerCount = 3;
		sockets = new ArrayList<Socket>(3);
		oos = new ArrayList<ObjectOutputStream>(3);
		ois = new ArrayList<ObjectInputStream>(3);

		cards = new Card[54];
		int k = 0;
		Suit[] suits = Suit.values();
		Point[] points = Point.values();
		for(int i = 0;i<4;i++)
		{
			for(int j = 2;j<13;j++)
				cards[k++] = new Card(suits[i],points[j],j-2);
			cards[k++] = new Card(suits[i],Point.A,11);
			cards[k++] = new Card(suits[i],Point._2,12);
		}
		cards[k++] = new Card(Suit.JOKER,Point.SMALL_JOKER,13);
		cards[k++] = new Card(Suit.JOKER,Point.BIG_JOKER,14);

		rate = 1;
		goals = new int[playerCount];
		Arrays.fill(goals,0);

		continueOrNot = true;
	}
	protected int[][] divideCards(int[] cards)
	{
		Random r = new Random();
		int initCandidate = r.nextInt(playerCount);
		int i,k;
		for(i = 0,k = initCandidate;i<playerCount;i++,k = k+1>=playerCount?k+1-playerCount:k+1)
		{
			oos.get(k).writeUTF("lord");
			oos.get(k).flush();
			boolean lordOrNot = ois.get(k).readBoolean();
			byte[] btAboutLord = {(byte)0,(byte)(lordOrNot?1:0)};
			for(int j = 1,l = k+1;j<playerCount;j++,l = l+1>-playerCount?l+1-playerCount:l+1)
			{
				btAboutLord[0] = (byte)(-j);
				oos.get(l).writeUTF("tellaboutlord");
				oos.get(l).write(btAboutLord);
				oos.get(l).flush();
			}
			if(lordOrNot)
			{
				lordIndex = k;
				break;
			}
		}
		if(k==initCandidate)
		{
			lordIndex = initCandidate;
			for(i = 0;i<playerCount;i++)
			{
				oos.get(i).writeUTF("autolord");
				oos.get(i).writeInt((lordIndex-i-playerCount)%playerCount);
			}
		}
		int[][] userCards = new int[playerCount][];
		int baseNum = 17;
		for(i = 0,k = 0;i<playerCount;i++,k += 17)
		{
			if(i==lordIndex)
			{
				userCards[i] = new int[baseNum+3];
				System.arraycopy(cards,k,userCards,0,baseNum);
				System.arraycopy(cards,51,userCards,baseNum,3);
			}
			else
				userCards[i] = Arrays.copyOfRange(cards,k,baseNum);
		}
	}
	protected void fight()
	{
		int i = lordIndex;
		int[] cardCounts = {17,17,17};
		cardCounts[lordIndex] = 19;
		for(;;i = i+1>=playerCount?i+1-playerCount:i+1)
		{
			oos.get(i).writeUTF("turn");
			int[] cardsToShow = (int[])ois.git(i).readObject();
			if((cardsToShow.length==4)&&(cards[cardsToShow[0]].getPoint()==cards[cardsToShow[1]].getPoint())&&(cards[cardsToShow[1]].getPoint()==cards[cardsToShow[2]].getPoint())&&(cards[cardsToShow[2]].getPoint()==cards[cardsToShow[3]].getPoint()))
				rate++;
			else if((cardsToShow.length==2)&&(cards[cardsToShow[0]].getSuit()==Suit.JOKER)&&(cards[cardsToShow[1]].getSuit()==Suit.JOKER))
				rate++;
			else ;
			int[] showCardsToSend = new int[cardsToShow.length+1];
			System.arraycopy(cardsToShow,0,showCardsToSend,1,cardsToShow.length);
			for(int j - 1,k = i+1;j<playerCount;k = k+1>=playerCount?k+1-playerCount:k+1)
			{
				showCardsToSend[0] = -j;
				oos.get(k).writeUTF("tellcards");
				oos.get(k).writeObject(showCardsToSend);
			}
			cardCounts[i] -= cardsToShow.length;
			if(cardsToShow[i].length==0)
				break;
		}
		int goal = BASE_RATE<<rate;
		if(i==lordIndex)
		{
			for(int j = 0;j<playerCount;j++)
				goals[j] -= goal;
			goals[i] += (goal<<1)+goal;
		}
		else
		{
			for(int j = 0;j<playerCount;j++)
				goals[j] += goal;
			goals[lordIndex] -= (goal<<1)+goal;
		}
		for(int j = 0;j<playerCount;j++)
		{
			oos.get(j).writeUTF(j==i?"win":"lose");
			for(int k = j,l = 0;l<playerCount;l++,k = k+1>=playerCount?k+1-playerCount:k+1)
				oos.get(j).writeInt(goals[k]);
		}
	}
}
