package ccprog3_mco;

import java.util.Scanner;

public class Game {
    private Board board;
    private int currentPlayer;
    public Game() 
    {
        board = new Board();
        currentPlayer = 1;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        System.out.println("Welcome to the Jungle Game!");
        System.out.println("Player 1 controls the pieces at the top, Player 2 at the bottom.");
        System.out.println("Use W, A, S, D to move your pieces.");

        while (!gameOver) 
        {
            board.displayBoard();
            System.out.println("Player " + currentPlayer + "'s turn.");

            System.out.print("Enter the coordinates of the piece to move: ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            System.out.print("Enter the direction (W(up), A(left), S(down), D(right): ");
            char d = scanner.next().charAt(0);

            if(board.getPiece(x,y).getType().equals("Animal") && (currentPlayer == board.getPiece(x,y).getPlayerId()))
            {
            board.movePiece(x,y,d);
            if(currentPlayer == 1)
            {
            	currentPlayer++;
            }
            else
            {
            	currentPlayer--;
            }
            }
            else
            {
            	if(currentPlayer != board.getPiece(x,y).getPlayerId())
            	{
            		System.out.println("Cannot move other players pieces! Try again");
            	}
            	else
            	{
            	System.out.println("That is not an animal piece! Try again");
            	}
            	System.out.println();
            }
            if(board.GameOver() == true)
            {
            	System.out.println();
            	System.out.println("Player "+currentPlayer+"has captured the enemy base and has won the game!");
            	gameOver = true;
            }
            
            System.out.println();
            
        }

        scanner.close();
        System.out.println("Thanks for playing!");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
