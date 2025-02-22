
/**
 * Class: Piece
 * Description: Main class responsible for handling the parameters of the pieces to be used in the game(Animals,Lake Blocks,Traps(to be implemented) and Bases) 
 * @author Lance Ethan S. Ong S14
 * 
 */
public class Piece {
	/* parameters of Piece
	 * power : level of each block
	 * player : which player the piece belongs to
	 * type: Identifier to find out what type of piece it is
	 */
    int power; 
    int player; 
    String type; 

    /**
     * Method: Piece
     * Description: Constructor used to initialize new piece Objects
     * @param power: integer to initialize powerlevel
     *        player: integer to initialize which player it belongs to
     *        type: String identifier
     * @author Lance Ethan S. Ong S14
     * 
     */
    public Piece(int power, int player, String type) {
        this.power = power;
        this.player = player;
        this.type = type;
    }
    
    /**
     * Method: display
     * Description: Used in the board
     * @return "?" - temporary filler
     * @author Lance Ethan S. Ong S14
     * 
     */
    public String display() {
        return "?";
    }
    /**
     * Method: display
     * Description: gets the PlayerId
     * @return player: int
     * @author Lance Ethan S. Ong S14
     * 
     */
    public int getPlayerId() {
        return this.player;
    }
    /**
     * Method: getType
     * Description: gets the type
     * @return type: String
     * @author Lance Ethan S. Ong S14
     * 
     */
    public String getType() {
        return this.type;
    }

    /**
     * Method: getPower
     * Description: gets the power of the piece
     * @return type: int
     * @author Lance Ethan S. Ong S14
     * 
     */
    public int getPower() {
        return this.power;
    }
}




