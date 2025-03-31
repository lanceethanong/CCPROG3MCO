// BoardController.java
package ccprog3_mco;

public class BoardController {
    private Board model;
    private BoardView view;
    private Piece activePiece;
    
    public BoardController(Board model, BoardView view) 
    {
        this.model = model;
        this.view = view;
    }
    
    public Tile getTile(int x, int y) {
        return model.getTile(x, y);
    }
    
    public void setActivePiece(Piece piece) {
        this.activePiece = piece;
    }
    
    public Piece getActivePiece() 
    {
        return activePiece;
    }
    
    public boolean tryMovePiece(int row, int col, int currentPlayer) 
    {
        if (activePiece == null) return false;        
        Tile targetTile = model.getTile(row, col);
        if (targetTile == null) return false;
        
        if (activePiece.isValidMove(targetTile)) {
            boolean moved = activePiece.moveTo(targetTile);
            if (moved) {
                activePiece = null;
                return true;
            }
        }
        
        activePiece = null;
        return false;
    }
}
