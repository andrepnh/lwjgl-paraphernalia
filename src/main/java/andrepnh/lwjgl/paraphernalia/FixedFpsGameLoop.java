package andrepnh.lwjgl.paraphernalia;

import andrepnh.lwjgl.paraphernalia.loop.steps.DefaultUpdater;
import andrepnh.lwjgl.paraphernalia.loop.steps.FixedFpsRenderer;
import andrepnh.lwjgl.paraphernalia.loop.steps.InstrumentedInputHandler;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class FixedFpsGameLoop implements GenericGameLoop {
    private static final int MILLISECONDS_PER_FRAME = 32; // 30fps
    
    private final GlobalState state;
    
    private final InstrumentedInputHandler inputHandler;
    
    private final DefaultUpdater updater;
    
    private final FixedFpsRenderer renderer;
    
    public FixedFpsGameLoop(GlobalState state) {
        this.state = state;
        inputHandler = new InstrumentedInputHandler();
        updater = new DefaultUpdater(state);
        renderer = new FixedFpsRenderer(state);
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
        renderer.renderUntil(MILLISECONDS_PER_FRAME, inputHandler.frameStart);
    }
    
}
