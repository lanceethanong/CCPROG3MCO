package ccprog3_mco;
/**
 * Class: Base
 * Description: Subclass of piece used to initialize the base blocks of each player
 * @author Lance Ethan S. Ong S14
 * 
 */
public class Base extends Piece {
    private int player; // identifies which player it belongs to

    /**
     * Method: Base
     * Description: Constructor used to initialize a base piece
     * @param: player: player number
     * @author Lance Ethan S. Ong S14
     * 
     */
    public Base(int player) 
    {
        super(0, player,"Base"); // Assigns 0 power indicating a neutral block and assigns it to either player 1 or 2
        this.player = player;
    }
    /**
     * Method: getPlayer
     * Description: used to get the player number
     * @return: player: int
     * @author Lance Ethan S. Ong S14
     * 
     */
    public int getPlayer() 
    {
        return player;
    }
    /**
     * Method: display
     * Description: used to dispaly the block on the board
     * @return: "P"+this.player+" " : String
     * @author Lance Ethan S. Ong S14
     * 
     */
    public String display() 
    {
        return "P"+this.player+" "; // P + player number = (ex. "P1 ")
    }
}
