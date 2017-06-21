package andrepnh.lwjgl.paraphernalia.loop.steps;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import java.util.concurrent.TimeUnit;

public class SlowRenderer {
    private final GlobalState state;
    
    private final DefaultRenderer delegate;

    public SlowRenderer(GlobalState state) {
        this.state = state;
        this.delegate = new DefaultRenderer(state);
    }
    
    public void render(long millisecondsDelay) {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecondsDelay);
        } catch (InterruptedException ex) { 
            throw new RuntimeException(ex);
        }
        delegate.render();
    }
}
