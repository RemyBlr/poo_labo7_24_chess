package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;

public class ChessGame implements ChessController {

    private ChessView view;
    private Board board;
    private PlayerColor currentPlayerColor;

    private Piece lastPieceCaptured = null;
    private boolean isCheckMate = false;
    private boolean isStaleMate = false;

    /*
     * Start a new game
     * The board must be reset to its initial position
     */
    @Override
    public void newGame() {
        board = new Board();
        isCheckMate = false;
        isStaleMate = false;
        currentPlayerColor = PlayerColor.WHITE;
        displayBoard();
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
        view.displayMessage("It's " + currentPlayerColor + "'s turn");
    }

    /**
     * Move a piece from a position to another
     * @param fromX: the x position of the piece to move
     * @param fromY: the y position of the piece to move
     * @param toX: the x position to move the piece to
     * @param toY: the y position to move the piece to
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        if(isCheckMate) {
            view.displayMessage("Checkmate! Game over.");
            return false;
        }
        if(isStaleMate) {
            view.displayMessage("Stalemate! Game over.");
            return false;
        }

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
        if(!from.isInsideBoard() || !to.isInsideBoard()) {
            view.displayMessage("Out of board");
            return false;
        }

        // Cannot move a piece to the same position
        if(pieceFrom.equals(pieceTo)) {
            view.displayMessage("You can't move a piece to the same position");
            return false;
        }

        // Check if the move is valid for the piece type
        if(!pieceFrom.isValidMove(move, board)) {
            view.displayMessage(pieceFrom.type() + " can't move to this position");
            return false;
        }

        // Can't eat your own piece
        if(pieceTo != null && pieceFrom.color().equals(pieceTo.color())) {
            view.displayMessage("You can't eat your own piece");
            return false;
        }

        //register piece Captured
        lastPieceCaptured = pieceTo;

        // execute move
        pieceFrom.executeMove(move, board, view);
        pieceFrom.afterMove();

        // check if the king is checked
        if(isKingChecked(pieceFrom, move)) {
            view.displayMessage("You can't leave your King in check");

            return false;
        }

        // switch player and save last move
        currentPlayerColor = (currentPlayerColor == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
        //view.displayMessage("It's " + currentPlayerColor + "'s turn");
        String message = "It's " + currentPlayerColor + "'s turn" + (getPlayerKing().isChecked(board) ? " and King is Checked!" : "");
        view.displayMessage(message);
        // check if the game is over
        if (isCheckMate()) {
            view.displayMessage("Checkmate! Game over.");
            isCheckMate = true;
            return false;
        }
        else if (isStaleMate()) {
            view.displayMessage("Stalemate! Game over.");
            isStaleMate = true;
            return false;
        }
        board.setLastMove(move);

        return true;
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

    /**
     * @return the king of the current player
     */
    private King getPlayerKing() {
        return board.getKing(currentPlayerColor);
    }

    /**
     * Check if the game is a stalemate
     * @return true if the game is a stalemate, false otherwise
     */
    private boolean isStaleMate() {
        // king is not checked
        if (getPlayerKing().isChecked(board)) return false;

        // get over all pieces
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board.getPiece(new Position(x, y));
                if (piece == null) continue;
                if (piece.color() != currentPlayerColor) continue;

                // test all possible moves
                for (int tx = 0; tx < 8; tx++) {
                    for (int ty = 0; ty < 8; ty++) {
                        Position toPos = new Position(tx, ty);
                        // hypothetic move
                        Move testMove = new Move(new Position(x, y), toPos);

                        // valid move
                        if (!piece.isValidMove(testMove, board)) continue;

                        // not ally piece
                        Piece pieceTo = board.getPiece(toPos);
                        if (pieceTo != null && pieceTo.color() == currentPlayerColor) continue;

                        // test move
                        Piece captured = board.getPiece(toPos);
                        // temp move
                        board.movePiece(testMove);

                        boolean stillInCheck = getPlayerKing().isChecked(board);

                        // reverse move
                        Move inverse = testMove.inverse();
                        board.movePiece(inverse);

                        // set captured piece back
                        if (captured != null) board.setPiece(captured, testMove.to());

                        // still legal move left to play
                        if (!stillInCheck) return false;
                    }
                }
            }
        }

        // not legal move left to play
        return true;
    }

    /**
     * Check if the king is checked after a move
     * @param pieceFrom: the piece to move
     * @param move: the move to execute
     * @return true if the king is checked, false otherwise
     */
    private boolean isKingChecked(Piece pieceFrom, Move move)
    {
        boolean isChecked = getPlayerKing().isChecked(board);
        if(isChecked)
        {
            rollback(pieceFrom, move);
            return true;
        }
        return false;
    }

    /**
     * Rollback a move
     * @param lastPieceMoved: the piece to rollback
     * @param move: the move to rollback
     */
    private void rollback(Piece lastPieceMoved, Move move) {
        lastPieceMoved.executeMove(move.inverse(), board, view);
        if(lastPieceCaptured != null) {
            board.setPiece(lastPieceCaptured, move.to());
            view.putPiece(lastPieceCaptured.type(), lastPieceCaptured.color(), lastPieceCaptured.pos().x(), lastPieceCaptured.pos().y());
        }
    }

    /**
     * Check if the move is valid for the king
     * @param from the position of the king
     * @param to the position to move the king to
     * @return true if the move is valid, false otherwise
     */
    private boolean isKingValidMove(Position from, Position to) {
        King king = getPlayerKing();
        Move simulationMove = new Move(from, to);
        Piece pieceTo = board.getPiece(to);

        if(pieceTo != null && king.color().equals(pieceTo.color())) return false;

        //register piece Captured
        lastPieceCaptured = pieceTo;

        // execute move
        king.executeMove(simulationMove, board, view);
        king.afterMove();
        boolean validMove = !king.isChecked(board);
        rollback(king, simulationMove);
        return validMove;
    }

    /**
     * Check if the game is a checkmate
     * @return true if the game is a checkmate, false otherwise
     */
    private boolean isCheckMate() {
        if(!getPlayerKing().isChecked(board)) return false;
        if(hasKingValidMoves()) return false;
        return checkMateTest();
    }

    /**
     * Check if the king has valid moves
     * @return true if the king has valid moves, false otherwise
     */
    private boolean hasKingValidMoves() {
        King king = getPlayerKing();

        Position[] moves = {
                king.pos().add(new Position(0,1)),      //top
                king.pos().add(new Position(1,1)),      //top-right
                king.pos().add(new Position(1,0)),      //right
                king.pos().add(new Position(1,-1)),     //bot-right
                king.pos().add(new Position(0,-1)),     //bot
                king.pos().add(new Position(-1,-1)),    //bot-left
                king.pos().add(new Position(-1,0)),     //left
                king.pos().add(new Position(-1,1)),     //top-left
        };

        for(Position move : moves) {
            boolean validMove = move.isInsideBoard() && isKingValidMove(king.pos(), move);
            if(validMove) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the game is a checkmate
     * @return true if the game is a checkmate, false otherwise
     */
    private boolean checkMateTest() {
        Piece checker = getPlayerKing().getCheckerPiece(board);

        if(checker == null) return false;

        //check if can eat the checker
        if(checkAnyAllyPieceValidMove(getPlayerKing().color(), checker.pos())) return false;

        // no ally piece can eat the checker (all king moves have been tested before)
        if(getPlayerKing().pos().distance(checker.pos()) == 1 || checker.type() == PieceType.KNIGHT) return true;

        Position[] posToCheck = getPositionInBetween(checker.pos(), getPlayerKing().pos());
        for (Position pos : posToCheck) {
            if(!checkAnyAllyPieceValidMove(getPlayerKing().color(), pos)) continue;
            return false;
        }
        return true;
    }

    /**
     * Check if any ally piece except the king has a valid move to a position
     * @param allyColor the color of the ally piece
     * @param pos the position to check
     * @return true if any ally piece has a valid move to the position, false otherwise
     */
    private boolean checkAnyAllyPieceValidMove(PlayerColor allyColor, Position pos) {
        Piece allyPiece;
        for(int x = 0; x < 8; x++) {
            for(int y = 0; y < 8; y++) {
                allyPiece = board.getPiece(new Position(x,y));
                if(allyPiece == null || allyPiece.color() != allyColor || allyPiece.type() == PieceType.KING) continue;
                if(!allyPiece.isValidMove(new Move(allyPiece.pos(), pos), board)) continue;
                return true;
            }
        }
        return false;
    }

    /**
     * Get the positions in between king and attacker
     * @param from the starting position
     * @param to the ending position
     * @return the positions in between
     */
    private Position[] getPositionInBetween(Position from, Position to) {
        Position[] positions = new Position[Math.max(from.distance(to) - 1,0)];
        if(positions.length >= 1) {
            Position direction = from.directionTo(to);
            for (int i = 0; i < positions.length; i++) {
                positions[i] = from.add(direction.mul(i + 1));
            }
        }
        return positions;
    }
}
