package ccprog3_mco;

public class Rat extends Piece
{
	public Rat(Tile tile, Player player) 
	{
        super(tile, player, 1);
    }

    public String display() 
    {
        return "R"+getPlayerId()+" "; 
    }
}
