package ccprog3_mco;
import java.util.ArrayList;
public class Player {
    private String name;
    private ArrayList<Piece> pieces; // Aggregation: Player HAS multiple Pieces
    private int number;
    
    public Player(int number,String name) 
    {
    	this.number = number;
        this.name = name;
        this.pieces = new ArrayList<>();
    }

    public String getName() 
    {
        return name;
    }
    
    public int getPlayerId() 
    {
        return number;
    }

    public ArrayList<Piece> getPieces() 
    {
        return pieces;
    }

    // Add a piece to the player's list
    public void addPiece(Piece piece) 
    {
        pieces.add(piece);
    }

    // Remove a piece when it is captured or eliminated
    public void capturedPiece(Piece piece) 
    {
        pieces.remove(piece);
    }

    // Check if the player has any pieces left
    public boolean hasPiecesLeft() 
    {
    	if(pieces.isEmpty())
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
}
