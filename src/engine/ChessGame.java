package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.piece.Piece;

public class ChessGame implements ChessController {

    private ChessView view;
    private Board board;
    private boolean isGameOver;
    private PlayerColor currentPlayerColor;

    public ChessGame() {
        newGame();
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();

        // Set the pieces on the board
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board.getPiece(i, j) != null) {
                    view.putPiece(board.getPiece(i, j).type(), board.getPiece(i, j).color(), i, j);
                }
            }
        }

        view.displayMessage("It's " + currentPlayerColor + "'s turn");

//    while(!isGameOver) {
//      view.displayMessage("It's " + currentPlayer + "'s turn");
//    }
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

        if(pieceFrom != null && pieceTo != null && pieceFrom.color() == pieceTo.color()) {
            view.displayMessage("You can't eat your own piece");
            return false;
        }

        // Check if the move is valid for the piece type
        if(!pieceFrom.isValidMove(fromX, fromY, toX, toY)) {
            view.displayMessage(pieceFrom + " can't move to this position");
            return false;
        }

        if (pieceTo != null) {
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
        return true;
    }

    /*
     * Start a new game
     * The board must be reset to its initial position
     */
    @Override
    public void newGame() {
        board = new Board();
        board.setInitialPosition();
        currentPlayerColor = PlayerColor.WHITE;
        isGameOver = false;
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
