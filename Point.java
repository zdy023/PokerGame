package xyz.davidChangx.game;
public enum Point
{
	A("A"),_2("2"),_3("3"),_4("4"),_5("5"),_6("6"),_7("7"),_8("8"),_9("9"),_10("10"),J("J"),Q("Q"),K("K"),SMALL_JOKER("SMALL JOKER"),BIG_JOKER("BIG JOKER");
	String name;
	Point(String name)
	{
		this.name = name;
	}
	public String toString()
	{
		return this.name;
	}
}
