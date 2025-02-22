package ccprog3_mco;

public class Base extends Piece {
    private int player; // Player who owns the base (1 or 2)

    public Base(int player) 
    {
        super(0, player,"Base"); // Base has no power and belongs to a player
        this.player = player;
    }

    public int getPlayer() 
    {
        return player;
    }
    
    public String display() 
    {
        return "P"+this.player+" ";
    }
}
