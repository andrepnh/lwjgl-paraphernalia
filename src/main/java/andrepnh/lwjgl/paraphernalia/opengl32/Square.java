package andrepnh.lwjgl.paraphernalia.opengl32;

public class Square {

    public static final int SIZE = 8;

    public float x;

    public float y;

    public float[] color;
    
    public float speed;

    public Square(float x, float y, float speed, float[] color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
    }

    Square(Square square) {
        this.x = square.x;
        this.y = square.y;
        this.speed = square.speed;
        this.color = square.color;
    }

    public void moveLeft() {
        moveLeft(1);
    }
    
    public void moveLeft(long factor) {
        x -= factor * speed;
        flipSidesIfNecessary();
    }

    public void moveLeft(float factor) {
        x -= factor * speed;
        flipSidesIfNecessary();
    }

    public void moveRight() {
        moveRight(1);
    }
    
    public void moveRight(long factor) {
        x += factor * speed;
        flipSidesIfNecessary();
    }

    public void moveRight(float factor) {
        x += factor * speed;
        flipSidesIfNecessary();
    }

    private void flipSidesIfNecessary() {
        if (Math.abs(x) > Context.MAX_UNITS) {
            x *= -1;
        }
    }
}
