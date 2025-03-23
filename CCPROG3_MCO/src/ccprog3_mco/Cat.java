package ccprog3_mco;

public class Cat extends Piece
{
	public Cat(Tile tile, Player player) 
	{
        super(tile, player, 2);
    }


    public String display() 
    {
        return "C"+getPlayerId()+" "; 
    }
}
