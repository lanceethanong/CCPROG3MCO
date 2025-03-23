package ccprog3_mco;

public class Trap extends Tile 
{
    private int player;
    public Trap(int x, int y, int player) 
    {
        super(x, y);
        this.player = player;
    }

    public int getPlayer() 
    {
        return this.player;
    }
    
    public void activateTrap(Piece piece) 
    {
        piece.stun();
    }
    
    public EmptyTile deactivateTrap() 
    {
        return new EmptyTile(this.getX(), this.getY());
    }
    public String display() 
    {
        return "# "; 
    }

}

