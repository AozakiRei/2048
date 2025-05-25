package model;

public class GameState {
    private int[][] board;
    private int score;
    private boolean gameOver;

    public GameState() {
        this.board = new int[4][4];
        this.score = 0;
        this.gameOver = false;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void resetGame() {
        this.board = new int[4][4];
        this.score = 0;
        this.gameOver = false;
    }
}