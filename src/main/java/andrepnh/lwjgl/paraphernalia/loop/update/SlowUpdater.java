package andrepnh.lwjgl.paraphernalia.loop.update;

import andrepnh.lwjgl.paraphernalia.loop.update.Updater;
import java.util.concurrent.TimeUnit;

public class SlowUpdater implements Updater {
    private final Updater delegate;
    
    private final long millisecondsDelay;

    public SlowUpdater(Updater delegate, long millisecondsDelay) {
        this.delegate = delegate;
        this.millisecondsDelay = millisecondsDelay;
    }
    
    @Override
    public void update() {
        try {
            TimeUnit.MILLISECONDS.sleep(millisecondsDelay);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        delegate.update();
    }
}
