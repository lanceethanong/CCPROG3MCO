package ccprog3_mco;

public interface Swimmer 
{
    /**
     * Moves the piece to a lake tile (identical to Piece.move() but for lakes)
     * @param targetLakeTile The lake tile to move to
     * @return The destination tile if successful, null otherwise
     */
    public Tile swim(Tile targetTile);
}