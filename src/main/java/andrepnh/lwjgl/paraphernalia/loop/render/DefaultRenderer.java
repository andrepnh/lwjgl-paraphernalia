package andrepnh.lwjgl.paraphernalia.loop.render;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;

public class DefaultRenderer implements Renderer {
    private final GlobalState state;

    public DefaultRenderer(GlobalState state) {
        this.state = state;
    }
    
    @Override
    public void render(float nextTickProgress) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        drawSquare(state.x, state.y, nextTickProgress);
        state.squares.forEach(array -> drawSquare(array[0], array[1], nextTickProgress));
        glfwSwapBuffers(state.window); // swap the color buffers
    }
    
    private void drawSquare(float x, float y, float nextTickProgress) {
        glPushMatrix();
        glTranslatef(x * (1 + nextTickProgress) * 0.1F, y * (1 + nextTickProgress) * 0.1F, 0);
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
