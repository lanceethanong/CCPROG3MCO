package ccprog3_mco;

public class Dog extends Piece
{
	public Dog(Tile tile, Player player) 
	{
        super(tile, player, 3);
    }


    public String display() 
    {
        return "D"+getPlayerId()+" "; 
    }
}
