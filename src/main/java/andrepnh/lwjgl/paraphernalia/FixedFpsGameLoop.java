package andrepnh.lwjgl.paraphernalia;

import java.util.concurrent.TimeUnit;

public class FixedFpsGameLoop implements GenericGameLoop {
    private static final int MILLISECONDS_PER_FRAME = 32; // 30fps
    
    private final LwjglGameLoop delegate;
    
    private long frameStart;

    public FixedFpsGameLoop(LwjglGameLoop delegate) {
        this.delegate = delegate;
    }

    @Override
    public void init() {
        delegate.init();
    }

    @Override
    public boolean exit() {
        return delegate.exit();
    }

    @Override
    public void handleInput() {
        frameStart = System.currentTimeMillis();
        delegate.handleInput();
    }

    @Override
    public void update() {
        delegate.update();
    }

    @Override
    public void render() {
        try {
            delegate.render();
            TimeUnit.MILLISECONDS.sleep(frameStart + MILLISECONDS_PER_FRAME - System.currentTimeMillis());
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
