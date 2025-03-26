package ccprog3_mco;

public class Lion extends Piece implements Hopper
{
	public Lion(Tile tile, Player player) 
	{
        super(tile, player, 7);
    }


    public String display() 
    {
        return "L"+getPlayerId()+" "; 
    }
       
}
