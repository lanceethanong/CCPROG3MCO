package ccprog3_mco;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class GamePanel extends JPanel implements Runnable {
    public static final int W = 1100;
    public static final int H = 800;
    final int FPS = 300;
    Thread gameThread;
    
    private Board board;
    private String player1Name, player2Name;
    private String player1Animal, player2Animal;
    private Player player1;
    private Player player2;
    private int currentPlayer;
    
    MouseMovement mouse = new MouseMovement();
    
    //for the name and picking animal
    private JPanel nameScreen, animalScreen;
    private JTextField player1Field, player2Field;
    private JButton nextButton;
    private List<String> animals;
    private JButton[] animalButtons;
    private JLabel p1AnimalLabel, p2AnimalLabel,p1Label,p2Label,jklabel;
    private JTextArea jklabel2;
    private Piece activeP;
    
    private final String[] animalRanks = {"Elephant", "Lion", "Tiger", "Leopard", "Wolf", "Dog", "Cat", "Rat"};
    
    public GamePanel() 
    {	
        setPreferredSize(new Dimension(W, H));
        setBackground(Color.black);
        setLayout(new BorderLayout());
        
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
        
        setupNameScreen();
    }
   

    
    private void setupNameScreen() 
    {
    	
        nameScreen = new JPanel();
        nameScreen.setLayout(new GridLayout(4, 2, 10, 10));
        nameScreen.setBackground(new Color(34, 139, 34)); // Jungle Green
        jklabel = new JLabel("Jungle King"); // Center text
        jklabel.setForeground(Color.YELLOW);
        jklabel.setFont(new Font("Calibri", Font.PLAIN, 50));
        jklabel2 = new JTextArea
        	(
        		"Instructions\n" +
        	    "Player 1 controls the top\n" +
        	    "Player 2 controls the bottom.\n" +
        	    "First to capture the enemy base Wins!\n" +
        	    "Only rats can cross lake blocks while tigers and lions can jump across\n"
        	    +"Be careful of traps as it drains your powers\n" 
        	    +
        	     "Good luck and have fun!"
        	    
        	);
        jklabel2.setForeground(Color.WHITE);
        jklabel2.setFont(new Font("Calibri", Font.PLAIN, 22));
        jklabel2.setBackground(new Color(34, 139, 34)); // Match background
        jklabel2.setEditable(false);
        p1Label = new JLabel("Player 1 Name:");
        p1Label.setForeground(Color.WHITE); //Sets the color of the font
        p1Label.setFont(new Font("Calibri",Font.PLAIN,30)); //Sets the font and font size
        player1Field = new JTextField();
        player1Field.setPreferredSize(new Dimension(50,50)); // Sets the size of the panel
        player1Field.setFont(new Font("Calibri",Font.PLAIN,40)); //Sets the font and font size
        player1Field.setForeground(Color.red);
        player1Field.setText("Player 1");
        p2Label = new JLabel("Player 2 Name:");
        player2Field = new JTextField();
        player2Field.setPreferredSize(new Dimension(100,100)); // Sets the size of the panel
        p2Label.setForeground(Color.WHITE); //Sets the color of the font
        p2Label.setFont(new Font("Calibri",Font.PLAIN,30)); //Sets the font and font size
        player2Field = new JTextField();
        player2Field.setPreferredSize(new Dimension(100,100)); // Sets the size of the panel
        player2Field.setFont(new Font("Calibri",Font.PLAIN,40)); //Sets the font and font size
        player2Field.setForeground(Color.BLUE);
        player2Field.setText("Player 2");
        
        nextButton = new JButton();
		nextButton.setBounds(0,100,100,50); //sets its location and size
		nextButton.setText("Next"); //Text description of the button
		nextButton.setFont(new Font("Calibri",Font.PLAIN,30)); //Sets the font and font size
        nextButton.addActionListener(e -> setupAnimalScreen());
        
        nameScreen.add(jklabel);
        nameScreen.add(jklabel2);
        nameScreen.add(p1Label);
        nameScreen.add(player1Field);
        nameScreen.add(p2Label);
        nameScreen.add(player2Field);
        nameScreen.add(nextButton);
        
        add(nameScreen, BorderLayout.CENTER);
    }
    
    private void setupAnimalScreen() 
    {
        player1Name = player1Field.getText();
        player2Name = player2Field.getText();
        player1 = new Player(1, player1Name);
        player2 = new Player(2, player2Name);

        animals = new ArrayList<>(List.of(animalRanks));
        Collections.shuffle(animals);

        remove(nameScreen);
        revalidate();
        repaint();

        // Main panel that holds both animal selection and ranking
        animalScreen = new JPanel();
        animalScreen.setLayout(new BorderLayout());

        // Panel for animal selection
        JPanel animalSelectionPanel = new JPanel();
        animalSelectionPanel.setLayout(new GridLayout(3, 4, 10, 10));

        animalButtons = new JButton[8];
        for (int i = 0; i < 8; i++) 
        {
            int index = i;
            animalButtons[i] = new JButton(String.valueOf(i + 1));
            animalButtons[i].setFont(new Font("Calibri",Font.PLAIN,30)); //Sets the font and font size
            animalButtons[i].addActionListener(e -> selectAnimal(index));
            animalSelectionPanel.add(animalButtons[i]);
        }

        p1AnimalLabel = new JLabel(player1Name + " : Not selected");
        p2AnimalLabel = new JLabel(player2Name + " : Not selected");

        animalSelectionPanel.add(p1AnimalLabel);
        animalSelectionPanel.add(p2AnimalLabel);

        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new GridLayout(9, 1, 5, 5)); // 8 rankings + 1 title
        rankingPanel.setPreferredSize(new Dimension(200, 300)); // Adjust height as needed
        rankingPanel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel("Power Ranking");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        rankingPanel.add(titleLabel);

        for (int i = 0; i < animalRanks.length; i++) 
        {
            JLabel rankLabel = new JLabel((i + 1) + ". " + animalRanks[i]);
            rankLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
            rankLabel.setHorizontalAlignment(JLabel.CENTER);
            rankingPanel.add(rankLabel);
        }

        // Add panels to the main animal screen
        animalScreen.add(animalSelectionPanel, BorderLayout.CENTER);
        animalScreen.add(rankingPanel, BorderLayout.EAST); // Adds ranking panel to the right

        add(animalScreen, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void selectAnimal(int index) 
    {
        if (player1Animal == null)
        {
            player1Animal = animals.get(index);
            animalButtons[index].setEnabled(false);
            JOptionPane.showMessageDialog(this, player1Animal, "Player 1 Picked", JOptionPane.INFORMATION_MESSAGE);
            p1AnimalLabel.setText(player1Name+" : " + player1Animal);
        } 
        else if (player2Animal == null)
        {
            player2Animal = animals.get(index);
            p2AnimalLabel.setText(player2Name+" : " +player2Animal);
            JOptionPane.showMessageDialog(this, player2Animal, "Player 2 Picked", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(this, determineWinner(), "Battle Result", JOptionPane.INFORMATION_MESSAGE);
            currentPlayer = determineIntWinner();
            remove(animalScreen);
            startGame();
        }
    }
    
    private String determineWinner() 
    {
        int p1Rank = getAnimalRank(player1Animal);
        int p2Rank = getAnimalRank(player2Animal);

        if (p1Rank < p2Rank) 
        {
            return player1Name +"'s Animal: " +player1Animal + " beats "+ player2Name + "'s Animal: "+player2Animal + "!"+ " and thus will get the first move";
        } 
        else
        {
        	return player2Name +"'s Animal: " +player2Animal + " beats "+ player1Name + "'s Animal: "+player1Animal + "!"+ " and thus will get the first move";
        }
    }
    
    private int determineIntWinner() 
    {
        int p1Rank = getAnimalRank(player1Animal);
        int p2Rank = getAnimalRank(player2Animal);
        
        if (p1Rank < p2Rank) 
        {
            return 1;
        } 
        else
        {
            return 2; 
        }
    }
    
    private int getAnimalRank(String animal) 
    {
        for (int i = 0; i < animalRanks.length; i++) 
        {
            if (animalRanks[i].equals(animal)) {
                return i;
            }
        }
        return -1;
    }
    
    private void startGame() 
    {
        remove(animalScreen);
        revalidate();
        repaint();
        board = new ClassicBoard(player1, player2); 
        launchGame();
    }

    
    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    
    private void update() 
    {
        if (player1 == null || player2 == null) {
            return;
        }

        if (mouse.mousePressed) {  // Dragging
            int row = mouse.y / Board.SQUARE_SIZE;
            int col = mouse.x / Board.SQUARE_SIZE;
            Tile clickedTile = board.getTile(row, col);

            if (activeP == null && clickedTile != null && clickedTile.getPiece() != null && clickedTile.getPiece().getPlayerId() == currentPlayer) {
                activeP = clickedTile.getPiece();
            }
        } 
        else if (activeP != null) 
        { // When mouse is released, move piece
            int row = mouse.y / Board.SQUARE_SIZE;
            int col = mouse.x / Board.SQUARE_SIZE;
            Tile targetTile = board.getTile(row, col);

            if (targetTile != null && activeP.isValidMove(targetTile)) 
            { // Ensure valid move
                activeP.moveTo(targetTile);
                switchTurn();
                System.out.println("Turn switched. Now it's Player " + currentPlayer + "'s turn.");
            } 
            else {
                System.out.println("Invalid move. Turn remains with Player " + currentPlayer);
            }
            activeP = null;
        }
    }
    
    public void switchTurn() {
        // Check if the current player has any pieces left


        if (currentPlayer == 1) {
            if (player2.hasPiecesLeft()) 
            {
                currentPlayer = 2; // Switch to Player 2 if they have pieces left
            } 
            else {
                // Player 2 has no pieces, so Player 1 continues their turn
                currentPlayer = 1;
            }
        } 
        else if (currentPlayer == 2) {
            if (player1.hasPiecesLeft()) 
            {
                currentPlayer = 1; // Switch to Player 1 if they have pieces left
            } 
            else 
            {
                // Player 1 has no pieces, so Player 2 continues their turn
                currentPlayer = 2;
            }
        }
    }
    

//    private void simulate() 
//    {
//        boolean moved = true;
//        int newRow = mouse.y / Board.SQUARE_SIZE;
//        int newCol = mouse.x / Board.SQUARE_SIZE;
//
//        Tile targetTile = board.getTile(newRow, newCol);
//
//        if (targetTile != null && targetTile.getPiece() == null) 
//        {
//            activeP.moveTo(targetTile);
//            activeP = null;  // Deselect the piece after moving
//        }
//    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (board != null) {
            Graphics2D g2 = (Graphics2D) g;
            board.displayBoard(g2);

            // Draw dragged piece at cursor position
            if (activeP != null) {
                drawPieceAtCursor(g2, activeP, mouse.x, mouse.y);
            }
        }
    }
    private void drawPieceAtCursor(Graphics2D g2, Piece piece, int x, int y) {
        String pieceType = piece.getType().toLowerCase();
        int playerID = piece.getPlayerId();

        String imagePath = "src/main/Pictures/" + pieceType + playerID +".png";
        try {
            Image pieceImage = ImageIO.read(new File(imagePath));
            int pieceSize = Board.SQUARE_SIZE - 10; // Slightly smaller than tile
            g2.drawImage(pieceImage, x - pieceSize / 2, y - pieceSize / 2, pieceSize, pieceSize, null);
        } catch (IOException e) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString(pieceType.charAt(0) + String.valueOf(playerID), x - 10, y);
        }
    }
}
