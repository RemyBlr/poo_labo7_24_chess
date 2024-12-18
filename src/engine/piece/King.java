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

        // Ne peut que se déplacer d'une case
        if (Math.abs(to.x() - from.x()) > 1 || Math.abs(to.y() - from.y()) > 1) {
            System.out.println("1 case only for the king.");
            return false;
        }

        if(isChecked(board, lastMove)) {
            System.out.println("Roi en échec !");
            return false;
        }

        return true;
    }

    public boolean isChecked(Board board, Move lastMove) {
        Move checkingKing;
        PlayerColor enemyColor = color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
        HashSet<Piece> enemyPieces = board.getPlayerPieces(enemyColor);
        for (Piece enemy : enemyPieces) {
            checkingKing = new Move(new Position(enemy.pos().x(), enemy.pos().y()), this.pos, enemy, this);
            if(!enemy.isValidMove(checkingKing , board, lastMove)) continue;
            return true;
        }
        return false;
    }
}
