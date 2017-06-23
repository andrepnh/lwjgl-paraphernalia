package andrepnh.lwjgl.paraphernalia.loop.render;

import java.util.concurrent.TimeUnit;

public class SlowRenderer implements Renderer {
    private final Renderer delegate;
    
    private final long millisecondsDelay;

    public SlowRenderer(Renderer delegate, long millisecondsDelay) {
        this.delegate = delegate;
        this.millisecondsDelay = millisecondsDelay;
    }
    
    @Override
    public void render() {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecondsDelay);
        } catch (InterruptedException ex) { 
            throw new RuntimeException(ex);
        }
        delegate.render();
    }
}
