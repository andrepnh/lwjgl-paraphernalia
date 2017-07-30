package andrepnh.lwjgl.paraphernalia;

import andrepnh.lwjgl.paraphernalia.Context;
import andrepnh.lwjgl.paraphernalia.Square;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBufferData;


public interface LoopStrategy {
    void loop(Context context);

    default void moveSquares(Context context) {
        context.autoSquares.forEach(Square::moveRight);
    }
    
    default void swapBuffers(Context context) {
        glfwSwapBuffers(context.window);
    }
    
    default void pollEvents() {
        glfwPollEvents();
    }
            
    default void render(List<Square> squares) {
        float[] attributes = newAttributesArray(squares.size());
        int[] elements = newElementsArray(squares.size());
        setAttributesAndElements(squares, attributes, elements);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        glBufferData(GL_ARRAY_BUFFER, attributes, GL15.GL_STATIC_DRAW);
        glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, elements, GL15.GL_STATIC_DRAW);

        // Drawing based on element buffer. The second parameter is the number of indices to draw,
        // the next is each element type and the last is the offset.
        GL11.glDrawElements(GL11.GL_TRIANGLES, elements.length, GL11.GL_UNSIGNED_INT, 0);

        int sig = GL11.glGetError();
        if (sig != 0) {
            System.out.println(sig);
        }
    }
    
    default void sleepMilli(int delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default void sleepNano(long delay) {
        try {
            TimeUnit.NANOSECONDS.sleep(delay);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    default float[] newAttributesArray(int squares) {
        // [x1, y1, r, g, b, 
        //  x2, y2, r, g, b,
        //  x3, y3, r, g, b, 
        //  x4, y4, r, g, b, 
        //  ...]
        return new float[squares * 20];
    }

    default int[] newElementsArray(int squares) {
        return new int[squares * 6];
    }

    default void setAttributesAndElements(List<Square> squares, float[] attributes, int[] elements) {
        int attributesCursor = 0, elementsCursor = 0, squareNumber = 0;
        for (Square square : squares) {
            attributesCursor = fillWithSquareVertexAttributes(attributes, attributesCursor, square);
            elementsCursor = fillWithElements(elements, elementsCursor, squareNumber++);
        }
    }

    default int fillWithSquareVertexAttributes(float[] vertices, int cursor, Square square) {
        final float halfSize = Square.SIZE * Context.UNIT_SIZE / 2;
        float adjustedX = square.x * Context.UNIT_SIZE,
            adjustedY = square.y * Context.UNIT_SIZE;

        // Top left
        vertices[cursor++] = adjustedX - halfSize;
        vertices[cursor++] = adjustedY - halfSize;
        vertices[cursor++] = square.color[0];
        vertices[cursor++] = square.color[1];
        vertices[cursor++] = square.color[2];
        // Top right
        vertices[cursor++] = adjustedX + halfSize;
        vertices[cursor++] = adjustedY - halfSize;
        vertices[cursor++] = square.color[0];
        vertices[cursor++] = square.color[1];
        vertices[cursor++] = square.color[2];
        // Bottom right
        vertices[cursor++] = adjustedX + halfSize;
        vertices[cursor++] = adjustedY + halfSize;
        vertices[cursor++] = square.color[0];
        vertices[cursor++] = square.color[1];
        vertices[cursor++] = square.color[2];
        // Bottom left
        vertices[cursor++] = adjustedX - halfSize;
        vertices[cursor++] = adjustedY + halfSize;
        vertices[cursor++] = square.color[0];
        vertices[cursor++] = square.color[1];
        vertices[cursor++] = square.color[2];

        return cursor;
    }

    default int fillWithElements(int[] elements, int cursor, int squareNumber) {
        int topLeftCorner = squareNumber * 4,
            topRightCorner = topLeftCorner + 1,
            bottomRightCorner = topRightCorner + 1,
            bottomLeftCorner = bottomRightCorner + 1;
        elements[cursor++] = topLeftCorner;
        elements[cursor++] = topRightCorner;
        elements[cursor++] = bottomRightCorner;
        
        elements[cursor++] = bottomRightCorner;
        elements[cursor++] = bottomLeftCorner;
        elements[cursor++] = topLeftCorner;

        return cursor;
    }
}