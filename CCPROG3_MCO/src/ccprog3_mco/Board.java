package ccprog3_mco;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
    
    public void displayBoard(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 16));

        for (int row = 0; row < getX(); row++) {
            for (int col = 0; col < getY(); col++) {
                String tileType = board[row][col].display();

                if (tileType.equals("~ ")) {
                    g.setColor(Color.CYAN); // Lake
                } else if (tileType.startsWith("P")) {
                    g.setColor(Color.BLUE); // Base
                } else if (tileType.equals("# ")) {
                    g.setColor(Color.RED); // Trap
                } else {
                    g.setColor(Color.LIGHT_GRAY); // Default ground
                }

                int x = col * SQUARE_SIZE;
                int y = row * SQUARE_SIZE;
                g.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

                // Draw P1 or P2 on the base
                if (tileType.startsWith("P")) {
                    int playerId = tileType.equals("P1") ? 1 : 2;
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 18));
                    g.drawString("P" + playerId, x + SQUARE_SIZE / 4, y + SQUARE_SIZE / 2);
                }

                if (board[row][col].getPiece() != null) {
                    Piece piece = board[row][col].getPiece();
                    drawPiece(g, piece, x, y, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
    }

    private void drawPiece(Graphics2D g, Piece piece, int x, int y, int width, int height) {
        String pieceType = piece.getType().toLowerCase();
        int playerId = piece.getPlayerId();
        
        String imagePath = "src/Pict/" + pieceType + playerId + ".png";
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) throw new IOException("Image not found: " + imagePath);
            Image pieceImage = ImageIO.read(imageFile);
            g.drawImage(pieceImage, x + 5, y + 5, width - 10, height - 10, null);
        } catch (IOException | NullPointerException e) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(pieceType.charAt(0) + String.valueOf(playerId), x + width / 4, y + height / 2);
        }
    }
}