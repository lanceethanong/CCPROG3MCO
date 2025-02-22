package ccprog3_mco;

public class Piece {
    int power;
    int player; // Each piece belongs to a single player (1 or 2)
    String type; // Type of piece: "Animal", "Lake", "Base"

    public Piece(int power, int player, String type) {
        this.power = power;
        this.player = player;
        this.type = type;
    }

    public String display() {
        return "?";
    }

    public int getPlayerId() {
        return this.player;
    }

    public String getType() {
        return this.type;
    }
}




