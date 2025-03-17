package ccprog3_mco;

 public class ClassicBoard extends Board 
 {
        public ClassicBoard() {
            super("Classic Board (9x7)");
        }


        public int getX() {
            return 9;
        }


        public int getY() {
            return 7;
        }

        public void initializeTiles() 
        {
          board[3][1] = new Lake(3,1);
          board[3][2] = new Lake(3,2);
          board[4][1] = new Lake(4,1);
          board[4][2] = new Lake(4,2);
          board[5][1] = new Lake(5,1);
          board[5][2] = new Lake(5,2);
          board[3][4] = new Lake(3,4);
          board[3][5] = new Lake(3,5);
          board[4][4] = new Lake(4,4);
          board[4][5] = new Lake(4,5);
          board[5][4] = new Lake(5,4);
          board[5][5] = new Lake(5,5);
          
          board[0][3] = new Base(0,3,1);
          board[8][3] = new Base(8,3,2);
          
          board[0][2] = new Trap(0,2,1);
          board[0][4] = new Trap(0,4,1);
          board[1][3] = new Trap(1,3,1);
          board[8][2] = new Trap(8,2,2);
          board[8][4] = new Trap(8,4,2);
          board[7][3] = new Trap(7,3,2);
          
          for(int i = 0; i < getX();i++)
          {
        	  for(int j = 0; j < getY();j++)
        	  {
        	         if(!(board[i][j] instanceof Trap) &&  !(board[i][j] instanceof Base) && !(board[i][j] instanceof Lake))
        	         {
        	        	 board[i][j] = new EmptyTile(i,j);
        	         }
        	  }
          }
          
        }
        
        public void initializePieces(Player player1, Player player2) 
        {
            // Player 1 pieces
        	board[0][0].setPiece(new Lion(board[0][0], player1));
 		    board[0][6].setPiece(new Tiger(board[0][6], player1));
 		    board[1][1].setPiece(new Dog(board[1][1], player1));
 		    board[1][5].setPiece(new Cat(board[1][5], player1));
 		    board[2][0].setPiece(new Rat(board[2][0], player1));
 		    board[2][2].setPiece(new Leopard(board[2][2], player1));
 		    board[2][4].setPiece(new Wolf(board[2][4], player1));
 		    board[2][6].setPiece(new Elephant(board[2][6], player1));

 		    // Player 2 pieces
 		    board[8][6].setPiece(new Lion(board[8][6], player2));
 		    board[8][0].setPiece(new Tiger(board[8][0], player2));
 		    board[7][5].setPiece(new Dog(board[7][5], player2));
 		    board[7][1].setPiece(new Cat(board[7][1], player2));
 		    board[6][6].setPiece(new Rat(board[6][6], player2));
 		    board[6][4].setPiece(new Leopard(board[6][4], player2));
 		    board[6][2].setPiece(new Wolf(board[6][2], player2));
 		    board[6][0].setPiece(new Elephant(board[6][0], player2));        		   
        }
        
        public Tile getTile(int x, int y) 
        {
            if (x < 0 || x >= getX() || y < 0 || y >= getY()) 
            {
                return null;
            }
            return board[x][y];
        }



}
