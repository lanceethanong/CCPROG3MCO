package ccprog3_mco;

public class Leopard extends Piece
{
	public Leopard(Tile tile, Player player) 
	{
        super(tile, player, 5);
    }


    public String display() 
    {
        return "l "+getPlayerId()+" "; 
    }

}
