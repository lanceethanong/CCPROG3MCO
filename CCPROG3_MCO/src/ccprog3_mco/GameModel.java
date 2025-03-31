package ccprog3_mco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameModel {
    public static final int W = 1100;
    public static final int H = 800;
    
    private Board board;
    private Player player1;
    private Player player2;
    private int currentPlayer;
    private Piece activePiece;
    
    private String player1Name, player2Name;
    private String player1Animal, player2Animal;
    private final String[] animalRanks = {"Elephant", "Lion", "Tiger", "Leopard", "Wolf", "Dog", "Cat", "Rat"};
    private List<String> animals;
    
    public GameModel() {
        animals = new ArrayList<>(List.of(animalRanks));
        Collections.shuffle(animals);
    }
    
    // Game state management
    public void initializeGame() {
        player1 = new Player(1, player1Name);
        player2 = new Player(2, player2Name);
        board = new ClassicBoard(player1, player2);
    }
    
    public void setPlayerNames(String p1Name, String p2Name) {
        this.player1Name = p1Name;
        this.player2Name = p2Name;
    }
    public void setCurrentPlayer(int firstPlayer){
        this.currentPlayer=firstPlayer;
    }
    
    public void selectAnimal(int player, int animalIndex) {
        String animal = animals.get(animalIndex);
        if (player == 1) {
            player1Animal = animal;
        } else {
            player2Animal = animal;
        }
        // Mark the animal as selected but don't remove it
        animals.set(animalIndex, animal); // Keep the animal name for display
    }
    
    public boolean bothAnimalsSelected() {
        return player1Animal != null && player2Animal != null;
    }
    
    public String determineWinner() 
    {
        int p1Rank = getAnimalRank(player1Animal);
        int p2Rank = getAnimalRank(player2Animal);

        if (p1Rank < p2Rank)
        {
            return player1Name + "'s " + player1Animal + " (Rank " + (p1Rank+1) + 
                   ") beats " + player2Name + "'s " + player2Animal + " (Rank " + (p2Rank+1) + 
                   ")!\n" + player1Name + " will move first!";
        } 
        else
        {
            return player2Name + "'s " + player2Animal + " (Rank " + (p2Rank+1) + 
                   ") beats " + player1Name + "'s " + player1Animal + " (Rank " + (p1Rank+1) + 
                   ")!\n" + player2Name + " will move first!";
        } 
    }
    
    public int determineIntWinner() {
        int p1Rank = getAnimalRank(player1Animal);
        int p2Rank = getAnimalRank(player2Animal);

        if (p1Rank < p2Rank) {
            return 1;
        } 
        else
        {
            return 2;
        } 
    }
    
    private int getAnimalRank(String animal) 
    {
        for (int i = 0; i < animalRanks.length; i++) {
            if (animalRanks[i].equals(animal)) {
                return i;
            }
        }
        return -1;
    }
    
    // Gameplay methods
    public void setActivePiece(Piece piece) {
        this.activePiece = piece;
    }
    
    public boolean tryMovePiece(int row, int col) 
    {
        if (activePiece == null) return false;
        
        Tile targetTile = board.getTile(row, col);
        if (targetTile != null && activePiece.isValidMove(targetTile)) {
            boolean moved = activePiece.moveTo(targetTile);
            if (moved) {
                switchTurn();
                return true;
            }
        }
        return false;
    }
    
    public void switchTurn() {
        if (currentPlayer == 1) {
            currentPlayer = player2.hasPiecesLeft() ? 2 : 1;
        } else {
            currentPlayer = player1.hasPiecesLeft() ? 1 : 2;
        }
    }

    // Getters
    public Board getBoard() { return board; }
    public Player getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public int getCurrentPlayer() { return currentPlayer; }
    public Piece getActivePiece() { return activePiece; }
    public String[] getAnimalRanks() { return animalRanks; }
    public List<String> getAnimals() { return animals; }
    public String getPlayer1Name() { return player1Name; }
    public String getPlayer2Name() { return player2Name; }
    public String getPlayer1Animal() { return player1Animal; }
    public String getPlayer2Animal() { return player2Animal; }
}