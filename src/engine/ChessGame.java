package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

public class ChessGame implements ChessController {

    private ChessView view;
    private Board board;
    private PlayerColor currentPlayer;

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        System.out.println(String.format("TO REMOVE : from (%d, %d) to (%d, %d)", fromX, fromY, toX, toY)); // TODO remove
        System.out.println("Hi, I'm at index " + (toX * 8 + toY));
        return false; // TODO
    }

    @Override
    public void newGame() {
        board = new Board();
        // first player is white
        currentPlayer = PlayerColor.WHITE;
        displayBoard();
        //board.setInitialPosition();
        //view.displayMessage("new game (TO REMOVE)"); // TODO
        //view.putPiece(PieceType.KING, PlayerColor.BLACK, 3, 4); // TODO exemple uniquement
    }

    public void displayBoard() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board.getPiece(x, y);
                if (piece != null) {
                    view.putPiece(piece.getType(), piece.getColor(), x, y);
                }
            }
        }
    }
}
