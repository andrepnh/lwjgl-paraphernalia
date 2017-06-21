package andrepnh.lwjgl.paraphernalia.loop.steps;

import andrepnh.lwjgl.paraphernalia.GlobalState;

public class DefaultUpdater {
    private final GlobalState state;

    public DefaultUpdater(GlobalState state) {
        this.state = state;
    }
    
    public void update() {
        state.squares.replaceAll(this::move);
    }
    
    private int[] move(int[] square) {
        int x = square[0], y = square[1];
        if (y == 0) { // bottom line
            if (x < 9) { // room left
                return new int[]{x + 1, y};
            } else { // reached end
                return new int[]{x, 1};
            }
        } else if (x == 9 && y < 9) { // climbing up
            return new int[]{x, y + 1};
        } else { // top line
            if (x > 0) { // tracking back
                return new int[]{x - 1, y};
            } else { // back to origin
                return new int[]{x, y - 1};
            }
        }
    }
}
