package andrepnh.lwjgl.paraphernalia.loop.steps;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import java.util.concurrent.TimeUnit;

public class SlowUpdater {
    private final GlobalState state;
    
    private final DefaultUpdater delegate;

    public SlowUpdater(GlobalState state) {
        this.state = state;
        this.delegate = new DefaultUpdater(state);
    }
    
    public void update(long millisecondsDelay) {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecondsDelay);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        delegate.update();
    }
}
