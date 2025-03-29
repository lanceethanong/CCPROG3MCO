package ccprog3_mco;

public abstract class Board {
	private String boardName;
    protected Tile[][] board;
    private Player player1;
    private Player player2;
    public static final int SQUARE_SIZE = 80;
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;
    
    public Board(String name, Player player1, Player player2) {
        this.boardName = name;
        board = new Tile[getX()][getY()]; 
        this.player1 = player1;
        this.player2 = player2;
        initializeTiles();
        initializePieces(player1, player2);
    }

    public String getBoardName() {
        return this.boardName;
    }

    public void setBoardName(String name) {
        this.boardName = name;
    }
    
    public abstract Tile getTile(int x, int y);
    public abstract void initializeTiles();
    public abstract void initializePieces(Player player1, Player player2);
    public abstract int getX();
    public abstract int getY();
    
    public boolean isGameOver() {
        for (int row = 0; row < getX(); row++) {
            for (int col = 0; col < getY(); col++) {
                Tile tile = board[row][col];
                if (tile instanceof Base && tile.getPiece() != null) {
                    Piece piece = tile.getPiece();
                    Base base = (Base) tile;
                    if (piece.getPlayerId() != base.getPlayer()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public Tile[][] getBoard() {
        return board;
    }
    
    public Player getPlayer1() {
        return player1;
    }
    
    public Player getPlayer2() {
        return player2;
    }
}