package ccprog3_mco;

import javax.swing.*;


public class GameController {
    private GameModel model;
    private GameView view;
    private MouseMovement mouse;

    
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.mouse = new MouseMovement();
        setupMouseListeners(); // Make sure this is called
    }
    
    private void setupMouseListeners() {
        view.addMouseListener(mouse);
        view.addMouseMotionListener(mouse);
    }
    
    public void update() {
        if (model.getBoard() == null) return;
        
        if (mouse.mousePressed && model.getActivePiece() == null) {
            // Handle piece selection
            int row = mouse.y / Board.SQUARE_SIZE;
            int col = mouse.x / Board.SQUARE_SIZE;
            Tile clickedTile = model.getBoard().getTile(row, col);
            
            if (clickedTile != null && clickedTile.getPiece() != null && 
                clickedTile.getPiece().getPlayerId() == model.getCurrentPlayer()) {
                model.setActivePiece(clickedTile.getPiece());
            }
        } 
        else if (!mouse.mousePressed && model.getActivePiece() != null) {
            // Handle piece movement
            int row = mouse.y / Board.SQUARE_SIZE;
            int col = mouse.x / Board.SQUARE_SIZE;
            
            if (model.tryMovePiece(row, col)) {
                view.updateTurnIndicator();
            }
            model.setActivePiece(null);
        }
    }
    
    public void handleNextButton() {
        model.setPlayerNames(view.getPlayer1NameInput(), view.getPlayer2NameInput());
        view.setupAnimalScreen();
    }
    
    public void handleAnimalSelection(int index) {
        if (model.getPlayer1Animal() == null) {
            model.selectAnimal(1, index);
            JOptionPane.showMessageDialog(null,model.getPlayer1Animal(),model.getPlayer1Name()+"'s Selection",JOptionPane.INFORMATION_MESSAGE);
            view.updateAnimalSelection(1, model.getPlayer1Animal());
            view.disableAnimalButton(index); // Disable just the selected button
        } else if (model.getPlayer2Animal() == null) {
            model.selectAnimal(2, index);
            JOptionPane.showMessageDialog(null,model.getPlayer2Animal(),model.getPlayer2Name()+"'s Selection",JOptionPane.INFORMATION_MESSAGE);
            view.updateAnimalSelection(2, model.getPlayer2Animal());
            view.disableAnimalButton(index); // Disable just the selected button

            if (model.bothAnimalsSelected()) {
                // Show all animal names and disable all buttons
                view.revealAllAnimals();

                // Determine and show winner
                String result = model.determineWinner();
                JOptionPane.showMessageDialog(view, result, "Battle Result", JOptionPane.INFORMATION_MESSAGE);

                // Set first player
                int firstPlayer = model.determineIntWinner();
                model.setCurrentPlayer(firstPlayer);

                // Initialize game and start
                model.initializeGame();
                view.startGame();
            }
        }
    }
    
//    private void handleMousePress(int x, int y) {
//        if (model.getBoard() == null) return;
//        
//        int row = y / Board.SQUARE_SIZE;
//        int col = x / Board.SQUARE_SIZE;
//        Tile clickedTile = model.getBoard().getTile(row, col);
//        
//        if (clickedTile != null && clickedTile.getPiece() != null 
//            && clickedTile.getPiece().getPlayerId() == model.getCurrentPlayer()) {
//            model.setActivePiece(clickedTile.getPiece());
//            view.repaint();
//        }
//    }
//    
//    private void handleMouseRelease(int x, int y) {
//        if (model.getActivePiece() != null) {
//            int row = y / Board.SQUARE_SIZE;
//            int col = x / Board.SQUARE_SIZE;
//            
//            if (model.tryMovePiece(row, col)) {
//                view.updateTurnIndicator();
//            }
//            
//            model.setActivePiece(null);
//            view.repaint();
//        }
//    }
    
    // Getters for mouse position
    public int getMouseX() { return mouse.x; }
    public int getMouseY() { return mouse.y; }
}