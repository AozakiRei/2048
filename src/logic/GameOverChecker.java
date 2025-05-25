package logic;

import model.GameState;

public class GameOverChecker {
    private GameState gameState;

    public GameOverChecker(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isGameOver() {
        return !hasEmptySpaces() && !canMerge();
    }

    private boolean hasEmptySpaces() {
        for (int i = 0; i < gameState.getBoard().length; i++) {
            for (int j = 0; j < gameState.getBoard()[i].length; j++) {
                if (gameState.getBoard()[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMerge() {
        for (int i = 0; i < gameState.getBoard().length; i++) {
            for (int j = 0; j < gameState.getBoard()[i].length; j++) {
                if (i < gameState.getBoard().length - 1 && gameState.getBoard()[i][j] == gameState.getBoard()[i + 1][j]) {
                    return true;
                }
                if (j < gameState.getBoard()[i].length - 1 && gameState.getBoard()[i][j] == gameState.getBoard()[i][j + 1]) {
                    return true;
                }
            }
        }
        return false;
    }
}