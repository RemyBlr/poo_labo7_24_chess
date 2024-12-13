package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.piece.MovableOncePiece;

public class King extends MovableOncePiece {

    public King(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType type() {
        return PieceType.KING;
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY, Board board, Move lastMove) {
        //samePosition deja pris en charge
        return !(Math.abs(toX - fromX) > 1 || Math.abs(toY - fromY) > 1);
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }


    private boolean checkHorses() {
        int numThreats = 8;
        int[] xCoords = { -2, -2, -1, -1, 1, 1, 2, 2};
        int[] yCoords = { -1, 1, -2, 2, -2, 2, -1, 1};
        for (int i = 0; i < numThreats; i++) {
            //int tarX =
        }
        return false;
    }

    public boolean isChecked()
    {
        //Vérifier les attaques des cavaliers
        //    mouvements_cavalier = [(-2, -1), (-2, 1), (-1, -2), (-1, 2), (1, -2), (1, 2), (2, -1), (2, 1)]
        //    for dx, dy in mouvements_cavalier:
        //        x, y = roi_x + dx, roi_y + dy
        //        if dans_les_limites(x, y) and echiquier[x][y] == "cavalier_adverse":
        //            return True


        return false;
    }
}
