package ccprog3_mco;


public class Board {
    private Piece[][] board;
    private Lake lake1; // First lake object
    private Lake lake2; // Second lake object
    private static final int rows = 9;
    private static final int cols = 7;
    private Base p1Base;
    private Base p2Base;
    
    public Board() 
    {
        board = new Piece[rows][cols]; // Example 8x8 grid
        lake1 = new Lake(); // Initialize first lake
        lake2 = new Lake(); // Initialize second lake
        p1Base = new Base(1); // Initialize player 1 base
        p2Base = new Base(2); // Initialize player 2 base
        initializePieces();
    }

    public final void initializePieces() {
        // Assign lake1 to positions (3,1) to (4,2)
        board[3][1] = lake1;
        board[3][2] = lake1;
        board[4][1] = lake1;
        board[4][2] = lake1;
        board[5][1] = lake1;
        board[5][2] = lake1;

        // Assign lake2 to positions (3,4) to (4,5)
        board[3][4] = lake2;
        board[3][5] = lake2;
        board[4][4] = lake2;
        board[4][5] = lake2;
        board[5][4] = lake2;
        board[5][5] = lake2;
        
        board[0][3] = p1Base;
        board[8][3] = p2Base;
        
        board[0][0] = new Animal("Lion",7,1);
        board[0][8] = new Animal("Tiger",6,2);
    }

    public void displayBoard() 
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == null) {
                    System.out.print(". "); // Empty cell
                } else {
                    System.out.print(board[i][j].display());
                }
            }
            System.out.println(); // New line after each row
        }
    }
    
    public boolean movePiece(int x, int y, char move) 
    {
        Piece piece = board[x][y];
        if (piece == null) 
        {
            return false; // No piece to move
        }

        int newX = x, newY = y;

        switch (move) {
            case 'W': newX--; break; // Move up
            case 'A': newY--; break; // Move left
            case 'S': newX++; break; // Move down
            case 'D': newY++; break; // Move right
            default: return false; // Invalid direction
        }

        // Boundary check
        if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) 
        {
            return false; // Invalid move
        }

        Piece target = board[newX][newY];

        // 1. Handle Base capture (win condition)
        if (target != null && target.getType().equals("Base")) 
        {
            Base base = (Base) target;
            if (base.getPlayerId() != piece.getPlayerId()) 
            {
                // Capture opponent's base
                board[newX][newY] = piece; // Move the piece to the base
                board[x][y] = null; // Clear the original position
                return true; // This move wins the game
            }
            return false; // Can't enter own base
        }

        // 2. Handle Lakes (impassable except for Rat)
        if (target != null && target.getType().equals("Lake")) 
        {

                if (piece.getType().equals("Animal") && ((Animal) piece).getAnimal().equals("Rat")) 
                {
                    // Rat-specific logic
                    if (board[x][y] == lake1 || board[x][y] == lake2) 
                    {
                        // Rat is already in the lake
                        if (target == lake1 || target == lake2) {
                            // Rat is moving within the lake
                            if (board[newX][newY] == null) 
                            {
                                // Move to an empty lake tile
                                board[newX][newY] = piece;
                                board[x][y] = target; // Restore the lake tile
                                return true;
                            } else if (board[newX][newY].getType().equals("Animal") && ((Animal) board[newX][newY]).getAnimal().equals("Rat")) {
         
                                board[newX][newY] = piece;
                                board[x][y] = target; // Restore the lake tile
                                return true;
                            } else 
                            {
                                return false; // Cannot capture non-Rat pieces in the lake
                            }
                        }
                    } 
                    else 
                    {
                        // Rat is moving into the lake
                        if (board[newX][newY] == null) 
                        {
                            // Move to an empty lake tile
                            board[newX][newY] = piece;
                            board[x][y] = null;
                            return true;
                        } 
                        else if (board[newX][newY].getType().equals("Animal") && ((Animal) board[newX][newY]).getAnimal().equals("Rat")) 
                        {
                            // Capture opponent's Rat in the lake
                            board[newX][newY] = piece;
                            board[x][y] = null;
                            return true;
                        } 
                        else 
                        {
                            return false; // Cannot capture non-Rat pieces in the lake
                        }
                    }
                }
            if(piece.getType().equals("Animal") && ((Animal) piece).getAnimal().equals("Lion") || ((Animal) piece).getAnimal().equals("Tiger"))
            {
            	if(target == lake1)
            	{
            	    if(checkForRat(lake1) == false)
            	    {
            	    	if(piece == board[2][1] || piece == board[2][2])
            	    	{
            	    	newX = 6;
            	    	}

                        if(piece == board[6][1] || piece == board[6][2])
                        {
                        newX = 2;
                        }
                        if(piece == board[3][0] || piece == board[4][0] || piece == board[5][0])
                        {
                        newY = 3;
                        }
                        if(piece == board[3][3] || piece == board[4][3] || piece == board[5][3])
                        {
                        newY = 0;
                        }
            	    }
            	    Piece dp = board[newX][newY];

                    // If the destination is empty, move the Tiger/Lion
                    if (dp == null) {
                        board[newX][newY] = piece;
                        board[x][y] = null;
                        return true;
                    }
                    // If the destination has an opponent's piece, check if it can be captured
                    else if (dp.getType().equals("Animal") && dp.getPlayerId() != piece.getPlayerId()) 
                    {
                        if (canCapture(piece, dp)) {
                            board[newX][newY] = piece; // Capture the opponent's piece
                            board[x][y] = null;
                            return true;
                        } else 
                        {
                            return false; // Cannot capture the opponent's piece
                        }
                    } else 
                    {
                        return false; // Cannot capture your own piece or non-animal pieces
                    }
            	}
            	if(target == lake2)
            	{
            		 if(checkForRat(lake2) == false)
             	    {
             	    	if(piece == board[2][4] || piece == board[2][5])
             	    	{
             	    	 newX = 6;
             	    	 }
                         if(piece == board[6][4] || piece == board[6][5])
                         {
                         newX = 2;
                         }
                         if(piece == board[3][6] || piece == board[4][6] || piece == board[5][6])
                         {
                         newY = 3;
                         }
                         if(piece == board[3][3] || piece == board[4][3] || piece == board[5][3])
                         {
                         newY = 6;
                         }
                         
                         
             	    }
            		 
            		 Piece dp = board[newX][newY];

                     // If the destination is empty, move the Tiger/Lion
                     if (dp == null) 
                     {
                         board[newX][newY] = piece;
                         board[x][y] = null;
                         return true;
                     }
                     // If the destination has an opponent's piece, check if it can be captured
                     else if (dp.getType().equals("Animal") && dp.getPlayerId() != piece.getPlayerId()) 
                     {
                         if (canCapture(piece, dp)) {
                             board[newX][newY] = piece; // Capture the opponent's piece
                             board[x][y] = null;
                             return true;
                         } else 
                         {
                             return false; // Cannot capture the opponent's piece
                         }
                     } else 
                     {
                         return false; // Cannot capture your own piece or non-animal pieces
                     }
            	}
            }
            else
            {
            return false; // Other pieces cannot move into lakes
            }
        }

        // 3. Handle piece interactions
        if (target != null) 
        {
            // Check same team
            if (target.getPlayerId() == piece.getPlayerId()) 
            {
                return false; // Can't move onto same-team pieces
            }

            // Standard combat or special exception
            if (canCapture(piece, target)) 
            {
                board[newX][newY] = piece; // Move the attacking piece
                board[x][y] = null; // Clear the original position
                return true;
            }
            return false; // Attacker loses
        }

        // 4. Move to empty space
        board[newX][newY] = piece;
        board[x][y] = null;
        return true;
    }
    
    public Piece getPiece(int x, int y) 
    {
        return board[x][y];
    }
    
    private boolean checkForRat(Lake lake) 
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getType().equals("Animal") && ((Animal) piece).getAnimal().equals("Rat") && piece == lake) {
                    return true; // Rat is found in the lake
                }
            }
        }
        return false; // No Rat found in the lake
    }
    
    private boolean canCapture(Piece p, Piece t) 
    {
        // Special rule: Rat (power 1) can capture Elephant (power 8)
        if (p.power == 1 && t.power == 8) 
        {
            return true;
        }
        // Standard rule: Attacker must have equal or higher power
        if(p.power >= t.power)
        {
        return true;
        }
        else
        {
        return false;
        }
    }

}

