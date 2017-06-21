package andrepnh.lwjgl.paraphernalia.loop.steps;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import java.util.concurrent.TimeUnit;

public class FixedFpsRenderer {
    private final GlobalState state;
    private final DefaultRenderer delegate;

    public FixedFpsRenderer(GlobalState state) {
        this.state = state;
        delegate = new DefaultRenderer(state);
    }
    
    public void renderUntil(long millisecondsPerFrame, long frameStart) {
        try {
            delegate.render();
            TimeUnit.MILLISECONDS.sleep(frameStart + millisecondsPerFrame - System.currentTimeMillis());
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
