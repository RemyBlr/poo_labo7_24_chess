package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.piece.*;

public class ChessGame implements ChessController {

    private ChessView view;
    private Board board;
    private boolean isGameOver;
    private PlayerColor currentPlayerColor;

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
        Move move = new Move(from, to);

        // A piece must be selected to move a piece
        if(pieceFrom == null) {
            view.displayMessage("Select one piece to move");
            return false;
        }

        // Check if the piece is the current player's piece
        if(!pieceFrom.color().equals(currentPlayerColor)) {
            view.displayMessage("It's not your turn");
            return false;
        }

        // Positions must be inside the board
        if(!from.isInsideBoard() || !to.isInsideBoard()) { // isInsideBoard() doit être dans Piece ou Board ???
            view.displayMessage("Out of board");
            return false;
        }

        // Cannot move a piece to the same position
        if(from.equals(to)) {
            view.displayMessage("You can't move a piece to the same position");
        }

        System.out.println("(" + from + ") -> (" + to + ") Type=" + pieceFrom.type());

        // Check if the move is valid for the piece type
        if(!pieceFrom.isValidMove(move, board)) {
            view.displayMessage(pieceFrom.type() + " can't move to this position");
            return false;
        }

        // Can't eat your own piece
        if(pieceTo != null && pieceFrom.color().equals(pieceTo.color())) { // J'ai du mettre après le isValidMove car le roi ne peut pas roque sinon
            view.displayMessage("You can't eat your own piece");
            return false;
        }

        if (board.isCheckMate() || board.isStaleMate()) {
            isGameOver = true;
            view.displayMessage("Game over");
            newGame();
            return false;
        }

        // execute move
        pieceFrom.executeMove(move, board, view, board.getLastMove());

        pieceFrom.afterMove();

        // switch player and save last move
        currentPlayerColor = (currentPlayerColor == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
        view.displayMessage("It's " + currentPlayerColor + "'s turn");
        board.setLastMove(move); // save last move for checks

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

    /*
     * Display the board
     */
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
