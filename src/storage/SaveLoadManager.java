import java.io.*;
import java.util.*;
import model.GameState;

public class SaveLoadManager {
    private static final String SAVE_FILE = "savegame.txt";

    public void saveGame(GameState gameState) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(gameState.getScore() + "\n");
            for (int[] row : gameState.getBoard()) {
                writer.write(Arrays.toString(row).replaceAll("[\\[\\],]", "") + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameState loadGame() {
        GameState gameState = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            int score = Integer.parseInt(reader.readLine());
            int[][] board = new int[4][4];
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.trim().split(" ");
                for (int col = 0; col < values.length; col++) {
                    board[row][col] = Integer.parseInt(values[col]);
                }
                row++;
            }
            gameState = new GameState();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameState;
    }
}