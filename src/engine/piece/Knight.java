package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;

public class Knight extends Piece {

    public Knight(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public PieceType type() {
        return PieceType.KNIGHT;
    }

    // Check if the move is a L move
    private boolean isLMove(int fromX, int fromY, int toX, int toY) {
        return (Math.abs(toX - fromX) == 2 && Math.abs(toY - fromY) == 1) || (Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 2);
    }

    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board, Move lastMove) {
        if(!isLMove(fromX, fromY, toX, toY)) {
            return false;
        }
        return true;
    }
}
