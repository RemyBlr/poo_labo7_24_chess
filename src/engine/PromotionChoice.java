package engine;

import chess.ChessView;

public enum PromotionChoice implements ChessView.UserChoice {
    QUEEN("Queen"),
    ROOK("Rook"),
    BISHOP("Bishop"),
    KNIGHT("Knight");

    private final String label;

    PromotionChoice(String label) {
        this.label = label;
    }

    @Override
    public String textValue() {
        return label;
    }
}
