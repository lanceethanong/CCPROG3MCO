package ccprog3_mco;

public class EmptyTile extends Tile 
{
     public EmptyTile(Board board,int x,int y) 
	 {
    	 
	        super(board,x, y);
	 }
	    
	 public String display() 
	 {
	        return ". "; 
	 }
}
