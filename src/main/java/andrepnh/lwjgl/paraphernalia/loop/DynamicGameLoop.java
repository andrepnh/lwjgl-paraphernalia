package andrepnh.lwjgl.paraphernalia.loop;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.tick.TickHandlerFactory;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import andrepnh.lwjgl.paraphernalia.loop.tick.TickHandler;
import andrepnh.lwjgl.paraphernalia.loop.tick.TickState;

public class DynamicGameLoop {
    private final GlobalState state;
    
    private final TickHandlerFactory tickHandlerFactory;
    
    private TickState previousTickState;

    public DynamicGameLoop(GlobalState state) {
        this.state = state;
        this.tickHandlerFactory = new TickHandlerFactory();
    }
    
    public void loop() {
        while (!glfwWindowShouldClose(state.window)) {
            TickHandler tickHandler = tickHandlerFactory.getInstance(state.currentLoopKind);
            previousTickState = tickHandler.runSteps(state, previousTickState);
        }
    }
}
