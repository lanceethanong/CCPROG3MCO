package ccprog3_mco;

public class ClassicBoard extends Board {
    public ClassicBoard(Player player1, Player player2) {
        super("Classic Board (9x7)", player1, player2);
    }

    public int getX() {
        return 9;
    }

    public int getY() {
        return 7;
    }

    public void initializeTiles() {
        board[3][1] = new Lake(this,3,1);
        board[3][2] = new Lake(this,3,2);
        board[4][1] = new Lake(this,4,1);
        board[4][2] = new Lake(this,4,2);
        board[5][1] = new Lake(this,5,1);
        board[5][2] = new Lake(this,5,2);
        
        board[3][4] = new Lake(this,3,4);
        board[3][5] = new Lake(this,3,5);
        board[4][4] = new Lake(this,4,4);
        board[4][5] = new Lake(this,4,5);
        board[5][4] = new Lake(this,5,4);
        board[5][5] = new Lake(this,5,5);
        
        board[0][3] = new Base(this,0,3,1);
        board[8][3] = new Base(this,8,3,2);
        
        board[0][2] = new Trap(this,0,2,1);
        board[0][4] = new Trap(this,0,4,1);
        board[1][3] = new Trap(this,1,3,1);
        board[8][2] = new Trap(this,8,2,2);
        board[8][4] = new Trap(this,8,4,2);
        board[7][3] = new Trap(this,7,3,2);
        
        for(int i = 0; i < getX();i++) {
            for(int j = 0; j < getY();j++) {
                if(!(board[i][j] instanceof Trap) && !(board[i][j] instanceof Base) && !(board[i][j] instanceof Lake)) {
                    board[i][j] = new EmptyTile(this,i,j);
                }
            }
        }
    }
    
    public void initializePieces(Player player1, Player player2) {
        // Player 1 pieces
        new Lion(board[0][0], player1);
        new Tiger(board[0][6], player1);
        new Dog(board[1][1], player1);
        new Cat(board[1][5], player1);
        new Rat(board[2][0], player1);
        new Leopard(board[2][2], player1);
        new Wolf(board[2][4], player1);
        new Elephant(board[2][6], player1);

        // Player 2 pieces
        new Lion(board[8][6], player2);
        new Tiger(board[8][0], player2);
        new Dog(board[7][5], player2);
        new Cat(board[7][1], player2);
        new Rat(board[6][6], player2);
        new Leopard(board[6][4], player2);
        new Wolf(board[6][2], player2);
        new Elephant(board[6][0], player2);
    }
    
    @Override
    public Tile getTile(int x, int y) {
        if (x < 0 || x >= getX() || y < 0 || y >= getY()) {
            return null;
        }
        return board[x][y];
    }
}








