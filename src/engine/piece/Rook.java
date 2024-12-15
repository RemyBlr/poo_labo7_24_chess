package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;

public class Rook extends MovableOncePiece {

    public Rook(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public PieceType type() {
        return PieceType.ROOK;
    }

    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board, Move lastMove) {

        if(fromX != toX && fromY != toY) {
            return false;
        }

        // Cannot go through obstacles
        if(fromX == toX) {
            int step = fromY < toY ? 1 : -1;
            for(int i = fromY + step; i != toY; i += step) {
                if(board.getPiece(fromX, i) != null) {
                    return false;
                }
            }
        } else {
            int step = fromX < toX ? 1 : -1;
            for(int i = fromX + step; i != toX; i += step) {
                if(board.getPiece(i, fromY) != null) {
                    return false;
                }
            }
        }

//        for(int i = fromX, j = fromY; i < toX && toY < fromY; ++i, ++j) {
//            if( != null) {
//
//            }
//        }

        return true;
    }
}
