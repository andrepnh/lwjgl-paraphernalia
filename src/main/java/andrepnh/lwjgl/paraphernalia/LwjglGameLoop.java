package andrepnh.lwjgl.paraphernalia;

import andrepnh.lwjgl.paraphernalia.loop.steps.DefaultInputHandler;
import andrepnh.lwjgl.paraphernalia.loop.steps.DefaultRenderer;
import andrepnh.lwjgl.paraphernalia.loop.steps.DefaultUpdater;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class LwjglGameLoop implements GenericGameLoop {
    private final GlobalState state;
    
    private final DefaultInputHandler inputHandler;
    
    private final DefaultUpdater updater;
    
    private final DefaultRenderer renderer;
    
    public LwjglGameLoop(GlobalState state) {
        this.state = state;
        inputHandler = new DefaultInputHandler();
        updater = new DefaultUpdater(state);
        renderer = new DefaultRenderer(state);
    }
    
    @Override
    public boolean exit() {
        return glfwWindowShouldClose(state.window);
    }

    @Override
    public void handleInput() {
        inputHandler.handleInput();
    }

    @Override
    public void update() {
        updater.update();
    }

    @Override
    public void render() {
        renderer.render();
    }

}
