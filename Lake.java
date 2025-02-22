package ccprog3_mco;

public class Lake extends Piece {
    public Lake() {
        super(0, 0,"Lake"); // Lake has no power and belongs to no player
    }

    public String display() {
        return "~ ";
    }
}
