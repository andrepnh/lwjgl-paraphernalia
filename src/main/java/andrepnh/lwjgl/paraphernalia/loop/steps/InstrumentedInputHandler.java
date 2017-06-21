package andrepnh.lwjgl.paraphernalia.loop.steps;

public class InstrumentedInputHandler {
    private final DefaultInputHandler delegate = new DefaultInputHandler();
    
    public long frameStart;
    
    public void handleInput() {
        frameStart = System.currentTimeMillis();
        delegate.handleInput();
    }
    
}
