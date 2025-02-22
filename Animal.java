
public class Animal extends Piece {
    protected String name;

    public Animal(String name, int power, int player) 
    {
        super(power, player,"Animal"); // Call the Piece constructor
        this.name = name;
    }
    
    public String getAnimal()
    {
    	return name;
    }

    public String display() {
        return String.valueOf(name.charAt(0)) + " ";
    }
}
