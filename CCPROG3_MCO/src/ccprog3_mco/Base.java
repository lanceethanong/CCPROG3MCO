package ccprog3_mco;
/**
 * Class: Base
 * Description: Subclass of piece used to initialize the base blocks of each player
 * @author Lance Ethan S. Ong S14
 * 
 */
public class Base extends Tile 
{
    private int player;

    public Base(Board board,int x, int y, int player) 
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
        return "P"+getPlayer(); 
    }

}
