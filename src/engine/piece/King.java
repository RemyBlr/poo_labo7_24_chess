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

    public King(PlayerColor color, Position pos) {
        super(color, pos);
    }

    @Override
    public PieceType type() {
        return PieceType.KING;
    }

    @Override
    public boolean isValidMove(Move move, Board board, Move lastMove) {
        Position from = move.from(), to = move.to();
        return !(Math.abs(to.x() - from.x()) > 1 || Math.abs(to.y() - from.y()) > 1);
    }

    public boolean isChecked(Board board, Move lastMove) {
        PlayerColor enemyColor = color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
        HashSet<Piece> enemyPieces = board.getPlayerPieces(enemyColor);
        for (Piece enemy : enemyPieces) {
            if(!enemy.isValidMove(lastMove, board, lastMove)) continue; // Résultat peut-être éronné ici
            return true;
        }
        return false;
    }
}
