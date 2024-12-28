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

    private Piece lastPieceCaptured = null;

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
        if(pieceFrom.equals(pieceTo)) {
            view.displayMessage("You can't move a piece to the same position");
            return false;
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
        /*
        if (board.isCheckMate() || board.isStaleMate()) {
            System.out.println("ENDGAME !");
            isGameOver = true;
            view.displayMessage("Game over");
            newGame();
            return false;
        }
        */
        //register piece Captured
        lastPieceCaptured = pieceTo;

        // execute move
        pieceFrom.executeMove(move, board, view, board.getLastMove());

        pieceFrom.afterMove();
        boolean isChecked = getPlayerKing().isChecked(board);
        System.out.println("King " + getPlayerKing().color().name() + " is checked ? : " + isChecked);
        if(isChecked)
        {
            System.out.println("rollbackNeeded !");
            //RollBack
            view.displayMessage("Rollback");
            rollback(pieceFrom, move.inverse());
            return false;
        }

        // switch player and save last move
        currentPlayerColor = (currentPlayerColor == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
        view.displayMessage("It's " + currentPlayerColor + "'s turn");
        board.setLastMove(move); // save last move for checks

        if (board.isCheckMate() || board.isStaleMate()) {
            System.out.println("ENDGAME !");
            isGameOver = true;
            view.displayMessage("Game over");
            newGame();
            //return false;
        }

        return true;
    }

    private King getPlayerKing() {
        return board.getKing(currentPlayerColor);
    }

    private void rollback(Piece lastPieceMoved, Move move) {
        lastPieceMoved.executeMove(move, board, view, board.getLastMove());
        if(lastPieceCaptured != null) {
            System.out.println("rollback ! lastPieceCaptured = " + lastPieceCaptured.type().name() + " at : " + lastPieceCaptured.pos());
            view.putPiece(lastPieceCaptured.type(), lastPieceCaptured.color(), lastPieceCaptured.pos().x(), lastPieceCaptured.pos().y());
        }
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

    //Tentative checkmate
    private boolean isKingValidMove(Position from, Position to) {
        King king = getPlayerKing();
        Move simulationMove = new Move(from, to);
        Piece pieceTo = board.getPiece(to);

        if(pieceTo != null && king.color().equals(pieceTo.color())) { // J'ai du mettre après le isValidMove car le roi ne peut pas roque sinon
            view.displayMessage("isKingValidMove: You can't eat your own piece");
            return false;
        }

        //register piece Captured
        lastPieceCaptured = pieceTo;
        if(lastPieceCaptured != null){
            System.out.println("isKingValidMove lastPieceCaptured = " + lastPieceCaptured.type().name());
        }
        // execute move
        king.executeMove(simulationMove, board, view, board.getLastMove());
        king.afterMove();
        boolean validMove = !king.isChecked(board);
        rollback(king, simulationMove.inverse());
        System.out.println("rollback ! valid = " + validMove);
        return validMove;
    }

    private boolean isCheckMate() {
        King king = getPlayerKing();
        if(!king.isChecked(board)) return false;

        Position[] moves = {
                king.pos().add(new Position(0,1)), //top
                king.pos().add(new Position(1,1)), //top-right
                king.pos().add(new Position(1,0)), //right
                king.pos().add(new Position(1,-1)),//bot-right
                king.pos().add(new Position(0,-1)), //bot
                king.pos().add(new Position(-1,-1)), //bot-left
                king.pos().add(new Position(-1,0)), //left
                king.pos().add(new Position(-1,1)),//top-left
        };

        for(Position move : moves) {
            boolean validMove = move.isInsideBoard() && isKingValidMove(king.pos(), move);
            System.out.println("validMove = " + validMove);
            if(validMove) {
                return false;
            }
        }
        return true;
    }
}
