package ccprog3_mco;

public abstract class Tile 
{
	protected int x;
	protected int y;
	protected String type;
	private Piece piece;
	public Tile(int x,int y, String type)
	{
		this.x = x;
		this.y = y;
		this.type = null;
	}
	
	public void setPiece(Piece piece)
	{
		this.piece = piece;
	}
	
	public Piece getPiece()
	{
		return piece;
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
		return type;
	}
	
	public String display()
	{
		return ". ";
	}

	public String display() 
	{
	    if (piece != null) {
	        return piece.display() + " ";
	    }
	    return getDefaultSymbol(); // Restore correct tile symbol
	}

	public String getDefaultSymbol() 
	{
	    if (this instanceof Lake) return "~ ";
	    if (this instanceof Trap) return ". ";
	    return ". "; // Default empty tile
	}
}
