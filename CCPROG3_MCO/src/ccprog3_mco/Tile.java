package ccprog3_mco;

public abstract class Tile 
{
	protected int x;
	protected int y;
	private Piece piece;
	public Tile(int x,int y)
	{		
		this.x = x;
		this.y = y;
	}
	
	public void setPiece(Piece piece)
	{
		this.piece = piece;
	}
	
	public Piece getPiece()
	{
		return piece;
	}
        
        public String getPieceName(){
            return piece.getClass().getSimpleName();
        }
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public String getType()
	{
		return getClass().getSimpleName();
	}
	
	public String display() 
	{
	    if (piece != null) 
	    {
	        return piece.display() + " ";
	    }
	    return getDefaultSymbol(); // Restore correct tile symbol
	}


	public String getDefaultSymbol() 
	{
	    if (getType().equals("Lake")) return "~ ";
	    if (getType().equals("Trap")) return "# ";
	    if (getType().equals("Base")) return "B ";
	    return ". ";
	}
}
