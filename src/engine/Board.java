package engine;

import chess.PlayerColor;
import engine.Piece;

public class Board {
    private final int BOARD_SIZE = 8;
    private Position[][] board;

    public Board() {
        this.board = new Position[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
        setInitialPosition();
    }

    public void initializeBoard() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            for(int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = new Position(x, y);
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

    public void setPawns() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            board[x][1].setOccupant(new Pawn(PlayerColor.WHITE));
            board[x][6].setOccupant(new Pawn(PlayerColor.BLACK));
        }
    }

    public void setRooks() {
        board[0][0].setOccupant(new Rook(PlayerColor.WHITE));
        board[7][0].setOccupant(new Rook(PlayerColor.WHITE));
        board[0][7].setOccupant(new Rook(PlayerColor.BLACK));
        board[7][7].setOccupant(new Rook(PlayerColor.BLACK));
    }

    public void setKnights() {
        board[1][0].setOccupant(new Knight(PlayerColor.WHITE));
        board[6][0].setOccupant(new Knight(PlayerColor.WHITE));
        board[1][7].setOccupant(new Knight(PlayerColor.BLACK));
        board[6][7].setOccupant(new Knight(PlayerColor.BLACK));
    }

    public void setBishops() {
        board[2][0].setOccupant(new Bishop(PlayerColor.WHITE));
        board[5][0].setOccupant(new Bishop(PlayerColor.WHITE));
        board[2][7].setOccupant(new Bishop(PlayerColor.BLACK));
        board[5][7].setOccupant(new Bishop(PlayerColor.BLACK));
    }

    public void setQueens() {
        board[3][0].setOccupant(new Queen(PlayerColor.WHITE));
        board[3][7].setOccupant(new Queen(PlayerColor.BLACK));
    }

    public void setKings() {
        board[4][0].setOccupant(new King(PlayerColor.WHITE));
        board[4][7].setOccupant(new King(PlayerColor.BLACK));
    }

    public void setEmptySquares() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            for(int y = 2; y < 6; y++) {
                board[x][y].setOccupant(null);
            }
        }
    }

    public void removePiece(int x, int y) {
        board[x][y].setOccupant(null);
    }

    public void movePiece(int fromX, int fromY, int toX, int toY) {
        board[toX][toY].setOccupant(board[fromX][fromY].getOccupant());
        board[fromX][fromY].setOccupant(null);
    }

    public Piece getPiece(int x, int y) {
        return board[x][y].getOccupant();
    }

    public boolean isCheckMate() {
        return false;
    }

    public boolean isStaleMate() {
        return false;
    }
}