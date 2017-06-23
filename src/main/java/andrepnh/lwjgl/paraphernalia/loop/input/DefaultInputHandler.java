package andrepnh.lwjgl.paraphernalia.loop.input;

import andrepnh.lwjgl.paraphernalia.loop.input.InputHandler;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;

public class DefaultInputHandler implements InputHandler {
    
    @Override
    public void handleInput() {
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }
}
