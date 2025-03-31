package ccprog3_mco;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BoardView {
    private Board model;
    
    public BoardView(Board model) 
    {
        this.model = model;
    }
    
    public void displayBoard(Graphics2D g) {
        g.setFont(new Font("Arial", Font.BOLD, 16));

        for (int row = 0; row < model.getX(); row++) {
            for (int col = 0; col < model.getY(); col++) {
                String tileType = model.getBoard()[row][col].display();

                if (tileType.equals("~ ")) {
                    g.setColor(Color.CYAN); // Lake
                } else if (tileType.startsWith("P")) {
                    g.setColor(Color.BLUE); // Base
                } else if (tileType.equals("# ")) {
                    g.setColor(Color.RED); // Trap
                } else {
                    g.setColor(Color.LIGHT_GRAY); // Default ground
                }

                int x = col * Board.SQUARE_SIZE;
                int y = row * Board.SQUARE_SIZE;
                g.fillRect(x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE);

                if (tileType.startsWith("P")) {
                    int playerId = tileType.equals("P1") ? 1 : 2;
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 18));
                    g.drawString("P" + playerId, x + Board.SQUARE_SIZE / 4, y + Board.SQUARE_SIZE / 2);
                }

                if (model.getBoard()[row][col].getPiece() != null) {
                    initiatePiece(g, model.getBoard()[row][col].getPiece(), x, y);
                }
            }
        }
    }

    
    private void initiatePiece(Graphics2D g, Piece piece, int x, int y) 
    {
        String pieceType = piece.getType().toLowerCase();
        int playerId = piece.getPlayerId();
        
        String imagePath = "src/Pictures/" + pieceType + playerId + ".png";
        try {
            File imageFile = new File(imagePath);
            Image pieceImage = ImageIO.read(imageFile);
            g.drawImage(pieceImage, x + 5, y + 5, 
                       Board.SQUARE_SIZE - 10, Board.SQUARE_SIZE - 10, null);
        } catch (IOException | NullPointerException e) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(pieceType.charAt(0) + String.valueOf(playerId), 
                        x + Board.SQUARE_SIZE / 4, y + Board.SQUARE_SIZE / 2);
        }
    }

}