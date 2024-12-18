package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.King;
import engine.piece.MovableOncePiece;
import engine.piece.Piece;

public class ChessGame implements ChessController {

    private ChessView view;
    private Board board;
    private boolean isGameOver;
    private PlayerColor currentPlayerColor;
    private Move lastMove = new Move(new Position(-1,-1), new Position(-1,-1));

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
//        if(!pieceFrom.color().equals(currentPlayerColor)) {
//            view.displayMessage("It's not your turn");
//            return false;
//        }

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

        // Roque déplacé dans le isValidMove de la classe King

        // Check if the move is valid for the piece type
        if(!pieceFrom.isValidMove(move, board, lastMove)) {
            view.displayMessage(pieceFrom + " can't move to this position");
            return false;
        }

        // Can't eat your own piece
        if(pieceTo != null && pieceFrom.color().equals(pieceTo.color())) { // J'ai du mettre après le isValidMove car le roi ne peut pas roque sinon
            view.displayMessage("You can't eat your own piece");
            return false;
        }

        /*
        if(lastMove.isEnPassant()) {
            int direction = (pieceFrom.color() == PlayerColor.WHITE) ? 1 : -1;
            board.removePiece(new Position(toX, toY - direction));
            view.removePiece(to.x(), to.y() - direction);
        }
        else {
            view.removePiece(toX, toY);
            board.removePiece(to);
        }
        */

        if (board.isCheckMate() || board.isStaleMate()) {
            isGameOver = true;
            view.displayMessage("Game over");
            newGame();
            return false;
        }

        // Roque
        if (pieceFrom.type() == PieceType.KING && ((King) pieceFrom).isRoquable(move, board)) {
            // Mettre à jour le roi
            board.movePiece(move);
            view.removePiece(fromX, fromY);
            view.putPiece(PieceType.KING, pieceFrom.color(), to.x(), to.y());

            // Mettre à jour la tour
            boolean small = to.x() - fromX == 2;
            if(small) {
                board.movePiece(new Move(new Position(7, fromY), new Position(5, fromY)));
                view.removePiece(7, fromY);
                view.putPiece(PieceType.ROOK, pieceFrom.color(), 5, fromY);
            } else {
                board.movePiece(new Move(new Position(0, fromY), new Position(3, fromY)));
                view.removePiece(0, fromY);
                view.putPiece(PieceType.ROOK, pieceFrom.color(), 3, fromY);
            }

        } else { // Normal move
            board.movePiece(move);
            view.removePiece(fromX, fromY);
            view.putPiece(pieceFrom.type(), pieceFrom.color(), to.x(), to.y());
        }

        // Où mettre ça ? Comment ne pas istanceof ça ?
        if(pieceFrom instanceof MovableOncePiece) {
            ((MovableOncePiece) pieceFrom).setHasMoved();
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
