package ccprog3_mco;

public class Rat extends Piece implements Swimmer
{
	public Rat(Tile tile, Player player) 
	{
        super(tile, player, 1);
    }

    public String display() 
    {
        return "R"+getPlayerId()+" "; 
    }
    
    public Tile swim(Tile targetTile) 
    {
        if (targetTile == null || !targetTile.getType().equals("Lake")) 
        {
            return null;
        }
        
        // Identical to move() logic but returns Tile
        currentTile.setPiece(null);
        targetTile.setPiece(this);
        currentTile = targetTile;
        return currentTile;
    }
}
