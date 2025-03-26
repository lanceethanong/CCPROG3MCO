package ccprog3_mco;

public class Trap extends Tile 
{
    private int player;
    public Trap(Board board,int x, int y, int player) 
    {
        super(board,x, y);
        this.player = player;
    }

    public int getPlayer() 
    {
        return this.player;
    }
    public String display() 
    {
        return "# "; 
    }

}

