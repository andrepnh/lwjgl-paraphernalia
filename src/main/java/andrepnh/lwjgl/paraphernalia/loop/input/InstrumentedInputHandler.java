package andrepnh.lwjgl.paraphernalia.loop.input;

import andrepnh.lwjgl.paraphernalia.loop.input.InputHandler;

public class InstrumentedInputHandler implements InputHandler {
    private final DefaultInputHandler delegate = new DefaultInputHandler();
    
    public long frameStart;
    
    @Override
    public void handleInput() {
        frameStart = System.currentTimeMillis();
        delegate.handleInput();
    }
    
}
