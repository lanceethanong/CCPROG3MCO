package ccprog3_mco;

/**
 * Class: Lake
 * Description: Is a subclass of Piece used to initialize the Lake Blocks on the Board
 * @author Lance Ethan S. Ong S14
 * 
 */
public class Lake extends Tile 
{
    public Lake(int x,int y) 
    {
        super(x, y,"Lake");
    }
    
    public String display() 
    {
        return "~ "; 
    }
}