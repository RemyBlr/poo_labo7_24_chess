package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.Piece;

public class ChessGame implements ChessController {

    private ChessView view;
    private Board board;
    private boolean isGameOver;
    private PlayerColor currentPlayerColor;
    private Move lastMove = new Move(-1,-1,-1,-1);

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
        Piece pieceFrom = board.getPiece(fromX, fromY), pieceTo = board.getPiece(toX, toY);

        if(fromX == toX && fromY == toY) {
            view.displayMessage("You can't move a piece to the same position");
        }

        if(pieceFrom == null) {
            view.displayMessage("Select one piece to move");
            return false;
        }

        // Check if the piece is the current player's piece
        if(pieceFrom.color() != currentPlayerColor) {
            view.displayMessage("It's not your turn");
            return false;
        }

        if(fromX < 0 || fromX >= 8 || fromY < 0 || fromY >= 8 || toX < 0 || toX >= 8 || toY < 0 || toY >= 8) {
            view.displayMessage("Out of board");
            return false;
        }

        if(pieceTo != null && pieceFrom.color() == pieceTo.color()) {
            view.displayMessage("You can't eat your own piece");
            return false;
        }

        Move move = new Move(fromX, fromY, toX, toY);

        // pawn moves two squares (info for en passant
        if (pieceFrom.type() ==  PieceType.PAWN) {
            int direction = (pieceFrom.color() == PlayerColor.WHITE) ? 1 : -1;
            if (Math.abs(toY - fromY) == 2) {
                move.setDoublePawnMove(true);
            }
        }

        // Check if the move is valid for the piece type
        System.out.println("(" + fromX + ", " + fromY + ") -> (" + toX + ", " + toY + ") Type=" + pieceFrom.type());
        if(!pieceFrom.isValidMove(fromX, fromY, toX, toY, board, lastMove)) {
            view.displayMessage(pieceFrom + " can't move to this position");
            return false;
        }

        //System.out.println("en passant ? : " + move.isEnPassant());
        //System.out.println("last move ? : " + lastMove);

        if(lastMove.isEnPassant()) {
            int direction = (pieceFrom.color() == PlayerColor.WHITE) ? 1 : -1;
            board.removePiece(toX, toY - direction);
            view.removePiece(toX, toY - direction);
        }
        else {
            view.removePiece(toX, toY);
            board.removePiece(toX, toY);
        }

        board.movePiece(fromX, fromY, toX, toY);
        view.removePiece(fromX, fromY);
        view.putPiece(pieceFrom.type(), pieceFrom.color(), toX, toY);

        if (board.isCheckMate() || board.isStaleMate()) {
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
                Piece piece = board.getPiece(x, y);
                if (piece != null) {
                    view.putPiece(piece.type(), piece.color(), x, y);
                }
            }
        }
    }
}
