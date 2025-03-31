package ccprog3_mco;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameView extends JPanel {
    private GameModel model;
    private GameController controller;
    private BoardView boardView;
    private BoardController boardController;
    private Board board;
    // UI components
    private JPanel nameScreen, animalScreen, turnPanel;
    private JTextField player1Field, player2Field;
    private JButton nextButton;
    private JButton[] animalButtons;
    private JLabel p1AnimalLabel, p2AnimalLabel, p1Label, p2Label, jklabel, turnIndicatorTop, turnIndicatorBottom;
    private JTextArea jklabel2;
    
    public GameView(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(GameModel.W, GameModel.H));
        setBackground(Color.black);
        setLayout(new BorderLayout());
        
        setupNameScreen();
    }
    
    private void setupNameScreen() {
        nameScreen = new JPanel();
        nameScreen.setLayout(new GridLayout(4, 2, 10, 10));
        nameScreen.setBackground(new Color(34, 139, 34));
        
        jklabel = new JLabel("Jungle King");
        jklabel.setForeground(Color.YELLOW);
        jklabel.setFont(new Font("Calibri", Font.PLAIN, 50));
        
        jklabel2 = new JTextArea(
            "Instructions\n" +
            "Player 1 controls the top\n" +
            "Player 2 controls the bottom.\n" +
            "First to capture the enemy base Wins!\n" +
            "Only rats can cross lake blocks while tigers and lions can jump across\n" +
            "Be careful of traps as it drains your powers and stuns you for one turn\n" +
            "Animals can only capture other animals if they have the same or\n" +
            "higher power(With the exception of Rat and Elephant)\n" +
            "Good luck and have fun!"
        );
        jklabel2.setForeground(Color.WHITE);
        jklabel2.setFont(new Font("Calibri", Font.PLAIN, 17));
        jklabel2.setBackground(new Color(34, 139, 34));
        jklabel2.setEditable(false);
        
        p1Label = new JLabel("Player 1 Name:");
        p1Label.setForeground(Color.WHITE);
        p1Label.setFont(new Font("Calibri", Font.PLAIN, 30));
        
        player1Field = new JTextField();
        player1Field.setPreferredSize(new Dimension(50, 50));
        player1Field.setFont(new Font("Calibri", Font.PLAIN, 40));
        player1Field.setForeground(Color.red);
        player1Field.setText("Player 1");
        
        p2Label = new JLabel("Player 2 Name:");
        p2Label.setForeground(Color.WHITE);
        p2Label.setFont(new Font("Calibri", Font.PLAIN, 30));
        
        player2Field = new JTextField();
        player2Field.setPreferredSize(new Dimension(100, 100));
        player2Field.setFont(new Font("Calibri", Font.PLAIN, 40));
        player2Field.setForeground(Color.BLUE);
        player2Field.setText("Player 2");
        
        nextButton = new JButton();
        nextButton.setBounds(0, 100, 100, 50);
        nextButton.setText("Next");
        nextButton.setFont(new Font("Calibri", Font.PLAIN, 30));
        nextButton.addActionListener(e -> controller.handleNextButton());
        
        nameScreen.add(jklabel);
        nameScreen.add(jklabel2);
        nameScreen.add(p1Label);
        nameScreen.add(player1Field);
        nameScreen.add(p2Label);
        nameScreen.add(player2Field);
        nameScreen.add(nextButton);
        
        add(nameScreen, BorderLayout.CENTER);
    }
    
    public void setupAnimalScreen() {
        remove(nameScreen);
        
        animalScreen = new JPanel();
        animalScreen.setLayout(new BorderLayout());
        
        JPanel animalSelectionPanel = new JPanel();
        animalSelectionPanel.setLayout(new GridLayout(3, 4, 10, 10));
        
        animalButtons = new JButton[8];
        for (int i = 0; i < 8; i++) {
            int index = i;
            animalButtons[i] = new JButton(String.valueOf(i + 1));
            animalButtons[i].setFont(new Font("Calibri", Font.PLAIN, 30));
            animalButtons[i].addActionListener(e -> controller.handleAnimalSelection(index));
            animalSelectionPanel.add(animalButtons[i]);
        }
        
        p1AnimalLabel = new JLabel(model.getPlayer1Name() + " : Not selected");
        p2AnimalLabel = new JLabel(model.getPlayer2Name() + " : Not selected");
        animalSelectionPanel.add(p1AnimalLabel);
        animalSelectionPanel.add(p2AnimalLabel);
        
        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new GridLayout(9, 1, 5, 5));
        rankingPanel.setPreferredSize(new Dimension(200, 300));
        rankingPanel.setBackground(Color.LIGHT_GRAY);
        
        JLabel titleLabel = new JLabel("Power Ranking");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        rankingPanel.add(titleLabel);
        
        for (int i = 0; i < model.getAnimalRanks().length; i++) {
            JLabel rankLabel = new JLabel((i + 1) + ". " + model.getAnimalRanks()[i]);
            rankLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
            rankLabel.setHorizontalAlignment(JLabel.CENTER);
            rankingPanel.add(rankLabel);
        }
        
        animalScreen.add(animalSelectionPanel, BorderLayout.CENTER);
        animalScreen.add(rankingPanel, BorderLayout.EAST);
        
        add(animalScreen, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    // In GameView.java
    public void startGame() {
        remove(animalScreen);

        // Create a main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Add turn indicators
        turnIndicatorTop = new JLabel(model.getPlayer1().getName() + "'s Turn", SwingConstants.CENTER);
        turnIndicatorBottom = new JLabel(model.getPlayer2().getName() + "'s Turn", SwingConstants.CENTER);

        turnIndicatorTop.setFont(new Font("Calibri", Font.BOLD, 32));
        turnIndicatorBottom.setFont(new Font("Calibri", Font.BOLD, 32));

        turnPanel = new JPanel();
        turnPanel.setLayout(new GridLayout(2, 1, 10, 10));
        turnPanel.setPreferredSize(new Dimension(250, 100));
        turnPanel.setBackground(Color.BLACK);

        turnPanel.add(turnIndicatorTop);
        turnPanel.add(turnIndicatorBottom);

        updateTurnIndicator();

        // Create board panel
        JPanel boardPanel = new JPanel() 
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                if (model.getBoard() != null) 
                {
                	board = model.getBoard();
                    boardView = new BoardView(board);
                    boardController = new BoardController(board, boardView);
                    boardView.displayBoard((Graphics2D) g);
                    // Draw active piece if being dragged
                    if (boardController.getActivePiece() != null) 
                    {
                        drawPieceAtCursor((Graphics2D) g, boardController.getActivePiece(), 
                                        controller.getMouseX(), controller.getMouseY());
                    }
                }
            }
        };
        boardPanel.setPreferredSize(new Dimension(
            model.getBoard().getY() * Board.SQUARE_SIZE,
            model.getBoard().getX() * Board.SQUARE_SIZE
        ));

        // Add components to main panel
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(turnPanel, BorderLayout.EAST);
        mainPanel.setOpaque(false);
        boardPanel.setOpaque(false);
        turnPanel.setOpaque(false);
        add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    
    public void updateTurnIndicator() {
        if (model.getCurrentPlayer() == 1) {
            turnIndicatorTop.setForeground(Color.RED);
            turnIndicatorBottom.setForeground(Color.BLACK);
        } else {
            turnIndicatorTop.setForeground(Color.BLACK);
            turnIndicatorBottom.setForeground(Color.BLUE);
        }
    }
    
    public void updateAnimalSelection(int player, String animal) {
        if (player == 1) {
            p1AnimalLabel.setText(model.getPlayer1Name() + " : " + animal);
        } else {
            p2AnimalLabel.setText(model.getPlayer2Name() + " : " + animal);
        }
    }

    public void disableAnimalButton(int index) {
        animalButtons[index].setEnabled(false);
    }

    public void revealAllAnimals() {
        for (int i = 0; i < animalButtons.length; i++) {
            String animal = model.getAnimals().get(i);
            if (animal != null) {
                animalButtons[i].setText(animal);
            }
            animalButtons[i].setEnabled(false);
        }
    }
    
    public String getPlayer1NameInput() {
        return player1Field.getText();
    }
    
    public String getPlayer2NameInput() {
        return player2Field.getText();
    }
    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model.getBoard() != null) {
            Graphics2D g2 = (Graphics2D) g;
            model.getBoard().getBoard();
            
            if (model.getActivePiece() != null) 
            {
                drawPieceAtCursor(g2, model.getActivePiece(), controller.getMouseX(), controller.getMouseY());
            }
        }
    }
    
    private void drawPieceAtCursor(Graphics2D g2, Piece piece, int x, int y) 
    {
        String pieceType = piece.getType().toLowerCase();
        int playerID = piece.getPlayerId();
        
        String imagePath = "src/Pictures/" + pieceType + playerID + ".png";
        try 
        {
            Image pieceImage = ImageIO.read(new File(imagePath));
            int pieceSize = Board.SQUARE_SIZE - 10;
            g2.drawImage(pieceImage, x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize, null);
        } catch (IOException e) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString(pieceType.charAt(0) + String.valueOf(playerID), x - 10, y);
        }
    }
}