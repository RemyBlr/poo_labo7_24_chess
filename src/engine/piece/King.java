package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;
import engine.piece.MovableOncePiece;

import java.util.HashMap;
import java.util.HashSet;

public class King extends MovableOncePiece {

    public King(PlayerColor color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public PieceType type() {
        return PieceType.KING;
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board, Move lastMove) {
        //samePosition deja pris en charge
        return !(Math.abs(toX - fromX) > 1 || Math.abs(toY - fromY) > 1);
    }

    public boolean isChecked(Board board) {
        PlayerColor enemyColor = color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
        HashSet<Piece> enemyPieces = board.getPlayerPieces(enemyColor);
        for (Piece enemy : enemyPieces) {
            if(!enemy.isValidMove(enemy.x, enemy.y, x, y)) continue;
            return true;
        }
        return false;
    }
}
