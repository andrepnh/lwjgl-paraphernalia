package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.loop.input.InputHandler;
import andrepnh.lwjgl.paraphernalia.loop.render.Renderer;
import andrepnh.lwjgl.paraphernalia.loop.update.Updater;

public interface TickHandler {
    InputHandler getInputHandler();
    
    Updater getUpdater();
    
    Renderer getRenderer();
}
