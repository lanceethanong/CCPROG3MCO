package ccprog3_mco;

public class Tiger extends Piece implements Hopper
{
	public Tiger(Tile tile, Player player) 
	{
        super(tile, player, 6);
    }


    public String display() 
    {
        return "T"+getPlayerId()+" "; 
    }
}
