package ccprog3_mco;

public abstract class Tile 
{
	protected int x;
	protected int y;
	private Piece piece;
	protected Board board;
	public Tile(Board board,int x,int y)
	{
		this.board = board;
		this.x = x;
		this.y = y;
	}
	
	public Board getBoard()
	{
		return board;
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
