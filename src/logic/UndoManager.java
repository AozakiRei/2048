package logic;

import java.util.Stack;
import model.GameState;

public class UndoManager {
    private Stack<GameState> history;
    private int maxHistorySize;

    public UndoManager(int maxHistorySize) {
        this.history = new Stack<>();
        this.maxHistorySize = maxHistorySize;
    }

    public void saveState(GameState state) {
        if (history.size() >= maxHistorySize) {
            history.remove(0); // Remove the oldest state
        }
        history.push(state);
    }

    public GameState undo() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null; // No state to undo
    }

    public boolean canUndo() {
        return !history.isEmpty();
    }
}