package ccprog3_mco;

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
    protected int power; 
    private Player player;
    protected Tile currentTile;
    private boolean stunned = false;

    /**
     * Method: Piece
     * Description: Constructor used to initialize new piece Objects
     * @param power: integer to initialize powerlevel
     * @param player: integer to initialize which player it belongs to
     * @param type: String identifier             
     * @author Lance Ethan S. Ong S14
     * 
     */
    public Piece(Tile tile, Player player, int power) 
    {
        this.currentTile = tile;
        this.player = player;
        this.power = power;
        
        tile.setPiece(this); // Automatically assign to tile
        player.addPiece(this); // Add piece to player's collection
    }
    

    
    public String getType() 
    {
        return this.getClass().getSimpleName(); // Returns the class name (e.g., "Soldier", "General")
    }
    
    public boolean movePiece(Tile board[][],char move)
    {
    	    	
    	int x = currentTile.getX();
        int y = currentTile.getY();
        
    	if(currentTile.getPiece() == null)
    	{
    		System.out.println("This is a"+currentTile.getType()+"and is not a Piece! Try Again");
    		return false;
    	}
    	
    	if(stunned) 
    	{
            System.out.println(display()+"is stunned and cant be moved");
            stunned = false; // Stun effect wears off after missing one turn
            power = getPower(); // Restore power
            return false;
        }
    	
        int newX = x, newY = y;
        
        switch (move) 
        {
        case 'W': case 'w': newX--; break; 
        case 'A': case 'a': newY--; break; 
        case 'S': case 's': newX++; break; 
        case 'D': case 'd': newY++; break; 
        default: return false; // Invalid direction
        }
        
        // Prevent out-of-bounds movement
        if (newX < 0 || newX >= board.length || newY < 0 || newY >= board[0].length) 
        {
            System.out.println("Move out of bounds! Try again.");
            return false;
        }

        // Get the destination tile
        Tile destinationTile = board[newX][newY];
        
        if(destinationTile.getType().equals("Base"))
        {
        	Base base = (Base) destinationTile;
        	if (base.getPlayer() != getPlayer().getPlayerId()) // Checks if the space is the enemy's base and not their own
            {
        		this.currentTile.setPiece(null); // Clear old position
        	    destinationTile.setPiece(this);
        	    this.currentTile = destinationTile;
                return true; // Returns a valid move(Win condition handled by Game class)
            }
        	else
        	{
        		System.out.println("Cannot go to your own base!");
                return false; // Invalid move
        	}
        }
        
        if(destinationTile.getType().equals("Lake"))
        {
        	if(currentTile.getType().equals("Rat"))
        	{
        		this.currentTile.setPiece(null); // Clear old position
        	    destinationTile.setPiece(this);
        	    this.currentTile = destinationTile;
        	    return true;
        	}
//        	if(currentTile.getType().equals("Lion") || currentTile.getType().equals("Tiger"))
//        	{
//        		this.currentTile.setPiece(null); // Clear old position
//        	    destinationTile.setPiece(this);
//        	    this.currentTile = destinationTile;
//        	    return true;
//        	}
        	else
        	{
        	System.out.println(getType()+"cannot go across the lake only Lions and Tigers can jump across. Try again.");
            return false; // invalid move	
        	}
        }
        
        if(destinationTile.getPiece() != null)
        {
        	Piece tPiece = destinationTile.getPiece();
        	if (tPiece.getPlayerId() == this.getPlayerId()) 
        	{
        		    System.out.println("You cannot move onto your own piece!");
        		    return false;
        	}
        	if(canCapture(tPiece))
        	{
        		System.out.println(display() + "has captured the enemy" + tPiece.display() + "!");
                tPiece.getPlayer().capturedPiece(tPiece);
                this.currentTile.setPiece(null); // Remove from old position
                destinationTile.setPiece(this);  // Move to new position
                this.currentTile = destinationTile; // Update piece's current tile
                return true;
        	}
        	else
        	{
        		System.out.println("You cannot capture"+tPiece.getType()+"power "+tPiece.getPower()+"As it is stronger than "+getType()+"power "+getPower());
        		System.out.println("Try again!");
                return false; //Invalid move 
        	}
        }
        
        this.currentTile.setPiece(null); // Clear old position
	    destinationTile.setPiece(this);
	    this.currentTile = destinationTile;
	    return true;
        
        
    	
    }
    
    public String display() {
        return "?";
    }
    public boolean canMove() 
    {
        if(stunned)
        {
        	return false;
        }
        else	
        {
        	return true;
        }
        	
    }

    public void stun() 
    {
        stunned = true;
        power = 0; // Power is temporarily set to 0
    }

    public void recover() 
    {
        stunned = false;
        power = getPower(); // Restore original power
    }

    public int getPower() 
    {
        return power; 
    }

    public boolean canCapture(Piece target) 
    {
    	if(getType().equals("Rat") && target.getType().equals("Elephant"))
    	{
    		return true;
    	}
    	if(getType().equals("Elephant") && target.getType().equals("Rat"))
    	{
    		return false;
    	}
    	if(this.power >= target.power)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

    public Player getPlayer()
    {
    	return player;
    }
    
    public int getPlayerId()
    {
    	return player.getPlayerId();
    }
    
    public boolean GameOver() 
    {
    	if (currentTile instanceof Base) 
    	{  
    	    Base base = (Base) currentTile;
    	    if (base.getPlayer() != this.getPlayer().getPlayerId()) 
    	    {
    	        System.out.println(this.display() + " has captured the enemy base! Game Over.");
    	        return true;
    	    }
    	    return false;
    	}
    	return false;
    }

}




