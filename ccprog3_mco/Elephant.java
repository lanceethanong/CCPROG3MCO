package ccprog3_mco;

public class Elephant extends Piece
{
	public Elephant(Tile tile, Player player) 
	{
        super(tile, player, 8);
    }


    public String display() 
    {
        return "E"+getPlayerId()+" "; 
    }
}
