package ccprog3_mco;

import javax.swing.JOptionPane;

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
    protected int power,originalPower; 
    private Player player;
    protected Tile currentTile,thisTile;
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
    public Piece(Tile tile, Player player, int power) {
        this.currentTile = tile;
        this.player = player;
        this.power = power;
        this.originalPower = power;
        tile.setPiece(this); 
        player.addPiece(this); 
    }
    
    
    public void moveTo(Tile targetTile) 
    {
        // is base
        if(targetTile.getType().equals("Base"))
        {
        	if(!ownTile(targetTile))
        	{
            if(canMove(targetTile))
            {           	
                move(targetTile);
                JOptionPane.showMessageDialog(null, "Player " + this.getPlayer().getPlayerId() + " wins by capturing the enemy base!");
                System.exit(0); //closes the game
            }
        	}
 
        }
        //is  trap
        else if(targetTile.getType().equals("Trap"))
        {
        	if(!ownTile(targetTile))
        	{
            if(canMove(targetTile))
            {                  
            	JOptionPane.showMessageDialog(null, currentTile.getPiece().getType()+"has stepped on the enemy trap and has their power drained", "Trapped", JOptionPane.WARNING_MESSAGE); //shows an error message
                 move(targetTile); // Move the piece to the new EmptyTile
                 this.stun();
                 
            }
        	}
        	else
        	{
        		if(targetTile.getPiece() != null)
        		{
        			if(isHigherPower(targetTile))
        			{
        				currentTile.setPiece(this); //deletes piece from the tile
        		        targetTile.setPiece(null); //puts it in the new tile
        		        currentTile = currentTile; //update position 
        			}
        		}
        	}
                             
        }
        
        else if (currentTile.getType().equals("Trap") && !targetTile.getType().equals("Trap")) 
        {                          	
            	this.recover(); // Restore the piece's power
                move(targetTile);
                            
        }
        // is lake
        else if(targetTile.getType().equals("Lake"))
        {
            if(canMove(targetTile))
            {
                if(canGoToLake(targetTile))
                {
                    move(targetTile);
                }else
                {
                    JOptionPane.showMessageDialog(null,"Only Rat can cross the Lake");
                }   
            }
            
        //is not an empty tile
        }
        else if(targetTile.getPiece() != null)
        { 
            if(isValidMove(targetTile))
            {
            	Piece capturedPiece = targetTile.getPiece();
                capturedPiece.getPlayer().capturedPiece(capturedPiece); // Remove the captured piece
                move(targetTile);
            }
        }
        // is an empty tile
        else if (targetTile.getPiece() == null && isValidMove(targetTile)) 
        {
            move(targetTile);
        }
        
     }
    
    public void move(Tile targetTile)
    {
        currentTile.setPiece(null); //deletes piece from the tile
        targetTile.setPiece(this); //puts it in the new tile
        currentTile = targetTile; //update position 
    }
    
    public boolean isValidMove(Tile targetTile)
    {
        // Allow movement into an empty tile
        if(targetTile.getPiece() == null) 
        {
            if(canMove(targetTile))
            {
                return true;
            }
            
            if(ownTile(targetTile))
            {
            	return false;
            }
            else
            {
                return false;
            }
        }
        
        else if(targetTile.getPiece() != null)
        {   // If there's a piece in the target tile, check if it can be captured
        	
            if(isHigherPower(targetTile))
            {            	
                if(canMove(targetTile))
                {
                    if(currentTile.getType().equals("Lake") && !isRat())
                    {            	
                    	JOptionPane.showMessageDialog(null, "Rat cannot capture any pieces outside the lake while inside the lake!", "Invalid Move", JOptionPane.ERROR_MESSAGE); //shows an error message
                        return false;
                    }
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        return false;
    }
    
    
    public boolean canMove(Tile targetTile) {
        int targetX = targetTile.getX();
        int targetY = targetTile.getY();
        int currentX = currentTile.getX();
        int currentY = currentTile.getY();

        // Check if the piece is stunned
        if (stunned) 
        {
            JOptionPane.showMessageDialog(null, 
                currentTile.getPiece().getPlayer().getName() + "'s " + currentTile.getPieceName() + " is stunned and thus cannot move",
                "Invalid Move", 
                JOptionPane.ERROR_MESSAGE);
            currentTile.getPiece().stunned = false;
            return false;
        }

        // Standard movement logic: move 1 tile horizontally or vertically
        if ((Math.abs(targetX - currentX) + Math.abs(targetY - currentY)) == 1) 
        {
            return true;
        }

        // Special case: Tiger and Lion hopping over lakes
        if ((this.getType().equals("Tiger") || this.getType().equals("Lion"))) 
        {
            // Check if the movement is in a straight line (horizontal or vertical)
            if (currentX == targetX || currentY == targetY) {
                // Check if the path is entirely over lake tiles
                if (isPathOverLake(currentTile, targetTile)) {
                    // Check if there is a Rat blocking the path on lake tiles
                    if (alongthePath(currentX, currentY, targetX, targetY)) {
                        return true; // Allow the hop over lakes
                    } else {
                        JOptionPane.showMessageDialog(null, "A Rat is blocking the path!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                } else {
                    // Tigers and Lions cannot move more than 1 tile unless crossing a lake
                    JOptionPane.showMessageDialog(null, "Cannot move in that direction!(Unless hopping across the lake)", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        
        // Invalid move
        JOptionPane.showMessageDialog(null, "Cannot move in that direction!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
        return false;            
    }

    public boolean isHigherPower(Tile targetTile){
        // If the tile is empty, allow movement
        if(targetTile.getPiece() == null) 
        {
            return true;
        }

        Piece targetPiece = targetTile.getPiece();

        // check if the current piece is a Rat and the target is an Elephant
        if(this.getType().equals("Rat") && targetPiece.getType().equals("Elephant"))
        {      	
            return true; // Rat can eat Elephant
        }
        if(ownPiece(targetTile))
        {
        	JOptionPane.showMessageDialog(null, "Cannot capture your own piece!", "Invalid Move", JOptionPane.ERROR_MESSAGE); //shows an error message
            return false;
        }

        // If the current piece has equal or higher power, allow capturing
        if(this.getPower() >= targetPiece.getPower())
        {
            if(this.getType().equals("Elephant") && targetPiece.getType().equals("Rat"))
            {
            	JOptionPane.showMessageDialog(null, "Special Rule: (Elephant cannot capture Rat)", "Invalid Move!", JOptionPane.ERROR_MESSAGE); //shows an error message
                return false;
            }
            return true;
        }

        JOptionPane.showMessageDialog(null, "Cannot move: " + this.getType() +"(Power:"+this.getPower()+") "+" cannot eat " + targetPiece.getType()+"(Power:"+targetPiece.getPower()+") ","Invalid Move!", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    public boolean ownPiece(Tile targetTile)
    {
        Piece targetPiece = targetTile.getPiece();
        if(targetPiece==null)
        {
            return false;
        }
        // prevents capturing own piece
        if(this.getPlayerId() == targetPiece.getPlayerId())
        {
            return true; // Cannot capture own piece
        }
        return false;
    }

    public boolean ownTile(Tile targetTile)
    {
        
        if(targetTile.getType().equals("Trap"))
        {
            Trap traptile = (Trap) targetTile;
            if(traptile.getPlayer() == this.getPlayerId())
            {
            	if(traptile.getPiece() == null)
            	{
            	JOptionPane.showMessageDialog(null, "Cannot step onto your own trap!", "Invalid Move!", JOptionPane.ERROR_MESSAGE); //shows an error message
            	
            	return true;
            	}
            	else
            	{
            	return true;
            	}
            }
            else
            return false;
        }
        // prevents capturing own piece
        else if(targetTile.getType().equals("Base"))
        {
        	Base basetile = (Base) targetTile;
        	 if(basetile.getPlayer() == this.getPlayerId())
             {
             	JOptionPane.showMessageDialog(null, "Cannot go to your own base!", "Invalid Move!", JOptionPane.ERROR_MESSAGE); //shows an error message
             	return true;
             }
            return false; // Cannot capture own piece
        }
        return false;
    }
    
    public boolean canGoToLake(Tile targetTile) 
    {
        if (targetTile.getType().equals("Lake") && isRat()) {
            return true; // Rats can enter lakes
        }

        return false;
    }
    
    public boolean isRat(){//returns true when its not a rat
        if(currentTile.getPieceName().equals("Rat")){
            return true;
        }
        return false;
    }
   
    public boolean alongthePath(int preX, int preY, int targetX, int targetY) 
    {
        Board board = currentTile.getBoard(); // Get the board from the current tile

        // Check vertical movement (upward direction)
        if (preX > targetX && preY == targetY) {
            for (int x = preX - 1; x > targetX; x--) {
                Tile intermediateTile = board.getTile(x, preY); // Get the tile on the path
                if (intermediateTile != null && intermediateTile.getType().equals("Lake") && intermediateTile.getPiece() != null && intermediateTile.getPiece().getType().equals("Rat")) {
                    // Path is blocked by a Rat on a lake tile
                    return false;
                }
            }
        }
        
        // Check vertical movement (downward direction)
        if (preX < targetX && preY == targetY) {
            for (int x = preX + 1; x < targetX; x++) {
                Tile intermediateTile = board.getTile(x, preY); // Get the tile on the path
                if (intermediateTile != null && intermediateTile.getType().equals("Lake") && intermediateTile.getPiece() != null && intermediateTile.getPiece().getType().equals("Rat")) {
                    // Path is blocked by a Rat on a lake tile
                    return false;
                }
            }
        }

        // Check horizontal movement (left direction)
        if (preY > targetY && preX == targetX) {
            for (int y = preY - 1; y > targetY; y--) {
                Tile intermediateTile = board.getTile(preX, y); // Get the tile on the path
                if (intermediateTile != null && intermediateTile.getType().equals("Lake") && intermediateTile.getPiece() != null && intermediateTile.getPiece().getType().equals("Rat")) {
                    // Path is blocked by a Rat on a lake tile
                    return false;
                }
            }
        }

        // Check horizontal movement (right direction)
        if (preY < targetY && preX == targetX) {
            for (int y = preY + 1; y < targetY; y++) {
                Tile intermediateTile = board.getTile(preX, y); // Get the tile on the path
                if (intermediateTile != null && intermediateTile.getType().equals("Lake") && intermediateTile.getPiece() != null && intermediateTile.getPiece().getType().equals("Rat")) {
                    // Path is blocked by a Rat on a lake tile
                    return false;
                }
            }
        }

        // If no obstacles are found, the path is clear
        return true;
    }
    
    public boolean isPathOverLake(Tile startTile, Tile endTile) {
        int startX = startTile.getX();
        int startY = startTile.getY();
        int endX = endTile.getX();
        int endY = endTile.getY();

        // Determine the direction of movement
        int deltaX = Integer.compare(endX, startX);
        int deltaY = Integer.compare(endY, startY);

        // Check each tile along the path
        int currentX = startX + deltaX;
        int currentY = startY + deltaY;
        while (currentX != endX || currentY != endY) 
        {
            Tile currentTile = startTile.getBoard().getTile(currentX, currentY);
            if (currentTile == null || !currentTile.getType().equals("Lake")) 
            {
                return false; // Path is not entirely over a lake
            }
            currentX += deltaX;
            currentY += deltaY;
        }

        return true; // Path is entirely over a lake
    }
    
    public String getType() 
    {
        return this.getClass().getSimpleName(); // Returns the class name (e.g., "Soldier", "General")
    }
    
    
    public String display() 
    {
        return "?";
    }

    public void stun() 
    {
        stunned = true;
        power = 0; // Power is temporarily set to 0
    }
    
    public Tile getCurrentTile() 
    {
        return currentTile;
    }
    
    public void recover() 
    {
        stunned = false;
        power = originalPower; // Restore original power
    }

    public int getPower() 
    {
        return power; 
    }

    public Player getPlayer()
    {
    	return player;
    }
    
    public int getPlayerId()
    {
    	return player.getPlayerId();
    }
    
   

}
