package andrepnh.lwjgl.paraphernalia;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import org.lwjgl.opengl.GL15;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderiv;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryUtil.NULL;

public class OpenGl32Tutorial {
    private static final int LAG = 100000;
    
    private static final String VERTEX_SHADER_SOURCE
        = "#version 150 core\n"
        
        + "in vec2 position;"
        + "in vec3 inColor;"
        + "out vec3 color;"
        
        + "void main() {"
        + "   color = inColor;"
        + "   gl_Position = vec4(position, 0.0, 1.0);"
        + "}";

    private static final String FRAGMENT_SHADER_SOURCE
        = "#version 150 core\n"
        
        + "uniform int lag;"
        + "in vec3 color;"
        + "out vec4 outColor;"
        
        + "void main() {"
        + "   int i;"
        + "   for (i = 0; i < lag; i++);"
        + "   outColor = vec4(color, 1.0f);"
        + "}";
    
    private long window;
    
    private int vao;
    
    private int vbo;
    
    private int vertexShader;
    
    private int fragmentShader;
    
    private int shaderProgram;
    
    public static void main(String[] args) {
        OpenGl32Tutorial openGl32 = new OpenGl32Tutorial();
        try {
            openGl32.initWindow(800, 600, "Triangle");
            openGl32.prepareGraphics();
            openGl32.mainLoop();
            openGl32.cleanup();
        } finally {
            openGl32.finish();
        }
    }
    
    private void initWindow(int width, int height, String title) {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        
        // Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });
        
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        // Center the window
        glfwSetWindowPos(
            window,
            (vidmode.width() - width) / 2,
            (vidmode.height() - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Make the window visible
        glfwShowWindow(window);
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
    }

    private void prepareGraphics() {
        prepareBuffers();
        prepareShaders();
    }
    
    private void mainLoop() {
        while (!glfwWindowShouldClose(window)) {
            System.out.println(System.currentTimeMillis() + " loop in");
            draw();
            
            glfwSwapBuffers(window);
            glfwPollEvents();
            System.out.println(System.currentTimeMillis() + " loop out");
        }   
    }
    
    private void cleanup() {
        glDeleteProgram(shaderProgram);
        glDeleteShader(fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwSetErrorCallback(null).free();
    }

    private void finish() {
        glfwDestroyWindow(window);
        // Terminate GLFW and free the error callback
        glfwTerminate();
    }
    
    private void prepareBuffers() {
        // Flattened vertices which will be sent to the GPU. The order of each vertex's attributes
        // doesn't matter as long as it is consistent. 
        // We're drawing two triangles to form a rectangle. Since some vertices are shared, we specify
        // them only once here to save memory. To actually draw the shapes we'll need an element 
        // buffer to tell drawn vertices and their order
        float[] vertices = new float[] {
            -0.5f,  0.5f, 1.0f, 0.0f, 0.0f, // Top-left: x, y, red
            0.5f,  0.5f, 0.0f, 1.0f, 0.0f, // Top-right: x, y, green
            0.5f, -0.5f, 0.0f, 0.0f, 1.0f, // Bottom-right: x, y, blue
            -0.5f, -0.5f, 1.0f, 1.0f, 1.0f  // Bottom-left: x, y, white
        };
        vao = glGenVertexArrays();
        // VAOs are used to relate VBO and element buffers to shader attributes, so that changing
        // between a different set of primitives and formats just requires binding its VAO (check
        // the shader sections for more details). To make that work, we have to bind the VAO
        // first so that it can track further bindings.
        glBindVertexArray(vao);
        
        // Now comes the element array used to specify drawn vertices. Values are indices in the 
        // vertex buffer (as seen from the shader, not on the raw array)
        int[] elements = new int[] { 
            0, 1, 2,
            2, 3, 0
        };
        int ebo = glGenBuffers();
        glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);
        
        vbo = glGenBuffers();
        // vbo is now the active buffer and can be used for upload
        glBindBuffer(GL_ARRAY_BUFFER, vbo); 
        // Data sent to GPU. We need shaders to explain how to use it.
        // The last parameter varies according to how much the data will be changed and drawn.
        // We use GL_STATIC_DRAW for data sent once and drawn many times. GL_DYNAMIC_DRAW when the
        // data changes from time to time, but is still drawn many times. GL_STREAM_DRAW should be
        // used for data sent and drawn just once.
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW); 
    }
    
    private void prepareShaders() {
        // The vertex shader projects vertices with a 3D world position onto your 2D screen,
        // also passing color and texture coordinates down the pipeline.
        vertexShader = loadShader(VERTEX_SHADER_SOURCE, GL20.GL_VERTEX_SHADER);
        // After the vertex shader, transformed vertices will form primitives (triangles or
        // lines, points, triangle strips, line strips, etc) in shape assembly. An optional geometry
        // shader will take those and can completely transform them (as in transforming point 
        // primitives to voxels).
        // The next step is rasterization, the transformation of shapes into pixel-sized fragments.
        // Vertex attributes like color are also interpolated here and then sent to the fragment 
        // shader. This shader takes each fragment and its interpolated attributes and outputs the 
        // final color for fragments.
        // Finally, all fragments are composed and blended together. There is still a process of depth
        // and stencil testing that, at least for now, just checks which fragments are obscured and 
        // should not be displayed.
        fragmentShader = loadShader(FRAGMENT_SHADER_SOURCE, GL20.GL_FRAGMENT_SHADER);
        shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        
        // Unlike vertex shaders, fragment shaders can write to multiple buffers, so we must specify
        // which output is written to which buffer before we link the shader program. Right now, 
        // since there's one output and colorNumber is 0 by default, we could remove the next line.
        glBindFragDataLocation(shaderProgram, 0, "outColor");
        glLinkProgram(shaderProgram);
        glUseProgram(shaderProgram);
        
        // Shaders and vertex data are all set, but OpenGL doesn't know how to handle the attributes.
        // We have to specify how they are formatted and ordered. First we need a reference to the
        // "position" input attribute in the vertex shader:
        int positionAttrib = glGetAttribLocation(shaderProgram, "position");
        // We need to enable the vertex attribute array (just boilerplate?)
        glEnableVertexAttribArray(positionAttrib);
        // Then we tell OpenGL how to fill input with data from the array. The second parameter 
        // specifies the number of values for that input, the same value as the "vec" dimension.
        // The third parameter is the type, the fourth tells if non-floating point numbers should be
        // normalized.
        // The last two parameters concerns the actual extraction of data from the array. The first
        // number, called stride, is how many bytes are between each input. In our case, since one 
        // vertex is immediately followed by another and since we've already told OpenGL each input 
        // is made of two values, this is 5 floats. The last parameter is the offset in bytes, 0.
        // Notice that we aren't explicitily referencing the VBO with the actual vertex data. Calls
        // to this OpenGL function will also store the currently active GL_ARRAY_BUFFER (our VBO).
        // Because of that we don't have to bind the VBO when drawing and we can use a different VBO
        // for each input attribute.
        // Without VAOs we'd have to set up all attributes again if we switched back to these shaders
        // with glUseProgram. With them, each call to glVertexAttribPointer will also store 
        // references to each VBO with raw vertex data and its attribute. That way, changing between
        // different vertex data an formats jus require binding VAOs.
        glVertexAttribPointer(positionAttrib, 2, GL_FLOAT, false, 5 * Float.BYTES, 0);
        
        int colorAttrib = glGetAttribLocation(shaderProgram, "inColor");
        glEnableVertexAttribArray(colorAttrib);
        glVertexAttribPointer(colorAttrib, 3, GL_FLOAT, false, 5 * Float.BYTES, 2 * Float.BYTES);
        
        int lagUniform = GL20.glGetUniformLocation(shaderProgram, "lag");
        GL20.glUniform1i(lagUniform, LAG);
    }
    
    private int loadShader(String source, int target) {
        int shader = GL20.glCreateShader(target);
        GL20.glShaderSource(shader, source);
        GL20.glCompileShader(shader);
        
        int[] compileStatus = {0};
        glGetShaderiv(shader, GL_COMPILE_STATUS, compileStatus);
        boolean compileErrors = compileStatus[0] != GL_TRUE;
        if (compileErrors) {
            String compileLog = glGetShaderInfoLog(shader);
            throw new IllegalStateException("Shader not compiled, see compiler log below"
                + System.lineSeparator() + compileLog);
        }
        return shader;
    }

    private void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        
        // Drawing based on element buffer. The second parameter is the number of indices to draw,
        // the next is each element type and the last is the offset.
        GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
        
        int sig = GL11.glGetError();
        if (sig != 0) {
            System.out.println(sig);
        }
    }

}
