package ccprog3_mco;

public class EmptyTile extends Tile 
{
     public EmptyTile(int x,int y) 
	 {
	        super(x, y);
	 }
	    
	 public String display() 
	 {
	        return ". "; 
	 }
}
