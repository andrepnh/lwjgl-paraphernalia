package andrepnh.lwjgl.paraphernalia;

/**
 * Simple game loop based on http://gameprogrammingpatterns.com/game-loop.html
 */
public interface GenericGameLoop {
    boolean exit();

    void handleInput();

    void update();

    void render();
    
    default void loop() {
        while (!exit()) {
            handleInput();
            update();
            render();
        }
    }
}
