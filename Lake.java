

/**
 * Class: Lake
 * Description: Is a subclass of Piece used to initialize the Lake Blocks on the Board
 * @author Lance Ethan S. Ong S14
 * 
 */
public class Lake extends Piece {
	/**
	 * Method: Lake
	 * Description: Constructor Used to initialize a Lake Object
	 * @author Lance Ethan S. Ong S14
	 * 
	 */
    public Lake() {
        super(0, 0,"Lake"); // used to access the Piece superclass(0s are used when it is a neutral block)
    }
    /**
	 * Method: display
	 * Description: How the lake block will be displayed on the board
	 * @return ~  : String
	 * @author Lance Ethan S. Ong S14
	 * 
	 */
    public String display() {
        return "~ ";
    }
}
