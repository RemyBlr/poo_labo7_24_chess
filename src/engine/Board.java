package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;

public class Board {
    private final int BOARD_SIZE = 8;
    private Piece[][] board;
    private Move lastMove;

    public Board() {
        this.board = new Piece[BOARD_SIZE][BOARD_SIZE];
        this.lastMove = null;
        initializeBoard();
        setInitialPosition();
    }

    public void initializeBoard() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            for(int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = null;
            }
        }
    }

    public void setInitialPosition() {
        setPawns();
        setRooks();
        setBishops();
        setKnights();
        setQueens();
        setKings();
        setEmptySquares();
    }

    private void addPiece(Piece piece) {
        board[piece.pos().x()][piece.pos().y()] = piece;
    }

    public void setPawns() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            addPiece(new Pawn(PlayerColor.WHITE, new Position(x, 1)));
            addPiece(new Pawn(PlayerColor.BLACK, new Position(x, 6)));
        }
    }

    public void setRooks() {
        addPiece(new Rook(PlayerColor.WHITE, new Position(0, 0)));
        addPiece(new Rook(PlayerColor.WHITE, new Position(7, 0)));
        addPiece(new Rook(PlayerColor.BLACK, new Position(0, 7)));
        addPiece(new Rook(PlayerColor.BLACK, new Position(7, 7)));
    }

    public void setKnights() {
        addPiece(new Knight(PlayerColor.WHITE, new Position(1, 0)));
        addPiece(new Knight(PlayerColor.WHITE, new Position(6, 0)));
        addPiece(new Knight(PlayerColor.BLACK, new Position(1, 7)));
        addPiece(new Knight(PlayerColor.BLACK, new Position(6, 7)));
    }

    public void setBishops() {
        addPiece(new Bishop(PlayerColor.WHITE, new Position(2, 0)));
        addPiece(new Bishop(PlayerColor.WHITE, new Position(5, 0)));
        addPiece(new Bishop(PlayerColor.BLACK, new Position(2, 7)));
        addPiece(new Bishop(PlayerColor.BLACK, new Position(5, 7)));
    }

    public void setQueens() {
        addPiece(new Queen(PlayerColor.WHITE, new Position(3, 0)));
        addPiece(new Queen(PlayerColor.BLACK, new Position(3, 7)));
    }

    public void setKings() {
        addPiece(new King(PlayerColor.WHITE, new Position(4, 0)));
        addPiece(new King(PlayerColor.BLACK, new Position(4, 7)));
    }

    public void setEmptySquares() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            for(int y = 2; y < 6; y++) {
                board[x][y] = null;
            }
        }
    }

    public void removePiece(Position pos) {
        Piece piece = board[pos.x()][pos.y()];
        if(piece == null) return;
        board[pos.x()][pos.y()] = null;
    }

    public void movePiece(Move move) {
        Position from = move.from(), to = move.to();

        board[from.x()][from.y()].setPos(to);
        board[to.x()][to.y()] = board[from.x()][from.y()];
        board[from.x()][from.y()] = null;
    }

    public void simulateMove(Move move) {
        Piece moving = board[move.from().x()][move.from().y()];
        board[move.to().x()][move.to().y()] = moving;
        board[move.from().x()][move.from().y()] = null;
    }

    public void undoMove(Move move, Piece captured) {
        Piece moving = board[move.to().x()][move.to().y()];
        board[move.from().x()][move.from().y()] = moving;
        board[move.to().x()][move.to().y()] = captured;
    }

    public void setLastMove(Move move) {
        this.lastMove = move;
    }

    public Move getLastMove() {
        return lastMove;
    }

    public Piece getPiece(Position pos) {
        return board[pos.x()][pos.y()];
    }

    public void setPiece(Piece piece, Position pos) {
        board[pos.x()][pos.y()] = piece;
    }

    public boolean isCheck(PlayerColor color) {
        // find king position
        Position kingPos = findKing(color);
        System.out.println("King position: " + kingPos);

        // check if attacked by enemy pieces
        PlayerColor enemyColor = (color == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece enemyPiece = board[x][y];
                if (enemyPiece != null && enemyPiece.color() == enemyColor) {
                    Move hypotheticalMove = new Move(new Position(x, y), kingPos);

                    if (enemyPiece.isValidMove(hypotheticalMove, this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Position findKing(PlayerColor color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board[x][y];
                if (piece != null
                        && piece.type() == PieceType.KING
                        && piece.color() == color) {
                    return new Position(x, y);
                }
            }
        }
        return null; // no king found, should never happen
    }

    // TODO
    public boolean isCheckMate() {
        return false;
    }

    // TODO
    public boolean isStaleMate() {
        return false;
    }
}