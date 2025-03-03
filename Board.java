package ccprog3_mco;
/**
 * Class: Board
 * Description: Main class used to handle the main gameBoard which include 
 *              1. Initializing the Board and its pieces
 *              2. Handling Player movement based on inputs from the Game method
 *              3. Handling special conditions for Lake, Base and Capture
 * 
 * @author Lance Ethan S. Ong & Nick Jenson S. Crescini S14
 * 
 */
    public class Board {
	/* The Main objects of the class(All assigned private to follow encapsulation)
	 * Piece[][] board: 2d array Used to create and access a Piece object and assign it to a space on the board(Mainly used for Animal movement)
	 * Lake lake1 & Lake lake2: creates 2 separate Lake objects used to distinguish between which block belongs in which lake
	 * rows: assigns the number of rows the board has, assigned a static value of 9 which is the same every game instance
	 * cols: assigns the number of columns the board has, assigned a static value of 7 which is the same every game instance
	 * Base p1Base & Base p2Base: used to create the Base object for p1 and p2 respectively
	 */
    private Piece[][] board;
    private Lake lake1; 
    private Lake lake2; 
    private static final int rows = 9;
    private static final int cols = 7;
    private Base p1Base;
    private Base p2Base;
    /**
     * Method: Board
     * Description:A constructor that When called initializes a new board Object together with its parameters and respective objects 
     * @author Lance Ethan S. Ong S14
     * 
     */
    public Board() 
    {
        board = new Piece[rows][cols]; //assigns the parameters of Piece
        lake1 = new Lake(); // Initializes lake1
        lake2 = new Lake(); // Initializes lake2
        p1Base = new Base(1); // Initializes p1Base
        p2Base = new Base(2); // Initializes p2Base
        initializePieces(); //Called to initialize all pieces
    }
    /**
     * Method: initializePieces
     * Description:A method used to assign all pieces to their respective positions on the board and assigned final because every game the initial positions are the same
     * @author Lance Ethan S. Ong S14
     * 
     */
    public final void initializePieces() {
        // Assigns the lake blocks that are part of lake1
        board[3][1] = lake1;
        board[3][2] = lake1;
        board[4][1] = lake1;
        board[4][2] = lake1;
        board[5][1] = lake1;
        board[5][2] = lake1;

        // Assign the lake blocks that are part of lake2
        board[3][4] = lake2;
        board[3][5] = lake2;
        board[4][4] = lake2;
        board[4][5] = lake2;
        board[5][4] = lake2;
        board[5][5] = lake2;
        
        // Assign the positions of each player's respective Bases
        board[0][3] = p1Base;
        board[8][3] = p2Base;
        
        // Creates and assigns an Animal Object which include its Name,powerlevel and player it belongs to and then assigns it to a place on the board
        board[8][2] = new Animal("Elephant",8,1);
        board[0][2] = new Animal("Dog",5,2);
        board[6][2] = new Animal("Tiger",6,2);
        board[2][2] = new Animal("Rat",1,1);
    }
    /**
     * Method: displayBoard
     * Description:A method used to display the Board on to the CLI which can be used by players to track the current status of the game 
     * @author Lance Ethan S. Ong S14
     * 
     */
    public void displayBoard() {
        // Print column numbers at the top
        System.out.print("  "); // Space for row numbers
        for (int j = 0; j < cols; j++) {
            System.out.print(j + " "); // Print column numbers
        }
        System.out.println(); // New line after column numbers

        // For loop to initialize all of the blocks into either blank or piece tiles
        for (int i = 0; i < rows; i++) {
            System.out.print(i + " "); // Print row number
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == null) { // If it is a null block
                    System.out.print(". "); 
                } 
                else { // If either an Animal, Base, or Lake Piece is found
                    System.out.print(board[i][j].display()); // Displays it on the board based on what the display method is found in their respective subclasses
                }
            }
            System.out.println(); // New line
        }
    }
    /**
     * Method: movePiece
     * Description:Method used as a way to not only move a piece based on user input 
     *             but also is used as a checker to see whether the move is valid and the respective actions to take once a move is done
     * @param x: integer of x coordinates
     *        y: integer of y coordinates
     *        move: char(W,A,S,D) use to indentify which direction the player wants to move
     * @return true or false: boolean (checks whether move is valid or not)            
     * @author Lance Ethan S. Ong S14
     * 
     */
    public boolean movePiece(int x, int y, char move) 
    {
        Piece piece = board[x][y]; // initializes a new object piece with the Piece data type to the x and y coordinates on the board
        if (piece == null) //If a non piece is selected
        {
            return false; // Invalid move
        }

        int newX = x, newY = y; //used to assign the new space after a move is made
        /*
         * Switch case move used to move a piece
         * (Assume the board is viewed 1 way and the same for each player where the bottom player moves up and the top player moves down to start)
         * W: forward(decreases the x value)
         * A: left(decreases the y value)
         * S: down(increases the x value)
         * D: right(increases the y value)
         */
        switch (move) 
        {
        case 'W': case 'w': newX--; break; 
        case 'A': case 'a': newY--; break; 
        case 'S': case 's': newX++; break; 
        case 'D': case 'd': newY++; break; 
        default: return false; // Invalid direction
        }

        // If a player is trying to move to an out of bounds tile
        if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) 
        {
        	System.out.println("Invalid space!");
            return false; // Invalid move
        }

        Piece target = board[newX][newY]; // Assigns an object target with Piece data type to identify whats on the tile after moving

        /*
         * Conditional statement that : Handles if the user lands on the enemy base signifying the end of the game
         * It checks whether the target is a Piece of the type Base and if it is the enemy base
         */
        if (target != null && target.getType().equals("Base")) // Assumes the target is a Piece and the type of the target is "Base"
        {
            Base base = (Base) target; //Assigns the target to the Base data type(Assumed that it is a base)
            if (base.getPlayerId() != piece.getPlayerId()) // Checks if the space is the enemy's base and not their own
            {
                board[newX][newY] = piece; // Moves the piece to the base
                board[x][y] = null; // Makes previous position back to a blank tile
                return true; // Returns a valid move(Win condition handled by Game class)
            }
            System.out.println("Cannot go to your own base!");
            return false; // Invalid move
        }

        /*
         * Conditional statement that handles all moves that involve the Piece going or interacting with the lake
         * Handles the conditions: 1. Checks whether the object is a Piece
         *                         2. Rat enters lake(Along with interactions with the enemy rat)
         *                         3. Tiger and Lion encounter a lake and whether they can cross it
         *                         4. Other animals enter lake
         *                         5. Checks Whether the rat or the Lion or Tiger can eat the piece across the lake or in the lake
         *                         6. Distinguishes between lakes
         *           
         */
        if (target != null && target.getType().equals("Lake") //if target is in a lake block
                || target == null && (isLake1Position(x,y) || isLake2Position(x,y))) // if target is going out of lakeblock
        {
         // Conditional statement to check whether the animal involved is a rat
            if (piece.getType().equals("Animal") && ((Animal) piece).getAnimal().equals("Rat")) 
            {
                if (board[newX][newY] instanceof Lake)
                {
                    board[newX][newY] = piece;
                    if (isLake1Position(x, y)) 
                    {
                        board[x][y] = lake1; // Restore lake tile
                        
                        
                    } 
                    else if (isLake2Position(x, y)) 
                    {
                        board[x][y] = lake2; // Restore lake tile
                       
                    }
                    else
                    {
                    	board[x][y] = null;
                        
                    }
                    return true;
                }
                 if (board[newX][newY] == null) // If the target block is empty
                  {
                        board[newX][newY] = piece; // Move the rat to the new empty tile

                        if (isLake1Position(x, y)) {
                            board[x][y] = lake1; // Restore lake tile
                            
                        } 
                        else if (isLake2Position(x, y)) {
                            board[x][y] = lake2; // Restore lake tile
                            
                        }
                        else{
                            board[x][y] = null;
                           
                        }
                        return true;
                    }
                    // If the target block has an Elephant and the Rat came from a lake, it cannot capture
                    else if (board[newX][newY] != null && ((Animal) board[newX][newY]).getAnimal().equals("Elephant"))
                    {
                        if (isLake1Position(x, y) || isLake2Position(x, y)) // Check if Rat came from lake
                        {
                            System.out.println("A Rat cannot capture an Elephant right after leaving the lake!");
                            return false; // Invalid move
                        }
                    }
           }
            if(piece.getType().equals("Animal") && ((Animal) piece).getAnimal().equals("Lion") 
                   || ((Animal) piece).getAnimal().equals("Tiger")){
                
                int tempNewX =0;
                int tempNewY = 0;
                if(move == 'W' || move == 'w'){
                    tempNewX = newX-4;
                }if(move == 'S' || move == 's'){
                    tempNewX = newX+4;
                }if(move == 'A' || move == 'a'){
                    tempNewY = newY-3;
                }if(move == 'D' || move == 'd'){
                    tempNewY = newY+3;
                }
                

            	if(!isRatBlockingPath(x,y,tempNewX,tempNewY,move))
            	{
                        if(piece == board[2][1] || piece == board[2][2]) // If they come from the top of the lake
            	    	{
            	    	newX = 6; //lets them go down to the next non lake block
            	    	}

                        if(piece == board[6][1] || piece == board[6][2]) // If they come from the bottom of the lake
                        {
                        newX = 2; //lets them go up to the next non lake block
                        }
                        if(piece == board[3][0] || piece == board[4][0] || piece == board[5][0]) // If they come from the left of the lake
                        {
                        newY = 3; //lets them go right to the next non lake block
                        }
                        if(piece == board[3][3] || piece == board[4][3] || piece == board[5][3]) // If they come from the right of the lake
                        {
                        newY = 0; //lets them go left to the next non lake block
                        }                      
                        if (piece == board[2][4] || piece == board[2][5]) {
                            newX = 6; // Jump to the bottom non-lake block
                        }

                        // If the piece is coming from the bottom of Lake 2
                        if (piece == board[6][4] || piece == board[6][5]) {
                            newX = 2; // Jump to the top non-lake block
                        }

                        // If the piece is coming from the left of Lake 2
                        if (piece == board[3][3] || piece == board[4][3] || piece == board[5][3]) {
                            newY = 6; // Jump to the right non-lake block
                        }

                        // If the piece is coming from the right of Lake 2
                        if (piece == board[3][6] || piece == board[4][6] || piece == board[5][6]) {
                            newY = 3; // Jump to the left non-lake block
                        }
                        Piece dp = board[newX][newY]; //Assigns the dp Object to the new position on the board
                        // If the new space is blank
                        if (dp == null) {
                            board[newX][newY] = piece; // Moves them to the new space
                            board[x][y] = null;
                            return true; // valid move
                        }
                }
            	else
                {
                 return false; // Invalid move, but don't change turn
                }          	
            }
            else //If the animal is not a rat,lion or tiger they cannot cross the lake
            {
            System.out.println("Animal cannot cross the lake. Try again.");
            return false; // invalid move
            }
        }
        
        

        /*
         * Condition for piece interaction that dosent involve the lake
         * It checks for: 1. Whether the target space has a Piece
         *                2. Checks whether it is the enemy's piece and not their own
         *                3. Checks through canCapture whether the player can capture the enemy's piece
         */
        if (target != null) //Assumes that there is a piece 
        {
           
            if (target.getPlayerId() == piece.getPlayerId()) //If the target piece is the player's
            {
            	System.out.println("You cannot capture your own piece");
                return false; //Invalid move
            }

            
            if (canCapture(piece, target)) //Checks if the player can capture the piece
            {
                board[newX][newY] = piece; //Captures the enemy's piece and removes it from the board
                board[x][y] = null; 
                return true; // valid move
            }
            else //if the enemy is stronger
            {
            System.out.println("You cannot capture your enemy piece");
            return false; //Invalid move 
            }
        }
        
        //If the space landed is just a blank space
        board[newX][newY] = piece; //moves the piece
        board[x][y] = null;
        return true; //valid move
    }
    
    /**
     * Method: getPiece
     * Description: When called gets returns the Piece on the board given the x and y
     * @param x
     *        y
     * @return board[x][y]: Piece
     * @author Lance Ethan S. Ong S14
     */
    public Piece getPiece(int x, int y) 
    {
        return board[x][y];
    }
    
    /**
     * Method: checksforRat
     * Description: Checks the Lake object to see whether there is a rat present on the lake which makes it uncrossable for the Lion or Tiger
     * @param lake: Lake
     * @return true or false: boolean
     * @author Lance Ethan S. Ong S14
     */
    private boolean isRatBlockingPath(int startX, int startY, int endX, int endY, char move) {
        if (move == 'w' || move == 'W') 
        {
            for (int i = startX - 1; i > endX + 1; i--) {
                if (board[i][startY] != null && 
                    board[i][startY].getType().equals("Animal") && 
                    ((Animal) board[i][startY]).getAnimal().equals("Rat")) {
                    System.out.println("A rat is blocking the lake crossing at (" + i + "," + startY + ")");
                    return true;
                }
            }
        }if (move == 's' || move == 'S') {
            for (int i = startX + 1; i < endX - 1; i++) {
                if (board[i][startY] != null && 
                    board[i][startY].getType().equals("Animal") && 
                    ((Animal) board[i][startY]).getAnimal().equals("Rat")) {
                    System.out.println("A rat is blocking the lake crossing at (" + i + "," + startY + ")");
                    return true;
                }
            }
        }if (move == 'a' || move == 'A') {
            for (int j = startY - 1; j > endY + 1; j--) {
                if (board[startX][j] != null && 
                    board[startX][j].getType().equals("Animal") && 
                    ((Animal) board[startX][j]).getAnimal().equals("Rat")) {
                    System.out.println("A rat is blocking the lake crossing at (" + startX + "," + j + ")");
                    return true;
                }
            }
        }if (move == 'd' || move == 'D') {
            for (int j = startY + 1; j < endY - 1; j++) {
                if (board[startX][j] != null && 
                    board[startX][j].getType().equals("Animal") && 
                    ((Animal) board[startX][j]).getAnimal().equals("Rat")) {
                    System.out.println("A rat is blocking the lake crossing at (" + startX + "," + j + ")");
                    return true;
                }
            }
        }
            return false;
    }


    private boolean isLake1Position(int x, int y) 
    {
        
    int[][] lake1Positions = 
    {
        {3, 1}, {3, 2}, {4, 1}, {4, 2}, {5, 1}, {5, 2},   
    };
    for(int[] pos:lake1Positions){
        if (pos[0]==x && pos[1]==y){
            return true;
        }
    }
    return false;
    }
    
    private boolean isLake2Position(int x, int y) 
    {
        
    int[][] lake2Positions = {
        {3, 4}, {3, 5}, {4, 4}, {4, 5}, {5, 4}, {5, 5},   
    };
    for(int[] pos:lake2Positions){
        if (pos[0]==x && pos[1]==y){
            return true;
        }
    }
    return false;
    }

    
    /**
     * Method: canCapture
     * Description: checks whether a Piece can eat the other piece based on power level
     * @param p: Piece
     *        t: Piece
     * @return true or false: boolean
     * @author Lance Ethan S. Ong S14
     */
    private boolean canCapture(Piece p, Piece t) 
    {
   

    	if(p.getPower() == 1 && t.getPower() == 8)
    	{
    		return true;
    	}

    	else if(p.getPower() >= t.getPower())
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}

        
    }
    
    public boolean GameOver() 
    {
        // Check if any piece is on the opponent's base
        if(board[0][3].getType().equals("Animal") && board[0][3].getPlayerId() == 2)
        {
        	return true;
        }

        if(board[8][3].getType().equals("Animal") && board[8][3].getPlayerId() == 1)
        {
        	return true;
        }
        
        return false;
    }

}

