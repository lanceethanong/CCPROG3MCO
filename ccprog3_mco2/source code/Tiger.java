package ccprog3_mco;

public class Tiger extends Piece
{
	public Tiger(Tile tile, Player player) {
        super(tile, player, 6);
    }


    public String display() 
    {
        return "T "; 
    }
}
