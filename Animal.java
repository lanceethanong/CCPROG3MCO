package ccprog3_mco;

/**
 * Class: Animal
 * Description: Is a subclass of Piece used to initialize the Animal pieces of each player and assign it its values
 * @author Lance Ethan S. Ong S14
 * 
 */
public class Animal extends Piece {
	
    protected String name; // assigns the Animal a Name identifier(Protected so that other subclasses can access)

    /**
     * Method: Animal
     * Description: Constructor used to initialize a new Animal object
     * @param Name: String used to assign the animal a name
     *        power: assigns their power level
     *        player: assigns which player it belongs to
     * @author Lance Ethan S. Ong S14
     * 
     */
    public Animal(String name, int power, int player) 
    {
        super(power, player,"Animal"); // used to access the Piece superclass
        this.name = name; 
    }
    
    /**
     * Method: getAnimal
     * Description: Used to get the Animals name
     * @return name: String
     * @author Lance Ethan S. Ong S14
     * 
     */
    public String getAnimal()
    {
    	return name;
    }
    /**
     * Method: display
     * Description: Used to display the Animal on the Board
     * @return String.valueOf(name.charAt(0)) + " " : char 
     * @author Lance Ethan S. Ong S14
     * 
     */
    public String display() 
    {
        return String.valueOf(name.charAt(0)) + " "; // Displays the first letter of the String = (ex. Lion = "L ")
    }
}