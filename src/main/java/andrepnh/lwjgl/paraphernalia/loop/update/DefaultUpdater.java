package andrepnh.lwjgl.paraphernalia.loop.update;

import andrepnh.lwjgl.paraphernalia.GlobalState;

public class DefaultUpdater implements Updater {
    private final GlobalState state;

    public DefaultUpdater(GlobalState state) {
        this.state = state;
    }
    
    @Override
    public void update(int millisecondsPerFrame) {
        state.squares.replaceAll(square -> move(square, millisecondsPerFrame));
    }
    
    private float[] move(float[] square, int millisecondsPerFrame) {
        float unitsMoved = millisecondsPerFrame * GlobalState.SQUARE_SPEED_UNITS_PER_MILLISECOND;
        float x = square[0], y = square[1];
        if (y <= 0) { // bottom line
            if (x < 9) { // room left
                return new float[]{x + unitsMoved, y};
            } else { // reached end
                return new float[]{x, 1};
            }
        } else if (x >= 9 && y < 9) { // climbing up
            return new float[]{x, y + unitsMoved};
        } else { // top line
            if (x > 0) { // tracking back
                return new float[]{x - unitsMoved, y};
            } else { // back to origin
                return new float[]{x, y - unitsMoved};
            }
        }
    }
}
