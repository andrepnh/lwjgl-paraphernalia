package andrepnh.lwjgl.paraphernalia.loop;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.render.Renderer;
import andrepnh.lwjgl.paraphernalia.loop.render.SlowRenderer;
import andrepnh.lwjgl.paraphernalia.loop.update.SlowUpdater;
import andrepnh.lwjgl.paraphernalia.loop.tick.TickHandlerFactory;
import andrepnh.lwjgl.paraphernalia.loop.update.Updater;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import andrepnh.lwjgl.paraphernalia.loop.tick.TickHandler;

public class DynamicGameLoop {
    private final GlobalState state;
    
    private final TickHandlerFactory tickHandlerFactory;

    public DynamicGameLoop(GlobalState state) {
        this.state = state;
        this.tickHandlerFactory = new TickHandlerFactory();
    }
    
    public void loop() {
        while (!glfwWindowShouldClose(state.window)) {
            TickHandler tickHandler = tickHandlerFactory.getInstance(state);
            tickHandler.getInputHandler().handleInput();
            Updater updater = state.updateDelay > 0 
                ? new SlowUpdater(tickHandler.getUpdater(), state.updateDelay) 
                : tickHandler.getUpdater();
            updater.update();
            Renderer renderer = state.renderDelay > 0 
                ? new SlowRenderer(tickHandler.getRenderer(), state.renderDelay) 
                : tickHandler.getRenderer();
            renderer.render();
        }
    }
}
