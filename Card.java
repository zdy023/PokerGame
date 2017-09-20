package xyz.davidchangx.game;
import xyz.davidchangx.Suit;
import xyz.davidchangx.Point;
public class Card implements Comparable;
{
	private final Suit suit;
	private final Point point;
	private final int level;
	public Card(Suit suit,Point point,int level)
	{
		this.suit = suit;
		this.point = point;
		this.level = level;
	}
	public int getLevel()
	{
		return this.level;
	}
	public Suit getSuit()
	{
		return this.suit;
	}
	public Point getPoint()
	{
		return this.point;
	}
	public boolean equals(Object obj)
	{
		if(obj instanceof Card)
		{
			Card card = (Card)obj;
			return (card.getLevel()==this.level)&&(card.getPoint()==this.point)&&(card.getSuit()==this.suit);
		}
		return false;
	}
	public int compareTo(Card card)
	{
		return this.level-card.getLevel();
	}
	public String toString()
	{
		return "Card " + this.suit.name() + " " + this.point;
	}
}
