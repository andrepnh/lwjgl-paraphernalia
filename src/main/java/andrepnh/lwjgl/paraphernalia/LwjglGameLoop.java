package andrepnh.lwjgl.paraphernalia;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class LwjglGameLoop implements GenericGameLoop {

    public GlobalState state;

    public LwjglGameLoop(GlobalState state) {
        this.state = state;
    }
    
    @Override
    public void init() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public boolean exit() {
        return glfwWindowShouldClose(state.window);
    }

    @Override
    public void handleInput() {
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    @Override
    public void update() {
        state.squares.replaceAll(this::move);
    }

    @Override
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        drawSquare(state.x, state.y);
        state.squares.forEach(array -> drawSquare(array[0], array[1]));
        glfwSwapBuffers(state.window); // swap the color buffers
    }
    
    private int[] move(int[] square) {
        int x = square[0], y = square[1];
        if (y == 0) { // bottom line
            if (x < 9) { // room left
                return new int[]{x + 1, y};
            } else { // reached end
                return new int[]{x, 1};
            }
        } else if (x == 9 && y < 9) { // climbing up
            return new int[]{x, y + 1};
        } else { // top line
            if (x > 0) { // tracking back
                return new int[]{x - 1, y};
            } else { // back to origin
                return new int[]{x, y - 1};
            }
        }
    }

    private void drawSquare(int x, int y) {
        glPushMatrix();
        glTranslatef(x * 0.1F, y * 0.1F, 0);
        glBegin(GL_QUADS);
        glColor3f(0, 0, 0);
        glVertex2d(0, 0);
        glVertex2d(0.08, 0);
        glVertex2d(0.08, 0.08);
        glVertex2d(0, 0.08);
        glEnd();
        glPopMatrix();
    }

}
