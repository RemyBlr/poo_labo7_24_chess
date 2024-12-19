package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.MovableOncePiece;
import engine.piece.Piece;

public class ChessGame implements ChessController {

    private ChessView view;
    private Board board;
    private boolean isGameOver;
    private PlayerColor currentPlayerColor;
    private Move lastMove = new Move(new Position(-1,-1), new Position(-1,-1), null, null);

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
        view.displayMessage("It's " + currentPlayerColor + "'s turn");
    }

    /*
     * Move a piece from a position to another
     *
     * @param fromX: the x position of the piece to move
     * @param fromY: the y position of the piece to move
     * @param toX: the x position to move the piece to
     * @param toY: the y position to move the piece to
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Position from = new Position(fromX, fromY), to = new Position(toX, toY);
        Piece pieceFrom = board.getPiece(from), pieceTo = board.getPiece(to);
        Move move = new Move(from, to, pieceFrom, pieceTo);

        if(!move.samePosition()) {
            view.displayMessage("You can't move a piece to the same position");
        }

        if(!move.pieceSelected()) {
            view.displayMessage("Select one piece to move");
            return false;
        }

        // Check if the piece is the current player's piece
        if(!move.isPlayerTurn(currentPlayerColor)) {
            view.displayMessage("It's not your turn");
            return false;
        }

        if(!move.isInsideBoard()) {
            view.displayMessage("Out of board");
            return false;
        }

        System.out.println("(" + fromX + ", " + fromY + ") -> (" + toX + ", " + toY + ") Type=" + pieceFrom.type());

        // Roque (petit ou grand)
        if (move.isRoque()) {
            // if(pieceFrom.Color() == Color.WHITE) l'inverse peut-être ?
            int incrX = -1; // Gauche
            if (toX > fromX) incrX = 1; // Droite
            for (int x = fromX + incrX; x != toX; x += incrX) {
                if (board.getPiece(new Position(x, fromY)) != null) {
                    view.displayMessage("You can't roque");
                    return false;
                }
            }

            int newKingX, newRookX;
            if(toX > fromX) {
                newKingX = fromX + 2;
                newRookX = fromX + 1;
            }
            else {
                newKingX = fromX - 2;
                newRookX = fromX - 1;
            }

            Move roqueMove = new Move(from, new Position(newKingX, fromY), pieceFrom, pieceTo);

            board.movePiece(roqueMove);
            view.removePiece(from.x(), from.y());
            view.putPiece(pieceFrom.type(), pieceFrom.color(), newKingX, fromY);

            Move rookMove = new Move(new Position(fromX, fromY), new Position(newRookX, fromY), pieceFrom, pieceTo);
            board.movePiece(rookMove);
            view.removePiece(toX, toY);
            view.putPiece(pieceTo.type(), pieceTo.color(), newRookX, toY);
        }

        if(move.isSameColor()) {
            view.displayMessage("You can't eat your own piece");
            return false;
        }

        // Check if the move is valid for the piece type
        if(!move.isValidMove(board, lastMove)) {
            view.displayMessage(pieceFrom + " can't move to this position");
            return false;
        }

        if(lastMove.isEnPassant()) {
            int direction = (pieceFrom.color() == PlayerColor.WHITE) ? 1 : -1;
            board.removePiece(new Position(toX, toY - direction));
            view.removePiece(to.x(), to.y() - direction);
        }
        else {
            view.removePiece(toX, toY);
            board.removePiece(to);
        }

        board.movePiece(move);
        view.removePiece(fromX, fromY);
        view.putPiece(pieceFrom.type(), pieceFrom.color(), toX, toY);

        if (move.isFinish(board)) {
            isGameOver = true;
            view.displayMessage("Game over");
            newGame();
            return false;
        }

        currentPlayerColor = (currentPlayerColor == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
        view.displayMessage("It's " + currentPlayerColor + "'s turn");
        this.lastMove = move; // save last move for checks
        return true;
    }

    /*
     * Start a new game
     * The board must be reset to its initial position
     */
    @Override
    public void newGame() {
        board = new Board();
        currentPlayerColor = PlayerColor.WHITE;
        isGameOver = false;
        displayBoard();
    }

    public void displayBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board.getPiece(new Position(x, y));
                if (piece != null) {
                    view.putPiece(piece.type(), piece.color(), x, y);
                }
            }
        }
    }
}
