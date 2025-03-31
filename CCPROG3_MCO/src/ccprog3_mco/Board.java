// BoardModel.java (extracted from Board.java)
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
        this.player1 = player1;
        this.player2 = player2;
        this.board = new Tile[getX()][getY()]; // Initialize the board array
        initializeTiles();
        initializePieces(player1, player2);
    }

    public String getBoardName() {
        return this.boardName;
    }

    public void setBoardName(String name) {
        this.boardName = name;
    }
    
    protected abstract Tile getTile(int x, int y);
    protected abstract void initializeTiles();
    protected abstract void initializePieces(Player player1, Player player2);
    protected abstract int getX();
    protected abstract int getY();
    
    
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