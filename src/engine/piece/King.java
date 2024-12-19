package engine.piece;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

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

        // Ne peut pas se mettre en échec tout seul
        if (isChecked(board, lastMove)) {
            System.out.println("King is checked.");
            return false;
        }

        // Roque (petit ou grand)
        if(isRoquable(move, board)) {
            System.out.println("Roque");
            return true;
        }

        // Ne peut se déplacer que d'une case ou tenter un roque
        if (Math.abs(to.x() - from.x()) > 1 || Math.abs(to.y() - from.y()) > 1) {
            System.out.println("1 case only for the king."); // Pourquoi on rentre ici même si le roi avance de 1 ?
            return false;
        }

        return true;
    }

    //TODO
    public boolean isChecked(Board board, Move lastMove) {
        /*
        Move hypotheticalMove;
        PlayerColor enemyColor = color.equals(PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
        HashSet<Piece> enemyPieces = board.getPlayerPieces(enemyColor);
        for (Piece enemy : enemyPieces) {
            hypotheticalMove = new Move(new Position(enemy.pos().x(), enemy.pos().y()), this.pos);
            if (enemy.isValidMove(hypotheticalMove, board, lastMove)) {
                return true;
            }
        }
        */
        return false;
    }

    // TODO
    public boolean isRoquable(Move move, Board board) {
        Position from = move.from(), to = move.to();
        boolean small = to.x() - from.x() == 2;

        if (!this.hasMoved() && Math.abs(to.x() - from.x()) == 2 && from.y() == to.y()) {
            // Petit roque
            if (small) {
                Piece rook = board.getPiece(new Position(7, from.y()));
                if (rook != null && rook instanceof Rook r && !r.hasMoved()) {
                    return true;
                }
            } else { // Grand roque
                Piece rook = board.getPiece(new Position(0, from.y()));
                if (rook != null && rook instanceof Rook r && !r.hasMoved()) {
                    return true;
                }
            }
        }

        return false;
    }
}
