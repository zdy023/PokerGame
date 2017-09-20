package xyz.davidchangx.game.pokergame;
import xyz.davidchangx.game.Player;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import xyz.davidchangx.game.Card;
import xyz.davidchangx.game.Suit;
import xyz.davidchangx.game.Point;
import javax.swing.JPanel;
import javax.swing.JButtion;
import java.awt.Graphics2D;
import java.util.ArrayList;
public class FightLandlordPlayer extends Player implements ActionListener
{
	JPanel[] job,goal,cardCount,showCards;
	JPanel myCardsPanel;
	public FightLandlordPlayer(String hostIP)
	{
		super(hostIP);

		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		JPanel centre = new JPanel();
		showCards = new JPanel[3];
		showCards[1] = new JPanel();
		showCards[2] = new JPanel();
		showCards[0] = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;
		gc.gridheight = 2;
		gbl.setConstraints(showCards[2],gc);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(showCards[1],gc);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.gridheight = 1;
		gc.weightx = 0.;
		gc.weighty = 0.;
		gbl.setConstraints(showCards[0],gc);
		centre.setLayout(gbl);
		centre.add(showCards[2]);
		centre.add(showCards[1]);
		centre.add(showCards[0]);
		c.add(centre,BorderLayout.CENTER);

		job = new JPanel[3];
		goal = new JPanel[3];
		cardCount = new JPanel[3];

		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east,BoxLayout.Y_AXIS));
		job[1] = new JPanel();
		goal[1] = new JPanel();
		cardCount[1] = new JPanel();
		east.add(job[1]);
		east.add(goal[1]);
		east.add(cardCount[1]);
		c.add(east,BorderLayout.EAST);

		JPanel west  = new JPanel();
		west.setLayout(new BoxLayout(west,BoxLayout.Y_AXIS));
		job[2] = new JPanel();
		goal[2] = new JPanel();
		cardCount[2] = new JPanel();
		west.add(job[2]);
		west.add(goal[2]);
		west.add(cardCount[2]);
		c.add(west,BorderLayout.WEST);

		JPanel south = new JPanel();
		south.setLayout(new BorderLayout());
		JButton sendOut = new JButton("出牌");
		sendOut.add((ActionListener)this::sendOut);
		south.add(sendOut,BorderLayout.SOUTH);
		JPanel southCentre = new JPanel(),southCentreWest = new JPanel();
		myCardsPanel = new JPanel();
		southCentre.setLayout(new BorderLayout());
		myCardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		southCentreWest.setLayout(new BoxLayout(southCentreWest,BoxLayout.Y_AXIS));
		job[0] = new JPanel();
		goal[0] = new JPanel();
		southCentreWest.add(job[0]);
		southCentreWest.add(goal[0]);
		southCentre.add(myCardsPanel,BorderLayout.CENTER);
		southCentre.add(southCentreWest,BorderLayout.WEST);
		south.add(southCentre,BorderLayout.CENTER);
		c.add(south,BorderLayout.SOUTH);

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

		userCards = new ArrayList<Integer>[3];
	}
	public void sendOut(ActionEvent e)
	{
	}
	private void paintCards(Graphics2D g,ArrayList<Integer> cards)
	{
	}
	public void actionPerformed(ActionEvent e)
	{
	}
	protected boolean check(ArrayList<Integer>[] cardsIndex)
	{
		Card[][] cards = new Card[3][];
		for(int i = 0;i<3;i++)
		{
			cards[i] = cardsIndex[i].stream().map((Integer x)->return FightLandlordPlayer.this.cards[x.intValue()]).sorted().toArray(Card[]::new);
		}
		if(cards[2].length==0)
		{
			if(cards[1].length==0)
			{
				boolean allTheSameOrNot = false,asQueueOrNot = false;;
				switch(cards[0].length)
				{
					case 0:
					default:
						return false;
					case 1:
						return true;
					case 2:
						return (cards[0][0].getPoint()==cards[0][1].getPoint())||((cards[0][0].getSuit()==Suit.JOKER)&&(cards[0][1].getSuit()==Suit.JOKER));
					case 3:
						return (cards[0][0].getPoint()==cards[0][1].getPoint())&&(cards[0][0].getPoint()==cards[0][2].getPoint());
					case 4:
						allTheSameOrNot = true;
						for(int i = 1;i<4;i++)
						{
							allTheSameOrNot = (cards[0][0].getPoint()==cards[0][i].getPoint())&&allTheSameOrNot;
						}
						if(allTheSameOrNot)
						{
							return true;
						}
						if(cards[0][0].getPoint()==cards[0][1].getPoint())
						{
							return (cards[0][0].getPoint()==cards[0][2].getPoint())&&(cards[0][3].getLevel()<=11);
						}
						allTheSameOrNot = true;
						for(int i = 2;i<4;i++)
						{
							allTheSameOrNot = (cards[0][1].getPoint()==cards[0][i].getPoint())&&allTheSameOrNot;
						}
						return allTheSameOrNot;
					case 5:
						if(cards[0][cards[0].length-1].getLevel()<=11)
						{
							asQueueOrNot = true;
							for(int i = cards[0].length-2;i>=0;i--)
							{
								asQueueOrNot = (cards[0][i].getLevel()==cards[0][i+1].getLevel()-1)&&asQueueOrNot;
							}
							if(asQueueOrNot)
								return true;
						}
						if(cards[0][1].getPoint()==cards[0][2].getPoint())
							return (cards[0][0].getPoint()==cards[0][1].getPoint())&&(cards[0][3].getPoint()==cards[0][4].getPoint())&&(cards[0][4].getLevel()<=11);
						allTheSameOrNot = true;
						for(int i = 3;i<5;i++)
						{
							allTheSameOrNot = (cards[0][2].getPoint()==cards[0][i].getPoint())&&allTheSameOrNot;
						}
						return (cards[0][0].getPoint()==cards[0][1].getPoint())&&allTheSameOrNot;
					case 6:
						if(cards[0][cards[0].length-1].getLevel()<=11)
						{
							asQueueOrNot = true;
							for(int i = cards[0].length-2;i>=0;i--)
							{
								asQueueOrNot = (cards[0][i].getLevel()==cards[0][i+1].getLevel()-1)&&asQueueOrNot;
							}
							if(asQueueOrNot)
								return true;
							asQueueOrNot = true;
							for(i = cards[0].length-2;i>=0;i -= 2)
							{
								asQueueOrNot = (cards[0][i].getLevel()==cars[0][i+1].getLevel())&&asQueueOrNot;
								if(i!=0)
									asQueueOrNot = (cards[0][i].getLevel()==cards[0][i-1].getLevel()+1)&&asQueueOrNot;
							}
							if(asQueueOrNot)
								return true;
							asQueueOrNot = true;
							for(i = cards[0].length-3;i>=0;i -= 3)
							{
								asQueueOrNot = (cards[0][i].getLevel()==cars[0][i+1].getLevel())&&(cards[0][i].getLevel()==cards[0][i+2].getLevel())&&asQueueOrNot;
								if(i!=0)
									asQueueOrNot = (cards[0][i].getLevel()==cards[0][i-1].getLevel()+1)&&asQueueOrNot;
							}
							if(asQueueOrNot)
								return true;
						}
						if(cards[0][0].getPoint()==cards[0][1].getPoint())
						{
							allTheSameOrNot = true;
							for(int i = 2;i<4;i++)
							{
								allTheSameOrNot = (cards[0][0].getPoint()==cards[0][i].getPoint())&&allTheSameOrNot;
							}
							if(allTheSameOrNot)
							{
								return cards[0][5].getLevel()<=11;
							}
						}
						if(cards[0][4].getPoint()==cards[0][5].getPoint())
						{
							allTheSameOrNot = true;
							for(int i = 3;i>=2;i--)
							{
								allTheSameOrNot = (cards[0][5].getPoint()==cards[0][i].getPoint())&&allTheSameOrNot;
							}
							if(allTheSameOrNot)
								return true;
						}
						allTheSameOrNot = true;
						for(int i = 2;i<=4;i++)
						{
							allTheSameOrNot = (cards[0][1].getPoint()==cards[0][i].getPoint())&&allTheSameOrNot;
						}
						return (cards[0][5].getLevel()<=11)&&allTheSameOrNot;
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
			}
		}
	}
	protected void reflect(String command)
	{
	}
}
