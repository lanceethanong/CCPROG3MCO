package ccprog3_mco;

/**
 * Class: Lake
 * Description: Is a subclass of Piece used to initialize the Lake Blocks on the Board
 * @author Lance Ethan S. Ong S14
 * 
 */
public class Lake extends Tile 
{
    public Lake(Board board,int x,int y) 
    {
        super(board,x, y);
    }
    
    public String display() 
    {
        return "~ "; 
    }
}