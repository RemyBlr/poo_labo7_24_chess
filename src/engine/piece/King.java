package engine.piece;

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

        // Ne peut pas se mettre en échec
        if (isChecked(board, lastMove)) {
            System.out.println("King is checked.");
            return false;
        }

        // Roque (petit ou grand)
        if(isRoquable(board, move)) {
            System.out.println("Roque");
            return true;
        }

        // Ne peut se déplacer que d'une case
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
    public boolean isRoquable(Board board, Move move) {
        Piece pieceTo = board.getPiece(move.to());

        if (pieceTo != null && pieceTo.type() == PieceType.ROOK
                && !this.hasMoved()
                && !((Rook) pieceTo).hasMoved()) {
            int incrX = -1; // Gauche
            if (pieceTo.pos().x() > this.pos().x()) incrX = 1; // Droite

            for (int x = this.pos().x() + incrX; x != pieceTo.pos().x(); x += incrX) {
                if (board.getPiece(new Position(x, this.pos().y())) != null) {
                    //view.displayMessage("You can't roque");
                    return false;
                }
            }

            int newKingX, newRookX;
            if(pieceTo.pos().x() > this.pos().x()) {
                newKingX = this.pos().x() + 2;
                newRookX = this.pos().x() + 1;
            }
            else {
                newKingX = this.pos().x() - 2;
                newRookX = this.pos().x() - 1;
            }

            Move roqueMove = new Move(this.pos, new Position(newKingX, this.pos().y()));

            board.movePiece(roqueMove);
            //view.removePiece(from.x(), from.y());
            //view.putPiece(pieceFrom.type(), pieceFrom.color(), newKingX, this.pos().y());

            Move rookMove = new Move(new Position(this.pos().x(), this.pos().y()), new Position(newRookX, this.pos().y()));
            board.movePiece(rookMove);
            //view.removePiece(pieceTo.pos().x(), pieceTo.pos().y());
            //view.putPiece(pieceTo.type(), pieceTo.color(), newRookX, pieceTo.pos().y());
        }

        return false;
    }
}
