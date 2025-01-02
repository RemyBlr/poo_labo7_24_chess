package engine;

import chess.PlayerColor;
import engine.piece.*;

public class Board {
    private final int BOARD_SIZE = 8;
    private Piece[][] board;
    private Move lastMove;

    private King whiteKing;
    private King blackKing;

    public Board() {
        this.board = new Piece[BOARD_SIZE][BOARD_SIZE];
        this.lastMove = null;
        initializeBoard();
        setInitialPosition();
    }

    /**
     * Initializes the board with null values
     */
    public void initializeBoard() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            for(int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = null;
            }
        }
    }

    /**
     * Set the initial position of the pieces on the board
     */
    public void setInitialPosition() {
        setPawns();
        setRooks();
        setBishops();
        setKnights();
        setQueens();
        setKings();
    }

    /**
     * Add a piece to the board
     * @param piece the piece to be added
     */
    private void addPiece(Piece piece) {
        board[piece.pos().x()][piece.pos().y()] = piece;
    }

    /**
     * Set the pawns on the board (second row for white, seventh row for black)
     */
    public void setPawns() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            addPiece(new Pawn(PlayerColor.WHITE, new Position(x, 1)));
            addPiece(new Pawn(PlayerColor.BLACK, new Position(x, 6)));
        }
    }

    /**
     * Set the rooks on the board
     */
    public void setRooks() {
        addPiece(new Rook(PlayerColor.WHITE, new Position(0, 0)));
        addPiece(new Rook(PlayerColor.WHITE, new Position(7, 0)));
        addPiece(new Rook(PlayerColor.BLACK, new Position(0, 7)));
        addPiece(new Rook(PlayerColor.BLACK, new Position(7, 7)));
    }

    /**
     * Set the knights on the board
     */
    public void setKnights() {
        addPiece(new Knight(PlayerColor.WHITE, new Position(1, 0)));
        addPiece(new Knight(PlayerColor.WHITE, new Position(6, 0)));
        addPiece(new Knight(PlayerColor.BLACK, new Position(1, 7)));
        addPiece(new Knight(PlayerColor.BLACK, new Position(6, 7)));
    }

    /**
     * Set the bishops on the board
     */
    public void setBishops() {
        addPiece(new Bishop(PlayerColor.WHITE, new Position(2, 0)));
        addPiece(new Bishop(PlayerColor.WHITE, new Position(5, 0)));
        addPiece(new Bishop(PlayerColor.BLACK, new Position(2, 7)));
        addPiece(new Bishop(PlayerColor.BLACK, new Position(5, 7)));
    }

    /**
     * Set the queens on the board
     */
    public void setQueens() {
        addPiece(new Queen(PlayerColor.WHITE, new Position(3, 0)));
        addPiece(new Queen(PlayerColor.BLACK, new Position(3, 7)));
    }

    /**
     * Set the kings on the board
     */
    public void setKings() {
        whiteKing = new King(PlayerColor.WHITE, new Position(4, 0));
        addPiece(whiteKing);
        blackKing = new King(PlayerColor.BLACK, new Position(4, 7));
        addPiece(blackKing);
    }

    /**
     * Remove a piece from the board
     * @param pos the position of the piece to be removed
     */
    public void removePiece(Position pos) {
        Piece piece = board[pos.x()][pos.y()];
        if(piece == null) return;
        board[pos.x()][pos.y()] = null;
    }

    /**
     * Move a piece on the board
     * @param move the move to be executed
     */
    public void movePiece(Move move) {
        Position from = move.from(), to = move.to();

        board[from.x()][from.y()].setPos(to);
        board[to.x()][to.y()] = board[from.x()][from.y()];
        board[from.x()][from.y()] = null;
    }

    /**
     * Set the last move
     * @param move the last move
     */
    public void setLastMove(Move move) {
        this.lastMove = move;
    }

    /**
     * @return the last move
     */
    public Move getLastMove() {
        return lastMove;
    }

    /**
     * Get the piece at a position
     * @param pos the position of the piece
     * @return the piece at the position
     */
    public Piece getPiece(Position pos) {
        return board[pos.x()][pos.y()];
    }

    /**
     * Set a piece at a position
     * @param piece the piece to be set
     * @param pos the position to set the piece
     */
    public void setPiece(Piece piece, Position pos) {
        board[pos.x()][pos.y()] = piece;
    }

    /**
     * Get the king of a color
     * @param color the color of the king
     * @return the king of the color
     */
    public King getKing(PlayerColor color) {
        return color == PlayerColor.WHITE ? whiteKing : blackKing;
    }
}