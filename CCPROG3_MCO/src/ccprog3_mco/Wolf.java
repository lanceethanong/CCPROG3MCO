package ccprog3_mco;

public class Wolf extends Piece
{
	public Wolf(Tile tile, Player player) {
        super(tile, player, 4);
    }


    public String display() 
    {
        return "W"+getPlayerId()+" "; 
    }
}
