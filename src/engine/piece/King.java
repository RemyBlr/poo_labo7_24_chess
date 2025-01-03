package engine.piece;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;

import java.util.HashSet;

public class King extends MovableOncePiece {

    public King(PlayerColor color, Position pos) {
        super(color, pos);
    }

    @Override
    public String textValue() {return getClass().getSimpleName();}

    @Override
    public PieceType type() {
        return PieceType.KING;
    }

    @Override
    public boolean isValidMove(Move move, Board board) {
        Position from = move.from(), to = move.to();
        Move lastMove = board.getLastMove();

        /*
        // Ne peut pas se mettre en échec tout seul
        if (isChecked(board, lastMove)) {
            System.out.println("King is checked.");
            return false;
        }
        */
        // Roque (petit ou grand)
        if(isRoquable(move, board)) {
            return true;
        }

        // Ne peut se déplacer que d'une case
        if (Math.abs(to.x() - from.x()) > 1 || Math.abs(to.y() - from.y()) > 1) {
            //System.out.println("1 case only for the king."); // Pourquoi on rentre ici même si le roi avance de 1 ?
            return false;
        }

        return true;
    }

    @Override
    public void executeMove(Move move, Board board, ChessView view, Move lastMove) {

        // check if castling move
        if (isRoquable(move, board)) {

            // move king
            board.movePiece(move);
            view.removePiece(move.from().x(), move.from().y());
            view.putPiece(this.type(), this.color(), move.to().x(), move.to().y());

            // move rook
            boolean isKingSide = (move.to().x() - move.from().x()) == 2;

            if(isKingSide) {
                // small roque
                Move rookMove = new Move(
                        new Position(7, move.from().y()),
                        new Position(5, move.from().y())
                );
                board.movePiece(rookMove);
                view.removePiece(7, move.from().y());
                view.putPiece(PieceType.ROOK, this.color(), 5, move.from().y());
            } else {
                // queen side roque
                Move rookMove = new Move(
                        new Position(0, move.from().y()),
                        new Position(3, move.from().y())
                );
                board.movePiece(rookMove);
                view.removePiece(0, move.from().y());
                view.putPiece(PieceType.ROOK, this.color(), 3, move.from().y());
            }
        } else {
            // normal king move
            super.executeMove(move, board, view, lastMove);
        }
    }

    public Piece getCheckerPiece(Board board) {
        Piece piece;
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                piece = board.getPiece(new Position(x,y));
                if(piece == null || piece.color() == this.color) continue;
                if(!piece.isValidMove(new Move(piece.pos(), pos), board)) continue;
                return piece;
            }
        }
        return null;
    }

    //TODO
    public boolean isChecked(Board board) {

        return getCheckerPiece(board) != null;
        /*
        Piece piece;
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                piece = board.getPiece(new Position(x,y));
                if(piece == null || piece.color() == this.color) continue;
                if(!piece.isValidMove(new Move(piece.pos(), pos), board)) continue;
                System.out.println(color.name() + " checked by " + piece.type().name());
                return true;
            }
        }
        return false;
        */
    }

    // TODO
    public boolean isRoquable(Move move, Board board) {
        Position from = move.from(), to = move.to();
        boolean small = to.x() - from.x() == 2;

        if (!this.hasMoved() && Math.abs(to.x() - from.x()) == 2 && from.y() == to.y()) {
            // Petit roque
            if (small) {
                Piece rook = board.getPiece(new Position(7, from.y()));
                System.out.println("Rook: " + rook);
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
