package engine;

import chess.PlayerColor;
import engine.piece.*;

import java.util.HashSet;

public class Board {
    private final int BOARD_SIZE = 8;
    private Piece[][] board;
    private Move lastMove;

//    HashSet<Piece> whitePieces = new HashSet<>();
//    HashSet<Piece> blackPieces = new HashSet<>();

    public Board() {
        this.board = new Piece[BOARD_SIZE][BOARD_SIZE];
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
        /*
        if(piece.color() == PlayerColor.WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
        */
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
        /*
        if(piece.color() == PlayerColor.WHITE) {
            whitePieces.remove(piece);
        } else {
            blackPieces.remove(piece);
        }
        */
        board[pos.x()][pos.y()] = null;
    }

    public void movePiece(Move move) {
        Position from = move.from(), to = move.to();

        board[from.x()][from.y()].setPos(to);
        board[to.x()][to.y()] = board[from.x()][from.y()];
        board[from.x()][from.y()] = null;
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

    public Piece[][] getBoardPieces() {
        return board;
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