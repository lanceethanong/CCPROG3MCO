package ccprog3_mco;
import java.util.*; // in order to use the Scanner and other utilities

/**
 * Class : Game
 * Description : Class used to start a game and is used for the main gameplay, 
 * It would make use of a turn based system that makes use of the Board class and its methods implemented for both movement 
 * and condition checking. 
 * @author Lance Ethan S. Ong  Nick Jenson S. Crescini S14
 */
public class Game {
	/*
	 * 
	 * Attributes of Game
	 * Board board: used to create a Board instance and use its methods
	 * int currentPlayer: keeps track of the player
	 * int x : x coordinates
	 * int y : y coordinates
	 * char d : for direction
	 * boolean notOutOfBounds : makes sure x and y are valid coordinates on the board
	 */
    private Board board;
    private int currentPlayer;
    private int x;
    private int y;
    private char d;
    private boolean notOutOfBounds;
    private ArrayList<Animal> animals;
    /**
     * Method : Game
     * Description : constructor used to initialize a Game Instance
     * 
     */
    public Game() 
    {
        board = new Board(); // creates a new Game Board
        currentPlayer = FirstPlayer(); // Calls the firstplayer method to determine which player will start the game
    }
    /**
     * Method : startGame
     * Description : Used to handle the gameflow(start to end) along with the user inputs and updating of the board along with showing the status of the game 
     * @author Nick Jenson S. Crescini S14
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in); //Initializes the scanner
        boolean gameOver = false; // sets it to false to check if the game is still going

        // Start messages
        System.out.println("Welcome to Jungle King! Your goal is to capture the enemy's base before they do");
        System.out.println("Player 1 controls the pieces at the top, Player 2 at the bottom.");
        System.out.println("Use W, A, S, D to move your pieces.");

        while (!gameOver) //keeps game going until a player captures a base 
        {
        	// Displays the board(updated after every turn)
            board.displayBoard();
            System.out.println("Player " + currentPlayer + "'s turn.");
            System.out.println("x is the vertical numbers and y is the horizontal number");
            
            // Enter the coordinates of the piece you want to move
            System.out.print("Enter the coordinates of the piece to move(x,y): ");
            // gets the next 2 int inputs
            x = scanner.nextInt(); 
            y = scanner.nextInt();
            notOutOfBounds = true;
            //Keeps asking the user while they dont provide a valid coordinates
            while(notOutOfBounds)
            {
                if(x>8 || y>6 || x<0 || y<0){
                System.out.print("Out of Bounds Enter again: "); // Asked to reenter coordinates
                x = scanner.nextInt();
                y = scanner.nextInt();
                }
                else //If valid ends the loop
                notOutOfBounds=false;
            }
                
            Piece selectedPiece = board.getPiece(x, y); // initializes the variable with the Piece type to be able to access the getPiece method to get what piece is in those coordinates
            
            //If the user selects an empty tile is prompted to input another set of coordinates until it is not null
            while(selectedPiece==null){
                System.out.print("No piece on the selected tile! Pick Again(x,y): ");
                x = scanner.nextInt();
                y = scanner.nextInt();
                selectedPiece=board.getPiece(x, y);
            }
            //If the user selects a lake or base tile is prompted to input another set of coordinates until it is an Animal piece
            while(!selectedPiece.getType().equals("Animal")){
                System.out.print("You can only move animal pieces! Pick Again(x,y):");
                x = scanner.nextInt();
                y = scanner.nextInt();
                selectedPiece=board.getPiece(x, y);
            }
            //Asks for the direction
            System.out.print("Enter the direction (W(up), A(left), S(down), D(right): ");
            d = scanner.next().charAt(0); //scans the first character 
            while(d!='w' && d!='a' && d!='s' && d!='d' && d!='W' && d!='A' && d!= 'S' && d!='D') //Keeps going until a valid direction is given
            {
                System.out.print("Incorrect Input \n Enter the direction (W(up), A(left), S(down), D(right):");
                d=scanner.next().charAt(0);
            }
            while((d=='w' && x == 0) || (d=='a' && y==0) || (d=='s' && x==8) || (d=='d' && y==6)){ //Keeps going if the spot the piece wants to go to is out of bounds
                System.out.print("Out of Bounds!! \n Enter the direction (W(up), A(left), S(down), D(right):");
                d=scanner.next().charAt(0);
            }
               

            if(currentPlayer == selectedPiece.getPlayerId()){ //Checks if the selectedPiece actually belongs to the player or not
                boolean isValidMove = board.movePiece(x,y,d); //isValidMove is assigned to movePiece so that the movePiece can be accessed and assuming its valid move the pieces in the board along with updating the board
                while(!isValidMove) //Until isValidMove becomes true keeps asking for the direction
                {
                    System.out.println("Invalid move. Try again.");
                    System.out.print("Enter the direction (W(up), A(left), S(down), D(right):");
                    d=scanner.next().charAt(0);
                    isValidMove = board.movePiece(x,y,d); //until it becomes valid 
                }
                
                    
            // Checks the board to see if a user has captured the opponents base or not     
            if(board.GameOver() == true)
            { //If base is sucessfully captured
            	board.displayBoard();
            	System.out.println();
            	System.out.println("Player "+currentPlayer+" has captured the enemy base and has won the game!"); //Win message
            	gameOver = true; // sets gameOver to true to end the main Game loop
            }
            else // If not the game moves over to the next player to make a move 
            {
            	if(currentPlayer == 1)//switch to player2
            	{
                	currentPlayer++;
                }
                else//switch to player1
                {
                	currentPlayer--;
                }
            }
            }
            else //If the user selects the opponents piece 
            {
            		
            	System.out.println("Cannot move other players pieces! Try again"); 
            	System.out.println();
            }
            
           
            System.out.println();
            
        }

        scanner.close(); //closes scanner just to be sure
        System.out.println("Thanks for playing!");
    }
    
    /**
     * Method: FirstPlayer
     * Description: A method that determines who goes first based on a card game where each player 
     * is asked to pick a number and based on what animals each player picks decides who gets the first move
     * @return int 1 || 2
     * References:  https://www.geeksforgeeks.org/collections-shuffle-method-in-java-with-examples/(Used for the collection.shuffle utility)
     * @author Lance Ethan S. Ong S14
     */
    
    private int FirstPlayer() 
    {
    	animals = new ArrayList<>(); //creates a new temporary arrayList that is not saved to the board or game and only used for this purpose
    	// adds all animals with their respective power(player set to 0 as for this purpose it does not belong to anyone)
    	animals.add(new Animal("Elephant", 8, 0));
    	animals.add(new Animal("Lion", 7, 0));
    	animals.add(new Animal("Tiger", 6, 0));
    	animals.add(new Animal("Leopard", 5, 0));
    	animals.add(new Animal("Wolf", 4, 0));
    	animals.add(new Animal("Dog", 3, 0));
    	animals.add(new Animal("Cat", 2, 0));
    	animals.add(new Animal("Rat", 1, 0));

        Collections.shuffle(animals); //Java utility that shuffles the order of the index of each Instance in the ArrayList
        Scanner scanner = new Scanner(System.in); //Initiates a new scanner object(not closed as it is still needed for the actually gameplay)

        System.out.println("Each player, pick an animal piece (1-8):"); //Asks user to pick a number
        System.out.print("Player 1, choose a number: ");
        int c1 = scanner.nextInt() - 1; //-1 to associate it with the index and avoid out of bounds
        int c2 = -1;
        while (sameNum) 
        {
            System.out.print("Player 2, choose a number: ");
            c2 = scanner.nextInt() - 1;
            if (c2 != c1) 
            {
               sameNum = false;
            } 
            else 
            {
                System.out.println("Number already chosen try again!");
            }
        }
        
        //Gets the animal names using the Animal data type(mainly for display purposes)
        Animal animal1 = animals.get(c1);
        Animal animal2 = animals.get(c2);
        
        //Displays the animal each user picked 
        System.out.println("Player 1 chose: " + animal1.getAnimal());
        System.out.println("Player 2 chose: " + animal2.getAnimal());
        
        //If player1 chose the animal with the higher power
        if(animals.get(c1).getPower() > animals.get(c2).getPower())
        {
        	System.out.println("Player 1 chose the stronger animal and therefore will get the first move");
        	System.out.println();
        	System.out.println();
        	return 1; // returns 1 to signify the currentPlayer is player 1
        }
        //If player 2 chose the animal with the higher power
        else
        {
        	System.out.println("Player 2 chose the stronger animal and therefore will get the first move");
        	System.out.println();
        	System.out.println();
        	return 2; // returns 2 to signify the currentPlayer is player 2
        }
        
    }
}
