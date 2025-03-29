package ccprog3_mco;

import java.awt.Graphics2D;

public class BoardController {
    private Board model;
    private BoardView view;
    private Piece activePiece;
    
    public BoardController(Board model, BoardView view) {
        this.model = model;
        this.view = view;
    }
    
    public boolean isGameOver() {
        return model.isGameOver();
    }
    
    public Tile getTile(int x, int y) {
        return model.getTile(x, y);
    }
    
    public void displayBoard(Graphics2D g) {
        view.displayBoard(g);
    }
    
    public Player getPlayer1() {
        return model.getPlayer1();
    }
    
    public Player getPlayer2() {
        return model.getPlayer2();
    }
    
    public void setActivePiece(Piece piece) {
        this.activePiece = piece;
    }
    
    public Piece getActivePiece() {
        return activePiece;
    }
    
    public boolean tryMovePiece(int row, int col, int currentPlayer) 
    {
        if (activePiece == null) return false;        
        Tile targetTile = model.getTile(row, col);
        if (targetTile == null)
        {
        	
        	return false;
        	
        }
        
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
