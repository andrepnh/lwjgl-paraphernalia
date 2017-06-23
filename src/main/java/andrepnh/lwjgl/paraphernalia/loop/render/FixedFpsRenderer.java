package andrepnh.lwjgl.paraphernalia.loop.render;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import java.util.concurrent.TimeUnit;

public class FixedFpsRenderer implements Renderer {
    private final DefaultRenderer delegate;
    private final long millisecondsPerFrame;
    private final long frameStart;

    public FixedFpsRenderer(GlobalState state, long millisecondsPerFrame, long frameStart) {
        this.millisecondsPerFrame = millisecondsPerFrame;
        this.frameStart = frameStart;
        delegate = new DefaultRenderer(state);
    }
    
    @Override
    public void render() {
        try {
            delegate.render();
            TimeUnit.MILLISECONDS.sleep(frameStart + millisecondsPerFrame - System.currentTimeMillis());
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
