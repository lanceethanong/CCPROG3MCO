
package ccprog3_mco;
import java.util.*; // in order to use the Scanner and other utilities

	public class Game {
	    private Player player1;
	    private Player player2;
	    private Board board;
	    private Scanner scanner;
	    private String name1,name2;
	    private int currentPlayer;
	    public Game() 
	    {
	        scanner = new Scanner(System.in);
	        initializeGame();
	    }
	    
	    private void initializeGame() 
	    {
	        System.out.println("Welcome to Jungle King! Your goal is to capture the enemy's base before they do");
	        System.out.println("Player 1 controls the pieces at the top, Player 2 at the bottom.");
	        System.out.println("Use W, A, S, D to move your pieces.");
	        System.out.println("Enter name for Player 1:");
	        name1 = scanner.nextLine();
	        if(name1 == null)
	        {
	        player1 = new Player(1);
	        }
	        else
	        {
	        player1 = new Player(1,name1);	
	        }
	        System.out.println("Enter name for Player 2:");
	        name2 = scanner.nextLine();
	        if(name2 == null)
	        {
	        player2 = new Player(2);
	        }
	        else
	        {
	        player2 = new Player(2,name2);	
	        }
	        board = new ClassicBoard();
	        board.initializePieces(player1, player2);
	        System.out.println("Starting Game!(Standard Game)");	        
	        startGame();
	        
	    }
	    
	    public void startGame() 
	    {
	        Scanner scanner = new Scanner(System.in); //Initializes the scanner
	        boolean gameOver = false; // sets it to false to check if the game is still going
	        currentPlayer = firstPlayer();
	        // Start messages

	        while (!gameOver) //keeps game going until a player captures a base 
	        {
	        	// Displays the board(updated after every turn)
	        	if(currentPlayer == 1)
	        	{
	        		if(player1.getName().equals(""))
	        		{
	        			System.out.println("Player " + currentPlayer + "'s turn.");
	        		}
	        		else
	        		{
	        			System.out.println(player1.getName()+"'s turn.");	
	        		}
	        	}
	        	else
	        	{
	        		
	        		if(player2.getName().equals(null))
	        		{
	        			System.out.println("Player " + currentPlayer + "'s turn.");
	        		}
	        		else
	        		{
	        			System.out.println(player2.getName()+"'s turn.");	
	        		}
	        	}
	            System.out.println("x is the vertical numbers and y is the horizontal number");
	            // Enter the coordinates of the piece you want to move
	            System.out.print("Enter the coordinates of the piece to move (x y): ");
	            int x = scanner.nextInt();
	            int y = scanner.nextInt();
	            
	          
	            boolean notOutOfBounds = true;
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
	                
	            Piece selectedPiece = board.getTile(x, y).getPiece();
	            
	            //If the user selects an empty tile is prompted to input another set of coordinates until it is not null
	            while(selectedPiece==null)
	            {
	                System.out.print("No piece on the selected tile! Pick Again(x,y): ");
	                x = scanner.nextInt();
	                y = scanner.nextInt();
	                selectedPiece=board.getTile(x, y).getPiece();
	            }
	            //Asks for the direction
	            System.out.print("Enter the direction (W(up), A(left), S(down), D(right): ");
	            char d = scanner.next().charAt(0); //scans the first character 
	            while(d!='w' && d!='a' && d!='s' && d!='d' && d!='W' && d!='A' && d!= 'S' && d!='D') //Keeps going until a valid direction is given
	            {
	                System.out.print("Incorrect Input \n Enter the direction (W(up), A(left), S(down), D(right):");
	                d=scanner.next().charAt(0);
	            }
	            while((d=='w' && x == 0) || (d=='a' && y==0) || (d=='s' && x==8) || (d=='d' && y==6))
	            { //Keeps going if the spot the piece wants to go to is out of bounds
	                System.out.print("Out of Bounds!! \n Enter the direction (W(up), A(left), S(down), D(right):");
	                d=scanner.next().charAt(0);
	            }
	               
	                boolean isValidMove = selectedPiece.movePiece(board.board,d); //isValidMove is assigned to movePiece so that the movePiece can be accessed and assuming its valid move the pieces in the board along with updating the board
	                while(!isValidMove) //Until isValidMove becomes true keeps asking for the direction
	                {
	                    System.out.println("Invalid move. Try again.");
	                    System.out.print("Enter the direction (W(up), A(left), S(down), D(right):");
	                    d=scanner.next().charAt(0);
	                    isValidMove = selectedPiece.movePiece(board.board,d); //until it becomes valid 
	                }
	                
	                    
	            // Checks the board to see if a user has captured the opponents base or not     
	            if(board.GameOver() == true)
	            { //If base is sucessfully captured
	            	System.out.println();
	            	System.out.println("Player "+currentPlayer+" has captured the enemy base and has won the game!"); //Win message
	            	gameOver = true; // sets gameOver to true to end the main Game loop
	            }
	            else // If not the game moves over to the next player to make a move 
	            {
	                if (currentPlayer == 1) 
	                { 
	                    if (player2.hasPiecesLeft()) 
	                    {
	                        currentPlayer = 2; 
	                    } 
	                    else if (player1.hasPiecesLeft()) 
	                    {
	                        currentPlayer = 1; 
	                    } 
	                } 
	                else if (currentPlayer == 2) 
	                { 
	                    if (player1.hasPiecesLeft()) 
	                    {
	                        currentPlayer = 1; 
	                    }
	                    else if (player2.hasPiecesLeft()) 
	                    {
	                        currentPlayer = 2; 
	                    } 
	                }
	            }
	            
	           
	            System.out.println();
	            
	        }

	        scanner.close(); //closes scanner just to be sure
	        System.out.println("Thanks for playing!");
	    }
	    
	    private int firstPlayer() {
	        boolean sameNum = true;
	        ArrayList<String[]> animals = new ArrayList<>();
	        animals.add(new String[]{"Elephant", "8"});
	        animals.add(new String[]{"Lion", "7"});
	        animals.add(new String[]{"Tiger", "6"});
	        animals.add(new String[]{"Leopard", "5"});
	        animals.add(new String[]{"Wolf", "4"});
	        animals.add(new String[]{"Dog", "3"});
	        animals.add(new String[]{"Cat", "2"});
	        animals.add(new String[]{"Rat", "1"});
	        
	        Collections.shuffle(animals); //Java utility that shuffles the order of the index of each Instance in the ArrayList
	        Scanner scanner = new Scanner(System.in); //Initiates a new scanner object(not closed as it is still needed for the actually gameplay)

	        System.out.println("Each player, pick an animal piece (1-8):"); //Asks user to pick a number
	        revealCards(-1, -1, animals); // Display all cards face down
	        if(name1 == "")
	        {
	        System.out.print("Player 1, choose a number: ");
	        }
	        else
	        {
	        	System.out.print(name1+" choose a number: ");
	        }
	        int c1 = scanner.nextInt() - 1; //-1 to associate it with the index and avoid out of bounds
	        revealCards(c1, -1, animals); // Reveal Player 1's choice

	        int c2 = -1;
	        while (sameNum) 
	        {
	        	if(name2 == "")
	        	{
	            System.out.print("Player 2, choose a number: ");
	        	}
	        	else
	        	{
	        		System.out.print(name2+" choose a number: ");
	        	}
	            c2 = scanner.nextInt() - 1;
	            if (c2 != c1) 
	            {
	               sameNum = false;
	            } 
	            else 
	            {
	                System.out.println("Number already chosen, try again!");
	            }
	        }

	        // Display both players' choices
	        revealCards(c1, c2, animals);

	        //Gets the animal names using the Animal data type(mainly for display purposes)
	        String[] animal1 = animals.get(c1);
	        String[] animal2 = animals.get(c2);
	        if(name1 == "")
	        {
	        System.out.println("Player 1 chose: " + animal1[0]);
	        }
	        else
	        {
	        System.out.println(name1+" chose: " + animal1[0]);	
	        }
	        if(name2 == "")
	        {
	        System.out.println("Player 2 chose: " + animal2[0]);
	        }
	        else
	        {
	        System.out.println(name2+" chose: " + animal2[0]);	
	        }
	        
	        int power1 = Integer.parseInt(animal1[1]);
	        int power2 = Integer.parseInt(animal2[1]);
	        
	        if (power1 > power2) 
	        {
	        	if(name1 == "")
	        	{
	            System.out.println("Player 1 chose the stronger animal and therefore will get the first move");
	        	}
	        	else
	        	{
	        	System.out.println(name1+" chose the stronger animal and therefore will get the first move");	
	        	}
	            return 1;
	        } 
	        else 
	        {
	        	if(name2 == "")
	        	{
	            System.out.println("Player 2 chose the stronger animal and therefore will get the first move");
	        	}
	        	else
	        	{
	        	System.out.println(name2+" chose the stronger animal and therefore will get the first move");	
	        	}
	            return 2;
	        }
	    }

	    private void revealCards(int index1, int index2, ArrayList<String[]> animals) {
	        String[] cards = {".", ".", ".", ".", ".", ".", ".", "."};
	        if (index1 != -1) {
	            cards[index1] = animals.get(index1)[0];
	        }
	        if (index2 != -1) {
	            cards[index2] = animals.get(index2)[0];
	        }
	        for (String card : cards) {
	            System.out.print(card + " ");
	        }
	        System.out.println();
	    }
	    public static void main(String[] args) {
	        Game game = new Game();
	        game.initializeGame();
	    }
	}

///**
// * Class : Game
// * Description : Class used to start a game and is used for the main gameplay, 
// * It would make use of a turn based system that makes use of the Board class and its methods implemented for both movement 
// * and condition checking. 
// * @author Lance Ethan S. Ong  Nick Jenson S. Crescini S14
// */
//public class Game {
//	/*
//	 * 
//	 * Attributes of Game
//	 * Board board: used to create a Board instance and use its methods
//	 * int currentPlayer: keeps track of the player
//	 * int x : x coordinates
//	 * int y : y coordinates
//	 * char d : for direction
//	 * boolean notOutOfBounds : makes sure x and y are valid coordinates on the board
//	 */
//    private Board board;
//    private int currentPlayer;
//    private int x;
//    private int y;
//    private char d;
//    private boolean notOutOfBounds;
//    private ArrayList<Animal> animals;
//    /**
//     * Method : Game
//     * Description : constructor used to initialize a Game Instance
//     * 
//     */
//    public Game() 
//    {
//        board = new Board(); // creates a new Game Board
//        currentPlayer = FirstPlayer(); // Calls the firstplayer method to determine which player will start the game
//    }
//    /**
//     * Method : initializeGame
//     * Description : Used to handle the gameflow(start to end) along with the user inputs and updating of the board along with showing the status of the game 
//     * @author Nick Jenson S. Crescini S14
//     */
    
//    
//    /**
//     * Method: FirstPlayer
//     * Description: A method that determines who goes first based on a card game where each player 
//     * is asked to pick a number and based on what animals each player picks decides who gets the first move
//     * @return int 1 || 2
//     * References:  https://www.geeksforgeeks.org/collections-shuffle-method-in-java-with-examples/(Used for the collection.shuffle utility)
//     * @author Lance Ethan S. Ong S14
//     */
//    
    
//}

