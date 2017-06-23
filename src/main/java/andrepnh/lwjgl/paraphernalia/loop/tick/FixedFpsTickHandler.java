package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.loop.tick.TickHandler;
import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.update.DefaultUpdater;
import andrepnh.lwjgl.paraphernalia.loop.render.FixedFpsRenderer;
import andrepnh.lwjgl.paraphernalia.loop.input.InputHandler;
import andrepnh.lwjgl.paraphernalia.loop.input.InstrumentedInputHandler;
import andrepnh.lwjgl.paraphernalia.loop.render.Renderer;
import andrepnh.lwjgl.paraphernalia.loop.update.Updater;

public class FixedFpsTickHandler implements TickHandler {
    private static final int MILLISECONDS_PER_FRAME = 32; // 30fps

    private final GlobalState state;
    
    private final InstrumentedInputHandler inputHandler;
    
    private final DefaultUpdater updater;
    
    public FixedFpsTickHandler(GlobalState state) {
        this.state = state;
        this.inputHandler = new InstrumentedInputHandler();
        this.updater = new DefaultUpdater(state);
    }
    
    @Override
    public InputHandler getInputHandler() {
        return inputHandler;
    }

    @Override
    public Updater getUpdater() {
        return updater;
    }

    @Override
    public Renderer getRenderer() {
        return new FixedFpsRenderer(state, MILLISECONDS_PER_FRAME, inputHandler.frameStart);
    }
    
}
