package logic;

import java.util.Random;

public class RandomGenerator {
    private static final int[] VALUES = {2, 4};
    private Random random;

    public RandomGenerator() {
        random = new Random();
    }

    public int generateRandomValue() {
        return VALUES[random.nextInt(VALUES.length)];
    }

    public int[] getRandomPosition(int[][] board) {
        int emptyCount = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    emptyCount++;
                }
            }
        }

        if (emptyCount == 0) {
            return null; // No empty position available
        }

        int randomIndex = random.nextInt(emptyCount);
        int[] position = new int[2];
        int count = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    if (count == randomIndex) {
                        position[0] = i;
                        position[1] = j;
                        return position;
                    }
                    count++;
                }
            }
        }
        return null;
    }
}