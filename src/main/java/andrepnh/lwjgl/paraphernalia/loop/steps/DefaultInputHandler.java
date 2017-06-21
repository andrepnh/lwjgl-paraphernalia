package andrepnh.lwjgl.paraphernalia.loop.steps;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class DefaultInputHandler {
    
    public void handleInput() {
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }
}
