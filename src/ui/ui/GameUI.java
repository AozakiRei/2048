package ui;

import logic.GameManager;
import model.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameUI extends JFrame {
    private static final int SIZE = 4;
    private static final int TILE_SIZE = 100;
    private static final int GAP_SIZE = 10;
    private JLabel[][] tiles;
    private JLabel scoreLabel;
    private int score;
    private GameManager gameManager;

    public GameUI() {
        setTitle("2048 Game");
        setSize(SIZE * (TILE_SIZE + GAP_SIZE) + GAP_SIZE, SIZE * (TILE_SIZE + GAP_SIZE) + GAP_SIZE + 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        score = 0;
        scoreLabel = new JLabel("Score: " + score);
        add(scoreLabel, BorderLayout.NORTH);

        JPanel board = new JPanel();
        board.setLayout(new GridLayout(SIZE, SIZE, GAP_SIZE, GAP_SIZE));
        tiles = new JLabel[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles[i][j] = new JLabel("", SwingConstants.CENTER);
                tiles[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                tiles[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                tiles[i][j].setOpaque(true);
                tiles[i][j].setBackground(Color.WHITE);
                board.add(tiles[i][j]);
            }
        }

        add(board, BorderLayout.CENTER);

        gameManager = new GameManager();
        refreshBoard();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });

        setVisible(true);
    }

    private void handleKeyPress(KeyEvent e) {
        boolean moved = false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                moved = gameManager.move(0);
                break;
            case KeyEvent.VK_DOWN:
                moved = gameManager.move(1);
                break;
            case KeyEvent.VK_LEFT:
                moved = gameManager.move(2);
                break;
            case KeyEvent.VK_RIGHT:
                moved = gameManager.move(3);
                break;
        }
        if (moved) {
            refreshBoard();
            if (gameManager.isGameOver()) {
                JOptionPane.showMessageDialog(this, "游戏结束！", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void refreshBoard() {
        GameState state = gameManager.getGameState();
        int[][] board = state.getBoard();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                updateTile(i, j, board[i][j]);
            }
        }
        updateScore(state.getScore());
    }

    public void updateTile(int row, int col, int value) {
        tiles[row][col].setText(value > 0 ? String.valueOf(value) : "");
        tiles[row][col].setBackground(value > 0 ? getColorForValue(value) : Color.WHITE);
    }

    public void updateScore(int newScore) {
        score = newScore;
        scoreLabel.setText("Score: " + score);
    }

    private Color getColorForValue(int value) {
        switch (value) {
            case 2: return new Color(238, 228, 218);
            case 4: return new Color(237, 224, 200);
            case 8: return new Color(242, 177, 121);
            case 16: return new Color(245, 149, 99);
            case 32: return new Color(246, 124, 95);
            case 64: return new Color(246, 94, 59);
            case 128: return new Color(237, 207, 114);
            case 256: return new Color(237, 204, 97);
            case 512: return new Color(237, 200, 80);
            case 1024: return new Color(237, 197, 63);
            case 2048: return new Color(237, 194, 46);
            default: return new Color(204, 192, 179);
        }
    }

    public void startGame() {
        setVisible(true);
    }

    public static void main(String[] args) {
        new GameUI();
    }
}