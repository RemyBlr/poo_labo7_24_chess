package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;

import java.util.ArrayList;
import java.util.HashSet;

public class Board {
    private final int BOARD_SIZE = 8;
    private Position[][] board;

    HashSet<Piece> whitePieces = new HashSet<>();
    HashSet<Piece> blackPieces = new HashSet<>();

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

    private void addPiece(Piece piece) {
        if(piece.color() == PlayerColor.WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
        board[piece.getX()][piece.getY()].setOccupant(piece);
    }

    public void setPawns() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            addPiece(new Pawn(PlayerColor.WHITE, x,1));
            addPiece(new Pawn(PlayerColor.BLACK, x,6));
        }
    }

    public void setRooks() {
        addPiece(new Rook(PlayerColor.WHITE,0,0));
        addPiece(new Rook(PlayerColor.WHITE,7,0));
        addPiece(new Rook(PlayerColor.BLACK,0,7));
        addPiece(new Rook(PlayerColor.BLACK,7,7));
    }

    public void setKnights() {
        addPiece(new Knight(PlayerColor.WHITE,1,0));
        addPiece(new Knight(PlayerColor.WHITE,6,0));
        addPiece(new Knight(PlayerColor.BLACK,1,7));
        addPiece(new Knight(PlayerColor.BLACK,6,7));
    }

    public void setBishops() {
        addPiece(new Bishop(PlayerColor.WHITE,2,0));
        addPiece(new Bishop(PlayerColor.WHITE,5,0));
        addPiece(new Bishop(PlayerColor.BLACK,2,7));
        addPiece(new Bishop(PlayerColor.BLACK,5,7));
    }

    public void setQueens() {
        addPiece(new Queen(PlayerColor.WHITE,3,0));
        addPiece(new Queen(PlayerColor.BLACK,3,7));
    }

    public void setKings() {
        addPiece(new King(PlayerColor.WHITE,4,0));
        addPiece(new King(PlayerColor.BLACK,4,7));
    }

    public void setEmptySquares() {
        for(int x = 0; x < BOARD_SIZE; x++) {
            for(int y = 2; y < 6; y++) {
                board[x][y].setOccupant(null);
            }
        }
    }

    public void removePiece(int x, int y) {
        Piece piece = board[x][y].getOccupant();
        if(piece == null) return;
        if(piece.color() == PlayerColor.WHITE) {
            whitePieces.remove(piece);
        } else {
            blackPieces.remove(piece);
        }
        board[x][y].setOccupant(null);
    }

    public void movePiece(int fromX, int fromY, int toX, int toY) {

        board[fromX][fromY].getOccupant().setXY(toX, toY);

        board[toX][toY].setOccupant(board[fromX][fromY].getOccupant());
        board[fromX][fromY].setOccupant(null);
    }

    public Piece getPiece(int x, int y) {
        return board[x][y].getOccupant();
    }

    public HashSet<Piece> getPlayerPieces(PlayerColor color) {
        return color == PlayerColor.WHITE ? whitePieces : blackPieces;
    }

    public Position getKingPosition(PlayerColor color)
    {
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Position cell = board[x][y];
                if(!cell.isOccupied()) continue;
                Piece piece = cell.getOccupant();
                if( piece.color() != color || piece.type() != PieceType.KING) continue;
                return cell;
            }
        }
        return null;
    }

    public boolean isCheckMate() {
        return false;
    }

    public boolean isStaleMate() {
        return false;
    }
}