package andrepnh.lwjgl.paraphernalia;

/**
 * Simple game loop based on http://gameprogrammingpatterns.com/game-loop.html
 */
public interface GenericGameLoop {
    void init();

    boolean exit();

    void handleInput();

    void update();

    void render();
    
    default void loop() {
        init();
        while (!exit()) {
            handleInput();
            update();
            render();
        }
    }
}
