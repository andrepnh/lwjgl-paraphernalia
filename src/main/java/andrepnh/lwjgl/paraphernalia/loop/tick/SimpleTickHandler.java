package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.loop.tick.TickHandler;
import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.input.DefaultInputHandler;
import andrepnh.lwjgl.paraphernalia.loop.render.DefaultRenderer;
import andrepnh.lwjgl.paraphernalia.loop.update.DefaultUpdater;
import andrepnh.lwjgl.paraphernalia.loop.input.InputHandler;
import andrepnh.lwjgl.paraphernalia.loop.render.Renderer;
import andrepnh.lwjgl.paraphernalia.loop.update.Updater;

public class SimpleTickHandler implements TickHandler {
    private final GlobalState state;
    
    private final DefaultInputHandler inputHandler;
    
    private final DefaultUpdater updater;
    
    private final DefaultRenderer renderer;

    public SimpleTickHandler(GlobalState state) {
        this.state = state;
        this.inputHandler = new DefaultInputHandler();
        this.updater = new DefaultUpdater(state);
        this.renderer = new DefaultRenderer(state);
        
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
        return renderer;
    }
    
}
