package andrepnh.lwjgl.paraphernalia.opengl32;

public class SimpleLoopStrategy implements LoopStrategy {

    @Override
    public void loop(Context context) {
        moveSquares(context);
        sleepMilli(context.updateDelayFactor);
        render(context.squares);
        sleepMilli(context.renderDelayFactor);
        swapBuffers(context);
        pollEvents();
    }
    
}
