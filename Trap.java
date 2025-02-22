package ccprog3_mco;
/**
 * Class: Trap(Not yet configured)
 * Description: A class that initiates traps which drains the powers of whoever steps on them(To be configured)
 * 
 * @author Lance Ethan S. Ong S14
 *
 */
public class Trap extends Piece {
    private int player; //Which player the trap belongs to

    /**
     * Method: Trap
     * Description: Constructor that initiates a trap object
     * @param player: int
     * 
     * @author Lance Ethan S. Ong S14
     *
     */
    public Trap(int player) 
    {
        super(0, player,"Trap"); // Traps have no power and belongs to a player
        this.player = player;
    }
    /**
     * Method: getPlayer
     * Description: gets the Player who owns the trap
     * @return player: int
     * 
     * @author Lance Ethan S. Ong S14
     *
     */
    public int getPlayer() 
    {
        return player;
    }
    /**
     * Method: display
     * Description: displays the trap on the cli
     * @return ". ": String
     * 
     * @author Lance Ethan S. Ong S14
     *
     */
    public String display() 
    {
        return ". "; 
    }
}
