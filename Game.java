/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ccprog3_mco;

/**
 *
 * @author cresc
 */


import java.util.*;

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
        
        //will continue until game is not over
        while (!gameOver) 
        {
            board.displayBoard();
            System.out.println("Player " + currentPlayer + "'s turn.");
            System.out.println("x is the vertical numbers and y is the horizontal number");
            
            boolean validInput = false;
            int x = -1,y = -1;
            //input coordinates
            System.out.print("Enter the coordinates of the piece to move(x,y): ");
            
            validInput=false;
            while(!validInput){
            try{
                x = scanner.nextInt();
                y = scanner.nextInt();
                validInput=true;
                //when input valid
            } catch(InputMismatchException e) { // when invalid input example letters
                System.out.println("Invalid input! Please enter numeric values.");
                System.out.println("Pick Again(x,y):");
                scanner.next(); // clear invalid input
            }
            }

            boolean notOutOfBounds = true;
            //check if out of bounds
            while(notOutOfBounds){
                if(x>8 || y>6){
                System.out.print("Out of Bounds Enter again: ");
                validInput=false;
                while(!validInput){
                try{
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    validInput=true;
                } catch(InputMismatchException e) {
                    System.out.println("Invalid input! Please enter numeric values.");
                    System.out.println("Pick Again(x,y):");
                    scanner.next(); // Clear invalid input
                }
                }
                }else
                    notOutOfBounds=false;
            }
                
            Piece selectedPiece = board.getPiece(x, y);
            
            //when empty tile or null
            while(selectedPiece==null){
                System.out.print("No piece on the selected tile! Pick Again(x,y): ");
                validInput=false;
                while(!validInput){
                try{
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    validInput=true;
                } catch(InputMismatchException e) {
                    System.out.println("Invalid input! Please enter numeric values.");
                    System.out.println("Pick Again(x,y):");
                    scanner.next(); // Clear invalid input
                }
                }
                selectedPiece=board.getPiece(x, y);
            }
            //when picked a base
            while(!selectedPiece.getType().equals("Animal")){
                System.out.print("You can only move animal pieces! Pick Again(x,y):");
                validInput=false;
                while(!validInput){
                try{
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    validInput=true;
                } catch(InputMismatchException e) {
                    System.out.println("Invalid input! Please enter numeric values.");
                    System.out.println("Pick Again(x,y):");
                    scanner.next(); // Clear invalid input
                }
                }
                selectedPiece=board.getPiece(x, y);
            }
            
            System.out.print("Enter the direction (W(up), A(left), S(down), D(right): ");
            char d = scanner.next().charAt(0);
            while(d!='w' && d!='a' && d!='s' && d!='d' && d!='W' && d!='A' && d!= 'S' && d!='D'){
                System.out.print("Incorrect Input \n Enter the direction (W(up), A(left), S(down), D(right):");
                d=scanner.next().charAt(0);
            }
            while((d=='w' && x == 0) || (d=='a' && y==0) || (d=='s' && x==8) || (d=='d' && y==6)){
                System.out.print("Out of Bounds!! \n Enter the direction (W(up), A(left), S(down), D(right):");
                d=scanner.next().charAt(0);
            }
               

            if(currentPlayer == selectedPiece.getPlayerId()){
                boolean isValidMove = board.movePiece(x,y,d);
                //check if the move is valid
                while(!isValidMove){
                    System.out.println("Invalid move. Try again.");
                    System.out.print("Enter the direction (W(up), A(left), S(down), D(right):");
                    d=scanner.next().charAt(0);
                    isValidMove = board.movePiece(x,y,d);
                }
                
                    
            //checks if the game is over  
            if(board.GameOver() == true){
            	board.displayBoard();
            	System.out.println();
            	System.out.println("Player "+currentPlayer+" has captured the enemy base and has won the game!");
            	gameOver = true;
            }
            else{
            	if(currentPlayer == 1){
                	currentPlayer++;
                }
                else{
                	currentPlayer--;
                }
            }
            }
            else
            {
            	if(currentPlayer != board.getPiece(x,y).getPlayerId()){
            		System.out.println("Cannot move other players pieces! Try again");
            	}
            	else{
            	System.out.println("That is not an animal piece! Try again");
            	}
            	System.out.println();
            }
            
           
            System.out.println();
            
        }

        scanner.close();
        System.out.println("Thanks for playing!");
    }
}
