package logic;

import model.GameState;
import logic.RandomGenerator;
import logic.UndoManager;
import logic.GameOverChecker;

public class GameManager {
    private GameState gameState;
    private RandomGenerator randomGenerator;
    private UndoManager undoManager;
    private GameOverChecker gameOverChecker;

    public GameManager() {
        this.gameState = new GameState();
        this.randomGenerator = new RandomGenerator();
        this.undoManager = new UndoManager(20); // 传入最大历史记录数
        this.gameOverChecker = new GameOverChecker(gameState);
        initializeGame();
    }

    private void initializeGame() {
        gameState.resetGame();
        addRandomTile();
        addRandomTile();
    }

    public void addRandomTile() {
        int[][] board = gameState.getBoard();
        int[] pos = randomGenerator.getRandomPosition(board);
        if (pos != null) {
            board[pos[0]][pos[1]] = randomGenerator.generateRandomValue();
        }
    }

    public boolean move(int direction) {
        boolean moved = false;
        switch (direction) {
            case 0: // Up
                moved = moveUp();
                break;
            case 1: // Down
                moved = moveDown();
                break;
            case 2: // Left
                moved = moveLeft();
                break;
            case 3: // Right
                moved = moveRight();
                break;
        }
        if (moved) {
            // 存储当前状态的深拷贝
            undoManager.saveState(cloneGameState(gameState));
            addRandomTile();
        }
        return moved;
    }

    private boolean moveUp() {
        int[][] board = gameState.getBoard();
        boolean moved = false;
        int score = gameState.getScore();

        for (int col = 0; col < 4; col++) {
            int[] temp = new int[4];
            int idx = 0;
            // 收集非零元素
            for (int row = 0; row < 4; row++) {
                if (board[row][col] != 0) {
                    temp[idx++] = board[row][col];
                }
            }
            // 合并
            for (int i = 0; i < 3; i++) {
                if (temp[i] != 0 && temp[i] == temp[i + 1]) {
                    temp[i] *= 2;
                    score += temp[i];
                    temp[i + 1] = 0;
                    i++; // 跳过下一个
                }
            }
            // 移动合并后的结果到顶部
            int[] newCol = new int[4];
            int newIdx = 0;
            for (int i = 0; i < 4; i++) {
                if (temp[i] != 0) {
                    newCol[newIdx++] = temp[i];
                }
            }
            // 检查是否有变化
            for (int row = 0; row < 4; row++) {
                if (board[row][col] != newCol[row]) {
                    moved = true;
                    board[row][col] = newCol[row];
                }
            }
        }
        gameState.setScore(score);
        return moved;
    }

    private boolean moveDown() {
        int[][] board = gameState.getBoard();
        boolean moved = false;
        int score = gameState.getScore();

        for (int col = 0; col < 4; col++) {
            int[] temp = new int[4];
            int idx = 0;
            // 收集非零元素
            for (int row = 3; row >= 0; row--) {
                if (board[row][col] != 0) {
                    temp[idx++] = board[row][col];
                }
            }
            // 合并
            for (int i = 0; i < 3; i++) {
                if (temp[i] != 0 && temp[i] == temp[i + 1]) {
                    temp[i] *= 2;
                    score += temp[i];
                    temp[i + 1] = 0;
                    i++;
                }
            }
            // 移动合并后的结果到底部
            int[] newCol = new int[4];
            int newIdx = 0;
            for (int i = 0; i < 4; i++) {
                if (temp[i] != 0) {
                    newCol[newIdx++] = temp[i];
                }
            }
            // 检查是否有变化
            for (int row = 3, i = 0; row >= 0; row--, i++) {
                if (board[row][col] != newCol[i]) {
                    moved = true;
                    board[row][col] = newCol[i];
                }
            }
        }
        gameState.setScore(score);
        return moved;
    }

    private boolean moveLeft() {
        int[][] board = gameState.getBoard();
        boolean moved = false;
        int score = gameState.getScore();

        for (int row = 0; row < 4; row++) {
            int[] temp = new int[4];
            int idx = 0;
            // 收集非零元素
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0) {
                    temp[idx++] = board[row][col];
                }
            }
            // 合并
            for (int i = 0; i < 3; i++) {
                if (temp[i] != 0 && temp[i] == temp[i + 1]) {
                    temp[i] *= 2;
                    score += temp[i];
                    temp[i + 1] = 0;
                    i++;
                }
            }
            // 移动合并后的结果到最左侧
            int[] newRow = new int[4];
            int newIdx = 0;
            for (int i = 0; i < 4; i++) {
                if (temp[i] != 0) {
                    newRow[newIdx++] = temp[i];
                }
            }
            // 检查是否有变化
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != newRow[col]) {
                    moved = true;
                    board[row][col] = newRow[col];
                }
            }
        }
        gameState.setScore(score);
        return moved;
    }

    private boolean moveRight() {
        int[][] board = gameState.getBoard();
        boolean moved = false;
        int score = gameState.getScore();

        for (int row = 0; row < 4; row++) {
            int[] temp = new int[4];
            int idx = 0;
            // 收集非零元素
            for (int col = 3; col >= 0; col--) {
                if (board[row][col] != 0) {
                    temp[idx++] = board[row][col];
                }
            }
            // 合并
            for (int i = 0; i < 3; i++) {
                if (temp[i] != 0 && temp[i] == temp[i + 1]) {
                    temp[i] *= 2;
                    score += temp[i];
                    temp[i + 1] = 0;
                    i++;
                }
            }
            // 移动合并后的结果到最右侧
            int[] newRow = new int[4];
            int newIdx = 0;
            for (int i = 0; i < 4; i++) {
                if (temp[i] != 0) {
                    newRow[newIdx++] = temp[i];
                }
            }
            // 检查是否有变化
            for (int col = 3, i = 0; col >= 0; col--, i++) {
                if (board[row][col] != newRow[i]) {
                    moved = true;
                    board[row][col] = newRow[i];
                }
            }
        }
        gameState.setScore(score);
        return moved;
    }

    public boolean isGameOver() {
        return gameOverChecker.isGameOver();
    }

    public GameState getGameState() {
        return gameState;
    }

    // 添加GameState深拷贝方法
    private GameState cloneGameState(GameState state) {
        int[][] oldBoard = state.getBoard();
        int[][] newBoard = new int[oldBoard.length][oldBoard[0].length];
        for (int i = 0; i < oldBoard.length; i++) {
            System.arraycopy(oldBoard[i], 0, newBoard[i], 0, oldBoard[i].length);
        }
        GameState copy = new GameState();
        copy.setBoard(newBoard);
        copy.setScore(state.getScore());
        copy.setGameOver(state.isGameOver());
        return copy;
    }
}